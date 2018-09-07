Indexes
=======


Unique Indexes
--------------


Unique indexes enforce uniqueness across all their entries. Thus if you try to insert a document into this book’s sample application’s users collection with an already indexed username value, 
then the insert will fail with the following exception:

.. code-block:: bash

	E11000 duplicate key error index:
	gardening.users.$username_1 dup key: { : "kbanker" }


When creating a unique index on a collection that already contains data, you run the risk of failure since it’s possible that duplicate keys may already exist in the collection. 
When duplicate keys exist, the index creation fails.
If you do find yourself needing to create a unique index on an established collection, 
you have a couple of options. The first is to repeatedly attempt to create the unique index and use the failure messages to manually remove the documents with duplicate keys. 
But if the data isn’t so important, you can also instruct the database to drop documents with duplicate keys automatically using the dropDups option. 
To take an example, if your users collection already contains data, and if you don’t care that documents with duplicate keys are removed, then you can issue the index creation command like this:

.. code-block:: bash

	db.users.ensureIndex({username: 1}, {unique: true, dropDups: true})


Sparce Indexes
--------------
Indexes are dense by default. 
This means that for every document in an indexed collection, there will be a corresponding entry in the index even if the document lacks the indexed key. 
So for each of entries without indexed key, there will still exist a null entry in the index. 

In a sparse index, only those documents having some value for the indexed key will appear. 
If you want to create a sparse index, all you have to do is specify ``{sparse:true}``. 
So for example, you can create a unique, sparse index on sku like so:

.. code-block:: bash

	db.products.ensureIndex({sku: 1}, {unique: true, sparse: true})


Create Indexes, Drop Indexes , Get Indexes Info
-----------------------------------------------

.. code-block:: bash

	> use sample
	> db.users.insert({username:'ali','uid':1})

	> spec = { "ns" : "sample.users", "key" : { "uid" : 1 }, "name" : "uid_1" }
	> db.system.indexes.insert(spec, true)


	> db.system.indexes.find()
	{ "v" : 1, "key" : { "_id" : 1 }, "ns" : "sample.users", "name" : "_id_" }
	{ "v" : 1, "key" : { "uid" : 1 }, "ns" : "sample.users", "name" : "uid_1" }


	> db.runCommand({'deleteIndexes':'users', index:'uid_1'})
	{ "nIndexesWas" : 2, "ok" : 1 }
	> db.system.indexes.find()
	{ "v" : 1, "key" : { "_id" : 1 }, "ns" : "sample.users", "name" : "_id_" }


	> db.users.ensureIndex({uid:1})
	> db.system.indexes.find()
	{ "v" : 1, "key" : { "_id" : 1 }, "ns" : "sample.users", "name" : "_id_" }
	{ "v" : 1, "key" : { "uid" : 1 }, "ns" : "sample.users", "name" : "uid_1" }


	> db.users.getIndexSpecs()
	[
		{
			"v" : 1,
			"key" : {
				"_id" : 1
			},
			"ns" : "sample.users",
			"name" : "_id_"
		},
		{
			"v" : 1,
			"key" : {
				"uid" : 1
			},
			"ns" : "sample.users",
			"name" : "uid_1"
		}
	]


	> db.users.dropIndex("uid_1")
	{ "nIndexesWas" : 2, "ok" : 1 }
	
Building Indexes
----------------

The index builds in two steps. In the first step, the values to be indexed are sorted. 
A sorted data set makes for a much more efficient insertion into the B-tree. 
Note that the progress of the sort is indicated by the ratio of the number of documents sorted to the total number of documents:

.. code-block:: bash

	[conn1] building new index on { open: 1.0, close: 1.0 } for stocks.values
	1000000/4308303 23%
	2000000/4308303 46%
	3000000/4308303 69%
	4000000/4308303 92%
	Tue Jan 4 09:59:13 [conn1]
	external sort used : 5 files in 55 secs

For step two, the sorted values are inserted into the index. Progress is indicated in the same way, 
and when complete, the time it took to complete the index build is indicated as the insert time into system.indexes:

.. code-block:: bash

	1200300/4308303 27%
	2227900/4308303 51%
	2837100/4308303 65%
	3278100/4308303 76%
	3783300/4308303 87%
	4075500/4308303 94%
	Tue Jan 4 10:00:16 [conn1] done building bottom layer, going to commit
	Tue Jan 4 10:00:16 [conn1] done for 4308303 records 118.942secs
	Tue Jan 4 10:00:16 [conn1] insert stocks.system.indexes 118942ms

In addition to examining the MongoDB log, you can check the index build progress by running the shell’s currentOp() method:

.. code-block:: bash

	> db.currentOp()
	{
	"inprog" : [
	{
	"opid" : 58,
	"active" : true,
	"lockType" : "write",
	"waitingForLock" : false,
	"secs_running" : 55,
	"op" : "insert",
	"ns" : "stocks.system.indexes,
	"query" : {
	},
	"client" : "127.0.0.1:53421",
	"desc" : "conn",
	"msg" : "index: (1/3) external sort 3999999/4308303 92%"
	}
	]
	}
	
The last field, msg, describes the build’s progress. Note also the lockType, which indicates that the index build takes a write lock. 
This means that no other client can read or write from the database at this time. 
This means that no other client can read or write from the database at this time. 
If you’re running in production, this is obviously a bad thing, and it’s the reason why long index builds can be so vexing.



