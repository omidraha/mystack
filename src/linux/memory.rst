Memory
======

How to see system and process memory usage
------------------------------------------


.. code-block:: bash

    $ apt-get install sysstat
    $ pidstat -r
    $ pidstat -r -p $(pidof java)

This will show both your memory and your swap usage:

.. code-block:: bash

    # Size options are: -k, -m, -g
    $ free -m