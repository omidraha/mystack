Tips
====

Atlassian installation approach
-------------------------------

Install ``Atlassian Jira`` and ``atlassian-confluence`` with docker images by `cptactionhank`:

https://github.com/cptactionhank


https://github.com/cptactionhank/docker-atlassian-jira

http://cptactionhank.github.io/docker-atlassian-jira/

https://hub.docker.com/r/cptactionhank/atlassian-jira/



https://github.com/cptactionhank/docker-atlassian-confluence

http://cptactionhank.github.io/docker-atlassian-confluence/


Install ``Atlassian Bitbucket Server``  with docker image by `Atlassian`:

https://bitbucket.org/atlassian/docker-atlassian-bitbucket-server/overview


https://hub.docker.com/r/atlassian/bitbucket-server/


Install ``Atlassian Bitbucket Server``  on the cloud with docker images by `Atlassian`:


https://developer.atlassian.com/blog/2015/12/atlassian-docker-orchestration/



Videos:

http://www.youtube.com/watch?v=mqVMoUjmkP0

http://www.youtube.com/watch?v=zhAT-gZcpBM


Other links:


https://bitbucket.org/atlassian/bamboo-docker-plugin


Atlassian license Prices
------------------------

Bitbucker server

https://www.atlassian.com/software/bitbucket/pricing?tab=host-on-your-server

https://www.atlassian.com/licensing/bitbucket-server/

Confluence

https://www.atlassian.com/software/confluence/pricing?tab=host-on-your-server

https://www.atlassian.com/licensing/confluence/

JIRA

https://www.atlassian.com/software/jira/pricing?tab=host-on-your-server#tab-9eb6ae11

https://es.atlassian.com/licensing/jira-software

HipChat

https://www.hipchat.com/pricing

https://www.hipchat.com/server#pricing-show

https://www.atlassian.com/purchase/product/com.atlassian.hipchat.server

https://blog.hipchat.com/2014/05/27/hipchat-is-now-free-for-unlimited-users/

Others:

https://confluence.atlassian.com/display/CLOUDKB/Pros+and+Cons+of+Cloud+vs.+Server

https://confluence.atlassian.com/bitbucket/associate-an-existing-domain-with-an-account-221449746.html

https://jira.atlassian.com/browse/CLOUD-6999?src=confmacro




For local server payment is one-time payment and include 12 months of software maintenance (support and updates).

.. code-block:: bash

    Product                 Users (up to)       Your Own Server (one-time payment)  Atlassian Cloud server (per month)

    Bitbucket server
                            5                       x                                     Free
                            10                      10$                                   10$
                            25                      1,800$                                25$
                            50                      3,300$                                50$
    JIRA
                            10                      10$                                   10$
                            15                      x                                     75$
                            25                      1,800$                                150$
                            50                      3,300$                                300$
    Confluence
                            10                      10$                                   10$
                            15                      x                                     50$
                            25                      1,200$                                100$
                            50                      2,200$                                200$
    HipChat Basic
                                                    x                                     Free

    HipChat Plus
                                                 x                                        2$ (per user/month)
                           10                    10$/year                                 x
                           25                    1,800$/year                              x
                           50                    3,300/year                               x

HipChat Basic

    Group chat
    Instant messaging
    File sharing
    Unlimited users and integrations

HipChat Plus

    Video chat
    Screensharing
    File sharing
    Unlimited users and integrations
    Much more

Sample Own Server prices:

    2GB/ 2 Core     20$/month

    4GB/ 2 Core     40$/month


Run Jira with docker
--------------------


.. code-block:: bash

    $ docker  pull cptactionhank/atlassian-jira:latest

    $ docker run  -p 80:8080 -v /home/rsa/workspace/docker/atlassian/jira:/var/atlassian/jira --env "CATALINA_OPTS=-Xms64m -Xmx768m -Datlassian.plugins.enable.wait=300"   cptactionhank/atlassian-jira:latest

    $ docker create --restart=no --name "jira-container" -p 80:8080  -v /home/or/workspace/docker/atlassian/jira:/var/atlassian/jira   --env "CATALINA_OPTS=-Xms64m -Xmx768m -Datlassian.plugins.enable.wait=300" cptactionhank/atlassian-jira:latest
    $ docker start --attach "jira-container"

Data Directories:

    ``/var/atlassian/jira``

Expose Ports:

    ``8080``

