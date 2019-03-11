Rancher
=======


Install
--------


Docker
++++++

Rancher 2.x:

.. code-block:: bash

    $ docker run -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher

https://github.com/rancher/rancher#quick-start

Minimum System
++++++++++++++

For install ``etcd`` and ``Control Plane`` on one node:

    Ram: 2GB

    CPU: 1 core


Vagrant
+++++++

.. code-block:: bash

    $ vagrant init ubuntu/xenial64
    $ vim Vagrantfile
    # Set ping able Network from host
        config.vm.network "private_network", ip: "192.168.33.10
    # Set More memory
        config.vm.provider "virtualbox" do |vb|
          vb.memory = "4096"
        end
    # vagrant reload
    $ vagrant up --provider virtualbox
    $ vagrant ssh
    $ apt-get update
    $ apt-get upgrade
    $ curl https://releases.rancher.com/install-docker/1.12.sh | sh
    $ sudo usermod -aG docker ubuntu


Now go to:

    http://192.168.10.119:1010/env/1a5/infra/hosts/add?driver=custom



https://www.vagrantup.com/docs/virtualbox/configuration.html

Setting Up a Rancher
--------------------

http://docs.rancher.com/rancher/v1.5/en/quick-start-guide/

https://github.com/infracloudio/rancher-vagrant-setup


Resiliency Planes
-----------------

For production deployments, it is best practice that each plane runs on dedicated physical or virtual hosts.
For development, multi-tenancy may be used to simplify management and reduce costs.

Data Plane

    This plane is comprised of one or more etcd containers.
    Etcd is a distributed reliable key-value store which stores all Kubernetes state.
    This plane may be referred to as stateful, meaning the software comprising the plane maintains application state.

Orchestration Plane

    This plane is comprised of stateless components that power our Kubernetes distribution.

Compute Plane

    This plane is comprised of the Kubernetes pods.

http://docs.rancher.com/rancher/v1.5/en/kubernetes/resiliency-planes/

http://docs.rancher.com/rancher/v1.5/en/kubernetes/resiliency-planes/#separated-planes

http://docs.rancher.com/rancher/v1.5/en/kubernetes/resiliency-planes/#overlapping-planes


Host Requirements for Kubernetes
++++++++++++++++++++++++++++++++

For overlapping planes setup:

    At least 1 CPU, 2GB RAM. Resource requirements vary depending on workload.

For separated planes setup:

A minimum of five hosts is required for this deployment type.

Data Plane:
    Add 3 or more hosts with 1 CPU, >=1.5GB RAM, >=20GB DISK. When adding the host, label these hosts with ``etcd=true``.
Orchestration Plane:
    Add 1 or more hosts with >=1 CPU and >=2GB RAM. When adding the host, label these hosts with ``orchestration=true``.
    You can get away with 1 host, but you sacrifice high availability.
    In the event of this host failing, some K8s features such as the API,
    rescheduling pods in the event of failure, etc. will not occur until a new host is provisioned.
Compute Plane:
    Add 1 or more hosts. When adding the host, label these hosts with ``compute=true``.


http://docs.rancher.com/rancher/v1.5/en/kubernetes/#host-requirements-for-kubernetes

My result:
    with ``etcd=true`` 222MB RAM

    with ``orchestration=true`` 400MB RAM

    with ``compute=true`` 400MB RAM

Backup Rancher server data
--------------------------

.. code-block:: bash

    $ docker stop <container_name_of_original_server>
    $ docker create --volumes-from <container_name_of_original_server> --name rancher-data rancher/server
    $ docker export rancher-data > rancher-data.tar
    $ docker run -d --volumes-from rancher-data --restart=unless-stopped -p 80:8080 rancher/server

    $ docker cp <container_name_of_original_server>:/var/lib/mysql <path on host>


https://docs.rancher.com/rancher/v1.5/en/upgrading/#single-container


Links
------

http://rancher.com/kubernetes/

http://rancher.com/comparing-rancher-orchestration-engine-options/

https://orchestration.io/2016/06/30/deploying-kubernetes-with-rancher/

http://blog.kubernetes.io/2016/07/kubernetes-in-rancher-further-evolution.html

http://rancher.com/cattle-swarm-kubernetes-side-side/

http://docs.rancher.com/rancher/v1.5/en/installing-rancher/installing-server/#single-container

http://docs.rancher.com/rancher/v1.5/en/hosts/#supported-docker-versions


https://github.com/rancher/rancher/wiki/Kubernetes-Management

https://kubernetes.io/docs/user-guide/walkthrough/

http://cdn2.hubspot.net/hubfs/468859/Comparing%20Rancher%20Orchestration%20Engine%20Options.pdf

https://cdn2.hubspot.net/hubfs/468859/Deploying%20and%20Scaling%20Kubernetes%20with%20Rancher%20-%202nd%20ed.pdf
