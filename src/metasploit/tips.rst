Tips
====

Configure db
------------

.. code-block:: bash

    # service postgresql start

    # adduser msf
    # passwd msf

    root@local:/# su - postgres

    postgres@local:~$ psql

    postgres=# CREATE DATABASE msf;
    postgres=# CREATE USER msf WITH PASSWORD 'msf';
    postgres=# GRANT ALL PRIVILEGES ON DATABASE msf to msf;

    root@local:/# msfconsole

    msf > db_status
    [*] postgresql selected, no connection

    $ db_connect msf:msf@127.0.0.1:5432/msf
    [*] Rebuilding the module cache in the background...

    msf > db_status
    [*] postgresql connected to msf

    msf > search ftp

    msf > db_rebuild_cache
    [*] Purging and rebuilding the module cache in the background...
    msf >


SSH Username Enumeration
------------------------

.. code-block:: bash

    msf > use auxiliary/scanner/ssh/ssh_enumusers

    msf auxiliary(ssh_enumusers) > set RHOSTS 127.0.0.1
    RHOSTS => 127.0.0.1

    msf auxiliary(ssh_enumusers) > set USER_FILE /home/msf/user_list
    USER_FILE => /home/msf/user_list

    msf auxiliary(ssh_enumusers) > run

    [*] 127.0.0.1:22 - SSH - Checking for false positives
    [*] 127.0.0.1:22 - SSH - Starting scan
    [+] 127.0.0.1:22 - SSH - User 'root' found
    [+] 127.0.0.1:22 - SSH - User 'admin' found
    [!] 127.0.0.1:22 - SSH - User 'administrator' not found
    [*] Scanned 1 of 1 hosts (100% complete)
    [*] Auxiliary module execution completed


Anonymous FTP Access Detection
------------------------------

.. code-block:: bash

    msf > use auxiliary/scanner/ftp/anonymous

    msf auxiliary(anonymous) > set RHOSTS 127.0.0.1

    msf auxiliary(anonymous) > run

    [+] 127.0.0.1:21 - Anonymous READ (220 (vsFTPd 2.2.2))
    220 Ready)
    [*] Scanned 1 of 1 hosts (100% complete)
    [*] Auxiliary module execution completed


FTP Version Scanner
-------------------

.. code-block:: bash

    msf > use auxiliary/scanner/ftp/ftp_version


SMTP User Enumeration Utility
-----------------------------

.. code-block:: bash

    msf > use auxiliary/scanner/smtp/smtp_enum

    msf auxiliary(smtp_enum) > set RHOSTS 127.0.0.1
    msf auxiliary(smtp_enum) >  run

    [*] 127.0.0.1 could not be enumerated (no EXPN, no VRFY, invalid RCPT)
    [*] Scanned 1 of 1 hosts (100% complete)
    [*] Auxiliary module execution completed

    msf auxiliary(smtp_enum) > set RHOSTS 127.0.0.2
    msf auxiliary(smtp_enum) >  run

    [+] 127.0.0.2:25 Users found: , postmaster
    [*] Scanned 1 of 1 hosts (100% complete)
    [*] Auxiliary module execution completed


SMTP Open Relay Detection
-------------------------

.. code-block:: bash

    msf > use auxiliary/scanner/smtp/smtp_relay


SMTP Banner Grabber
-------------------

.. code-block:: bash

    msf > use auxiliary/scanner/smtp/smtp_version


MS03-026 Microsoft RPC DCOM Interface Overflow
----------------------------------------------

.. code-block:: bash

    msf > use exploit/windows/dcerpc/ms03_026_dcom


This module exploits a stack buffer overflow in the RPCSS service,

this vulnerability was originally found by the Last Stage of Delirium research group and has been widely exploited ever since.

This module can exploit the English versions of Windows NT 4.0 SP3-6a, Windows 2000, Windows XP, and Windows 2003 all in one request :)

http://www.rapid7.com/db/modules/exploit/windows/dcerpc/ms03_026_dcom

https://community.rapid7.com/community/metasploit/blog/2013/03/12/exploit-popularity-contest


Docker file
-----------

# install setup tools
curl https://bitbucket.org/pypa/setuptools/raw/bootstrap/ez_setup.py | python -
# install pip
curl -L https://raw.github.com/pypa/pip/master/contrib/get-pip.py | python -
# install  python-dev
aptitude install python-dev gcc