https://hub.docker.com/r/cptactionhank/atlassian-jira/

https://github.com/cptactionhank/docker-atlassian-jira

http://cptactionhank.github.io/docker-atlassian-jira/


Run Confluence with docker
--------------------------

.. code-block:: bash

    $ docker  pull cptactionhank/atlassian-confluence:latest

    $ docker run docker run -p 80:8090 -v /home/or/workspace/docker/atlassian/confluence:/var/atlassian/confluence --env "CATALINA_OPTS=-Xms64m -Xmx768m -Datlassian.plugins.enable.wait=300" cptactionhank/atlassian-confluence:latest

Data Directories:

    ``/var/atlassian/confluence``

Expose Ports:

    ``8080``

https://hub.docker.com/r/cptactionhank/atlassian-confluence/

https://github.com/cptactionhank/docker-atlassian-confluence

http://cptactionhank.github.io/docker-atlassian-confluence/


Run Bitbucket Server  with docker
---------------------------------

.. code-block:: bash

    $ docker pull atlassian/bitbucket-server
    $ docker run -v /home/or/workspace/docker/atlassian/bitbucket:/var/atlassian/application-data/bitbucket -p 7990:7990 -p 7999:7999 atlassian/bitbucket-server

Expose Ports:

    ``7990``

    ``7999``

Data Directories:

    ``/var/atlassian/application-data/bitbucket``

https://hub.docker.com/r/atlassian/bitbucket-server/


JIRA is Unable to Start due to Could not create necessary subdirectory
----------------------------------------------------------------------

.. code-block:: bash

    # on host system
    $ mkdir /<JIRA-HOME-PATH>
    $ mkdir /home/or/workspace/docker/atlassian/jira/
    $ sudo chown -R <USER-THAT-RUN-JIRA>:<USER-THAT-RUN-JIRA> /<JIRA-HOME-PATH>
    $ sudo chown -R daemon:daemon /home/or/workspace/docker/



https://confluence.atlassian.com/display/JIRAKB/JIRA+is+Unable+to+Start+due+to+Could+not+create+necessary+subdirectory

https://github.com/docker/docker/issues/2259

Atlassian Docker compose file
-----------------------------


.. code-block:: yaml

    jira:
      image: cptactionhank/atlassian-jira:7.0.5
      restart: always
      links:
        - database
      volumes:
        - ~/workspace/docker/atlassian/jira:/var/atlassian/jira

    confluence:
      image: cptactionhank/atlassian-confluence:5.9.4
      restart: always
      links:
        - database
      volumes:
        - ~/workspace/docker/atlassian/confluence:/var/atlassian/confluence

    bitbucket:
      image: atlassian/bitbucket-server:4.3:
      restart: always
      links:
        - database
      volumes:
        - ~/workspace/docker/atlassian/bitbucket:/var/atlassian/application-data/bitbucket

    database:
      image: postgres:9.4
      restart: always
      volumes:
        - ~/workspace/docker/postgres:/var/lib/postgresql/data

    nginx:
      image: nginx
      restart: always
      ports:
        - "80:80"
      links:
        - jira
        - confluence
        - bitbucket
      volumes:
        - ./config/nginx/nginx.conf:/etc/nginx/nginx.conf:ro


The ``nginx.conf`` file:

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
        client_max_body_size        0;

        server {
            listen       80;
            server_name  jira.example.com www.jira.example.com;

            location / {
                proxy_pass              http://jira:8080;

                proxy_set_header        X-Forwarded-Host $host;
                proxy_set_header        X-Forwarded-Server $host;
                proxy_set_header        X-Forwarded-For $remote_addr;
                proxy_set_header        X-Real-IP $remote_addr;

                proxy_set_header        Host        $host;

            }
        }

        server {
            listen       80;
            server_name  wiki.example.com www.wiki.example.com;

            location / {
                proxy_pass                      http://confluence:8090;

                proxy_set_header        X-Forwarded-Host $host;
                proxy_set_header        X-Forwarded-Server $host;
                proxy_set_header        X-Forwarded-For $remote_addr;
                proxy_set_header        X-Real-IP $remote_addr;

                proxy_set_header        Host            $host;
            }
        }

        server {
            listen       80;
            server_name  bitbucket.example.com www.bitbucket.example.com;

            location / {
                proxy_pass              http://bitbucket:7990;

                proxy_set_header        X-Forwarded-Host $host;
                proxy_set_header        X-Forwarded-Server $host;
                proxy_set_header        X-Forwarded-For $remote_addr;
                proxy_set_header        X-Real-IP $remote_addr;

                proxy_set_header        Host            $host;
            }
        }
    }



