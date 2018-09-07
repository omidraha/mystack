Remote Desktop
==============

Configure users to connect to Debian from a Windows machine using Remote Desktop
--------------------------------------------------------------------------------



.. code-block:: bash

    $ sudo apt-get install xrdp
    $ sudo apt-get install xfce4
    $ apt-get install xfce4 xfce4-goodies gnome-icon-theme
    $ echo xfce4-session >~/.xsession

For good measure, it is a good idea to edit the ``startwm.sh`` file
to ensure that ``XRDP`` will always use ``xfce4``

Add the following lines at the end of the file:

.. code-block:: bash

    $ sudo vim /etc/xrdp/startwm.sh
        . /etc/X11/Xsession
        . /usr/bin/startxfce4


Then using the RDP client from Windows 7, ``mstsc.exe``;


https://community.spiceworks.com/how_to/92663-configure-users-to-connect-to-ubuntu-14-04-from-a-windows-machine-using-remote-desktop

http://scarygliders.net/2011/11/17/x11rdp-ubuntu-11-10-gnome-3-xrdp-customization-new-hotness/?PageSpeed=noscript


if not works:

.. code-block:: bash

    $ sudo apt-get install tightvncserver
    $ sudo adduser vnc
    $ gpasswd -a vnc sudo
    $ mkdir /home/vnc
    $ chown -R vnc:vnc /home/vnc









