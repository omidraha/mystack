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

zcache
******


https://slushpool.com/help/get-started/getting_started_zcash

https://support.slushpool.com/section/29-zcash-mining-setup


flypool
+++++++

https://flypool.org/

minergate
+++++++++

https://www.minergate.com/pool-stats/eth


Hash Rate
---------



Hash Rate Measured & its Unit
+++++++++++++++++++++++++++++


Hash rate denominations

    1 kH/s is 1,000 (one thousand) hashes per second

    1 MH/s is 1,000,000 (one million) hashes per second.

    1 GH/s is 1,000,000,000 (one billion) hashes per second.

    1 TH/s is 1,000,000,000,000 (one trillion) hashes per second.

    1 PH/s is 1,000,000,000,000,000 (one quadrillion) hashes per second.

    1 EH/s is 1,000,000,000,000,000,000 (one quintillion) hashes per second.

Common Hash rate Conversions


    1 MH/s = 1,000 kH/s

    1 GH/s = 1,000 MH/s = 1,000,000 kH/s

    1 TH/s = 1,000 GH/s = 1,000,000 MH/s = 1,000,000,000 kH/s

    1 PH/s = 1,000 TH/s = 1,000,000 GH/s = 1,000,000,000 MH/s

    1 EH/s = 1,000 PH/s = 1,000,000 TH/s = 1,000,000,000 GH/s

https://coinsutra.com/hash-rate-or-hash-power/


What to mine
------------

https://whattomine.com/calculators

https://www.cointelligence.com/content/cryptocurrencies-can-still-mine-cpu-gpu-2018/
