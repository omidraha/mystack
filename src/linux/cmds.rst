Cmds
====


Add User
--------

.. code-block:: bash

    $ sudo adduser <username>

Delete a User
-------------

.. code-block:: bash

    $ sudo deluser <username>
    $ sudo userdel <username>

If, instead, you want to delete the user's home directory when the user is deleted,
you can issue the following command as root:

.. code-block:: bash

    $ sudo deluser --remove-home <username>
    $ sudo deluser -r <username>
    $ sudo userdel -r <username>

Changing User Password
----------------------

.. code-block:: bash

    $ sudo passwd <username>


Allowing other users to run sudo
--------------------------------

.. code-block:: bash

    $ sudo adduser <username> sudo
    $ sudo visudo
    # $ vim /etc/sudoers
        # User privilege specification
        root	ALL=(ALL:ALL) ALL
        or      ALL=(ALL:ALL) ALL
        # Allow members of group sudo to execute any command
        %sudo	ALL=(ALL:ALL) ALL



Note:  If the user matches more than one rule. In that case the last matching rule wins. So, you need to move:

.. code-block:: bash

    or      ALL=(ALL:ALL) ALL

To the end of the file, it should work.

http://askubuntu.com/questions/7477/how-can-i-add-a-new-user-as-sudoer-using-the-command-line

https://help.ubuntu.com/community/RootSudo#Allowing_other_users_to_run_sudo

Delete a user from one group
-----------------------------

.. code-block:: bash

    $ groupdel group


http://www.computerhope.com/unix/groupdel.htm

Remove sudo privileges from a user (without deleting the user)
--------------------------------------------------------------

.. code-block:: bash

    $ sudo deluser username sudo


http://askubuntu.com/a/335989

Users and Groups name list
--------------------------

.. code-block:: bash

    getent passwd | awk -F':' '{ print $1}'
    getent passwd | awk -F: '{print $1}' | while read name; do groups $name; done
    kuser (KDE User Manager)



apt-file search
---------------

ERROR: cmake/modules/FindKDE4Internal.cmake not found in

.. code-block:: bash

    apt-file search FindKDE4Internal.cmake
    kdelibs5-dev: /usr/share/kde4/apps/cmake/modules/FindKDE4Internal.cmake



mtu
---

.. code-block:: bash

    ifconfig eth0 mtu 1400  #  1360, 1406 or 1407 , default is 1500


dpkg-reconfigure
----------------

.. code-block:: bash

    dpkg-reconfigure kdm
    dpkg-reconfigure gdm

rfkill
------

.. code-block:: bash

    # ifconfig wlan0 up
    SIOCSIFFLAGS: Operation not possible due to RF-kill

.. code-block:: bash

    # rfkill list
    0: phy0: Wireless LAN
            Soft blocked: yes
            Hard blocked: no

.. code-block:: bash

    # rfkill unblock 0

.. code-block:: bash

    # rfkill list
    0: phy0: Wireless LAN
            Soft blocked: no
            Hard blocked: no

.. code-block:: bash

    # ifconfig wlan0 up

Run wireshark with capture packets privilege
--------------------------------------------

``http://wiki.wireshark.org/CaptureSetup/CapturePrivileges``

.. code-block:: bash

    setcap 'CAP_NET_RAW+eip CAP_NET_ADMIN+eip' /usr/bin/dumpcap
    groupadd wireshark
    usermod -a -G wireshark omidraha
    chgrp wireshark /usr/bin/dumpcap
    chmod 4750 /usr/bin/dumpcap
    dpkg-reconfigure wireshark-common

      ┌────────────────────────────────────────────────────────────────┤ Configuring wireshark-common ├─────────────────────────────────────────────────────────────────┐
      │                                                                                                                                                                 │
      │ Dumpcap can be installed in a way that allows members of the "wireshark" system group to capture packets. This is recommended over the alternative of running   │
      │ Wireshark/Tshark directly as root, because less of the code will run with elevated privileges.                                                                  │
      │                                                                                                                                                                 │
      │ For more detailed information please see /usr/share/doc/wireshark-common/README.Debian.                                                                         │
      │                                                                                                                                                                 │
      │ Enabling this feature may be a security risk, so it is disabled by default. If in doubt, it is suggested to leave it disabled.                                  │
      │                                                                                                                                                                 │
      │ Should non-superusers be able to capture packets?                                                                                                               │
      │                                                                                                                                                                 │
      │                                                 <Yes>                                                    <No>                                                   │
      │                                                                                                                                                                 │
      └─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘


Install, Remove, Purge and get Info of Packages
-----------------------------------------------

To install package

.. code-block:: bash

    dpkg -i package-file-name

To remove (uninstall) package

.. code-block:: bash

    dpkg -r package-file-name

To Purge package

.. code-block:: bash

    dpkg -P package-file-name

To get info of package

.. code-block:: bash

    dpkg -l | grep 'package-file-name'


Create A Local Debian Mirror With apt-mirror
--------------------------------------------

`http://www.howtoforge.com/local_debian_ubuntu_mirror`


