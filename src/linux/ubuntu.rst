Ubuntu
======

Ubuntu Server
-------------

Ubuntu Server 14.04.3 LTS

    The Long Term Support version of Ubuntu Server,

    including the Icehouse release of OpenStack and support guaranteed until April 2019 — 64-bit only.

Ubuntu Server 15.10

    The latest version of Ubuntu Server,

    including the Liberty release of OpenStack and support for nine months – 64-bit only.



SSH only allows public key authentication when you first login with password
----------------------------------------------------------------------------

because I encrypted my home directory.
SSH can't read the authorized_keys file until you log in,
so basically it forces you to password authenticate first.
See the troubleshooting section of the following:

https://help.ubuntu.com/community/SSH/OpenSSH/Keys

.. code-block:: bash

    $ sudo mkdir /etc/ssh/<username>
    $ sudo chown <username>:<username> /etc/ssh/<username>
    $ sudo chmod 755 /etc/ssh/<username>
    $ mv ~/.ssh/authorized_keys /etc/ssh/<username>
    $ chmod 644 /etc/ssh/<username>/authorized_keys
    $ chown <username>:<username> /etc/ssh/<username>/authorized_keys
    $ sudo vim /etc/ssh/sshd_config
        AuthorizedKeysFile    /etc/ssh/%u/authorized_keys
    $ sudo service ssh restart

    $ ssh <remote-host>
    # Note: Now you can see an empty home directory with default files,
    # and some files about unmounted encrypted partition.
    # And you need to run ``ecryptfs-mount-private`` command and enter password for encryption partition
    $ ecryptfs-mount-private

http://askubuntu.com/a/162270


Checks the Ubuntu version
-------------------------

.. code-block:: bash

    # give you the the description including the OS name
    $ lsb_release -d
    # will give you just the codename
    $ lsb_release -c
    # For the release number only, use
    $ lsb_release -r
    # For all lsb version details, use
    $ lsb_release -a

http://askubuntu.com/questions/150917/what-terminal-command-checks-the-ubuntu-version


ubuntu UFW
----------


Ubuntu's default firewall (UFW: Uncomplicated Firewall) denies all forwarding traffic by default, which is needed by docker.

Enable forwarding with UFW:

Edit UFW configuration using the nano text editor.

.. code-block:: bash

    $ sudo vim /etc/default/ufw

Scroll down and find the line beginning with DEFAULT_FORWARD_POLICY.

Replace:

.. code-block:: bash

    DEFAULT_FORWARD_POLICY="DROP"

With:

.. code-block:: bash

    DEFAULT_FORWARD_POLICY="ACCEPT"

Finally:

.. code-block:: bash

    $ sudo ufw reload


https://www.digitalocean.com/community/tutorials/docker-explained-how-to-containerize-python-web-applications


Connect to wireless network manually
------------------------------------

nmcli
+++++


Check to see which ESSID we can see:

.. code-block:: bash

    $ sudo apt-get install network-manager
    $ nmcli dev wifi

Verify the name of the ESSID and we proceed on using it on the next line including the password needed for it (This includes WEP and WPA type passwords):

.. code-block:: bash

    $ nmcli dev wifi connect ESSID_NAME password ESSID_PASSWORD



Automatic Wireless Connection on Login
++++++++++++++++++++++++++++++++++++++


.. code-block:: bash

    sudo nano /etc/network/interfaces


.. code-block:: bash

    auto wlan0
    iface wlan0 inet static
    address ASSIGNED_IP
    netmask 255.255.255.0
    gateway THE_GATEWAY
    wireless-essid YOURSSID
    wireless-key WIRELESSKEY_HERE

https://askubuntu.com/questions/16584/how-to-connect-and-disconnect-to-a-network-manually-in-terminal

.. code-block:: bash

    sudo apt-get install wpasupplicant
    sudo apt-get install wireless-tools



Make apt-get not prompt for replacement of configuration files
--------------------------------------------------------------


.. code-block:: bash

    $ apt-get -o Dpkg::Options::=--force-confnew -y dist-upgrade



--force-confold:

    do not modify the current configuration file, the new version is installed with a .dpkg-dist suffix. With this option alone, even configuration files that you have not modified are left untouched. You need to combine it with --force-confdef to let dpkg overwrite configuration files that you have not modified.

--force-confnew:

    always install the new version of the configuration file, the current version is kept in a file with the .dpkg-old suffix.

--force-confdef:

    ask dpkg to decide alone when it can and prompt otherwise. This is the default behavior of dpkg and this option is mainly useful in combination with --force-confold.

--force-confmiss:

    ask dpkg to install the configuration file if it’s currently missing (for example because you have removed the file by mistake).


https://raphaelhertzog.com/2010/09/21/debian-conffile-configuration-file-managed-by-dpkg/




