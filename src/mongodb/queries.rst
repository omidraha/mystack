Queries
=======

Identifying slow queries
------------------------

It’s safe to assume that for most apps, queries shouldn’t take much longer than 100 milliseconds. 
The MongoDB logger has this assumption ingrained, since it prints a warning whenever any operation, including a query, takes more than 100 ms. 
The logs, therefore, are the first place you should look for slow queries.

.. code-block:: bash

	grep -E '([0-9])+ms' mongod.log
 

.explain()
----------
For indexed queries, nscanned is the number of index keys in the range that Mongo scanned, and nscannedObjects is the number of documents it looked at to get to the final result. 
nscannedObjects includes at least all the documents returned, even if Mongo could tell just by looking at the index that the document was definitely a match. 

Thus, you can see that nscanned >= nscannedObjects >= n always. For simple queries you want the three numbers to be equal. It means you've created the ideal index and Mongo is using it. 

http://emptysqua.re/blog/optimizing-mongodb-compound-indexes/

http://docs.mongodb.org/manual/reference/method/cursor.explain/


When mongo queries plan expired
-------------------------------

The optimizer automatically expires a plan after any of the following events:

* 100 writes are made to the collection.
* Indexes are added or removed from the collection.
* the reIndex rebuilds the index
* the mongod process restarts.
* A query using a cached query plan does a lot more work than expected. 
	Here, what qualifies as “a lot more work” is a value for nscanned exceeding the cached nscanned value by at least a factor of 10.
* Every time a query rans a 1000 or so times, MongoDB checks to see if the selected query plan still is the best one.

http://esampaio.com/post/understanding-mongodb-query-plans

http://docs.mongodb.org/manual/core/read-operations/#query-optimization



Query Operations that Cannot Use Indexes Effectively
----------------------------------------------------

Some query operations cannot use indexes effectively or cannot use indexes at all. 
Consider the following situations:

The inequality operators ``$nin`` and ``$ne`` are not very selective, as they often match a large portion of the index.
As a result, in most cases, a ``$nin`` or ``$ne`` query with an index may perform no better than a $nin or $ne query that must scan all documents in a collection.

Queries that specify regular expressions, with inline JavaScript regular expressions or ``$regex`` operator expressions, cannot use an index. 
However, the regular expression with anchors to the beginning of a string can use an index.

http://docs.mongodb.org/manual/core/read-operations/#query-operations-that-cannot-use-indexes-effectively