.. code-block:: bash

	apt-get install apt-mirror

	vim /etc/apt/mirror.list

		set base_path    /mnt/sdc1/OR/apt-mirror
		# set mirror_path  $base_path/mirror
		# set skel_path    $base_path/skel
		# set var_path     $base_path/var
		# set cleanscript $var_path/clean.sh
		# set defaultarch  <running host architecture>
		# set postmirror_script $var_path/postmirror.sh
		# set run_postmirror 0
		set nthreads     20
		set _tilde 0
		deb http://172.16.1.210/repo/debian testing  main contrib non-free # 32 bit
		deb-amd64 http://172.16.1.210/repo/debian testing  main contrib non-free  # 64 bit
		# set cleanscript $var_path/clean.sh
		clean http://172.16.1.210/repo/debian

	su - apt-mirror -c apt-mirror

	/mnt/sdc1/OR/apt-mirror/var/clean.sh


Named pipe
----------

In computing, a named pipe (also known as a FIFO for its behavior) is an extension to the traditional pipe concept on Unix and Unix-like systems, and is one of the methods of inter-process communication (IPC).

The concept is also found in Microsoft Windows, although the semantics differ substantially.

A traditional pipe is "unnamed" because it exists anonymously and persists only for as long as the process is running.

A named pipe is system-persistent and exists beyond the life of the process and must be deleted once it is no longer being used.

Processes generally attach to the named pipes (usually appearing as a file) to perform inter-process communication.

Instead of a conventional, unnamed, shell pipeline, a named pipeline makes use of the filesystem.

It is explicitly created using mkfifo() or mknod(), and two separate processes can access the pipe by name, one process can open it as a reader, and the other as a writer.

.. code-block:: bash

	mkfifo /tmp/testfifo
	tail -f /tmp/testfifo

and in another console:

.. code-block:: bash

	echo HELLO! > /tmp/testfifo


Give Privilege to a non-root process to bind to ports under 1024
----------------------------------------------------------------


.. code-block:: bash

	setcap 'cap_net_bind_service=+ep' $(readlink -f `which python`)


How do I test whether a number is prime?
----------------------------------------

`<http://www.madboa.com/geek/openssl/#prime-test>`_

.. code-block:: bash

	$ openssl prime 119054759245460753
	1A6F7AC39A53511 is not prime

You can also pass hex numbers directly.

.. code-block:: bash

	$ openssl prime -hex 2f
	2F is prime


Download from YouTube
---------------------

`<https://github.com/rg3/youtube-dl>`_

.. code-block:: bash

	# apt-get install youtube-dl
	$ youtube-dl https://www.youtube.com/watch?v=video_id --proxy http://host:port
	$ youtube-dl -v -i --no-mtime --no-check-certificate   --youtube-skip-dash-manifest https://www.youtube.com/watch?v=video_id


Download YouTube Video as a audio
---------------------------------

Download mp3 of video from YouTube

.. code-block:: bash

    # apt-get install yt-dlp
    $ yt-dlp -x --audio-format mp3   https://www.youtube.com/watch?v=video_id


How to use youtube-dl from a python program
-------------------------------------------

.. code-block:: python

    url = raw_input('URL:')

    dl = youtube_dl.YoutubeDL({'outtmpl': u'%(id)s.mp4',
                               'forceduration': True,
                               'restrictfilenames': True,
                               'format': '18/22/5',
                               'writesubtitles': True})

    res = dl.extract_info(url)

    duration = res['duration']
    title = res['title']
    vid = res['id']
    ext = res['ext']
    web_page_url = res['webpage_url']
    subtitles = entry['subtitles']

Youtube options:

https://github.com/rg3/youtube-dl/blob/1ad6b891b21b45830736698a7b59c30d9605a562/youtube_dl/__init__.py#L290

Download Youtube videos with Youtube subtitles on
-------------------------------------------------

.. code-block:: bash

     # To download sub
     $ youtube-dl --no-mtime --proxy http://127.0.0.1:8080 -f 18  --write-sub --sub-lang en --write-auto-sub --convert-subtitles srt  URL
     # To embed sub
     $ youtube-dl --no-mtime --proxy http://127.0.0.1:8080 -f 18  --embed-subs --sub-lang en --write-auto-sub --convert-subtitles srt URL


Redirect output to null
-----------------------

.. code-block:: bash

     $ echo 123 >/dev/null 2>&1

cron
----

You do not have to restart cron every time you make a change because cron always checks for changes, But to restart cron whenever you made change:

.. code-block:: bash

    $ sudo service crond restart

In Ubuntu:

.. code-block:: bash

    $ sudo service cron status
    $ sudo service cron restart

Display the current crontab:

.. code-block:: bash

    $ crontab -l

Edit the current crontab:

.. code-block:: bash

    $ crontab -e

Syntax of crontab (field description)

.. code-block:: bash

    * * * * * /path/to/command arg1 arg2

    * * * * * command to be executed
	- - - - -
	| | | | |
	| | | | ----- Day of week (0 - 7) (Sunday=0 or 7)
	| | | ------- Month (1 - 12)
	| | --------- Day of month (1 - 31)
	| ----------- Hour (0 - 23)
	------------- Minute (0 - 59)

How do I use operators?

