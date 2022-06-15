Debian
======


Removed unused packages
-----------------------

http://www.waveguide.se/?article=how-to-quickly-remove-all-unused-packages-under-debian

https://www.debian-administration.org/article/134/Removing_unnecessary_packages_with_deborphan


.. code-block:: bash

    $ deborphan
    $ deborphan --guess-data
    $ deborphan --guess-all
    $ dpkg --purge `deborphan --guess-all`


.. code-block:: bash

    $ egrep '^Status: |^Package: ' /var/lib/dpkg/status | egrep -B 1 'half-installed|half-configured|unpacked|triggers-awaited|triggers-pending'
    $ dpkg --audit



Annoying autorenaming in Guake
------------------------------

http://askubuntu.com/questions/254566/annoying-autorenaming-in-guake


First install `gconf-editor`

.. code-block:: bash

    $ sudo apt-get install gconf-editor


Then run `gconf-editor` and browse to `/apps/guake/general` and unmarked `use_vte_titles` key




How do you uninstall a library in Linux?
----------------------------------------

http://stackoverflow.com/questions/1439950/whats-the-opposite-of-make-install-ie-how-do-you-uninstall-a-library-in-lin


If sudo make uninstall is unavailable:

In a debian based system, instead of doing make install

you can run sudo checkinstall (or .rpm etc. equivalent) to make a .deb that is also automatically installed.
You can then remove it using synaptic


.. code-block:: bash

    $ sudo checkinstall


Thunderbird
-----------

The `Icedove` debian package  is an unbranded Thunderbird

The `Enigmail` is a security extension to Mozilla Thunderbird and Seamonkey.

It enables you to write and receive email messages signed and/or encrypted with the OpenPGP standard.

Sending and receiving encrypted and digitally signed email is simple using Enigmail.


Yandex Setting up mail clients
------------------------------

https://help.yandex.com/mail/mail-clients.xml

How to adjust screen lock settings on Linux debian desktop
----------------------------------------------------------

To adjust screen lock settings from the command line, you can edit ~/.kde/share/config/kscreensaverrc.

Create the file if it does not exist. Once the file is edited, the change will automatically take effect immediately.

.. code-block:: bash

    $ vi ~/.kde/share/config/kscreensaverrc

    [ScreenSaver]
    Lock=true
    LockGrace=300000
    PlasmaEnabled=false
    Timeout=900


How to install Google Earth on Debian
-------------------------------------

.. code-block:: bash

    # aptitude install googleearth-package
    $ make-googleearth-package
    $ apt-get install -f
    # dpkg -i googleearth_4.2.205.5730+0.5.2-1_i386.deb


Restarting Networking
---------------------

.. code-block:: bash

    # systemctl restart  networking
    # systemctl restart  network-manager

https://wiki.debian.org/NetworkManager


Package manager is locked
-------------------------

.. code-block:: bash

    $ rm /var/lib/dpkg/lock /var/cache/apt/archives/lock /var/lib/apt/lists/lock


Some index files failed to download
-----------------------------------

.. code-block:: bash

    $ sudo apt-get update
    E: Some index files failed to download. They have been ignored, or old ones used instead.


It means that these sources cannot be reached, try selecting another server to fetch from.
