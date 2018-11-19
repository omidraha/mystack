DOCKER
======

.. code-block:: bash

	$ docker version
	$ docker search repo
	$ docker pull username/repo
	$ docker run learn/tutorial echo "hello world"
	$ docker run learn/tutorial apt-get install -y ping
	# shows information about running containers
	$ docker ps
	# shows information about running nd stopped containers
	$ docker ps -a
	# return the details of the last container started
	$ docker ps -l
	# create a new container
	$ docker run IMAGE_ID CMD PARAMS
	# tells Docker to run the container in the background.
	$ docker run -d IMAGE_ID
	# to docker map any ports exposed in our image to our host.
	$ docker run -P IMAGE_ID
	$ docker run -p 5000:5000 IMAGE_ID
	$ docker inspect IMAGE_ID
	$ docker run -i -t IMAGE_ID /bin/bash
	# Create a new image from a container's changes
	$ docker commit CONTAINER_ID IMAGE_NAME
	# The -m flag allows us to specify a commit message, much like you would with a commit on a version control system.
	$ docker commit CONTAINER_ID IMAGE_NAME -m="COMMIT MESSAGE"
	# The -a flag allows us to specify an author for our update
	$ docker commit CONTAINER_ID IMAGE_NAME -a="Author Name"
	# Examine the processes running inside the container
	$ docker top CONTAINER_ID
	# restart the old container again
	$ docker start CONTAINER_ID
	$ docker stop CONTAINER_ID
	# Attach to a running container
	$ docker attach CONTAINER_ID
	# docker execute an command on container and keep stdin interactive
	$ docker exec -it  CONTAINER_ID  /bin/bash
	# Build an image from a Dockerfile
	$ docker build -t IMAGE_TAG_NAME FOLDER_PATH_OF_DOCKER_FILE
	# Add a tag to an existing image after you commit or build it.
	$ docker tag IMAGE_ID IMAGE_REPOSITORY_NAME:NEW_TAG_NAME
	# Remove image from Docker host
	$ docker rmi IMAGE_ID
	$ docker inspect CONTAINER_ID | grep IPAddress | cut -d '"' -f 4.
	# Narrow down the information we want to return by requesting a specific element
	$ docker inspect -f '{{ .NetworkSettings.IPAddress }}' CONTAINER_ID
	$ docker logs CONTAINER_ID
	# This causes the docker logs command to act like the tail -f command and watch the container's standard out.
	$ docker logs -f CONTAINER_ID
	# Adding a data volume
	$ docker run -i -t -v /HOST/DIRECTORY IMAGE_ID CMD
	# Mount a host directory as a data volume using the -v flag
	$ docker run -i -t -v /HOST/DIRECTORY:/CONTAINER/DIRECTORY IMAGE_ID CMD
	# Docker defaults to a read-write volume but we can also mount a directory read-only.
	$ docker run -i -t -v /ONE/PATH/IN/HOST:/ONE/PATH/IN/CONTAINER:ro IMAGE_ID CMD
	$ docker images --tree
	# Remove all Exited Docker containers
	$ docker ps -a | grep Exited | cut -d ' ' -f 1 | xargs docker rm
	$ docker ps -a | grep Exited | awk '{print $1}'| xargs docker rm
	$ docker rm $(docker ps -a -q)
	# remove <none> images
	$ docker images | grep none | awk '{print $3}'| xargs docker rmi
	# remove all images
	$ docker rmi $(docker images -q)
	# remove container after running
	$ docker run  --rm -i -t  IMAGE_ID CMD




Note:

An image can't have more than 127 layers regardless of the storage driver.

This limitation is set globally to encourage optimization of the overall size of images.


Create base kali image
----------------------

http://www.jamescoyle.net/how-to/1503-create-your-first-docker-container


.. code-block:: bash

	# Install dependencies (debbootstrap)
    apt-get install debootstrap

	# Fetch the latest Kali debootstrap script from git
    curl "http://git.kali.org/gitweb/?p=packages/debootstrap.git;a=blob_plain;f=scripts/kali;hb=HEAD" > kali-debootstrap

	# Download kali packages
    debootstrap kali ./kali-root http://http.kali.org/kali ./kali-debootstrap

	# Create image
    tar -C kali-root -c . | docker import - kali_base_1.0.9

	# Run image
    docker run -t -i kali_base_1.0.9 /bin/bash


Install docker on ``Debian``
----------------------------

.. code-block:: bash

    $ sudo apt-get purge lxc-docker*
    $ sudo apt-get purge docker.io*
    $ sudo apt-get update
    $ sudo apt-get install apt-transport-https ca-certificates
    $ sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
    $ sudo vim /etc/apt/sources.list.d/docker.list
        # On Debian Stretch/Sid
        deb https://apt.dockerproject.org/repo debian-stretch main
    $ sudo apt-get update
    $ sudo apt-cache policy docker-engine
    $ sudo apt-get install docker-engine
    $ sudo service docker start