An operator allows you to specifying multiple values in a field. There are three operators:

The asterisk (*):

This operator specifies all possible values for a field. For example,
an asterisk in the hour time field would be equivalent to every hour or an asterisk in the month field would be equivalent to every month.

The comma (,):

This operator specifies a list of values, for example: "1,5,10,15,20, 25".

The dash (-):

This operator specifies a range of values, for example: "5-15" days ,
which is equivalent to typing "5,6,7,8,9,....,13,14,15" using the comma operator.

The separator (/):

This operator specifies a step value, for example: "0-23/" can be used in the hours field to specify command execution every other hour.
Steps are also permitted after an asterisk, so if you want to say every two hours, just use \*/2.

Resources:

	`<http://www.cyberciti.biz/faq/how-do-i-add-jobs-to-cron-under-linux-or-unix-oses/>`_

	`<http://www.thegeekstuff.com/2011/12/crontab-command/>`_

	`<http://www.computerhope.com/unix/ucrontab.htm>`_


http://crontab.guru/


Generate random base64 characters
---------------------------------

.. code-block:: bash

	$ openssl rand -base64 741


Set Socket Buffer Sizes
-----------------------

.. code-block:: bash

	# sysctl -w net.core.rmem_max=2096304
	# sysctl -w net.core.wmem_max=2096304


Ping
----

-s packetsize

Specifies the number of data bytes to be sent.
The default is 56, which translates into 64 ICMP data bytes when combined with the 8 bytes (in my local system, 28 bytes) of ICMP header data.

-M pmtudisc_opt

Select Path MTU Discovery strategy.
pmtudisc_option may be either do (prohibit fragmentation, even local one), want (do PMTU discovery, fragment locally when packet size is large), or dont (do not set DF flag).



.. code-block:: bash

	# ping -c 1 -M do -s 1472  google.com
	PING google.com (173.194.113.167) 1472(1500) bytes of data.
	1480 bytes from www.google.com (173.194.113.167): icmp_seq=1 ttl=42 time=262 ms

	--- google.com ping statistics ---
	1 packets transmitted, 1 received, 0% packet loss, time 0ms
	rtt min/avg/max/mdev = 262.920/262.920/262.920/0.000 m


Change owner of directory
-------------------------


.. code-block:: bash

	$ chown -R or:or .



Locate/print block device attributes
------------------------------------

.. code-block:: bash

	# blkid
	/dev/sda6: UUID="2fc31bf0-68f1-4566-975b-cb995277db10" TYPE="swap"
	/dev/sda1: UUID="ec3c1569-29bb-4a63-bd75-337c57c7b600" TYPE="ext4"


Create a new UUID value
-----------------------

.. code-block:: bash

	$ uuidgen
	d2ad5b28-b306-4096-aca2-dd66c37da5af



SSH
---

.. code-block:: bash

    # socks5 proxy with dynamic tcp/ip
    $ ssh -D 8080 user@remote_host

.. code-block:: bash

    $ ssh -L 8080:localhost:80 user@remote_host


.. code-block:: bash

    # connect to remote running program on the remote host, for example TinyProxy
    $ ssh -N user@remote_host -L 8080:localhost:8888


Force ssh client to use only password authentication
----------------------------------------------------

.. code-block:: bash

    ssh  -o PreferredAuthentications=password -o PubkeyAuthentication=no user@remote_host

Secure copy
-----------

.. code-block:: bash

	$ scp -r Prj username@remote_ip:/directory/path/in/remote/ip/



Install SSH server and SSH client
---------------------------------

.. code-block:: bash

    $ sudo apt-get install openssh-server
    $ sudo apt-get install openssh-client

https://wiki.debian.org/SSH

Create a new ssh key
--------------------

.. code-block:: bash

	$ ssh-keygen -t rsa -C "mail@example.com"
	Generating public/private rsa key pair.
	Enter file in which to save the key (/home/or/.ssh/id_rsa): /home/or/.ssh/bitbucket_rsa
	Enter passphrase (empty for no passphrase):
	Enter same passphrase again:
	Your identification has been saved in /home/or/.ssh/bitbucket_rsa.
	Your public key has been saved in /home/or/.ssh/bitbucket_rsa.pub.
	$ ssh-add ~/.ssh/bitbucket_rsa
	$ vim ~/.ssh/config
	IdentityFile ~/.ssh/bitbucket_rsa
	$ chmod 400 ~/.ssh/bitbucket_rsa


SSH connection with public key
------------------------------

.. code-block:: bash

    $ vim ~/.ssh/authorized_keys
        # add public key

Disable the Password for Root Login
-----------------------------------

.. code-block:: bash

    $ sudo vim /etc/ssh/sshd_config
        PasswordAuthentication no

    $ sudo /etc/init.d/ssh restart

Youtube download trick
----------------------

.. code-block:: bash

    $ youtube-dl --no-mtime  --verbose -i 'ytsearch100:table tennis training' --get-title
    $ youtube-dl --no-mtime  --verbose -i 'ytsearch100:table tennis training'



Run process as background and never die
---------------------------------------

.. code-block:: bash

    $ nohup node server.js > /dev/null 2>&1 &
    $ ./run.py > /dev/null 2>&1 &


