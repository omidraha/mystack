Postgres-XL
-----------



Nodes Concept
-------------

Postgres-XL is composed of three major components called the GTM, Coordinator and Datanode.

The GTM is responsible to provide ACID property of transactions.

The Datanode stores table data and handle SQL statements locally.

The Coordinator handles each SQL statements from applications, determines which Datanode to go, and sends plans on to the appropriate Datanodes.

The Coordinators and Datanodes of Postgres-XL are essentially PostgreSQL database servers

You usually should run GTM on a separate server because GTM has to take care of transaction requirements from all the Coordinators and Datanodes.
To group multiple requests and responses from Coordinator and Datanode processes running on the same server,
you can configure GTM-Proxy. GTM-Proxy reduces the number of interactions and the amount of data to GTM. GTM-Proxy also helps handle GTM failures.


It is often good practice to run both Coordinator and Datanode on the same server
because we don't have to worry about workload balance between the two,
and you can often get at data from replicated tables locally without sending an additional request out on the network.
You can have any number of servers where these two components are running.
Because both Coordinator and Datanode are essentially PostgreSQL instances,
you should configure them to avoid resource conflict. It is very important to assign them different working directories and port numbers.


https://www.postgres-xl.org/documentation/tutorial-arch.html


Table distributing concept
--------------------------

.. code-block:: bash

    CREATE TABLE DISTRIBUTE BY ...


REPLICATION

    Each row of the table will be replicated to all the Datanode of the Postgres-XL database cluster.

ROUNDROBIN

    Each row of the table will be placed in one of the Datanodes in a round-robin manner. The value of the row will not be needed to determine what Datanode to go.

HASH ( column_name )

        Each row of the table will be placed based on the hash value of the specified column. Following type is allowed as distribution column: INT8, INT2, OID, INT4, BOOL, INT2VECTOR, OIDVECTOR, CHAR, NAME, TEXT, BPCHAR, BYTEA, VARCHAR, NUMERIC, MONEY, ABSTIME, RELTIME, DATE, TIME,TIMESTAMP, TIMESTAMPTZ, INTERVAL, and TIMETZ.
        Please note that floating point is not allowed as a basis of the distribution column.


MODULO ( column_name )

    Each row of the table will be placed based on the modulo of the specified column. Following type is allowed as distribution column: INT8, INT2, INT4, BOOL, ABSTIME, RELTIME, DATE.

    Please note that floating point is not allowed as a basis of the distribution column.

If DISTRIBUTE BY is not specified, columns with UNIQUE constraint will be chosen as the distribution key. If no such column is specified, distribution column is the first eligible column in the definition. If no such column is found, then the table will be distributed by ROUNDROBIN.


You could Alter a replicated table to make it a distributed table.

https://www.postgres-xl.org/documentation/sql-createtable.html

https://www.postgres-xl.org/documentation/tutorial-createcluster.html

Shard limitation
-----------------


* (...) in distributed tables, UNIQUE constraints must include the distribution column of the table
* (...) the distribution column must be included in PRIMARY KEY
* (...) column with REFERENCES (FK) should be the distribution column.
* (...) PRIMARY KEY must be the distribution column as well.

In Postgres-XL, in distributed tables, UNIQUE constraints must include the distribution column of the table.
This is because Postgres-XL currently only allows that it can push down to the Datanodes to be enforced locally.
If we include the distribution column in unique constraints, it stands to reason that it can be enforced locally.
If a table is distributed by ROUNDROBIN, we cannot enforce UNIQUE constraints because it does not have a distribution column;
it is possible that the same value for a column exists on multiple nodes.
There's no restriction in UNIQUE constraint in replicated tables. When an expression is used on a UNIQUE constraint,
this expression must contain the distribution column of its parent table. It cannot use other columns as well.

As mentioned when discussing UNIQUE constraint, the distribution column must be included in PRIMARY KEY.
Other restrictions apply to the PRIMARY KEY as well. When an expression is used on a PRIMARY KEY constraint,
this expression must contain the distribution column of its parent table. It cannot use other columns as well.

Please note that column with REFERENCES should be the distribution column.
This limitation is introduced because constraints are enforced only locally in each Datanode.

In Postgres-XL, you cannot specify both PRIMARY KEY and REFERENCES key for different columns.

In Postgres-XL, you cannot omit the column name in REFERENCES clause.

