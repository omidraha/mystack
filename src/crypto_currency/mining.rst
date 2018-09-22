Mining
======



Mining software
---------------


https://en.bitcoin.it/wiki/Mining_software

cgminer
+++++++

https://github.com/ckolivas/cgminer

.. code-block:: bash

    $ sudo apt-get install cgminer
    $ cgminer --userpass omidraha.worker1:anything --url stratum+tcp://jp.stratum.slushpool.com:3333


bfgminer
++++++++

http://bfgminer.org/

https://linuxhint.com/bfgminer-ubuntu/

https://bitcointalk.org/?topic=877081

.. code-block:: bash

    $ sudo apt-get install bfgminer
    $ bfgminer -o stratum+tcp://jp.stratum.slushpool.com:3333 -u omidraha.worker1 -p anything


poclbm
++++++


https://github.com/m0mchil/poclbm

.. code-block:: bash

    $ git clone https://github.com/m0mchil/poclbm
    $ cd poclbm
    $ poclbm.py omidraha.worker1:anything@jp.stratum.slushpool.com:3333


MultiMiner
++++++++++

https://github.com/nwoolls/MultiMiner

https://github.com/nwoolls/MultiMiner/wiki/Installation


Pool
----

https://en.bitcoin.it/wiki/Comparison_of_mining_pools

slushpool
+++++++++

https://slushpool.com/help/get-started/getting_started

https://slushpool.com/help/get-started/advanced_mining

https://slushpool.com/help/get-started/mining_beginners
