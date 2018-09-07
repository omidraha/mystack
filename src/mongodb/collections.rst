Collections
=================


Capped collection
---------------------

Capped collections guarantee that insertion order and natural order are identical. 

http://docs.mongodb.org/manual/reference/glossary/#term-natural-order

Capped Collections are circular, fixed-size collections that keep documents well-ordered, even without the use of an index. 
This means that capped collections can receive very high-speed writes and sequential reads.
These collections are particularly useful for keeping log files but are not limited to that purpose. Use capped collections where appropriate.

Use Natural Order for Fast Reads
----------------------------------

To return documents in the order they exist on disk, return sorted operations using the $natural operator. On a capped collection, this also returns the documents in the order in which they were written.
Natural order does not use indexes but can be fast for operations when you want to select the first or last items on disk.

http://docs.mongodb.org/manual/core/capped-collections/
 
