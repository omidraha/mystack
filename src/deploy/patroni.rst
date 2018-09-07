Patroni
=======


.. code-block:: bash

    $ pip install patroni


.. code-block:: bash

    $ git clone https://github.com/zalando/patroni
    $ cd patroni
    $ pip install -r requirements.txt


.. code-block:: bash

    $ patronictl show-config
    $ patronictl edit-config


pg_ctl, postgres, pg_basebackup and pg_rewind must be accessible within $PATH or
you should put bin_dir into postgresql section.

postgresql:
  bin_dir: /usr/lib/postgresql/9.6
