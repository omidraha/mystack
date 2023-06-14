Etcd
====


In order to expose the etcd API to clients outside of the Docker host
you'll need use the host IP address when configuring etcd.

.. code-block:: bash

    $ export HostIP="192.168.1.40"


.. code-block:: bash

    $ docker pull gcr.io/etcd-development/etcd:v3.3.4
    $ rm -rf /tmp/etcd-data.tmp && mkdir -p /tmp/etcd-data.tmp \
        docker rmi gcr.io/etcd-development/etcd:v3.3.4 || true && \
        docker run \
        -p 2379:2379 \
        -p 2380:2380 \
        --mount type=bind,source=/tmp/etcd-data.tmp,destination=/etcd-data \
        --name etcd-gcr-v3.3.4 \
        gcr.io/etcd-development/etcd:v3.3.4 \
        /usr/local/bin/etcd \
        --name s1 \
        --data-dir /etcd-data \
        --listen-client-urls http://0.0.0.0:2379 \
        --advertise-client-urls http://0.0.0.0:2379 \
        --listen-peer-urls http://0.0.0.0:2380 \
        --initial-advertise-peer-urls http://0.0.0.0:2380 \
        --initial-cluster s1=http://0.0.0.0:2380 \
        --initial-cluster-token tkn \
        --initial-cluster-state new


.. code-block:: bash

    $ docker exec etcd-gcr-v3.3.4 /bin/sh -c "/usr/local/bin/etcd --version"
    $ docker exec etcd-gcr-v3.3.4 /bin/sh -c "ETCDCTL_API=3 /usr/local/bin/etcdctl version"
    $ docker exec etcd-gcr-v3.3.4 /bin/sh -c "ETCDCTL_API=3 /usr/local/bin/etcdctl endpoint health"
    $ docker exec etcd-gcr-v3.3.4 /bin/sh -c "ETCDCTL_API=3 /usr/local/bin/etcdctl put foo bar"
    $ docker exec etcd-gcr-v3.3.4 /bin/sh -c "ETCDCTL_API=3 /usr/local/bin/etcdctl get foo"


https://github.com/coreos/etcd/releases

https://coreos.com/etcd/docs/latest/v2/docker_guide.html


