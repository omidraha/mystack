Backups
=======

Barman
------

Barman (Backup and Recovery Manager) is an open-source administration tool for disaster recovery of PostgreSQL servers written in Python. 

It allows your organisation to perform remote backups of multiple servers in business critical environments and help DBAs during the recovery phase.

http://www.pgbarman.org/about/

Install pg_dump
----------------

.. code-block:: bash

    sudo apt-get install postgresql-client

How To Backup and Restore PostgreSQL Database Using pg_dump and psql
--------------------------------------------------------------------

Use ``pg_dump`` to get backup:

.. code-block:: bash

    $ pg_dump -U <USER> -h <REMOTE-HOST> -d <DATABASE-NAME> > <DB-DATA.SQL>
    $ pg_dump -U postgres -h 127.0.0.1 -d redmine  > redmine.sql

Use ``psql`` to restore backup:

.. code-block:: bash

    $ psql -U <USER> -h <REMOTE-HOST> -d <DATABASE-NAME>  -f <DB-DATA.SQL>
    $ psql -U postgres -h 172.17.0.2 -d redmine -f redmine_backup_pg_dump.sql


http://www.thegeekstuff.com/2009/01/how-to-backup-and-restore-postgres-database-using-pg_dump-and-psql/

http://www.postgresql.org/docs/9.1/static/backup.html
