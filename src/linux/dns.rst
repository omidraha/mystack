DNS.
====


Transparent DNS proxies
+++++++++++++++++++++++

Some ISP's are now using a technology called 'Transparent DNS proxy'.

Using this technology, they will intercept all DNS lookup requests (TCP/UDP port 53) and transparently proxy the results.

This effectively forces you to use their DNS service for all DNS lookups.

If you have changed your DNS settings to use an 'open' DNS service such as Google, Comodo or OpenDNS,

expecting that your DNS traffic is no longer being sent to your ISP's DNS server,

you may be surprised to find out that they are using transparent DNS proxying.


DNSCrypt
++++++++

DNSCrypt encrypts and authenticates DNS traffic between user and DNS resolver.

While IP traffic itself is unchanged, it prevents local spoofing of DNS queries,

ensuring DNS responses are sent by the server of choice.

https://wiki.archlinux.org/index.php/DNSCrypt

dnscrypt-proxy
++++++++++++++

DNSCrypt is a protocol for securing communications between a client and a DNS resolver,

preventing spying, spoofing or man-in-the-middle attacks.

To use it, you'll need a tool called dnscrypt-proxy,

which "can be used directly as your local resolver or as a DNS forwarder,

authenticating requests using the DNSCrypt protocol and passing them to an upstream server".

.. code-block:: bash

    $ apt-get install dnscrypt-proxy

    # vim /lib/systemd/system/dnscrypt-proxy.socket
    [Socket]
    ListenStream=
    ListenDatagram=
    ListenStream=127.0.0.1:40
    ListenDatagram=127.0.0.1:40

    # vim /etc/resolv.conf
    nameserver 127.0.0.1

    # no needs to run manually, currently run with system and listen on `127.0.0.1:40`
    ## dnscrypt-proxy  -R adamas  --local-address=127.0.0.1:40

    # systemctl status dnscrypt-proxy

.. code-block:: bash

    $ sudo vim /etc/default/dnscrypt-proxy
        # https://github.com/dyne/dnscrypt-proxy/blob/master/dnscrypt-resolvers.csv
        DNSCRYPT_PROXY_RESOLVER_NAME=4armed # cisco, cs-cfi, cloudns-syd ..

The ``dnsmasq`` can be used as both ``dhchp`` and ``dns server``.

Here we configure it to use with ``dnscrypt-proxy``

.. code-block:: bash

    $ dnsmasq
    # vim /etc/dnsmasq.conf
    no-resolv
    server=127.0.0.1#40
    listen-address=127.0.0.1
    # systemctl restart  dnsmasq

Now you can see all your dns query is secured with type ``quic`` on the filter box of ``wireshark``

And view related listening port:

.. code-block:: bash

    # netstat -uanp
    Active Internet connections (servers and established)
    Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
    udp        0      0 127.0.0.1:40            0.0.0.0:*                           1/init
    udp        0      0 0.0.0.0:53              0.0.0.0:*                           3089/dnsmasq
    udp        0      0 0.0.0.0:68              0.0.0.0:*                           2000/dhclient
    udp        0      0 0.0.0.0:68              0.0.0.0:*                           2221/dhclient
    udp        0      0 0.0.0.0:33908           0.0.0.0:*                           853/dnscrypt-proxy
    udp6       0      0 :::53                   :::*                                3089/dnsmasq


https://github.com/jedisct1/dnscrypt-proxy

https://wiki.archlinux.org/index.php/DNSCrypt

resolveconf
+++++++++++


.. code-block:: bash

    $ /etc/resolv.conf


Normally the resolvconf program is run only by network interface configuration programs such as ifup(8),

ifdown, NetworkManager(8), dhclient(8), and pppd(8); and  by  local  nameservers such as dnsmasq(8).

These programs obtain nameserver information from some source and push it to resolvconf.

.. code-block:: bash

    $ resolvconf
    $ /etc/network/interface



https://wiki.debian.org/HowTo/dnsmasq

https://sfxpt.wordpress.com/2011/02/06/providing-dhcp-and-dns-services-with-dnsmasq/



dnssec-trigger and unbound
++++++++++++++++++++++++++


.. code-block:: bash

    # apt-get inastall dnssec-trigger
    # apt-get inastall unbound



How do install dig?
+++++++++++++++++++


.. code-block:: bash

    $ sudo apt-get install dnsutils


http://askubuntu.com/a/25100/237607


Disable builtin dnsmasq on the network manager
++++++++++++++++++++++++++++++++++++++++++++++

.. code-block:: bash

    $ pstree -sp $(pidof dnsmasq)
    $ lsof -i :53
    $ netstat -uanp

.. code-block:: bash

    $ sudo vim /etc/NetworkManager/NetworkManager.conf

        [main]
        plugins=ifupdown,keyfile,ofono
        # dns=dnsmasq


    $ sudo service network-manager restart
    $ sudo service networking restart
    $ killall -9 dnsmasq

https://unix.stackexchange.com/a/304129


Deploying a DNS Server using Docker
+++++++++++++++++++++++++++++++++++

http://www.damagehead.com/blog/2015/04/28/deploying-a-dns-server-using-docker/




.. code-block:: bash

    $ docker run --name bind -it --rm \
        --publish 53:53/tcp --publish 53:53/udp --publish 10000:10000/tcp \
        --volume /srv/docker/bind:/data \
        sameersbn/bind:9.9.5-20170129


We create the forward zone example.com by selecting Create master zone
and in the Create new zone dialog set the Zone type to Forward,
the Domain Name to example.com, the Master server to ns.example.com
and set Email address to the domain administrator’s email address and select Create.
Next, create the DNS entry for ns.example.com pointing to 172.17.42.1 and apply the configuration


To complete this tutorial we will create a address (A) entry for webserver.example.com
and then add a domain name alias (CNAME) entry www.example.com which will point to webserver.example.com.

To create the A entry, select the zone example.com and then select the Address option.
Set the Name to webserver and the Address to 192.168.1.1. To create the CNAME entry,
select the zone example.com and then select the Name Alias option.
Set the Name to www and the Real Name to webserver and apply the configuration.

And now, the moment of truth

.. code-block:: bash

    $ host webserver.example.com 192.168.1.10
    $ host www.example.com 192.168.1.10

The `192.168.1.10` is address of dns server( local host machine)



Resolve all domain name to specific IP
--------------------------------------


.. code-block:: bash

    $ sudo vim /etc/hosts
        127.0.0.1 example.com
        127.0.0.1 www.example.com

.. code-block:: bash

    $ sudo apt-get install dnsmasq

    $ sudo vim  /etc/dnsmasq.conf
        conf-dir=/etc/dnsmasq.d/,*.conf

.. code-block:: bash

    $ sudo vim /etc/dnsmasq.d/demo.conf
        no-dhcp-interface=wlp3s0
        bogus-priv
        address=/#/192.168.1.10

.. code-block:: bash

    $ sudo systemctl restart dnsmasq

The `192.168.1.10` is address of dns server( local host machine)
