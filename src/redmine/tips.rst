Tips
====

Path of redmine plugins
-----------------------

.. code-block:: bash

    $ /srv/redmine/plugins


How to install a new plugin
---------------------------

http://www.redmine.org/projects/redmine/wiki/Plugins


How to install CKEditor plugin for redmine
------------------------------------------

http://www.redmine.org/plugins/redmine-ckeditor

https://github.com/a-ono/redmine_ckeditor


.. code-block:: bash

    $ cd /srv/redmine/plugins
    $ git clone https://github.com/a-ono/redmine_ckeditor
    $ cd /srv/redmine/
    $ bundle install
    $ bundle update
    $ vim /srv/redmine/config/environments/development.rb # set `config.eager_load = false`
    $ vim /srv/redmine/config/environments/test.rb # set `config.eager_load = false`
    $ vim /srv/redmine/config/environments/production.rb # set `config.eager_load = true`
    $ rake redmine:plugins:migrate RAILS_ENV=production
    $ service nginx restart

How to restart redmine
----------------------

To restart redmine, just restart your web server

.. code-block:: bash

    $ service nginx restart


how to show code changes on issues
----------------------------------

http://www.redmine.org/boards/2/topics/21287

http://www.redmine.org/boards/2/topics/22654?r=37948

http://www.redmine.org/projects/redmine/wiki/RedmineSettings#Referencing-issues-in-commit-messages


Backup Redmine
--------------

Redmine backups should include:

    data (stored in your redmine database)

    attachments (stored in the files directory of your Redmine install)

Backup raw ``redmine`` mysql database:

.. code-block:: bash

    $ /usr/bin/mysqldump -u <username> -p<password> <redmine_database> | gzip > /path/to/backup/db/redmine_`date +%y_%m_%d`.gz
    $ mysqldump -u root redmine | gzip > redmine_`date +%y_%m_%d`.gz

Backup ``redmine`` mysql database with ``postgres`` compatibility format:

.. code-block:: bash

    $ mysqldump -u root  --compatible=postgresql --default-character-set=utf8 redmine | gzip > redmine_`date +%y_%m_%d`.gz

But, this option may be not work when you want to restore data base to ``postgresql``,

even when used with some script like:

https://github.com/lanyrd/mysql-postgresql-converter


Backup mysql ``redmine`` database with ``pgloader`` from ``mysql`` server and directly restore to to the ``postgres`` server:


.. code-block:: bash

    $ sudo apt-get install pgloader
    $ su - postgres
    postgres@debian:~$ createdb redmine
    # listen to remote running program on the remote host on the local host
    $ ssh -N <USER>@<REMOTE-HOST>  -L 3306:localhost:3306
    postgres@debian:~$ pgloader mysql://<USER>:<PASSWORD>@127.0.0.1/redmine postgresql:///redmine

Log of migration:

.. code-block:: bash

    $ tail -f   /tmp/pgloader/pgloader.log

Note that warning is not important:

    ``warning: table "<TABLE-NAME>" does not exist, skipping``


https://github.com/dimitri/pgloader

http://pgloader.io/howto/pgloader.1.html

http://pgloader.io/howto/mysql.html


Download backup files:

.. code-block:: bash

    $ scp <user>@<remote_host>:/<path/of/backup/file> .
    $ scp <user>@<remote_host>:/srv/redmine/files/ files



http://www.redmine.org/projects/redmine/wiki/RedmineInstall#Backups

http://www.redmine.org/projects/redmine/wiki/RedmineUpgrade



Setup redmine with docker image
-------------------------------

Setup redmine with docker image and restore data from backup

.. code-block:: bash

    $ docker pull postgresql
    $ docker pull redmine

    $ docker run --name postgres-01 -e POSTGRES_USER=postgres
                                    -e POSTGRES_PASSWORD=postgres  postgres

    # create redmine data base
    $ createdb  -U postgres -h 172.17.0.2 redmine

    # restore db data to postgres
    $ psql -U postgres -h 172.17.0.2 -d redmine -f redmine_backup_pg_dump.sql
    $ docker run --name redmine-01 -e POSTGRES_ENV_POSTGRES_USER=postgres
                                   -e POSTGRES_ENV_POSTGRES_PASSWORD=postgres
                                   -e POSTGRES_ENV_POSTGRES_DB=redmine
                                   --link postgres-01:postgres redmine

Postgres data path is ``/var/lib/postgresql/data``.

Redmine data path is ``/usr/src/redmine``, and two important folders within this are ``files`` and ``plugins``


https://github.com/docker-library/redmine

https://github.com/docker-library/redmine/blob/master/3.0/docker-entrypoint.sh

https://hub.docker.com/_/redmine/

https://hub.docker.com/_/postgres/

https://hub.docker.com/_/mysql/