1. `nohup` means: Do not terminate this process even when the `stty` is cut off.

2. `> /dev/null` means: `stdout` goes to `/dev/null` (which is a dummy device that does not record any output).

3. `2>&1` means: `stderr` also goes to the `stdout` (which is already redirected to `/dev/null`).

4. `&` at the end means: run this command as a background task.


Eject CD/DVD-ROM
----------------

`eject` - eject removable media

.. code-block:: bash

    $ eject
    $ eject -t

`-t`
    With this option the drive is given a CD-ROM tray close command. Not all devices support this command.


Search for a package
--------------------

.. code-block:: bash

    $ apt-cache search package_name


Un mount cd-rom device that is busy error
-----------------------------------------


.. code-block:: bash

    # umount /cdrom
    # fuser -km /cdrom
    # umount -l /mnt


Login with linux FTP username and password
------------------------------------------

.. code-block:: bash

    $ ftp ftp://username:password@my.domain.com


Download torrent
----------------

.. code-block:: bash

        $ aria2c download.torrent


Debug SSH
---------

.. code-block:: bash

    # ssh -vT root@127.0.0.1


Detect ssh authentication types available
-----------------------------------------

.. code-block:: bash

       ssh -o PreferredAuthentications=none   127.0.0.1
       Permission denied (publickey,password).

       ssh -o PreferredAuthentications=none   127.0.0.2
       Permission denied (publickey).

       ssh -o PreferredAuthentications=none   127.0.0.3
       Permission denied (publickey,gssapi-keyex,gssapi-with-mic,password).



http://stackoverflow.com/questions/3585586/how-can-i-programmatically-detect-ssh-authentication-types-available

Avoid SSH's host verification for known hosts?
----------------------------------------------

.. code-block:: bash

    ssh -o "StrictHostKeyChecking no" 127.0.0.1


http://superuser.com/questions/125324/how-can-i-avoid-sshs-host-verification-for-known-hosts

Set environment variables on linux
----------------------------------

.. code-block:: bash

	$ export PATH=${PATH}:/home/or/bin


Base64 decode encode
--------------------


.. code-block:: bash

	or@debian:~$ echo 'Test' | base64
	VGVzdAo=

	or@debian:~$ echo 'Test' | base64  | base64 -d
	Test



Extract compressed files
------------------------

.. code-block:: bash

    # Decompressed a file that is created using gzip command.
    # File is restored to their original form using this command.
    $ gzip -d mydata.doc.gz
    $ gunzip mydata.doc.gz

    # Decompressed a file that is created using bzip2 command.
    # File is restored to their original form using this command.
    $ bzip2 -d mydata.doc.bz2
    $ gunzip mydata.doc.bz2

    # Extract compressed files in a ZIP archive.
    $ unzip file.zip
    $ unzip data.zip resume.doc

    # Untar or decompressed a file(s) that is created using tar compressing through gzip and bzip2 filter
    $ tar -zxvf data.tgz
    $ tar -zxvf pics.tar.gz *.jpg
    $ tar -jxvf data.tbz2

    # Extract tar files and to another directory
    $ tar -xvf archive.tar -C /target/directory

    # List files from a GZIP archive
    $ gzip -l mydata.doc.gz

    # List files from a ZIP archive
    $ unzip -l mydata.zip

    # List files from a TAR archive
    $ tar -ztvf pics.tar.gz
    $ tar -jtvf data.tbz2

    # To unzip a file that is only compressed with bz2 use
    $ bunzip2 filename.bz2

    # To unzip things that are compressed with .tar.bz2 use
    $ tar -xvjpf filename.tar.bz2

    # To unzip things that are compressed with  .gz use
    $ gunzip file.doc.gz

    # Don't store full absolute paths in the archive
    # This will archive `/home/or/ws/data` directory without absolute path to the `data.tar` file
    $ tar -cf data.tar  -C /home/or/ws/ data

Options for tar files:

    Type at the command prompt

        tar xvzf file-1.0.tar.gz – to uncompress a gzip tar file (.tgz or .tar.gz)
        tar xvjf file-1.0.tar.bz2 – to uncompress a bzip2 tar file (.tbz or .tar.bz2)
        tar xvf file-1.0.tar – to uncompressed tar file (.tar)

        x = eXtract, this indicated an extraction c = create to create )
        v = verbose (optional) the files with relative locations will be displayed.
        z = gzip-ped; j = bzip2-zipped
        f = from/to file … (what is next after the f is the archive file)

    The files will be extracted in the current folder.
    HINT: if you know that a file has to be in a certain folder, move to that folder first.
    Then download, then uncompress – all in the correct folder.
    Yes, I’m lazy.. no I don’t like to copy files between directories, and then delete others to clean up.
    Download them in the correct directory and save yourself 2 jobs.


List All Environment Variables
------------------------------

.. code-block:: bash

    $ env

    $ printenv

    $ printenv | less

    $ printenv | more


Set Environment variable
------------------------


.. code-block:: bash

    $ export MY_VAR="my_val"



Set proxy in command line
-------------------------