In Postgres-XL, you cannot specify more than one foreign key constraints.

Postgres-XL does not support exclusion constraints.

Postgres-XL does not allow to modify the value of distribution column.


https://www.postgres-xl.org/documentation/ddl-constraints.html

https://www.postgres-xl.org/documentation/dml-update.html


https://stackoverflow.com/questions/28547437/migrating-from-postgresql-to-postgres-xl-distributed-tables-design

https://www.postgres-xl.org/documentation/upgrading.html

High Availability
-----------------

You can add slaves for each node analogous to PostgreSQL’s streaming replication.   In addition, the cluster can be configured such that the Global Transaction Manager (GTM) can have a GTM Standby.

In terms of automatic failover, it is currently not part of the core project, but Corosync/Pacemaker has been used for this purpose.


https://www.postgres-xl.org/documentation/warm-standby-failover.html

https://www.postgres-xl.org/faq/

https://github.com/ClusterLabs/PAF

https://github.com/bitnine-oss/postgres-xl-ha


Download
--------

https://www.postgres-xl.org/download/


Setting up Postgres-XL cluster
------------------------------


Install Postgres-XL
+++++++++++++++++++


On each hosts:

* postgres-xl-gtm (192.168.0.140)
* postgres-xl-cr1 (192.168.0.141)
* postgres-xl-dn1 (192.168.0.142)
* postgres-xl-dn2 (192.168.0.143)

Do the following commands:


.. code-block:: bash

    # Install requirements
    $ sudo apt-get upgrade
    $ sudo apt-get install build-essential
    $ sudo apt-get install libreadline-dev
    $ apt-get install zlib1g-dev
    $ apt-get install flex
    # Download postgres-xl
    $ wget https://www.postgres-xl.org/downloads/postgres-xl-9.5r1.6.tar.bz2
    $ tar -xvjpf postgres-xl-9.5r1.6.tar.bz2
    $ cd postgres-xl-9.5r1.6
    # Install postgres-xl
    $ ./configure
    $ make
        All of Postgres-XL successfully made. Ready to install.
    $ sudo make install
        Postgres-XL installation complete.
    # Install pgxc_ctl
    $ cd contrib
    $ make
    $ sudo make install


.. code-block:: bash

    $ sudo adduser postgres
    $ su postgres
    $ vim /home/postgres/.bashrc
        export PATH=/usr/local/pgsql/bin:$PATH

    $ mkdir ~/.ssh

To fix these probable errors:

.. code-block:: bash

    bash: gtm_ctl: command not found
    bash: pg_ctl: command not found

    initdb: invalid locale settings; check LANG and LC_* environment variables

Add these lines to ``/etc/environment``:

.. code-block:: bash

    $ vim /etc/environment

        PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/usr/local/pgsql/bin:"
        export LANG=en_US.utf-8
        export LC_ALL=en_US.utf-8



On ``postgres-xl-gtm`` host:


.. code-block:: bash

    $ su postgres
    $ ssh-keygen -t rsa
        Enter file in which to save the key (/home/postgres/.ssh/id_rsa):
    $  cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys

    $ scp ~/.ssh/authorized_keys postgres@192.168.0.141:~/.ssh/
    $ scp ~/.ssh/authorized_keys postgres@192.168.0.142:~/.ssh/
    $ scp ~/.ssh/authorized_keys postgres@192.168.0.143:~/.ssh/


On every hosts:

.. code-block:: bash

    $ chmod 700 ~/.ssh
    $ chmod 600 ~/.ssh/authorized_keys


On ``postgres-xl-gtm`` host check ssh connecting to other hosts:

.. code-block:: bash

    $ ssh postgres@192.168.0.141
    $ ssh postgres@192.168.0.142
    $ ssh postgres@192.168.0.143


Configure Postgres-XL
+++++++++++++++++++++


Configure ``pgxc_ctl.conf`` on ``postgres-xl-gtm`` host:

