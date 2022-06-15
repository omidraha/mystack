Tools
=====


mongosniff
----------

`http://docs.mongodb.org/manual/reference/program/mongosniff/`


Use the following command to connect to a mongod or mongos running on port 27017 and 27018 on the localhost interface:

.. code-block:: bash

	mongosniff --source NET lo 27017 27018


Use the following command to only log invalid BSON objects for the mongod or mongos running on the localhost interface and port 27018, for driver development and troubleshooting:

.. code-block:: bash

	mongosniff --objcheck --source NET lo 27018