https://docs.docker.com/engine/installation/debian/

Install docker on ``Ubuntu Server``
-----------------------------------

https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/#set-up-the-repository

.. code-block:: bash

    $ sudo apt-get update
    $ sudo apt-get install apt-transport-https ca-certificates curl software-properties-common
    $ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    $ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    $ sudo apt-get update
    $ sudo apt-get install docker-ce


Set HTTP Proxy for docker
-------------------------

https://docs.docker.com/engine/articles/systemd/#http-proxy


.. code-block:: bash

	# systemctl status docker | grep Loaded
		Loaded: loaded (/lib/systemd/system/docker.service; enabled; vendor preset: enabled)

.. code-block:: bash

	$ vim  /lib/systemd/system/docker.service

Add ``Environment`` to ``docker.service``:

.. code-block:: bash

	[Service]
	Environment="HTTP_PROXY=http://127.0.0.1:8080/" "NO_PROXY=localhost,127.0.0.1"

.. code-block:: bash

	$ sudo systemctl show docker --property Environment
	$ sudo systemctl daemon-reload
	$ sudo systemctl show docker --property Environment
	$ sudo systemctl restart docker


Set HTTP Proxy for docker on Ubuntu 12.04.3 LTS
-----------------------------------------------

.. code-block:: bash

    $ sudo vim /etc/default/docker
        export http_proxy="http://PROXY_IP:PROXY_PORT"
    $ sudo service docker restart

http://stackoverflow.com/questions/26550360/docker-ubuntu-behind-proxy

how to let docker container work with sshuttle?
-----------------------------------------------

we need -l 0.0.0.0 so that docker containers with "remote ip" can connect to the tunnel.

.. code-block:: bash

    $ sshuttle -l 0.0.0.0 -vvr <USER>@<IP> 0/0

http://stackoverflow.com/a/30837252


How can I use docker without sudo?
----------------------------------

.. code-block:: bash

	$ sudo groupadd docker
	$ sudo usermod -a -G docker ${USER}
	$ sudo service docker restart
	# To prevent log out and log back in again,
	# to pick up the new docker group permissions on the current bash session
	$ newgrp docker


https://docs.docker.com/engine/installation/debian/

http://askubuntu.com/questions/477551/how-can-i-use-docker-without-sudo


Install Docker Compose
----------------------


.. code-block:: bash

    $ sudo su
    $ curl -L https://github.com/docker/compose/releases/download/1.9.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
    $ sudo chmod +x /usr/local/bin/docker-compose
    $ exit
    $ docker-compose --version
    # docker-compose version 1.9.0, build 2585387

https://docs.docker.com/compose/install/

Dockerfile reference:

https://docs.docker.com/engine/reference/builder/

Docker Compose
--------------

Reference:

https://docs.docker.com/compose/reference/

https://docs.docker.com/compose/compose-file/


Install docker machine
----------------------

.. code-block:: bash

    $ apt-get install virtualbox
    $ sudo curl -L https://github.com/docker/machine/releases/download/v0.9.0-rc2/docker-machine-`uname -s`-`uname -m` >/usr/local/bin/docker-machine && chmod +x /usr/local/bin/docker-machine
    $ docker-machine -v
    # docker-machine version 0.6.0-rc4, build a71048c


https://docs.docker.com/machine/install-machine/

https://github.com/docker/machine

How to use docker machine
-------------------------

Docker Machine allows you to provision Docker on virtual machines that reside either on your local system or on a cloud provider.

Docker Machine creates a host on a VM and you use the Docker Engine client as needed to build images and create containers on the host.

You all might have had that moment like “ Ahh man! I have to execute all these commands again!!”.

And if you are that guy who hates to configure a docker host again and again, docker-machine is there for the rescue.

So, you can leave all the installation and configuration tasks of docker to docker-machine.

Docker machine lets you spin up docker host VMs locally on your laptop,

a cloud-provider (AWS, Azure etc) and your private data center (OpenStack, Vsphere etc).

Not only docker host provisioning, using docker machine you can manage deploy and manage containers on individual hosts.


First, ensure that the latest VirtualBox is correctly installed on your system.

.. code-block:: bash

    $ docker-machine ls
    $ docker-machine create --driver virtualbox <machine-name>
    $ docker-machine create --driver virtualbox default
    #(default) Boot2Docker v1.9.1 has a known issue with AUFS.
    #(default) See here for more details: https://github.com/docker/docker/issues/18180
    #(default) Consider specifying another storage driver (e.g. 'overlay') using '--engine-storage-driver' instead.
    $ docker-machine create --engine-storage-driver overlay --driver virtualbox default
    $ docker-machine env <machine-name>
    # export DOCKER_TLS_VERIFY="1"
    # export DOCKER_HOST="tcp://192.168.99.100:2376"
    # export DOCKER_CERT_PATH="/home/or/.docker/machine/machines/default"
    # export DOCKER_MACHINE_NAME="default"
    # # Run this command to configure your shell:
    # # eval $(docker-machine env default)
    $ eval $(docker-machine env default)
    $ docker ps
    $ docker images
    $ docker-machine stop <machine-name>
    $ docker-machine restart <machine-name>
    $ docker-machine start <machine-name>
    $ docker history IMAGE_ID


