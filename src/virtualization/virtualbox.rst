Virtual box
===========

Install latest version
----------------------


.. code-block:: bash

	$ sudo apt-get purge virtualbox-\*
	# download from https://www.virtualbox.org/wiki/Linux_Downloads
	$ wget <desired_package.deb>
	$ sudo dpkg -i <desired_package.deb>


Unistall running virtualbox
---------------------------

.. code-block:: bash

	$ sudo service vboxdrv stop
	$ ps aux | grep VBoxSVC
	$ pidof VBoxSVC
	$ sudo kill -9 7811
	$ sudo apt-get purge virtualbox-\*