.. code-block:: bash

    $ export http_proxy="http://127.0.0.1:8080"
    $ export https_proxy="https://127.0.0.1:8080"
    $ export ftp_proxy="http://127.0.0.1:8080"


How can I tunnel all of my network traffic through SSH?
-------------------------------------------------------

http://superuser.com/questions/62303/how-can-i-tunnel-all-of-my-network-traffic-through-ssh


.. code-block:: bash

    $ sudo sshuttle --dns -vvr username@remote_ip.121  0/0




How can you completely remove a package?
----------------------------------------

http://askubuntu.com/questions/151941/how-can-you-completely-remove-a-package

.. code-block:: bash

    $ sudo apt-get purge package_name

This does not remove packages that were installed as dependencies, when you installed the package you're now removing.

Assuming those packages aren't dependencies of any other packages,

and that you haven't marked them as manually installed,

you can remove the dependencies with:

.. code-block:: bash

    $ sudo apt-get autoremove

or (if you want to delete their systemwide configuration files too):

.. code-block:: bash

    $ sudo apt-get --purge autoremove


How to forward X over SSH from Ubuntu machine ...
-------------------------------------------------

http://unix.stackexchange.com/questions/12755/how-to-forward-x-over-ssh-from-ubuntu-machine

X11 forwarding needs to be enabled on both the client side and the server side.

On the client side, the -X (capital X) option to ssh enables X11 forwarding,

and you can make this the default (for all connections or for a specific conection)

with ForwardX11 yes in ``~/.ssh/config``.


On the server side, edit the /etc/ssh/sshd_config file, and uncomment the following line:

.. code-block:: bash

    X11Forwarding Yes


The xauth program must be installed on the server side.

.. code-block:: bash

    $ aptitude install xauth

After making this change, you will need to restart the SSH server. To do this on most UNIX's, run:

.. code-block:: bash

    $ /etc/init.d/sshd restart

To confirm that ssh is forwarding X11,

Check for a line containing Requesting X11 forwarding in the output:

.. code-block:: bash

    $ ssh -v -X USER@SERVER

Note that the server won't reply either way.


SOCKS server and/or client
--------------------------

http://www.delegate.org/delegate/SOCKS/

http://ajitabhpandey.info/2011/03/delegate-a-multi-platform-multi-purpose-proxy-server/

Download delegate from http://delegate.hpcc.jp/anonftp/DeleGate/bin/linux/latest/ and extract it.

Then run binary file as:

Run a Http proxy that is connected to a socks:

.. code-block:: bash

    $ ./dg9_9_13 -P8080 SERVER=http SOCKS=127.0.0.1:9150 ADMIN="local@localhost.com"

    $ youtube-dl -v --proxy "http://127.0.0.1:8080" https://www.youtube.com/watch?v=VID


SSH hangs on debug1: expecting SSH2_MSG_KEX_ECDH_REPLY
------------------------------------------------------


Edit ``/etc/ssh/ssh_config``, uncomment the following lines


.. code-block:: bash

    Ciphers aes128-ctr,aes192-ctr,aes256-ctr,arcfour256,arcfour128,aes128-cbc,3des-cbc
    GSSAPIAuthentication yes
    GSSAPIDelegateCredentials no
    MACs hmac-md5,hmac-sha1,umac-64@openssh.com,hmac-ripemd160

Add the following line:

.. code-block:: bash

    HostKeyAlgorithms ssh-rsa,ssh-dss

Also change MTU may be useful:

.. code-block:: bash

    ifconfig eth0 mtu 578

http://superuser.com/questions/699530/git-pull-does-nothing-git-push-just-hangs-debug1-expecting-ssh2-msg-kex-ecd





What will this command do?
--------------------------

.. code-block:: bash

	$ exec 2>&1

The ``1``  number refer to ``stdout``, and The ``2``  number refer to ``stderr``

it duplicates, or copies, stderr onto stdout.

When you run a program, you'll get the normal output in stdout, but any errors or warnings usually go to stderr.
If you want to pipe all output to a file for example, it's useful to first combine stderr with stdout with ``2>&1``


http://stackoverflow.com/questions/1216922/sh-command-exec-21

http://www.catonmat.net/blog/bash-one-liners-explained-part-three/


Sample guake script
-------------------

.. code-block:: bash

	$ vim /home/or/workspace/bin/start.guake.sh

	guake -r "OR";
	guake -n New_Tab -r "root"; -e "su";
	guake -n New_Tab  -r "Ipython 2" -e "ipython";
	guake -n New_Tab  -r "workspace" -e "cd /home/or/workspace/;clear;";
	guake -n New_Tab  -r "prj" -e "cd /home/or/workspace/prj/;clear;";
	guake -n New_Tab  -r "dg" -e "cd /home/or/workspace/Tools/dg/dg9_9_13/DGROOT/bin/;clear;";

	$ chmod +x vim /home/or/workspace/bin/start.guake.sh


Verify that apt is pulling from the right repository
----------------------------------------------------

 .. code-block:: bash

	$ apt-cache policy <Packge-Name>

Example:

 .. code-block:: bash

	$ apt-cache policy docker-engine

