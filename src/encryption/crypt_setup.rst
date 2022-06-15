Crypt setup
===========

Setup cryptographic volumes for dm-crypt (including LUKS extension)

Encrypt home directory with `cryptsetup` module
===============================================

1. Backup home directory

2. Install modules:

.. code-block:: bash

	$ apt-get install cryptsetup

3.  Install header files (if you got warn about headers files in previous step)

.. code-block:: bash

	$ apt-get install  firmware-linux  firmware-realtek intel-microcode

4. Unmount partitions

.. code-block:: bash

	$ umount -a

5. Load modules in kernel

.. code-block:: bash

	$ modprobe xts
	$ modprobe dm-crypt
	$ modprobe aes
	$ modprobe aes-in
	$ modprobe aesni-intel
	$ modprobe  aes-x86_64

6. Encrypt wanted partition

.. code-block:: bash

	$ cryptsetup luksFormat -h --debug --cipher aes-xts-plain64  --hash sha256 /dev/sda5

another option for `cipher` is `aes-cbc-essive:sha512`

7. restart to effect new UUID

8. Open encrypted partition

.. code-block:: bash

	$ cryptsetup luksOpen /dev/sda5 home

9. Format partition with wanted partition type

.. code-block:: bash

	$ mkfs.ext4 /dev/mapper/home

10. Adding this partition to `fstab` file, also comment old line for home partition

.. code-block:: bash

	$ vim /etc/fstab
	/dev/mapper/home /home ext4 defaults 0 2

11. Get UUID of encryption partition

.. code-block:: bash

	$ blkid

12. Adding `UUID` of encryption partition to `etc/crypttab` file

.. code-block:: bash

	$ vim /etc/crypttab
	home UUID=<UUID OF /dev/mapper/home/> none luks

13. Mount encryption partition

.. code-block:: bash

	$ mount  /dev/mapper/home

14. Copy home directory from backup to this encryption partition

.. code-block:: bash

	$ mkdir /home/or
	$ cp -R /backup/or /home
	$ chown -R or /home/or

15. Update `image` file of `boot`

.. code-block:: bash

	$ update-initramfs -u

16. Check status of encrypted partition

.. code-block:: bash

	$ cryptsetup luksDump /dev/sda5

17. Backup headers of encryption partition

.. code-block:: bash

	$ cryptsetup luksHeaderBackup /dev/sda5 --header-backup-file /backup/sha5_ency_header.img


Resources:

	`Encrypt your linux home folder 2 ways and 10 steps <http://thesimplecomputer.info/encrypt-your-linux-home-folder-2-ways-and-10-steps>`_



TrueCrypt
=========


https://www.grc.com/misc/truecrypt/truecrypt.htm
