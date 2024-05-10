Network
=======


Watch network connections
-------------------------

.. code-block:: bash

    $ watch ss -tp

Established connections
-----------------------

.. code-block:: bash

    $ netstat lsof -i

Tcp connections
---------------

.. code-block:: bash

    $ netstat -ant   # -anu=udp

Connections with PIDs
---------------------

.. code-block:: bash

    $ netstat -tulpn

List of listening ports
-----------------------

.. code-block:: bash

    $ netstat -uanp



Capture packets
---------------

.. code-block:: bash

    $ sudo apt-get install tcpdump
    $ sudo tcpdump -i wlan0  src port 80 or dst port 80
    $ sudo apt-get install tshark
    $ tshark -i any

http://jvns.ca/blog/2016/03/16/tcpdump-is-amazing/


Change the default gateway
--------------------------

.. code-block:: bash

    $ sudo route del default
    $ sudo route add default gw 192.168.1.115

Or:

.. code-block:: bash

    $ vim /etc/network/interfaces

    auto eth0
    iface eth0 inet static
    address 192.168.1.119
    netmask 255.255.255.0
    broadcast 192.168.1.255
    gateway 192.168.1.115
    dns-nameservers 8.8.8.8 8.8.4.4

Set a static IP
---------------

.. code-block:: bash

    $ vim /etc/network/interfaces

    allow-hotplug eth0
    iface eth0 inet static
    address 192.168.1.119
    netmask 255.255.255.0
    broadcast 192.168.1.255
    gateway 192.168.1.115
    dns-nameservers 8.8.8.8 8.8.4.4



How do I install dig?
---------------------

.. code-block:: bash

    $ sudo apt-get istall dnsutils

Monitor bandwidth usage per process
-----------------------------------

.. code-block:: bash

    $ sudo apt-get install nethogs
    $ nethogs -a

.. code-block:: bash

    $ sudo apt-get install iptraf
    $ sudo iptraf-ng

.. code-block:: bash

    $ watch -n1 netstat -tunap

https://askubuntu.com/questions/532424/how-to-monitor-bandwidth-usage-per-process


Show your gateway
-----------------

.. code-block:: bash

    $ route -ne

Disable IP6
-----------

.. code-block:: bash

    $ sudo vim /etc/sysctl.conf
        net.ipv6.conf.all.disable_ipv6 = 1
        net.ipv6.conf.default.disable_ipv6 = 1
        net.ipv6.conf.lo.disable_ipv6 = 1
    $ sudo sysctl -p


Number of open connections per ip
---------------------------------


.. code-block:: bash

    $ netstat -ntu | awk -F"[ :]+" 'NR>2{print $6}'|sort|uniq -c|sort -nr

Specific port:

.. code-block:: bash

    $ netstat -ntu | grep ":80\|:443" | awk -F"[ :]+" '{print $6}'|sort|uniq -c|sort -nr

Or:

.. code-block:: bash

    netstat -na | grep ":443\|:80" | grep -v LISTEN | awk '{print $5}' | cut -d: -f1 | sort | uniq -c | sort -rn | head


Output:

.. code-block:: bash

     14 23.43.29.1
     12 76.55.52.34
      4 8.3.2.34
      1 192.163.2.42
      1 172.53.43.87

Connections types:
------------------

.. code-block:: bash

    $ netstat -ant | awk 'NR>1{print $6}' | sort | uniq -c | sort -rn

Output:

.. code-block:: bash

     93 ESTABLISHED
     15 TIME_WAIT
     15 LISTEN
      1 SYN_SENT
      1 Foreign
      1 CLOSE_WAIT


Port forwarding
---------------

Forward all TCP/UDP from local host port 80 to the remote server at port 80

.. code-block:: bash

    sudo socat -dd TCP4-LISTEN:80,fork,reuseaddr TCP4:1.2.3.4:80 &
    sudo socat -dd UDP-LISTEN:80,fork,reuseaddr UDP:1.2.3.4:80 &



Open port
----------

.. code-block:: bash

    $ ufw allow 80
    $ ufw allow 80/udp

Show MAC address
----------------

.. code-block:: bash

    ip addr show | grep link/ether
    ifconfig -a  | grep ether


External localhost
------------------

https://ngrok.com/docs

https://serveo.net/

http://localhost.run/

https://www.btunnel.in/