Output:

 .. code-block:: bash

	Installed: 1.9.1-0~stretch
	Candidate: 1.9.1-0~stretch
	Version table:
	*** 1.9.1-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
		100 /var/lib/dpkg/status
	 1.9.0-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.8.3-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.8.2-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.8.1-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.8.0-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.7.1-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.7.0-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.6.2-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.6.1-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.6.0-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages
	 1.5.0-0~stretch 500
		500 https://apt.dockerproject.org/repo debian-stretch/main amd64 Packages



Operation not permitted on file with root access
------------------------------------------------

.. code-block:: bash

    # ls -la   /etc/resolv.conf
    -r--r--r-- 1 root root 56 Jan  7 22:39 /etc/resolv.conf

    # chmod u+rwx  /etc/resolv.conf
    chmod: changing permissions of ‘/etc/resolv.conf’: Operation not permitted

    # lsattr /etc/resolv.conf
    ----i--------e-- /etc/resolv.conf

    # chattr -i  /etc/resolv.conf
    # lsattr /etc/resolv.conf
    -------------e-- /etc/resolv.conf


rsync and sudo over SSH
-----------------------

Add the line ``<username> ALL=NOPASSWD:<path to rsync>``,
where username is the login name of the user that rsync will use to log on.
That user must be able to use sudo

Note:
    Put the line after all other lines in the sudoers file!
    I first added the line after other user configurations,
    but it only worked when placed as absolutely last line in file on lubuntu 14.04.1.


.. code-block:: bash

    $ sudo  visudo
        <username> ALL=NOPASSWD:<path to rsync>

Example:

.. code-block:: bash

    $ which rsync
    /usr/bin/rsync

    $ sudo  visudo
        ubuntu ALL=NOPASSWD:/usr/bin/rsync


https://askubuntu.com/a/719440

http://stackoverflow.com/questions/21659637/how-to-fix-sudo-no-tty-present-and-no-askpass-program-specified-error


How to backup with rsync
------------------------

.. code-block:: bash

    $ rsync -avz -e ssh --rsync-path="sudo rsync" <username>@<remote_host>:/path/on/remote/host/to/backup /path/on/local/host/to/save/backup


Using rsync for local backups

.. code-block:: bash

    $ rsync -av --delete /Directory1/ /Directory2/

-a
    recursive (recurse into directories),
    links (copy symlinks as symlinks),
    perms (preserve permissions),
    times (preserve modification times),
    group (preserve group), owner (preserve owner),
    preserve device files, and preserve special files.

-v
    verbose. The reason I think verbose is important is so you can see exactly what rsync is backing up.
    Think about this: What if your hard drive is going bad, and starts deleting files without your knowledge,
    then you run your rsync script and it pushes those changes to your backups,
    thereby deleting all instances of a file that you did not want to get rid of?

–delete
    This tells rsync to delete any files that are in ``Directory2`` that aren’t in ``Directory1``.
    If you choose to use this option, I recommend also using the verbose options, for reasons mentioned above.

Full Daily Backup with Syncing  Hourly Backup by rsync and cron
---------------------------------------------------------------


.. code-block:: bash

    $ crontab  -e

        0 */2 * * * hourly_sync_backup.sh
        0 */8 * * * daily_full_archive_backup.sh

    $ service cron restart

    $ vim hourly_sync_backup.sh
        rsync -avz -e ssh --rsync-path="sudo rsync" <username>@<remote_host>:/path/on/remote/host/to/backup /path/on/local/host/to/save/hourly_sync_backup

    $ vim daily_full_archive_backup.sh
        rsync -avz -e ssh --rsync-path="sudo rsync" <username>@<remote_host>:/path/on/remote/host/to/backup /path/on/local/host/to/save/daily_full_archive_backup
        tar -P -cvjf /path/on/local/host/to/save/archives/daily_full_archive_backup_$(date +%Y_%m_%d).tar.bz2 /path/on/local/host/to/save/daily_full_archive_backup

Backup with rsync works but not in crontab
------------------------------------------

.. code-block:: bash

    $ rsync -avze "ssh -i ~/.ssh/my_key" ...


http://www.howtogeek.com/135533/how-to-use-rsync-to-backup-your-data-on-linux/?PageSpeed=noscript

https://www.marksanborn.net/howto/use-rsync-for-daily-weekly-and-full-monthly-backups/


Sample ssh config file
----------------------

.. code-block:: bash

    $ vim  ~/.ssh/config

    Host <alias-host-name>
        HostName <IP>
        User <username>
        IdentityFile ~/.ssh/<host>_key

    Host gb
        HostName github.com
        User omidraha
        IdentityFile ~/.ssh/github_key


.. code-block:: bash

    $ ssh gb

Compress directory
------------------


.. code-block:: bash

    $ tar -zcvf archive-name.tar.gz directory-name

Where:

    -z : Compress archive using gzip program

    -c: Create archive

    -v: Verbose i.e display progress while creating archive

    -f: Archive File name

http://www.cyberciti.biz/faq/how-do-i-compress-a-whole-linux-or-unix-directory/


How to add path of a program to $PATH environment variable?
-----------------------------------------------------------

Edit ``.bashrc`` in your home directory and add the following line:

