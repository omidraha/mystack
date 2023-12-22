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