.. code-block:: bash

    $ export dataDirRoot=$HOME/DATA/pgxl/nodes
    $ mkdir $HOME/pgxc_ctl
    $ pgxc_ctl

        /bin/bash
        Installing pgxc_ctl_bash script as /home/postgres/pgxc_ctl/pgxc_ctl_bash.
        ERROR: File "/home/postgres/pgxc_ctl/pgxc_ctl.conf" not found or not a regular file. No such file or directory
        Installing pgxc_ctl_bash script as /home/postgres/pgxc_ctl/pgxc_ctl_bash.
        Reading configuration using /home/postgres/pgxc_ctl/pgxc_ctl_bash --home /home/postgres/pgxc_ctl --configuration /home/postgres/pgxc_ctl/pgxc_ctl.conf
        Finished reading configuration.
           ******** PGXC_CTL START ***************

        Current directory: /home/postgres/pgxc_ctl

Create empty configuration file, on the ``PGXC`` console:

.. code-block:: bash


    PGXC$  prepare config empty
    PGXC$  exit


.. code-block:: bash

    $ vim ~/pgxc_ctl/pgxc_ctl.conf

        pgxcOwner=postgres
        coordPgHbaEntries=(192.168.0.0/24)
        datanodePgHbaEntries=(192.168.0.0/24)


Configure gtm master node:

.. code-block:: bash

    $ pgxc_ctl
    PGXC$  add gtm master gtm 192.168.0.140 20001 $dataDirRoot/gtm
    PGXC$  monitor all
    """
    Running: gtm master
    """

Configure coordinator nodes:

.. code-block:: bash

    PGXC$  add coordinator master cr1 192.168.0.141 30001 30011 $dataDirRoot/cr_master.1 none none
        """
        Success.
        Starting coordinator master cr1
        LOG:  redirecting log output to logging collector process
        HINT:  Future log output will appear in directory 'pg_log'.
        Done.
        """

    PGXC$  monitor all
        """
        Running: gtm master
        Running: coordinator master cr1
        """

    PGXC$  add coordinator master cr2 192.168.0.142 30002 30012 $dataDirRoot/cr_master.2 none none
        """
        Success.
        Starting coordinator master cr2
        LOG:  redirecting log output to logging collector process
        HINT:  Future log output will appear in directory 'pg_log'.
        Done.
        """

    PGXC$  monitor all
        """
        Running: gtm master
        Running: coordinator master cr1
        Running: coordinator master cr2
        """

Configure data nodes:

.. code-block:: bash

    PGXC$  add datanode master dn1 192.168.0.143 40001 40011 $dataDirRoot/dn_master.1 none none none
        """
        Success.
        Starting datanode master dn1.
        LOG:  redirecting log output to logging collector process
        HINT:  Future log output will appear in directory 'pg_log'.
        Done.
        """
    PGXC$  monitor all
        """
        Running: gtm master
        Running: coordinator master cr1
        Running: coordinator master cr2
        Running: datanode master dn1
        """

    PGXC$  add datanode master dn2 192.168.0.144 40002 40012 $dataDirRoot/dn_master.2 none none none
        """
        Success
        Starting datanode master dn2.
        LOG:  redirecting log output to logging collector process
        HINT:  Future log output will appear in directory 'pg_log'.
        Done.
        """
    PGXC$  monitor all
        """
        Running: gtm master
        Running: coordinator master cr1
        Running: coordinator master cr2
        Running: datanode master dn1
        Running: datanode master dn2
        """

    # To stop
    PGXC stop gtm master
    PGXC stop  coordinator master cr1
    PGXC stop coordinator master cr2
    PGXC stop datanode master dn1
    PGXC stop datanode master dn2

    # To start
    PGXC start gtm master
    PGXC start  coordinator master cr1
    PGXC start coordinator master cr2
    PGXC start datanode master dn1
    PGXC start datanode master dn2


https://stackoverflow.com/questions/29225743/installing-postgres-xl-in-linux-in-distributed-environment

https://ruihaijiang.wordpress.com/2015/09/17/postgres-xl-installation-example-on-linux/


Docker
------

https://github.com/tiredpixel/postgres-xl-docker


Ansible
-------

https://gitlab.com/ansible-postgres-xl/postgres-xl-cluster/tree/master


Django
------

https://github.com/omidraha/django-postgres-xl-example


Links
-----

https://www.postgres-xl.org/faq/

https://github.com/bitnine-oss/postgres-xl-ha


https://github.com/systemapic/wu/wiki/Installing-Postgresql-XL

https://www.postgres-xl.org/documentation/admin.html

https://stackoverflow.com/questions/42431018/can-postgres-xl-shard-replicate-and-auto-balance-at-the-same-time
