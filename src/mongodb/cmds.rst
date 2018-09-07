Cmds
====


How can I check the size of a collection?
-----------------------------------------


To view the size of a collection and other information, use the `db.collection.stats()` method from the mongo shell.

The following example issues db.collection.stats() for the orders collection:

 .. code-block:: bash

	db.orders.stats();

To view specific measures of size, use these methods:

 .. code-block:: bash

    db.collection.dataSize() # data size in bytes for the collection.
    db.collection.storageSize() # allocation size in bytes, including unused space.
    db.collection.totalSize() # the data size plus the index size in bytes.
    db.collection.totalIndexSize() # the index size in bytes.

Also, the following scripts print the statistics for each database and collection:

 .. code-block:: bash

	db._adminCommand("listDatabases").databases.forEach(function (d) {mdb = db.getSiblingDB(d.name); printjson(mdb.stats())})

	db._adminCommand("listDatabases").databases.forEach(function (d) {mdb = db.getSiblingDB(d.name); mdb.getCollectionNames().forEach(function(c) {s = mdb[c].stats(); printjson(s)})})


How can I check the size of indexes?
------------------------------------
To view the size of the data allocated for an index, use one of the following procedures in the mongo shell:


	Use the `db.collection.stats()` method using the index namespace. To retrieve a list of namespaces, issue the following command:

    	 .. code-block:: bash

    		db.system.namespaces.find()

	Check the value of indexSizes in the output of the db.collection.stats() command.

Example: Issue the following command to retrieve index namespaces:

	 .. code-block:: bash

		db.system.namespaces.find()

The command returns a list similar to the following:

	.. code-block:: bash

		{"name" : "test.orders"}
		{"name" : "test.system.indexes"}
		{"name" : "test.orders.$_id_"}

View the size of the data allocated for the orders.$_id_ index with the following sequence of operations:

 .. code-block:: bash

	use test
	db.orders.$_id_.stats().indexSizes

