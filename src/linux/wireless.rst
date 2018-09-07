Wireless
========


unifi
+++++

By package:

.. code-block:: bash

    $ sudo dpkg -i unifi_sysvinit_all.deb
    $ sudo service mongodb restart
    $ sudo systemctl restart unifi


https://www.ubnt.com/download/unifi/unifi-ap-ac-lr/default/unifi-5723-controller-debianubuntu-linux


By Docker:

.. code-block:: bash



.. code-block:: bash

    $ docker run --rm --init -p 8080:8080 -p 8443:8443 -p 3478:3478/udp -p 10001:10001/udp -e TZ='Asia/Tehran' -v ~/ws/unifi/data:/var/lib/unifi -v ~/ws/unifi/logs:/var/log/unifi --name unifi jacobalberty/unifi:5.7.28-sc


https://github.com/jacobalberty/unifi-docker


Browse the web ui:

https://localhost:8443/


Setup DHCP and DNS server:

.. code-block:: bash

    $ sudo vim /etc/dnsmasq.conf
        conf-dir=/etc/dnsmasq.d/,*.conf

.. code-block:: bash

     $ sudo vim /etc/dnsmasq.d/demo.conf
        # no-dhcp-interface=wlp3s0
        #interface=enp2s0
        #bogus-priv
        address=/#/192.168.1.10
        dhcp-range=192.168.1.10,192.168.1.20,12h
        dhcp-lease-max=25
        dhcp-option=option:router,192.168.1.1



Setup network:

.. code-block:: bash

    # Device > Config > Network > Configure IP (using DHCP)

    Device > Config > Network > Configure IP (static IP)
        IP address:
            192.168.1.2
        Subnet mask:
            255.255.255.0
        Gateway:
            192.168.1.10


    Device > Details > Overview > IP: 192.168.1.2 # @note: The 192.168.1.2 is AP IP

    Device > Adopt >
        IP: 192.168.1.2 #  @note: The 192.168.1.2 is AP IP
        username: admin
        password: admin
        port: 22
        inform: http://192.168.1.10:8080/inform     #   @note: The 192.168.1.10, is unifi (docker on your pc) server ip

.. code-block:: bash

    or@or:~$ ifconfig
    192.168.1.10

    or@or:~$ ssh admin@192.168.1.2
    box# info
        status: connected (http://192.168.1.10:8080/inform)

.. code-block:: bash

    Settings > Site > Device > Authentication

        ssh auth:   admin admin


    Settings > Network >
                Parent Interface: LAN
                Gateway/Subnet: 192.168.1.10/24
                DHCP mode:  None

    Settings > Wireless Network > Define a new wireless




How Automatic Detection of Captive Portal works
-----------------------------------------------


Basic strategy behind Captive Portal detection

The Automatic Detection of Captive Portal mechanism is based on a simple verification,
done by the Operational System (OS) of the client device (smartphone, tablet, laptop).
It simply tries to reach a specific URL and verify that such URL returns a well-known result.

If a Captive Portal is not in place,
the result will match the expected one and the OS will know that there is full access to internet.
If the URL returns a result other than the expected one,
then the OS will detect that there is a Captive Portal in place
and that it's needed to proceed with authentication in order to get full access to internet:
in this case the OS will open the Splash Page automatically.


Differences between Client devices

All client devices use the above described strategy to find out if they are behind a captive portal,
but the URL might vary depending on the specific model of smartphone, tablet, laptop
and depending on the specific OS version. In the following you can find the list of domains
that are contacted by each model in order to detect the captive portal.

If the domain is accessible and returns "Success", the Captive Portal is not triggered automatically.
"Success" response means the device is connected to the internet.


Android Captive Portal Detection

    clients3.google.com


Apple iPhone, iPad with iOS 6 Captive Portal Detection

    gsp1.apple.com
    *.akamaitechnologies.com
    www.apple.com
    apple.com


Apple iPhone, iPad with iOS 7, 8, 9 and recent versions of OS X

    www.appleiphonecell.com

    *.apple.com

    www.itools.info

    www.ibook.info

    www.airport.us

    www.thinkdifferent.us

    *.apple.com.edgekey.net

    *.akamaiedge.net

    *.akamaitechnologies.com


Windows

    ipv6.msftncsi.com

    ipv6.msftncsi.com.edgesuite.net

    www.msftncsi.com

    www.msftncsi.com.edgesuite.net

    teredo.ipv6.microsoft.com

    teredo.ipv6.microsoft.com.nsatc.net



https://success.tanaza.com/s/article/How-Automatic-Detection-of-Captive-Portal-works
