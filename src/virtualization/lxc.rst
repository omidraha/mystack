LXC
===


.. code-block:: bash

	$ sudo lxc-create -t debian -n p1

	Checking cache download in /var/cache/lxc/debian/rootfs-wheezy-amd64 ...
	Downloading debian minimal ...
	Download complete.
	Copying rootfs to /var/lib/lxc/p1/rootfs...Generating locales (this might take a while)...
	  en_US.UTF-8... done
	Generation complete.
	update-rc.d: using dependency based boot sequencing
	update-rc.d: using dependency based boot sequencing
	update-rc.d: using dependency based boot sequencing
	update-rc.d: using dependency based boot sequencing
	Creating SSH2 RSA key; this may take some time ...
	Creating SSH2 DSA key; this may take some time ...
	Creating SSH2 ECDSA key; this may take some time ...
	invoke-rc.d: policy-rc.d denied execution of restart.

	Current default time zone: 'Asia/Tehran'
	Local time is now:      Sun Jun 29 16:51:56 IRDT 2014.
	Universal Time is now:  Sun Jun 29 12:21:56 UTC 2014.

	Root password is 'root', please change !

