Tips
====

Haproxy with docker
-------------------

.. code-block:: bash

    $ docker pull haproxy
    $ docker run --rm -p 80:80 -p 9000:9000
        -v ~/workspace/haproxy/haproxy.cfg:/usr/local/etc/haproxy/haproxy.cfg:ro haproxy


haproxy.cfg

.. code-block:: bash

    global
            maxconn 1024
            daemon

    defaults
            log     global
            mode    http
            option  httplog
            option  dontlognull
            timeout connect 5000
            timeout client  50000
            timeout server  50000


    frontend www-http
        bind *:80
        default_backend web


    backend web
       mode http
       balance roundrobin
       option forwardfor
       http-request set-header X-Forwarded-Port %[dst_port]
       http-request add-header X-Forwarded-Proto https if { ssl_fc }
       option httpchk HEAD / HTTP/1.1\r\nHost:localhost
       server web_01 192.168.1.119:8000 check
       server web_02 192.168.1.119:8001 check


    listen status
    bind *:9000
        stats enable
        stats uri /
        stats hide-version
        stats auth admin:admin

The log directive mentions a syslog server to which log messages will be sent.
On Ubuntu rsyslog is already installed and running but it doesn't listen on any IP address.
We'll modify the config files of rsyslog later.

The maxconn directive specifies the number of concurrent connections on the frontend.
The default value is 2000 and should be tuned according to your VPS' configuration

The connect option specifies the maximum time to wait for a connection attempt to a VPS to succeed.

The client and server timeouts apply when the client or server is expected to acknowledge or
send data during the TCP process.
HAProxy recommends setting the client and server timeouts to the same value.

The retries directive sets the number of retries to perform on a VPS after a connection failure.

The option redispatch enables session redistribution in case of connection failures.
So session stickness is overriden if a VPS goes down.

timeout http-request

Is the time from the first client byte received,
until last byte sent to the client (regardless of keep alive).
So if your backend is too slow or the client is sending his request too slow,
the whole communication might take longer than this,
and the request is dropped (and a timeout sent to the client).

timeout http-keep-alive

The time to keep a connection open between haproxy and the client
(after the client response is sent out). This has nothing to do with the backend response time.
This has nothing to do with the length of a single request (i.e. http-request timeout).
This allows faster responses if the user requests multiple ressources (i.e. html, img, and js).
With keep alive the single requests can make use of the same tcp connection.
This way the load time for a full webpage is reduced.


timeout server

This is the timeout for your backend servers. When reached,
haproxy replies with 504 (gateway timeout).
This also has nothing to do with keep alive,
as it is only about the connection between proxy and backend.

https://cbonte.github.io/haproxy-dconv/configuration-1.5.html

https://www.digitalocean.com/community/tutorials/how-to-use-haproxy-to-set-up-http-load-balancing-on-an-ubuntu-vps

https://cbonte.github.io/haproxy-dconv/configuration-1.5.html#max-spread-checks

http://serverfault.com/questions/647060/haproxy-timeout-http-request-vs-timeout-http-keep-alive-vs-timeout-server

http://www.slideshare.net/haproxytech/haproxy-best-practice

http://stackoverflow.com/questions/8750518/difference-between-global-maxconn-and-server-maxconn-haproxy

http://stackoverflow.com/questions/28162452/how-to-make-ha-proxy-keepalive

http://killtheradio.net/technology/haproxys-keep-alive-functionality-and-how-it-can-speed-up-your-site/

https://github.com/postrank-labs/goliath/wiki/HAProxy

https://www.digitalocean.com/community/tutorials/an-introduction-to-haproxy-and-load-balancing-concepts

http://neo4j.com/docs/stable/ha-haproxy.html

https://serversforhackers.com/load-balancing-with-haproxy

https://www.datadoghq.com/blog/monitoring-haproxy-performance-metrics/

On multi-core systems, this setup however can cause problems,
as HAproxy is single-threaded - especially on virtual servers like Amazon EC2 and others
that give their users many low-power CPU cores that performance per core do not increases -
when you buy faster instance you actually get more cores -
and in case of Amazon, this is fixed value of 3.25 ECU per core (for m3 instances).

This of course causes that HAproxy will have similar performance
no matter how big instance is selected. Since version 1.5-dev13 HAproxy offers
to split processes and map them to CPU cores.
There are 2 options that need to be set: nbproc and cpu-map. To be accurate,
nbproc is not new option, it was in 1.4 as well,
but now you have control over it which core is doing what.


Here is example of simple configuration for system with 4 cores:

    global
        nbproc 4
        cpu-map 1 0
        cpu-map 2 1
        cpu-map 3 2
        cpu-map 4 3

First number is process starting with “1” and second is CPU core starting with “0”.
Above setup will cause haproxy to spread load on all 4 cores equally. But this is just a beginning.
You can dedicate only some cores to perform specified operations,
for example, for HTTP traffic you would use only 1 dedicated core while 3 other cores can do HTTPS,
just add bind-process directive:

    frontend access_http
       bind 0.0.0.0:80
       bind-process 1
    frontend access_https
       bind 0.0.0.0:443 ssl crt /etc/yourdomain.pem
       bind-process 2 3 4

You can even separate CPU cores for backend processing.

http://blog.onefellow.com/post/82478335338/haproxy-mapping-process-to-cpu-core-for-maximum

http://cbonte.github.io/haproxy-dconv/configuration-1.6.html?keyword=nbproc#nbproc

http://cbonte.github.io/haproxy-dconv/configuration-1.6.html?keyword=nbproc#cpu-map

Dynamic Backend
---------------

https://news.ycombinator.com/item?id=5222209

https://github.com/PearsonEducation/thalassa-aqueduct

Hot reconfiguration
+++++++++++++++++++

http://www.haproxy.org/download/1.2/doc/haproxy-en.txt

http://comments.gmane.org/gmane.comp.web.haproxy/10565

https://github.com/thisismitch/doproxy

http://alex.cloudware.it/2011/10/simple-auto-scale-with-haproxy.html

https://www.digitalocean.com/community/tutorials/how-to-automate-the-scaling-of-your-web-application-on-digitalocean

https://tech.shareaholic.com/2012/10/26/haproxy-a-substitute-for-amazon-elb/

http://michalf.me/blog:haproxy