.. code-block:: bash

    $ vim ~/.bashrc
        export PATH="/path/to/dir:$PATH"
    $ source ~/.bashrc

Could not open a connection to your authentication agent
--------------------------------------------------------

.. code-block:: bash

    $ eval `ssh-agent -s`

http://stackoverflow.com/a/17848593

How do I make `ls` show file sizes in megabytes?
------------------------------------------------


.. code-block:: bash

    $ ls -l --block-size=M
    $ ls -lh

http://unix.stackexchange.com/a/64150

How to check one file exist on specific path ?
----------------------------------------------

.. code-block:: bash

    #!/usr/bin/env bash
    if test -f /path/to/some/file; then
      echo "File exist"
    fi

Or to check file dose not exist:

.. code-block:: bash

    #!/usr/bin/env bash
    if test ! -f /path/to/some/file; then
      echo "File not exist"
    fi


what does echo $$, $? $# mean ?
-------------------------------

.. code-block:: bash

    $ echo $$, $$, $#, $*

$$ is the PID of the current process.

$? is the return code of the last executed command.

$# is the number of arguments in $*

$* is the list of arguments passed to the current process

http://www.unix.com/shell-programming-and-scripting/75297-what-does-echo-mean.html

Make ZSH the default shell
--------------------------

.. code-block:: bash

    chsh -s $(which zsh)

ulimit
------

The ulimit and sysctl programs allow to limit system-wide resource use.

This can help a lot in system administration,
e.g. when a user starts too many processes and therefore makes the system unresponsive for other users.

.. code-block:: bash

    $ ulimit -a
        core file size          (blocks, -c) 0
        data seg size           (kbytes, -d) unlimited
        scheduling priority             (-e) 0
        file size               (blocks, -f) unlimited
        pending signals                 (-i) 63619
        max locked memory       (kbytes, -l) 64
        max memory size         (kbytes, -m) unlimited
        open files                      (-n) 65536
        pipe size            (512 bytes, -p) 8
        POSIX message queues     (bytes, -q) 819200
        real-time priority              (-r) 0
        stack size              (kbytes, -s) 8192
        cpu time               (seconds, -t) unlimited
        max user processes              (-u) 63619
        virtual memory          (kbytes, -v) unlimited
        file locks                      (-x) unlimited

.. code-block:: bash

    $ sudo sysctl -a

www.linuxhowtos.org/Tips and Tricks/ulimit.htm


locate
------

.. code-block:: bash

    $ sudo apt-get install mlocate
    $ updatedb
    $ locate some-resource-name


Posting Form Data with cURL
---------------------------

Start your cURL command with curl -X POST and then add -F for every field=value
you want to add to the POST:

.. code-block:: bash

    $ curl -X POST -F 'username=or' -F 'password=pass' http://domain.tld/post

Diff
----

Eskil is a graphical tool to view the differences between files and directories

http://eskil.tcl.tk/index.html/doc/trunk/htdocs/download.html

Telegram
--------

Telegramm-cli

resolve_username tabletennis

https://github.com/luckydonald/pytg/issues/64


Convert Socks into an HTTP proxy
--------------------------------

By using `pproxy`:

