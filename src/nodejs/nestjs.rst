NestJS
======

Install `nodejs` and `npm` by Package manager (old version):


.. code-block:: bash

    sudo apt-get install nodejs npm

Remove `nodejs` and `npm`:

.. code-block:: bash

    sudo apt-get purge nodejs npm

Remove related files:

.. code-block:: bash

    sudo rm -rf /usr/local/bin/npm
    sudo rm -rf /usr/local/share/man/man1/node*
    sudo rm -rf /usr/local/lib/dtrace/node.d
    sudo rm -rf ~/.npm
    sudo rm -rf ~/.node-gyp
    sudo rm -rf /opt/local/bin/node
    sudo rm -rf opt/local/include/node
    sudo rm -rf /opt/local/lib/node_modules
    sudo rm -rf /usr/local/lib/node*
    sudo rm -rf /usr/local/include/node*
    sudo rm -rf /usr/local/bin/node*


Install `nodejs` and `npm` latest version:

.. code-block:: bash

    curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
    source ~/.bashrc
    chmod +x  ~/.nvm/nvm.sh
    nvm install 22
    node -v # should print `v22.2.0`
    npm -v # should print `10.7.0`

https://nodejs.org/en/download/package-manager


Install `nestjs` without `sudo`:

.. code-block:: bash

    npm i -g @nestjs/cli

Create a project:

.. code-block:: bash

    nest new my-app

Run project:

.. code-block:: bash

    npm run start

.. code-block:: bash

    npm i -D @swc/cli @swc/cor
    npm run start -- -b swc


.. code-block:: bash

    npm run lint
    npm run format

