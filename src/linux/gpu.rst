GPU
===


How to measure GPU usage?
-------------------------

https://askubuntu.com/questions/387594/how-to-measure-gpu-usage


nvidia-smi
++++++++++

https://developer.nvidia.com/nvidia-system-management-interface

.. code-block:: bash

    $ watch nvidia-smi

    $ nvidia-smi --loop-ms=1000


gpustat
+++++++

https://github.com/wookayin/gpustat

.. code-block:: bash

    $ pip install gpustat
    $ gpustat
    $ gpustat -cp
    $ gpustat -cpi
    $ watch gpustat

NOTE: This works with NVIDIA Graphics Devices only



glances
+++++++


https://github.com/nicolargo/glances


.. code-block:: bash

    $ pip install glances
    $ pip install glances[gpu]