https://docs.docker.com/machine/

https://docs.docker.com/machine/get-started/

https://docs.docker.com/machine/drivers/

https://docs.docker.com/machine/reference/

https://docs.docker.com/machine/get-started-cloud/

http://devopscube.com/docker-machine-tutorial-getting-started-guide/


Docker toolbox
--------------

https://www.docker.com/products/docker-toolbox


Others:
-------

https://dzone.com/articles/how-ansible-and-docker-fit

https://github.com/erroneousboat/docker-django


Docker misconceptions
---------------------

https://valdhaus.co/writings/docker-misconceptions/

Service orchestration and management tool
-----------------------------------------

Service discovery

https://github.com/hashicorp/serf

https://github.com/coreos/etcd

https://zookeeper.apache.org/

https://www.ansible.com/orchestration

https://blog.docker.com/tag/orchestration/

Docker on multi host
--------------------

https://blog.docker.com/2015/11/docker-multi-host-networking-ga/

https://docs.docker.com/engine/extend/plugins/

https://www.weave.works/i-just-created-a-cassandra-cluster-that-spans-3-different-network-domains-by-using-2-simple-shell-commands-how-cool-is-that/

https://blog.docker.com/2015/11/docker-multi-host-networking-ga/

An overlay network

Docker’s overlay network driver supports multi-host networking natively out-of-the-box.
This support is accomplished with the help of libnetwork, a built-in VXLAN-based overlay network driver,
and Docker’s libkv library.

https://docs.docker.com/engine/userguide/networking/dockernetworks/

Docker Engine supports multi-host networking out-of-the-box through the overlay network driver.
Unlike bridge networks, overlay networks require some pre-existing conditions before you can create one.
These conditions are:

    Access to a key-value store. Docker supports Consul, Etcd, and ZooKeeper (Distributed store) key-value stores.

    A cluster of hosts with connectivity to the key-value store.

    A properly configured Engine daemon on each host in the cluster.

    Hosts within the cluster must have unique hostnames because the key-value store uses the hostnames to identify cluster members.

https://docs.docker.com/engine/userguide/networking/get-started-overlay/

https://github.com/dave-tucker/docker-network-demos/blob/master/multihost-local.sh

https://www.auzias.net/en/docker-network-multihost/

http://stackoverflow.com/questions/34262182/docker-multi-host-networking-cluster-advertise-option


docker machine
--------------

https://docs.docker.com/machine/get-started-cloud/

https://docs.docker.com/machine/drivers/

http://devopscube.com/docker-machine-tutorial-getting-started-guide/



How to run a command on an already existing docker container?
-------------------------------------------------------------

if the container is stopped and can't be started due to an error,
you'll need to commit it. Then you can launch bash in an image:

.. code-block:: bash

    $ docker commit CONTAINER_ID temporary_image
    $ docker run -it temporary_image /bin/bash

Removing Docker data volumes?
-----------------------------

http://serverfault.com/a/738721

.. code-block:: bash

    $ du -h --max-depth=1 /var/lib/docker  | sort -hr
    $ docker volume rm $(docker volume ls -qf dangling=true)

Clear log history
-----------------

.. code-block:: bash

    $ vim docker-logs-clean.sh

        #!/bin/bash

        for container_id in $(docker ps -a --filter="name=$name" -q);

            do file=$(docker inspect $container_id | grep -G '"LogPath": "*"' | sed -e 's/.*"LogPath": "//g' | sed -e 's/",//g');

            if [ -f $file ]
              then
                  rm $file;
            fi

        done
    $ chmod +x docker-logs-clean.sh
    $ sudo ./docker-logs-clean.sh

https://github.com/docker/compose/issues/1083#issuecomment-216540808


Set maximum concurrent download for docker pull
-----------------------------------------------



.. code-block:: bash

    $ sudo vim  /lib/systemd/system/docker.service

    [Service]
    ExecStart=/usr/bin/dockerd -H fd:// --max-concurrent-downloads 1

    $ sudo systemctl daemon-reload
    $ systemctl restart docker



Override the ENTRYPOINT using docker run
----------------------------------------


.. code-block:: bash

    docker run -it  --entrypoint "/bin/bash"  --rm -v "$PWD":/ws/omr/  lsakalauskas/sdaps


Set image name when building a custom image
-------------------------------------------

.. code-block:: bash

    $ docker build -t image_name .
