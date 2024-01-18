k3s
===

.. code-block:: bash

    systemctl daemon-reload
    systemctl restart k3s



.. code-block:: bash

    sudo systemctl status k3s
    sudo journalctl -u k3s

Install
-------

.. code-block:: bash

    curl -sLS https://get.k3sup.dev | sh
    sudo install k3sup /usr/local/bin/

.. code-block:: bash

    export IP=192.168.0.1
    k3sup install --ip $IP --user ubuntu --k3s-extra-args '--disable traefik'


Deploy Nginx instead of Traefik as your ingress controller on K3s
------------------------------------------------------------------

https://www.suse.com/support/kb/doc/?id=000020082


DNS
---

https://ranchermanager.docs.rancher.com/v2.5/troubleshooting/other-troubleshooting-tips/dns#check-if-dns-pods-are-running

https://blog.differentpla.net/blog/2022/02/25/pod-dns-problems/

Clean unused kubernetes docker images
--------------------------------------

.. code-block:: bash

    sudo k3s crictl images
    sudo k3s crictl rmi --prune