Background indexing
-------------------
If you’re running in production and can’t afford to halt access to the database, you can specify that an index be built in the background. 
Although the index build will still take a write lock, the job will yield to allow other readers and writers to access the database. 
If your application typically exerts a heavy load on MongoDB, then a background index build will degrade performance, but this may be acceptable under certain circumstances. 
For example, if you know that the index can be built within a time window where application traffic is at a minimum, then background indexing in this case might be a good choice.
To build an index in the background, specify ``{background: true}`` when you declare the index. The previous index can be built in the background like so:

.. code-block:: bash

	db.values.ensureIndex({open: 1, close: 1}, {background: true})


Offline indexing
----------------
If your production data set is too large to be indexed within a few hours, then you’ll need to make alternate plans. 
This will usually involve taking a replica node offline, building the index on that node by itself, and then allowing the node to catch up with the master replica. 
Once it’s caught up, you can promote the node to primary and then take another secondary offline and build its version of the index. 
This tactic presumes that your replication oplog is large enough to prevent the offline node from becoming stale during the index build.


Backups
-------
Because indexes are hard to build, you may want to back them up. Unfortunately, not all backup methods include indexes. 
For instance, you might be tempted to use mongodump and mongorestore, but these utilities preserve collections and index declarations only. 
This means that when you run mongorestore, all the indexes declared for any collections you’ve backed up will be re-created. As always, if your data set is
large, the time it takes to build these indexes may be unacceptable.



The order of fields in an index
-------------------------------
* Equality Tests 
	Add all equality-tested fields to the compound index, in any order
* Sort Fields (ascending / descending only matters if there are multiple sort fields) 
	Add sort fields to the index in the same order and direction as your query's sort
* Range Filters 
	First, add the range filter for the field with the lowest cardinality (fewest distinct values in the collection)
	
	Then the next lowest-cardinality range filter, and so on to the highest-cardinality


http://emptysqua.re/blog/optimizing-mongodb-compound-indexes/

The order of fields in an index should be:

* First, fields on which you will query for exact values.
* Second, fields on which you will sort.
* Finally, fields on which you will query for a range of values.

http://blog.mongolab.com/2012/06/cardinal-ins/





Covered query
----------------
An index covers a query, a covered query, when:

* all the fields in the query are part of that index, and
* all the fields returned in the documents that match the query are in the same index.

For these queries, MongoDB does not need to inspect at documents outside of the index, which is often more efficient than inspecting entire documents.

Example:
Given a collection inventory with the following index on the type and item fields:

.. code-block:: bash

	{ type: 1, item: 1 }
	
This index will cover the following query on the type and item fields, which returns only the item field:

.. code-block:: bash

	db.inventory.find( { type: "food", item:/^c/ }, { item: 1, _id: 0 } )
	
However, this index will not cover the following query, which returns the item field and the _id field:

.. code-block:: bash

	db.inventory.find( { type: "food", item:/^c/ }, { item: 1 } )

http://docs.mongodb.org/manual/core/read-operations/#covering-a-query




Selectivity index
-------------------------

Selectivity is the ability of a query to narrow results using the index. 
Effective indexes are more selective and allow MongoDB to use the index for a larger portion of the work associated with fulfilling the query.

To ensure selectivity, write queries that limit the number of possible documents with the indexed field. Write queries that are appropriately selective relative to your indexed data.
Suppose you have a field called status where the possible values are new and processed. 
If you add an index on status you’ve created a low-selectivity index. The index will be of little help in locating records.

A better strategy, depending on your queries, would be to create a compound index that includes the low-selectivity field and another field. 
For example, you could create a compound index on status and created_at.
Another option, again depending on your use case, might be to use separate collections, one for each status.

http://docs.mongodb.org/manual/tutorial/create-queries-that-ensure-selectivity/



Use Indexes to Sort Query Results
--------------------------------------

For the fastest performance when sorting query results by a given field, create a sorted index on that field.

To sort query results on multiple fields, create a compound index. MongoDB sorts results based on the field order in the index. 
For queries that include a sort that uses a compound index, ensure that all fields before the first sorted field are equality matches.

Example

If you create the following index:

.. code-block:: bash

	{ a: 1, b: 1, c: 1, d: 1 }

The following query and sort operations can use the index:

.. code-block:: bash

	db.collection.find().sort( { a:1 } )
	db.collection.find().sort( { a:1, b:1 } )

	db.collection.find( { a:4 } ).sort( { a:1, b:1 } )
	db.collection.find( { b:5 } ).sort( { a:1, b:1 } )

	db.collection.find( { a:5 } ).sort( { b:1, c:1 } )

	db.collection.find( { a:5, c:4, b:3 } ).sort( { d:1 } )

	db.collection.find( { a: { $gt:4 } } ).sort( { a:1, b:1 } )
	db.collection.find( { a: { $gt:5 } } ).sort( { a:1, b:1 } )

	db.collection.find( { a:5, b:3, d:{ $gt:4 } } ).sort( { c:1 } )
	db.collection.find( { a:5, b:3, c:{ $lt:2 }, d:{ $gt:4 } } ).sort( { c:1 } )

However, the following queries cannot sort the results using the index:

.. code-block:: bash

	db.collection.find().sort( { b:1 } )
	db.collection.find( { b:5 } ).sort( { b:1 } )

Note:

For in-memory sorts that do not use an index, the sort() operation is significantly slower. 

The sort() operation will abort when it uses 32 megabytes of memory.

http://docs.mongodb.org/manual/tutorial/sort-results-with-indexes/