.. code-block:: bash

    $ sudo pip install pproxy[accelerated
    $ pproxy -l http://0.0.0.0:8118 -r socks5://0.0.0.0:9150 -vvvvv


By using `polipo` (Deprecated):

.. code-block:: bash

    $ sudo apt-get install polipo
    $ sudo service polipo stop

    $ sudo vim  /etc/polipo/config

        logSyslog = true
        logFile = /var/log/polipo/polipo.log

        # HTTP Proxy
        proxyAddress = "0.0.0.0"
        proxyPort=8080

        # Socks Proxy
        socksParentProxy = "127.0.0.1:9150"
        socksProxyType = socks5

        chunkHighMark = 50331648
        objectHighMark = 16384

        serverMaxSlots = 64
        serverSlots = 16
        serverSlots1 = 32

    $ sudo service polipo restart

How to use sshuttle
-------------------

.. code-block:: bash

        $ sshuttle -r username@sshserver 0.0.0.0/0

http://sshuttle.readthedocs.io/en/stable/usage.html#usage


locale.Error: unsupported locale setting
----------------------------------------

.. code-block:: bash

        $ export LC_ALL="en_US.UTF-8"
        $ export LC_CTYPE="en_US.UTF-8"
        $ sudo dpkg-reconfigure locales

https://stackoverflow.com/a/36257050

Shadowsocks
-----------

.. code-block:: bash

        $ sudo pip install shadowsocks
        $ sudo ssserver -c ~/ws/shadowproxy.json --user nobody -d start


https://github.com/shadowsocks/shadowsocks

https://xuri.me/2014/08/14/shadowsocks-setup-guide.html

V2ray
-----

.. code-block:: bash

    $ sudo bash <(curl -L https://raw.githubusercontent.com/v2fly/fhs-install-v2ray/master/install-release.sh)

    $ v2ray  run --config=s.json
    $ v2ray  run --config=c.json

Capture and recording screen
----------------------------


.. code-block:: bash

        $ sudo apt-get install byzanz
        $ byzanz-record -d 60 record.gif


Inotify Watches Limit
---------------------

.. code-block:: bash

     $ vim /etc/sysctl.conf
        fs.inotify.max_user_watches = 524288

    $ sudo sysctl -p --system

https://confluence.jetbrains.com/display/IDEADEV/Inotify+Watches+Limit


Monitor multiple remote log files with MultiTail
------------------------------------------------

.. code-block:: bash

    $ sudo apt-get install multitail

    # example for two log-files
    $ multitail log-file_a log-file_b

    # example for two log-files and two columns
    $ multitail -s 2 log-file_a log-file_a

    # example for two log-files and different colors
    $ multitail -ci green log-file_a -ci yellow -I log-file_a

    # example for one log file on remote
    $ multitail -l "ssh -t <user>@<host> tail -f log-file"

    # example for two log files on remote
    $ multitail -l "ssh -l <user>@<host> tail -f log-file_a" -l "ssh -l <user>@<host> tail -f log-file_b"




Register GPG key by curl instead of dirmngr
--------------------------------------------


.. code-block:: bash

    $ sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys D6BC243565B2087BC3F897C9277A7293F59E4889

Error traceback:

.. code-block:: bash

    Executing: /tmp/apt-key-gpghome.voccUPwlky/gpg.1.sh --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys D6BC243565B2087BC3F897C9277A7293F59E4889
    gpg: connecting dirmngr at '/run/user/0/gnupg/d.k4bafrtss9g1d86f8y5rxb8h/S.dirmngr' failed: IPC connect call failed
    gpg: keyserver receive failed: No dirmngr



Note that add `0x` prefix before the `5523BAEEB01FA116` key

.. code-block:: bash

    $ curl -sL "http://keyserver.ubuntu.com/pks/lookup?op=get&search=0x5523BAEEB01FA116" | sudo apt-key add




Install fonts
-------------


.. code-block:: bash

    $ sudo apt-get install font-manager

.. code-block:: bash

    $ font-manager


Install tzdata noninteractive
-----------------------------


.. code-block:: bash


    $ apt-get install -y tzdata
    $ ln -fs /usr/share/zoneinfo/Asia/Tehran /etc/localtime
    $ dpkg-reconfigure --frontend noninteractive tzdata

If you are fine with UTC:


.. code-block:: bash


    $ DEBIAN_FRONTEND=noninteractive apt-get install -y tzdata



Inotify Watches Limit
----------------------

Error:

External file changes sync slow: The current inotify limit is too low

Fixed:

.. code-block:: bash

    $ vim /etc/sysctl.conf

        fs.inotify.max_user_watches = 524288

    $ sudo sysctl -p --system

https://confluence.jetbrains.com/display/IDEADEV/Inotify+Watches+Limit


The following packages will be upgraded
---------------------------------------

The following packages will be upgraded:
  grub-efi-amd64-bin grub-efi-amd64-signed shim-signed

.. code-block:: bash

    $ sudo apt-get install <list of packages kept back>
    $ sudo apt-get update ; sudo apt-get dist-upgrade


Restart
-------

.. code-block:: bash

    journalctl --list-boots
    grep reboot /home/*/.bash_history
    grep reboot /root/.bash_history
    history | grep -i reboot
    history | grep -i init
    last reboot



Install jq
----------

.. code-block:: bash

    sudo apt-get install jq

PDF OCR to PDF
--------------

.. code-block:: bash

    docker pull jbarlow83/ocrmypdf-alpine

    docker tag jbarlow83/ocrmypdf-alpine ocrmypdf

    docker run --rm -i ocrmypdf  --skip-text - -  <in.pdf >out.pdf

Using a Bastion Host with SSH Agent Forwarding
-----------------------------------------------

Introduction
++++++++++++

A Bastion Host (or Jump Server) is an intermediate server used to securely access other servers within a private network.

This method enhances network security by centralizing and controlling access to internal servers.

Steps to Use a Bastion Host with SSH Agent Forwarding
+++++++++++++++++++++++++++++++++++++++++++++++++++++

1. **Setup SSH Agent on Local Machine:**
   Start the SSH agent and add your private key.

.. code-block:: bash

   eval $(ssh-agent)
   ssh-add /path/to/your/private/key

2. **Connect to Bastion Host with Agent Forwarding:**
   Use the `-A` option to enable SSH Agent Forwarding when connecting to the Bastion Host.

.. code-block:: bash

   ssh -A user@bastion-host

3. **SSH from Bastion Host to Destination Server:**
   From the Bastion Host, SSH into the destination server.

.. code-block:: bash

   ssh user@destination-server
   ```

Configuration Requirements
++++++++++++++++++++++++++

Ensure the SSH configuration on the Bastion Host allows agent forwarding. The `/etc/ssh/sshd_config` file should include:

.. code-block:: bash

    AllowAgentForwarding yes


Benefits
+++++++

- **Enhanced Security:** Limits direct access to internal servers.
- **Centralized Control:** Provides a single point for monitoring and managing access.
- **Key Management:** Keeps the private key on the local machine, reducing exposure.

Using a Bastion Host with SSH Agent Forwarding improves security and management of server access within a network.
