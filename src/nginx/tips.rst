Tips
====

Install Module
--------------

.. code-block:: bash

    $ /etc/nginx/sites-enabled/redmine

Change default welcome page of nginx
------------------------------------

.. code-block:: bash

    $ vim  /usr/share/nginx/html/index.html

Nginx with Docker
-----------------

 .. code-block:: bash

    $ docker pull nginx

Expose Ports:

    ``80``

Data Directories:

    ``/etc/nginx/nginx.conf``


https://hub.docker.com/_/nginx/

serve static files
++++++++++++++++++

.. code-block:: bash

    $ docker run --name nx -p8123:80 -v /home/or/ws/dw/nginx.conf:/etc/nginx/nginx.conf:ro -v /home/or/ws/dw:/usr/share/nginx/html/:ro  -d  nginx

The `nginx.conf` file

.. code-block:: bash

    user  nginx;
    worker_processes  1;

    error_log  /var/log/nginx/error.log warn;
    pid        /var/run/nginx.pid;


    events {
        worker_connections  1024;
    }


    http {

        include       /etc/nginx/mime.types;
        default_type  application/octet-stream;

        log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                          '$status $body_bytes_sent "$http_referer" '
                          '"$http_user_agent" "$http_x_forwarded_for"';

        access_log  /var/log/nginx/access.log  main;

        sendfile        on;
        #tcp_nopush     on;

        keepalive_timeout  65;

        #gzip  on;

        include /etc/nginx/conf.d/*.conf;

        proxy_connect_timeout       600;
        proxy_send_timeout          600;
        proxy_read_timeout          600;
        send_timeout                600;
        client_max_body_size        10m;

        server {
            listen 8123;
            server_name _;
            location / {
                root /usr/share/nginx/html/;
            }
        }
    }

Nginx config file
-----------------

Nginx Full example file:

https://www.nginx.com/resources/wiki/start/topics/examples/full/


Nginx customize error pages
---------------------------

.. code-block:: bash

        server {
        ...

        # Determines whether proxied responses with codes greater than or equal to 300
        # should be passed to a client or be redirected to nginx for processing with the error_page directive
        proxy_intercept_errors on;

        # 403 error
        error_page  403  /403.html;
        location  /403.html {
        # we assumed `403.html` file is there on this root path:
        root  /absolute/path/to/errors/folder/;
        # The file is only accessible through internal Nginx redirects (not requestable directly by clients):
        internal;
        }

        # 404 error
        error_page  404  /404.html;
        location  /404.html {
        # we assumed `404.html` file is there on this root path:
        root  /absolute/path/to/errors/folder/;
        internal;
        }

        # 50x errors
        error_page 500 502 503 504 @error;

        location @error {
          add_header Cache-Control no-cache;
          # we assumed `error.html` file is there on this root path:
          root  /absolute/path/to/errors/folder/;
          rewrite ^(.*)$ /error.html break;
        }
        } # server block

http://nginx.org/en/docs/http/ngx_http_proxy_module.html#proxy_intercept_errors

http://nginx.org/en/docs/http/ngx_http_core_module.html#error_page


Nginx maintenance mode
----------------------

.. code-block:: bash

    server {
        ...

        location / {
            proxy_pass http://web_server;

            # we assumed `maintenance` file can touch or remove on this root path:
            if (-e /absolute/path/to/switch/folder/maintenance) {
              error_page 503 @maintenance;
              return 503;
            }
        }

        location @maintenance {
            add_header Cache-Control no-cache;
            root  /src/collected_static/errors/;
            rewrite ^(.*)$ /maintenance.html break;
        }
    }

.. code-block:: bash

    # to switch on to maintenance mode
    $ touch /absolute/path/to/switch/folder/maintenance
    # to switch off to maintenance mode
    $ rm /absolute/path/to/switch/folder/maintenance


https://github.com/spesnova/docker-example-nginx/blob/master/files/nginx.conf


How to restrict access to directory and sub directories
-------------------------------------------------------

.. code-block:: bash

    location /st/ {
        autoindex off;
        alias /absolute/path/to/static/folder/
    }

http://nginx.org/en/docs/http/ngx_http_autoindex_module.html

Enable Nginx Status Page
------------------------

.. code-block:: bash

    user  nginx;
    worker_processes  1;

    error_log  /var/log/nginx/error.log warn;
    pid        /var/run/nginx.pid;

    events {
    }

    http {

        include       /etc/nginx/mime.types;
        default_type  application/octet-stream;

        log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                          '$status $body_bytes_sent "$http_referer" '
                          '"$http_user_agent" "$http_x_forwarded_for"';

        server {
            server_name _;
            # Server status
            location = /status {
                stub_status on;
                allow all;
            }
        }
    }



Tuning Nginx
------------

This number should be, at maximum, the number of CPU cores on your system.
since nginx doesn't benefit from more than one worker per CPU.

.. code-block:: bash

    worker_processes auto;

The ``epoll`` is a Linux kernel system call, a scalable I/O event notification mechanism,
first introduced in Linux kernel 2.5.44.
It is meant to replace the older POSIX select and poll system calls,
to achieve better performance in more demanding applications,
where the number of watched file descriptors is large (unlike the older system calls, which operate in O(n) time,
epoll operates in O(1) time).
epoll is similar to FreeBSD's kqueue, in that it operates on a configurable kernel object,
exposed to user space as a file descriptor of its own.

We'll also set nginx to use epoll to ensure we can handle a large number of connections
optimally and direct it to accept multiple conncetions at the same time.


This option is essential for linux, optimized to serve many clients with each thread

.. code-block:: bash

        use epoll;


Number of file descriptors used for Nginx. This is set in the OS with ``ulimit -n 200000``
or using ``/etc/security/limits.conf``.

.. code-block:: bash

    worker_rlimit_nofile 200000;

Only log critical errors

.. code-block:: bash

    error_log /var/log/nginx/error.log crit

The author of nginx claims that 10,000 idle connections will use only 2.5 MB of memory,


proxy_buffering: This directive controls whether buffering for this context and child contexts is enabled.
By default, this is "on".

proxy_buffers: This directive controls the number (first argument)
and size (second argument) of buffers for proxied responses.
The default is to configure 8 buffers of a size equal to one memory page (either 4k or 8k).
Increasing the number of buffers can allow you to buffer more information.

proxy_buffer_size: The initial portion of the response from a backend server,
which contains headers, is buffered separately from the rest of the response.
This directive sets the size of the buffer for this portion of the response.
By default, this will be the same size as proxy_buffers,
but since this is used for header information, this can usually be set to a lower value.

proxy_busy_buffers_size: This directive sets the maximum size of buffers
that can be marked "client-ready" and thus busy.
While a client can only read the data from one buffer at a time,
buffers are placed in a queue to send to the client in bunches.
This directive controls the size of the buffer space allowed to be in this state.

proxy_max_temp_file_size: This is the maximum size,
per request, for a temporary file on disk.
These are created when the upstream response is too large to fit into a buffer.

proxy_temp_file_write_size: This is the amount of data Nginx will write
to the temporary file at one time when the proxied server's response is too large
for the configured buffers.

proxy_temp_path: This is the path to the area on disk where Nginx should store any
temporary files when the response from the upstream server cannot fit into the configured buffers.

As you can see, Nginx provides quite a few different directives to tweak the buffering behavior.
Most of the time, you will not have to worry about the majority of these,
but it can be useful to adjust some of these values.
Probably the most useful to adjust are the proxy_buffers and proxy_buffer_size directives.

In contrast, if you have fast clients that you want to immediately serve data to,
you can turn buffering off completely.
Nginx will actually still use buffers if the upstream is faster than the client,
but it will immediately try to flush data to the client instead of waiting for the buffer to pool.
If the client is slow,
this can cause the upstream connection to remain open until the client can catch up.
When buffering is "off" only the buffer defined by the proxy_buffer_size directive will be used


http://stackoverflow.com/questions/7325211/tuning-nginx-worker-process-to-obtain-100k-hits-per-min

https://rwebs.ca/attempt-at-optimizing-digital-ocean-install-with-loader-io/

https://blog.martinfjordvald.com/2011/04/optimizing-nginx-for-high-traffic-loads/

http://www.freshblurbs.com/blog/2015/11/28/high-load-nginx-config.html

https://blog.martinfjordvald.com/2011/04/optimizing-nginx-for-high-traffic-loads/

https://www.digitalocean.com/community/tutorials/understanding-nginx-http-proxying-load-balancing-buffering-and-caching

https://www.maxcdn.com/blog/nginx-application-performance-optimization/

https://nelsonslog.wordpress.com/2013/05/19/nginx-proxy-buffering/


worker_connections
++++++++++++++++++

Determines how many clients will be served by each worker process.
Max clients = worker_connections * worker_processes
Max clients is also limited by the number of socket connections available on the system (~64k)

.. code-block:: bash

    worker_connections 1024;


Accept as many connections as possible, after nginx gets notification about a new connection.
May flood worker_connections, if that option is set too low.


It should be kept in mind that this number includes all connections
(e.g. connections with proxied servers, among others), not only connections with clients.
Another consideration is that the actual number of simultaneous connections cannot exceed
the current limit on the maximum number of open files, which can be changed by worker_rlimit_nofile.

.. code-block:: bash

    multi_accept on;

Since we will likely have a few static assets on the file system like logos,
CSS files, Javascript, etc that are going to be commonly used across your site it's quite
a bit faster to have nginx cache these for short periods of time.
Adding this outside of the events block tells nginx to cache 1000 files for 30 seconds,
excluding any files that haven't been accessed in 20 seconds, and only files that have 5 times or more.
If you aren't deploying frequently you can safely bump up these numbers higher.

Caches information about open FDs, frequently accessed files.
Changing this setting, in my environment, brought performance up from 560k req/sec, to 904k req/sec.
I recommend using some variant of these options, though not the specific values listed below.

.. code-block:: bash

    open_file_cache max=1000 inactive=20s;
    open_file_cache_valid 30s;
    open_file_cache_min_uses 5;
    open_file_cache_errors off;

Buffer log writes to speed up IO, or disable them altogether

.. code-block:: bash

    access_log off;
    #access_log /var/log/nginx/access.log main buffer=16k;


Since we're now setup to handle lots of connections,
we should allow browsers to keep their connections open for awhile so they don't have to reconnect to as often.
This is controlled by the keepalive_timeout setting. We're also going to turn on sendfile support,
tcp_nopush, and tcp_nodelay. sendfile optimizes serving static files from the file system, like your logo.
The other two optimize nginx's use of TCP for headers and small bursts of traffic for things
like Socket IO or frequent REST calls back to your site.

Sendfile copies data between one FD and other from within the kernel.
More efficient than read() + write(), since the requires transferring data to and from the user space.

.. code-block:: bash

        sendfile on;

The Tcp_nopush causes nginx to attempt to send its HTTP response head in one packet,
instead of using partial frames. This is useful for prepending headers before calling sendfile,
or for throughput optimization.

.. code-block:: bash

    tcp_nopush on;

don't buffer data-sends (disable Nagle algorithm). Good for sending frequent small bursts of data in real time.

.. code-block:: bash

    tcp_nodelay on;

Timeout for keep-alive connections. Server will close connections after this time.

.. code-block:: bash

    keepalive_timeout 15;

# Number of requests a client can make over the keep-alive connection. This is set high for testing.

.. code-block:: bash

    keepalive_requests 100000;

Allow the server to close the connection after a client stops responding. Frees up socket-associated memory.

.. code-block:: bash

    reset_timedout_connection on;

Send the client a "request timed out" if the body is not loaded by this time. Default 60.

.. code-block:: bash

    client_body_timeout 10;

If the client stops reading data, free up the stale client connection after this much time. Default 60.

.. code-block:: bash

    send_timeout 2;


nearly every browser on earth supports receiving compressed content so we definitely want to turn that on.
These also go in the same http section as above:

Compression. Reduces the amount of data that needs to be transferred over the network

.. code-block:: bash

    gzip on;
    gzip_min_length 1000;
    gzip_types text/plain text/css text/xml text/javascript application/json application/x-javascript application/xml application/xml+rss;
    gzip_proxied expired no-cache no-store private auth;
    gzip_disable "MSIE [1-6]\.";

One of the first things that many people try to do is to enable the gzip compression module available with nginx.
The intention here is that the objects which the server sends to requesting clients will be smaller, and thus faster to send.

However this involves the trade-off common to tuning, performing the compression takes CPU resources from your server,
which frequently means that you'd be better off not enabling it at all.

Generally the best approach with compression is to only enable it for large files,
and to avoid compressing things that are unlikely
to be reduced in size (such as images, executables, and similar binary files).

With that in mind the following is a sensible configuration:

.. code-block:: bash

    gzip  on;
    gzip_vary on;
    gzip_min_length 10240;
    gzip_proxied expired no-cache no-store private auth;
    gzip_types text/plain text/css text/xml text/javascript application/x-javascript application/xml;
    gzip_disable "MSIE [1-6]\.";

This enables compression for files that are over 10k, aren't being requested
by early versions of Microsoft's Internet Explorer, and only attempts to compress text-based files.

https://tweaked.io/guide/nginx/

proxy_buffering is turned on by default with nginx, so we just need to bump up the sizes of these buffers.
The first directive, proxy_buffers, is telling nginx to create and use 8 24k buffers for the response from the proxy.
The second directive is a special smaller buffer that will just contain the HEAD information,
so it's safe to make that smaller.
So what's this do? Well when you're proxying a connection nginx is playing the middle man
between the browser and your WSGI process. As the WSGI process writes data back to to nginx,
nginx stores this in a buffer and writes out to the client browser when the buffer is full.
If we leave these at the defaults nginx provides (8 buffers of either 4 or 8K depending on system),
what ends up happening is our big 50-200K of HTML markup
is spoon fed to nginx in small 4K bites and then sent out to the browser.
This is sub-optimal for most sites. What we want to have happen is for our WSGI process to finish and
move on to the next request as fast as possible. To do this it needs nginx to slurp up all of the output quickly.
Increasing the buffer sizes to be larger than most (or all) of the markup size of your apps pages let's this happen.

.. code-block:: bash

    location / {
        proxy_buffers 8 24k;
        proxy_buffer_size 2k;
        proxy_pass http://127.0.0.1:8000;
    }

http://www.revsys.com/12days/nginx-tuning/


http://dak1n1.com/blog/12-nginx-performance-tuning/

How to Optimize NGINX to Handle 100+K Requests per Minute
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++

http://tecadmin.net/optimize-nginx-to-handle-100k-requests-per-minute/


Load testing
------------

Load and Stress Testing as “Load testing is the process of putting demand
on a system or device and measuring its response.
Stress testing refers to tests that determine the robustness of
software by testing beyond the limits of normal operation”.

https://en.wikipedia.org/wiki/Load_testing

https://en.wikipedia.org/wiki/Stress_testing_%28software%29

http://www.devcurry.com/2010/07/10-free-tools-to-loadstress-test-your.html

http://dak1n1.com/blog/14-http-load-generate/

https://luoluca.wordpress.com/2015/05/24/docker-up-distributed-load-testing-with-tsung/


JMeter
------

https://www.digitalocean.com/community/tutorials/how-to-use-jmeter-to-record-test-scenarios

https://gist.github.com/hhcordero/abd1dcaf6654cfe51d0b

http://srivaths.blogspot.com/2014/08/distrubuted-jmeter-testing-using-docker.html

https://github.com/hauptmedia/docker-jmeter

https://docs.google.com/presentation/d/1Yi5C27C3Q0AnT-uw9SRnMeEqXSKLQ8h9O9Jqo1gQiyI/edit?pref=2&pli=1#slide=id.g2a7b2c954_016

https://www.digitalocean.com/community/tutorial_series/load-testing-with-apache-jmeter

https://www.digitalocean.com/community/tutorials/how-to-use-apache-jmeter-to-perform-load-testing-on-a-web-server

Linux TCP/IP tuning for scalability
-----------------------------------

Concurrent User Connections
If your implementation is creating a large number of concurrent user connections to
backend application servers , it is important to verify that there are enough local port
numbers available for outbound connections to the backend application.
Verification of the server port range can be done
using  the following command:

.. code-block:: bash

    $ sysctl net.ipv4.ip_local_port_range

If the range needs to be increased,  that can be done using the following command:

.. code-block:: bash

    $ sudo sysctl -w net.ipv4.ip_local_port_range="1024 64000"

http://www.lognormal.com/blog/2012/09/27/linux-tcpip-tuning/

http://stackoverflow.com/questions/1575453/how-many-socket-connections-can-a-web-server-handle



What is a Reverse Proxy vs. Load Balancer?
------------------------------------------


A reverse proxy accepts a request from a client, forwards it to a server that can fulfill it,
and returns the server’s response to the client.

A load balancer distributes incoming client requests among a group of servers,
in each case returning the response from the selected server to the appropriate client.

Load Balancing
++++++++++++++

Load balancers are most commonly deployed when a site needs multiple servers because the volume of requests
is too much for a single server to handle efficiently. Deploying multiple servers
also eliminates a single point of failure, making the website more reliable. Most commonly,
the servers all host the same content, and the load balancer’s job is to distribute the workload
in a way that makes the best use of each server’s capacity, prevents overload on any server,
and results in the fastest possible response to the client.

A load balancer can also enhance the user experience by reducing the number of error responses the client sees.
It does this by detecting when servers go down, and diverting requests away from them to the other servers in the group.
In the simplest implementation, the load balancer detects server health by intercepting
error responses to regular requests. Application health checks are a more flexible and sophisticated method
in which the load balancer sends separate health-check requests and requires a specified
type of response to consider the server healthy.

Another useful function provided by some load balancers is session persistence,
which means sending all requests from a particular client to the same server.
Even though HTTP is stateless in theory, many applications must store state information
just to provide their core functionality – think of the shopping basket on an e-commerce site.
Such applications underperform or can even fail in a load-balanced environment,
if the load balancer distributes requests in a user session to different servers
instead of directing them all to the server that responded to the initial request.

Nginx with dynamic upstreams
++++++++++++++++++++++++++++

https://tenzer.dk/nginx-with-dynamic-upstreams/

https://www.nginx.com/products/on-the-fly-reconfiguration/

https://github.com/GUI/nginx-upstream-dynamic-servers

https://github.com/cubicdaiya/ngx_dynamic_upstream

http://serverfault.com/questions/374643/nginx-dynamic-upstream-configuration-routing

https://github.com/Mashape/kong/issues/1129

https://news.ycombinator.com/item?id=9950715

https://github.com/bobrik/zoidberg-nginx

https://github.com/bobrik/zoidberg

https://github.com/openresty/lua-resty-dns

https://github.com/spro/simon

Reverse Proxy
+++++++++++++

Whereas deploying a load balancer makes sense only when you have multiple servers,
it often makes sense to deploy a reverse proxy even with just one web server
or application server. You can think of the reverse proxy as a website’s “public face.”
Its address is the one advertised for the website, and it sits at the edge of the site’s network to accept requests
from web browsers and mobile apps for the content hosted at the website. The benefits are two-fold:

    Increased security

    No information about your backend servers is visible outside your internal network,
    so malicious clients cannot access them directly to exploit any vulnerabilities.
    Many reverse proxy servers include features that help protect backend servers from
    distributed denial-of-service (DDoS) attacks, for example by rejecting traffic from particular
    client IP addresses (blacklisting), or limiting the number of connections accepted from each client.

    Increased scalability and flexibility

    Because clients see only the reverse proxy’s IP address,
    you are free to change the configuration of your backend infrastructure.
    This is particularly useful In a load-balanced environment, where you can scale the number of servers up and down
    to match fluctuations in traffic volume.

Another reason to deploy a reverse proxy is for web acceleration – reducing the time it takes to generate a response and return it to the client. Techniques for web acceleration include the following:

    Compression

    Compressing server responses before returning them to the client (for instance, with gzip)
    reduces the amount of bandwidth they require, which speeds their transit over the network.

    SSL termination

    Encrypting the traffic between clients and servers protects it as it crosses
    a public network like the Internet. But decryption and encryption can be computationally expensive.
    By decrypting incoming requests and encrypting server responses, the reverse proxy frees up resources
    on backend servers which they can then devote to their main purpose, serving content.

    Caching

    Before returning the backend server’s response to the client, the reverse proxy stores
    a copy of it locally. When the client (or any client) makes the same request, the reverse proxy
    can provide the response itself from the cache instead of forwarding the request to the backend server.
    This both decreases response time to the client and reduces the load on the backend server.



https://www.nginx.com/resources/glossary/reverse-proxy-vs-load-balancer/


Load balancing haproxy and nginx
--------------------------------

Understanding Load Balancing

Load Balancing, otherwise known as fault-tolerant proxying,
helps to disseminate requests going into one domain across multiple web servers,
where they access the stored data requested by clients. The main objective of load balancing is to avoid having
a single point of failure so that no part of the machine is important enough that if it fails the system will crash.

HAproxy was built to alleviate these concerns as a fast, reliable and free load balancer proxy for TCP and
HTTP based applications. It is written in C programming language with a single-process, event-driven mode
that was designed to reduce the cost of context switch and memory usage. Other systems that use pre-forked
or threaded servers use more memory, but HAproxy can process several hundreds of tasks in as fast as a millisecond.

Modes—TCP vs. HTTP

What makes HAproxy so efficient as a load balancer is its ability to perform Layer 4 load balancing. In TCP mode,
all user traffic will be forwarded based on IP range and port.  The user accesses the load balancer,
which will forward the request to the backend servers.
The backend server that is selected will then respond directly to the user, which streamlines the process.

The other form of load balancing is Layer 7, or HTTP load balancing,
which forwards the requests
to different backend servers based on the content of the user’s request.
This mode is more commonly used when running multiple application servers under the same domain and port,
because it searches the content of the package in order to sort the request. While HTTP mode is good for sorting,
TCP mode is ideal for speed since it doesn’t have to open the package to sort the requests.
Unlike a lot of other load balancers, HAproxy is unique because it has both options built in.

===================     =====================================
Nginx   	            HAproxy
===================     =====================================
Full Web Server	        Only Load Balancer
Complicated, Slower	    Faster
Works with Windows	    Only Open Source
No Admin Console	    Admin Console
Only HTTP Layer 7	    TCP (Layer 4) HTTP (Layer 7)
Good Caching	        Advanced Routing and Load Balancing
Native SSL	            Native SSL
===================     =====================================


HAProxy is really just a load balancer/reverse proxy. Nginx is a Webserver that can also function as a reverse proxy.

Here are some differences:

HAProxy:

    Does TCP as well as HTTP proxying (SSL added from 1.5-dev12)
    More rate limiting options
    The author answers questions here on Server Fault ;-)

Nginx:

    Supports SSL directly
    Is also a caching server

At Stack Overflow we mainly use HAProxy with nginx for SSL offloading so HAProxy is my recommendation.


If needed only for load balancing HA proxy is better.
But combining both nginix and HA proxy can be more useful,
as nginix is fast in providing static content, it will serve all request for static data and then
send all request to HA proxy which serve as load balancer
and send request to web server to serve request by balancing load.

HaProxy is the best opensource loadbalancer on the market.
Varnish is the best opensource static file cacher on the market.
Nginx is the best opensource webserver on the market.

(of course this is my and many other peoples opinion)

But generally.. not all queries go through the entire stack..

Everything goes through haproxy and nginx/multiple nginx's.
The only difference is you "bolt" on varnish for static requests..

    any request is loadbalanced for redundancy and throughput ( good, thats scalable redundancy )
    any request for static files is first hitting the varnish cache ( good, thats fast )
    any dynamic request goes direct to the backend ( great, varnish doesnt get used)

Overall, this model fits a scalable and growing architecture ( take haproxy out, if you dont have multiple servers )

Hope this helps :D

Note: I actually also introduce Pound for SSL queries aswell :D
You can have a server dedicated to decrypting SSL requests,
and passing out standard requests to the backend stack :D (It makes the whole stack run quicker and simpler )


Nginx

    A full web server, other features can also be used. Eg: HTTP Compression
    SSL Support
    Very light weight as Nginx was designed to be light from the start.
    Near Varnish caching performance
    Close to HAProxy load balancing performance

Varnish

    best for complex caching scenarios and incorporating with the applications.
    best static file cacher
    No SSL Support
    Memory and CPU eater

Haproxy

    best loadbalancer, for cutting edge load balancing features, comparable to hardware loadbalancers
    SSL is supported since 1.5.0
    Simpler, being just a tcp proxy without an http implementation, which makes it faster and less bug prone.

http://serverfault.com/questions/293501/should-nginx-be-at-the-front-of-haproxy-or-opposite

https://www.quora.com/Does-it-make-sense-to-put-Nginx-in-front-of-HAProxy

https://www.bizety.com/2016/01/27/haproxy-load-balancing-primer/

https://www.digitalocean.com/community/tutorials/how-to-create-a-high-availability-haproxy-setup-with-corosync-pacemaker-and-floating-ips-on-ubuntu-14-04

https://youtu.be/MKgJeqF1DHw

https://www.bizety.com/2016/01/27/haproxy-load-balancing-primer/

http://serverfault.com/questions/229945/what-are-the-differences-between-haproxy-and-ngnix-in-reverse-proxy-mode

http://serverfault.com/questions/204025/ordering-1-nginx-2-varnish-3-haproxy-4-webserver

http://nickcraver.com/blog/2016/02/17/stack-overflow-the-architecture-2016-edition/


Nginx vs Varnish
----------------

Varnish is a web application accelerator.
You install it in front of your web application and it will speed it up significantly.
Varnish stores data in virtual memory and leaves the task of deciding what is stored in memory
and what gets paged out to disk to the operating system. This helps avoid the situation where the operating system
starts caching data while they are moved to disk by the application.

Varnish is more advanced in terms of caching because Varnish caches whatever you tell it to cache.
It can cache just the PHP output, just the static files, both, or neither. It’s a very powerful tool.
But Nginx is more suitable as a web server.

I'm a fan of haproxy -> Varnish -> app server which we use heavily in our stack.
haproxy provides ssl termination, websockets, and generally acts as a content router.
Varnish is a caching reverse proxy which protects the app, handles TTL on content, etc. Lastly the app.
It's a little complex, but the flexibility is amazing.

https://www.scalescale.com/tips/nginx/nginx-vs-varnish/

https://www.narga.net/varnish-nginx-comparison-nginx-alone-better/?PageSpeed=noscript

https://www.reddit.com/r/devops/comments/3d9tw6/should_there_be_only_1_reverse_proxy_nginx_or/

An Introduction to HAProxy and Load Balancing Concepts
------------------------------------------------------

https://www.digitalocean.com/community/tutorials/an-introduction-to-haproxy-and-load-balancing-concepts

Redundant load balancers?
-------------------------

The point where the redundancy may fail is the load balancer itself. If
you do not make that component redundant, the load balancer will become
the single point of failure.

HA of a Load Balancer

An NGINX Plus HA cluster uses VRRP to manage a floating virtual IP address,
ensuring that the IP address is always available and traffic is not dropped
The NGINX Plus high-availability solution is based on keepalived, which itself uses an implementation
of the Virtual Router Redundancy Protocol (VRRP).
After you install the nginx-ha-keepalived package and configure keepalived,
it runs as a separate process on each NGINX instance in the cluster and manages a shared virtual IP address.
The virtual IP address is the IP address advertised to downstream clients,
for example via a DNS record for your service.

Based on initial configuration, keepalived designates one NGINX instance as master
and assigns the virtual IP address to it.
The master periodically verifies that keepalived and NGINX Plus are both running,
and sends VRRP advertisement messages at regular intervals to let the backup instance know it’s healthy.
If the backup doesn’t receive three consecutive advertisements,
it becomes the new master and takes over the virtual IP address.

https://en.wikipedia.org/wiki/Virtual_Router_Redundancy_Protocol

http://serverfault.com/questions/686878/how-to-make-redundant-load-balancers

http://d0.awsstatic.com/whitepapers/AWS_NGINX_Plus-whitepaper-final_v4.pdf

https://www.nginx.com/products/high-availability/

https://www.nginx.com/resources/admin-guide/nginx-ha-keepalived/

How To Set Up Highly Available Web Servers with Keepalived and Floating IPs on Ubuntu 14.04
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

https://www.digitalocean.com/community/tutorials/how-to-set-up-highly-available-web-servers-with-keepalived-and-floating-ips-on-ubuntu-14-04

How To Set Up Highly Available HAProxy Servers with Keepalived and Floating IPs on Ubuntu 14.04
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

https://www.digitalocean.com/community/tutorials/how-to-set-up-highly-available-haproxy-servers-with-keepalived-and-floating-ips-on-ubuntu-14-04

nginx automatic failover load balancing
---------------------------------------

http://serverfault.com/questions/140990/nginx-automatic-failover-load-balancing


Building a Load Balancer with LVS - Linux Virtual Server
--------------------------------------------------------

http://kaivanov.blogspot.com/2013/01/building-load-balancer-with-lvs-linux.html

Building A Highly Available Nginx Reverse-Proxy Using Heartbeat
---------------------------------------------------------------

http://opensourceforu.com/2009/03/building-a-highly-available-nginx-reverse-proxy-using-heartbeat/

Building a Highly-Available Load Balancer with Nginx and Keepalived on CentOS
-----------------------------------------------------------------------------

http://www.tokiwinter.com/building-a-highly-available-load-balancer-with-nginx-and-keepalived-on-centos/


HAProxy as a static reverse proxy for Docker containers
-------------------------------------------------------

http://oskarhane.com/haproxy-as-a-static-reverse-proxy-for-docker-containers/

How to setup HAProxy as Load Balancer for Nginx on CentOS 7
-----------------------------------------------------------

https://www.howtoforge.com/tutorial/how-to-setup-haproxy-as-load-balancer-for-nginx-on-centos-7/


Building a Load-Balancing Cluster with LVS
------------------------------------------

http://dak1n1.com/blog/13-load-balancing-lvs/


Doing Some local benchmark with Nginx
-------------------------------------

.. code-block:: bash

    user  nginx;
    worker_processes  1;
    worker_rlimit_nofile = 5000

    error_log  /var/log/nginx/error.log warn;
    pid        /var/run/nginx.pid;

    events {
        worker_connections  2048;
        use epoll;
        multi_accept on;
        accept_mutex on;
    }


    http {
        include       /etc/nginx/mime.types;
        default_type  application/octet-stream;

        log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                          '$status $body_bytes_sent "$http_referer" '
                          '"$http_user_agent" "$http_x_forwarded_for"';

        # access_log  /var/log/nginx/access.log  main;
        access_log off;

        sendfile        on;
        #tcp_nopush     on;

        keepalive_timeout  15;

        #gzip  on;

        include /etc/nginx/conf.d/*.conf;


        server {
            server_name _;
            charset     utf-8;
            client_max_body_size 50M;
            proxy_intercept_errors on;


            location  / {
                autoindex on;
                alias /;
            }

            # Server status
            location = /status {
                stub_status on;
                allow all;
            }

    }

    }

.. code-block:: bash

    $ docker run --rm -p 80:80 -v ~/workspace/nginx/nginx.conf:/etc/nginx/nginx.conf:ro  nginx
    $ ab -n 150000 -c 20000 http://127.0.0.1/
    $ ab -n 300000 -c 20000 http://127.0.0.1/
    $ ab -k -n 5000000 -c 20000 http://127.0.0.1/
    $ ab -k -c 10 -t 60 -n 10000000 http://127.0.0.1/

.. code-block:: bash

    # worker_processes  1;
    # worker_connections  1,000;

    # Failed requests:        0
    $ ab -n 1,000,000 -c 1,000 127.0.0.1/bin/tar

    # Failed requests:        0
    $ ab -n 1,000,000 -c 500 127.0.0.1/bin/tar

    # Failed requests:       191
    $ ab -n 1,000,000 -c 1,000 127.0.0.1/bin/tar

    # Failed requests:        77158
    $ ab -n 100,000 -c 10,000 127.0.0.1/bin/tar

    # Failed requests:        24346
    $ ab -n 100,000 -c 10,000 127.0.0.1/bin/tar

    # worker_processes  4;
    # worker_connections  1,000;

    # Failed requests:        38067
    $ ab -n 100,000 -c 10,000 127.0.0.1/bin/tar

    # worker_processes  4;
    # worker_connections  10,000;

    # Failed requests:        0
    # Time per request:       0.509 [ms]
    $ ab -n 100,000 -c 10,000 127.0.0.1/bin/tar


    # worker_processes  1;
    # worker_connections  10,000;

    # Failed requests:        0
    # Time per request:       0.509 [ms]
    $ ab -n 100,000 -c 10,000 127.0.0.1/bin/tar


    # worker_processes  1;
    # worker_connections  10,000;

    # Failed requests:        0
    # Time per request:       0.509 [ms]
    $ ab -n 100,000 -c 20,000 127.0.0.1/bin/tar

    # worker_processes  1;
    # worker_connections  10,000;

    # Failed requests:        0
    # Time per request:       0.544 [ms]
    $ ab -n 100,000 -c 10,000 127.0.0.1/bin/tar

Errors:

.. code-block:: bash

    # 2016/05/04 10:57:09 [alert] 6#6: 1000 worker_connections are not enough
    2016/05/04 11:39:44 [crit] 6#6: accept4() failed (24: Too many open files)

https://www.scalescale.com/tips/nginx/nginx-accept-failed-24-too-many-open-files/

http://www.cyberciti.biz/faq/linux-unix-nginx-too-many-open-files/

http://serverfault.com/questions/516802/too-many-open-files-with-nginx-cant-seem-to-raise-limit

apache benchmark
----------------

-c: ("Concurrency"). Indicates how many clients (people/users) will be hitting the site at the same time. While ab runs, there will be -c clients hitting the site. This is what actually decides the amount of stress your site will suffer during the benchmark.

-n: Indicates how many requests are going to be made. This just decides the length of the benchmark. A high -n value with a -c value that your server can support is a good idea to ensure that things don't break under sustained stress: it's not the same to support stress for 5 seconds than for 5 hours.

-k: This does the "KeepAlive" funcionality browsers do by nature. You don't need to pass a value for -k as it it "boolean" (meaning: it indicates that you desire for your test to use the Keep Alive header from HTTP and sustain the connection). Since browsers do this and you're likely to want to simulate the stress and flow that your site will have from browsers, it is recommended you do a benchmark with this.

The final argument is simply the host. By default it will hit http:// protocol if you don't specify it.

http://stackoverflow.com/questions/12732182/ab-load-testing

http://serverfault.com/questions/274253/apache-ab-choosing-number-of-concurrent-connections

http://www.pinkbike.com/u/radek/blog/Apache-Bench-you-probably-are-using-the-t-timelimit-option-incor.html

.. code-block:: bash

    "apr_socket_recv: Connection reset by peer (104)"


    $ sudo /sbin/sysctl -a | grep net.ipv4.tcp_max_syn_backlog
    net.ipv4.tcp_max_syn_backlog = 512

    $ sudo /sbin/sysctl -a | grep net.core.somaxconn
    net.core.somaxconn = 128

.. code-block:: bash

    $ sudo /sbin/sysctl -w net.ipv4.tcp_max_syn_backlog=1024

    $ sudo /sbin/sysctl -w net.core.somaxconn=256



http://stackoverflow.com/questions/30794548/about-the-concurrency-of-docker

http://blog.scene.ro/posts/apache-benchmark-apr_socket_recv/

https://easyengine.io/tutorials/php/fpm-sysctl-tweaking/

https://easyengine.io/tutorials/linux/sysctl-conf/

http://community.rtcamp.com/t/hitting-a-limit-with-the-tuning-or-am-i/831

http://serverfault.com/questions/231516/http-benchmarking

http://serverfault.com/questions/146605/understanding-this-error-apr-socket-recv-connection-reset-by-peer-104


The keepalive_timeout has nothing to do with the concurrent connections per second.
In fact, nginx can close an idle connection at any time when it reaches the limit
of worker_connections.

What's really important is the connections that nginx cannot close.  The active ones.
How long the connection is active depends on the request processing time.

The approximate calculation looks like this:

 worker_processes * worker_connections * K / average $request_time

where K is the average number of connections per request (for example, if you do proxy
pass, then nginx needs additional connection to your backend).

http://nginx.org/en/docs/http/ngx_http_core_module.html#var_request_time

https://blog.martinfjordvald.com/2011/04/optimizing-nginx-for-high-traffic-loads/

Nginx as a HTTP server:

    Max_clients = worker_processes * worker_connections

Nginx as a reverse proxy server:

    Max_clients = worker_processes * worker_connections/4

https://loader.io/

HTTP Keepalive Connections and Web Performance | NGINX
------------------------------------------------------

Modern web browsers typically open 6 to 8 keepalive connections
and hold them open for several minutes before timing them out.
Web servers may be configured to time these connections out and close them sooner.

If lots of clients use HTTP keepalives and the web server has a concurrency limit or scalability problem,
then performance plummets once that limit is reached.

NGINX’s HTTP-caching feature can cache responses from the upstream servers,
following the standard cache semantics to control what is cached and for how long.
If several clients request the same resource, NGINX can respond from its cache
and not burden upstream servers with duplicate requests.

https://www.nginx.com/blog/http-keepalives-and-web-performance/

https://www.nginx.com/blog/tuning-nginx/

Nginx Caching
-------------

By default, NGINX respects the Cache-Control headers from origin servers.
It does not cache responses with Cache-Control set to Private, No-Cache,
or No-Store or with Set-Cookie in the response header. NGINX only caches GET and HEAD client requests.

https://www.nginx.com/resources/wiki/start/topics/examples/reverseproxycachingexample/

https://www.nginx.com/blog/nginx-caching-guide/

Optimizing NGINX Speed for Serving Content
------------------------------------------

https://www.nginx.com/resources/admin-guide/serving-static-content/

http://stackoverflow.com/questions/4839039/tuning-nginx-centos-for-server-lots-of-static-content

http://blog.octo.com/en/http-caching-with-nginx-and-memcached/

https://github.com/bpaquet/ngx_http_enhanced_memcached_module

Fastest server for static files serving
---------------------------------------

http://gwan.com/benchmark

http://www.wikivs.com/wiki/G-WAN_vs_Nginx

Mount your document root as a ramdisk.

Cache full responses to most common queries rather than rebuilding the http.

Tweaking swappiness (or having no swap at all)

Firewall load balancing

DNS load balancing

Geolocation load balancing

Turning off logging/hostname resolution/various lookups.

Many cores, big pipes.

Mounting file system with noatime

http://stackoverflow.com/questions/13554706/fastest-server-for-static-files-serving

http://serverfault.com/a/443633

https://nbonvin.wordpress.com/2011/03/24/serving-small-static-files-which-server-to-use/


https://github.com/eucalyptus/architecture/blob/master/features/elb/3.3/elb-benchmark.wiki

Sample Nginx load balancing
---------------------------

nginx_lb:

.. code-block:: bash

    user  nginx;

    worker_processes  1;
    worker_rlimit_nofile 100000;


    error_log  /var/log/nginx/error.log warn;
    pid        /var/run/nginx.pid;


    events {
        worker_connections  1024;
        use epoll;
        multi_accept on;
        accept_mutex on;

    }


    http {

        include       /etc/nginx/mime.types;
        default_type  application/octet-stream;

        log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                          '$status $body_bytes_sent "$http_referer" '
                          '"$http_user_agent" "$http_x_forwarded_for"'
                          '$request_time';

        access_log off;

        sendfile        on;

        keepalive_timeout  15;

        aio on;
        directio 4m;
        tcp_nopush on;
        tcp_nodelay on;

        upstream web_server {

           server 192.168.1.119:81;
           server 192.168.1.119:82;
        }

        server {
            server_name _;
            charset     utf-8;
            client_max_body_size 50M;
            proxy_intercept_errors on;
    #        proxy_max_temp_file_size 0;

            location /{

                proxy_pass http://web_server;
            }

            # Server status
            location = /status {
                stub_status on;
                allow all;
            }

        }

        include /etc/nginx/conf.d/*.conf;
    }


nginx_cdn:

.. code-block:: bash

    user  nginx;

    worker_processes  1;
    worker_rlimit_nofile 100000;

    error_log  /var/log/nginx/error.log warn;
    pid        /var/run/nginx.pid;

    events {
        worker_connections  1024;
        use epoll;
        multi_accept on;
        accept_mutex on;

    }

    http {

        include       /etc/nginx/mime.types;
        default_type  application/octet-stream;

        log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                          '$status $body_bytes_sent "$http_referer" '
                          '"$http_user_agent" "$http_x_forwarded_for"'
                          '$request_time';

    #   access_log  /var/log/nginx/access.log  main;
        access_log off;

        sendfile        on;

        keepalive_timeout  15;

        gzip  on;

        aio on;
        directio 4m;
        tcp_nopush on;
        tcp_nodelay on;

        server {
            server_name _;
            charset     utf-8;
            client_max_body_size 50M;
            proxy_intercept_errors on;

            location  / {
                autoindex on;
                alias /;
            }

            location = /status {
                stub_status on;
                allow all;
            }

        }

        include /etc/nginx/conf.d/*.conf;
    }
