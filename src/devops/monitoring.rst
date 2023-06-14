Monitoring Tools
================


New Relic
---------

Configure ``newrelic`` with ``Gunicorn``:


.. code-block:: bash

    $ pip install newrelic
    $ newrelic-admin generate-config <LICENSE_KEY> newrelic.ini
    $ NEW_RELIC_CONFIG_FILE=newrelic.ini newrelic-admin run-program
        gunicorn -b 0.0.0.0:8000 -w ${GUNICORN_WORKERS:-3} pomegranate.wsgi:application



Install New Relic server on Debian/ubuntu

.. code-block:: bash

    $ echo deb http://apt.newrelic.com/debian/ newrelic non-free >> /etc/apt/sources.list.d/newrelic.list
    $ wget -O- https://download.newrelic.com/548C16BF.gpg | apt-key add -
    $ apt-get update
    $ apt-get install newrelic-sysmond
    $ nrsysmond-config --set license_key=<LICENSE_KEY>
    $ /etc/init.d/newrelic-sysmond start
    # To uninstall
    $ apt-get remove newrelic-sysmond

https://docs.newrelic.com/docs/servers/new-relic-servers-linux/installation-configuration/servers-installation-ubuntu-debian

Enabling New Relic Servers for Docker

.. code-block:: bash

    $ groupadd -r docker
    $ usermod -a -G docker newrelic

https://docs.newrelic.com/docs/servers/new-relic-servers-linux/installation-configuration/enabling-new-relic-servers-docker

https://docs.newrelic.com/docs/servers/new-relic-servers-linux/getting-started/new-relic-servers-docker

What is bam.nr-data.net
-----------------------

This is for RUM injections for our Browser monitoring product.

http://newrelic.com/browser-monitoring

https://discuss.newrelic.com/t/what-is-bam-nr-data-net/13848/2

https://docs.newrelic.com/docs/browser/new-relic-browser/page-load-timing-resources/page-load-timing-process

https://docs.newrelic.com/docs/new-relic-browser/instrumentation-for-page-load-timing

https://docs.newrelic.com/docs/browser/new-relic-browser/performance-quality/security-new-relic-browser


How to install Nginx New Relic plugin
-------------------------------------

http://nginx.org/en/linux_packages.html

http://newrelic.com/plugins/nginx-inc/13

http://haydenjames.io/using-new-relic-monitor-nginx-heres/

https://www.scalescale.com/tips/nginx/nginx-new-relic-plugin/

.. code-block:: bash

    $ wget http://nginx.org/keys/nginx_signing.key
    $ sudo apt-key add nginx_signing.key
    $ sudo vim /etc/apt/sources.list
        # Debian
        deb http://nginx.org/packages/debian/ jessie nginx
        deb-src http://nginx.org/packages/debian/ jessie nginx
        # Ubuntu
        deb http://nginx.org/packages/ubuntu/ trusty nginx
        deb-src http://nginx.org/packages/ubuntu/ trusty nginx


    $ sudo apt-get update
    $ sudo apt-get install nginx-nr-agent

Output:

.. code-block:: bash

    Thanks for using NGINX!

    NGINX agent for New Relic is installed. Configuration file is:
    /etc/nginx-nr-agent/nginx-nr-agent.ini

    Documentation and configuration examples are available here:
    /usr/share/doc/nginx-nr-agent/README.txt

    Please use "service nginx-nr-agent" to control the agent daemon.

    More information about NGINX products is available on:
    * https://www.nginx.com/


.. code-block:: bash

    $ sudo vim /etc/nginx-nr-agent/nginx-nr-agent.ini
    # update LICENCE KEY and [source] section

.. code-block:: bash

    $ sudo vim nginx.conf

        # Server status
        location = /status {
            stub_status on;
            allow 127.0.0.1;
            allow 172.17.0.0/16;
            deny all;
        }


Testing the New Relic Nginx plugin

    The best way to check if this is working is to tail the logs:

.. code-block:: bash

    $ tail -f /var/log/nginx-nr-agent.log



Real-time web log analyzer and interactive viewer
-------------------------------------------------


.. code-block:: bash

    $ goaccess -f nginx.log

    $ goaccess -f nginx.log --log-format="%h %^[%d:%^] \"%r\" %s %b \"%R\" \"%u\"" --date-format="%d/%b/%Y" --time-format="%T" -a > report.html


https://github.com/allinurl/goaccess

