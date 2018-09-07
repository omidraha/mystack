Tips
====

Setting up Virtual MIDI Piano Keyboard in Ubuntu
++++++++++++++++++++++++++++++++++++++++++++++++

.. code-block:: bash

    $ sudo apt-get install vmpk vkeybd
    $ sudo apt-get install amsynth
    $ sudo apt-get install qjackctl

After installing above packages
we need to connect the vmpk with amsynth through qjackctl,
because vmpk doesn’t produce sound by itself.

Lanuch ``Vmpk``, ``Amsynth``, ``Qjackctl`` one by one.


vmpk output > vmpk input

vmpk outpu > amsynth MIDI IN

https://sathisharthars.com/2014/03/01/setting-up-virtual-midi-piano-keyboard-in-ubuntu/

https://www.youtube.com/watch?v=827jmswqnEA
