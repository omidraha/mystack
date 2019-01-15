Tips
====

Install citus on single machine
--------------------------------



.. code-block:: bash

    $ curl -L https://raw.githubusercontent.com/citusdata/docker/master/docker-compose.yml > docker-compose.yml
    $ COMPOSE_PROJECT_NAME=citus docker-compose up -d


.. code-block:: bash

    $ citus_manager    python -u ./manager.py          Up
    $ citus_master     docker-entrypoint.sh postgres   Up      0.0.0.0:5432->5432/tcp
    $ citus_worker_1   docker-entrypoint.sh postgres   Up      5432/tcp

.. code-block:: bash

    $ docker exec -it citus_master psql -U postgres

.. code-block:: bash

    postgres=# SELECT * FROM master_get_active_worker_nodes();


.. code-block:: bash

       node_name    | node_port
    ----------------+-----------
     citus_worker_1 |      5432
    (1 row)


.. code-block:: bash

    $ COMPOSE_PROJECT_NAME=citus docker-compose down -v

https://docs.citusdata.com/en/v8.1/installation/single_machine_docker.html
