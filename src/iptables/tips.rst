Tips
====

Open one port
--------------

.. code-block:: bash

    $ sudo iptables -I INPUT -p tcp -s 0.0.0.0/0 --dport 8000 -j ACCEPT


