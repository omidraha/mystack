Tips
====


run npm command gives error "/usr/bin/env: node: No such file or directory"
---------------------------------------------------------------------------

.. code-block:: bash

    $ ln -s /usr/bin/nodejs /usr/bin/node"

https://github.com/nodejs/node-v0.x-archive/issues/3911

Grunt “Command Not Found” Error in Terminal
-------------------------------------------

.. code-block:: bash

    $ RUN npm install -g grunt-cli


Install Yarn
------------


Remove mistake packages:

.. code-block:: bash

    sudo apt-get purge cmdtest
    sudo apt-get purge yarn


Install with npm (Recommended way):

It is recommended to install Yarn through the npm package manager, which comes bundled with Node.js when you install it on your system.

Once you have npm installed you can run the following both to install and upgrade Yarn:

.. code-block:: bash

    npm install --global yarn

Alternative way (Debian / Ubuntu):

.. code-block:: bash

    curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
    echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list

    sudo apt update && sudo apt install yarn


Upgrade Node
------------

.. code-block:: bash

    sudo npm cache clean -f
    sudo npm install -g n
    sudo n stable
    sudo n latest