.. code-block:: bash


    $ mkdir -p ~/workspace/docker/atlassian/jira
    $ mkdir -p ~/workspace/docker/atlassian/confluence
    $ mkdir -p ~/workspace/docker/atlassian/bitbucket
    $ mkdir -p ~/workspace/docker/postgres
    $ mkdir -p ~/workspace/docker/nginx

    $ sudo chown -R daemon:daemon   ~/workspace/docker/atlassian

    $ docker-compose ps

    $ docker exec -it atlassian_jira_1 bash

.. code-block:: bash

    $ vim docker-compose.yaml
    $ docker-compose up


https://confluence.atlassian.com/display/BitbucketServerKB/Git+push+fails+-+client+intended+to+send+too+large+chunked+body


Backup atlassian product
------------------------

Automating JIRA Backups


    The XML backup includes all data in the database. However, it does not include your attachments directory, JIRA Home Directory or JIRA Installation Directory, which are stored on the filesystem.
    You can also perform XML backups manually. See Backing Up Data for details.
    Be aware that after installing JIRA and running the setup wizard, a backup service will automatically be configured to run every 12 hours.

    For production use or large JIRA installations,
    it is strongly recommended that you use native database-specific tools instead of the XML backup service.
    XML backups are not guaranteed to be consistent, as the database may be updated during the backup process.
    Inconsistent backups are created successfully without any warnings or error messages,
    but fail during the restore process.
    Database-native tools offer a much more consistent and reliable means of storing data.

https://confluence.atlassian.com/jira/automating-jira-backups-185729637.html

Backing Up Data

    This page describes how to back up your JIRA data, and establish processes for maintaining continual backups.
    Backing up your JIRA data is the first step in upgrading your server to a new JIRA revision,
    or splitting your JIRA instance across multiple servers.
    See also Restoring JIRA data and Restoring a Project from Backup.

Creating a complete backup of JIRA consists of two stages:

    1. Backing up database contents
        * Using native database backup tools
        * Using JIRA's XML backup utility
    2. Backing up the data directory

https://confluence.atlassian.com/jira/backing-up-data-185729581.html#BackingUpData-Usingnativedatabasebackuptools


Postgres File System Level Backup

http://www.postgresql.org/docs/9.3/static/backup-file.html

Using Rsync and SSH

http://troy.jdmz.net/rsync/index.html


Bamboo
------


https://confluence.atlassian.com/bamboo/getting-started-with-docker-and-bamboo-687213473.html

http://blogs.atlassian.com/2015/09/bamboo-docker-building-web-apps/?utm_source=twitter&utm_medium=social&utm_campaign=atlassian_bamboo-docker-addteq

http://www.systemsthoughts.com/2015/5-things-i-learned-using-docker-for-bamboo/

https://realpython.com/blog/python/django-development-with-docker-compose-and-machine/

https://pometeam.atlassian.net/builds/build/admin/ajax/viewAvailableVariables.action?planKey=POG-TEST-JOB1

https://realpython.com/blog/python/django-development-with-docker-compose-and-machine/

http://stackoverflow.com/questions/1419629/atlassian-bamboo-with-django-python-possible

https://jira.atlassian.com/browse/BAM-11368

https://answers.atlassian.com/questions/35809/how-to-parse-django-tests-with-bamboo

http://mike-clarke.com/2013/11/docker-links-and-runtime-env-vars/


http://stackoverflow.com/questions/31746182/docker-compose-wait-for-container-x-before-starting-y

https://github.com/docker/compose/issues/374

http://stackoverflow.com/questions/29377853/how-to-use-environment-variables-in-docker-compose

https://confluence.atlassian.com/bamboocloud/bamboo-variables-737184363.html

echo ${bamboo.agentWorkingDirectory}
echo ${bamboo.build.working.directory}


https://pometeam.atlassian.net/builds/admin/agent/addRemoteAgent.action

nohup java -jar atlassian-bamboo-agent-installer-5.10-OD-13-001.jar https://pometeam.atlassian.net/builds/agentServer/ -t <TOKEN>  > /dev/null 2>&1 &
