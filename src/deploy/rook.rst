Rook
====


Deploy CephFS on kubernetes with rook
-------------------------------------

.. code-block:: bash

    $ mkdir rook
    $ cd rook
    $ wget https://raw.githubusercontent.com/rook/rook/master/cluster/examples/kubernetes/ceph/operator.yaml
    $ wget https://raw.githubusercontent.com/rook/rook/master/cluster/examples/kubernetes/ceph/cluster.yaml

To disable TLS, edit ``cluster.yaml`` file:

.. code-block:: yaml

  dashboard:
    enabled: true
    # serve the dashboard under a subpath (useful when you are accessing the dashboard via a reverse proxy)
    # urlPrefix: /ceph-dashboard
    # serve the dashboard at the given port.
    # port: 8443
    # serve the dashboard using SSL
    ssl: false

Ingress file, ``ingress.yaml``:

.. code-block:: yaml

    apiVersion: extensions/v1beta1
    kind: Ingress
    metadata:
      name: rook-ceph-mgr-dashboard
      namespace: rook-ceph
      annotations:
        kubernetes.io/ingress.class: "nginx"
      rules:
      - host: rook-ceph.example.com
        http:
          paths:
          - path: /
            backend:
              serviceName: rook-ceph-mgr-dashboard
              servicePort: 8443


.. code-block:: bash

    $ kubectl create -f operator.yaml
    $ kubectl create -f cluster.yaml
    $ kubectl create -f ingress.yaml


.. code-block:: bash

    $ kubectl -n rook-ceph get pod
    '
        NAME                                     READY   STATUS      RESTARTS   AGE
        rook-ceph-mgr-a-ffc44857d-xgh4d          1/1     Running     0          50m
        rook-ceph-mon-a-86f5fc4bc-clmfb          1/1     Running     0          51m
        rook-ceph-mon-b-7955f84c5c-sqhvj         1/1     Running     0          51m
        rook-ceph-mon-c-684556d465-sfmvv         1/1     Running     0          51m
        rook-ceph-osd-0-65968b6d86-wkdxt         1/1     Running     0          50m
        rook-ceph-osd-prepare-ubuntu-191-h6g2x   0/2     Completed   0          50m
    '
    $ kubectl -n rook-ceph get service
    '
        NAME                      TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
        rook-ceph-mgr             ClusterIP   10.43.148.58    <none>        9283/TCP   50m
        rook-ceph-mgr-dashboard   ClusterIP   10.43.89.64     <none>        8443/TCP   50m
        rook-ceph-mon-a           ClusterIP   10.43.117.36    <none>        6789/TCP   51m
        rook-ceph-mon-b           ClusterIP   10.43.164.245   <none>        6789/TCP   51m
        rook-ceph-mon-c           ClusterIP   10.43.205.134   <none>        6789/TCP   51m
    '
    $ kubectl -n rook-ceph-system get pod

    '    NAME                                  READY   STATUS    RESTARTS   AGE
        rook-ceph-agent-w6lsz                 1/1     Running   0          1h
        rook-ceph-operator-5496d44d7c-wnm7h   1/1     Running   0          1h
        rook-discover-b4v7g
    '
    $ kubectl -n rook-ceph get ingress
    '
        NAME                      HOSTS                   ADDRESS         PORTS     AGE
        rook-ceph-mgr-dashboard   rook-ceph.example.com   192.168.0.191   80   13m
    '

Now browse:

    http://rook-ceph.example.com/#/dashboard

Username is admin and password is:

.. code-block:: bash

    $  kubectl -n rook-ceph get secret rook-ceph-dashboard-password -o jsonpath="{['data']['password']}" | base64 --decode && echo


https://github.com/rook/rook/issues/2433
