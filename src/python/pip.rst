PIP
===

Install pip
-----------

.. code-block:: bash

	# install setup tools
	curl https://bitbucket.org/pypa/setuptools/raw/bootstrap/ez_setup.py | python -
	# install pip
	curl -L https://raw.github.com/pypa/pip/master/contrib/get-pip.py | python -


Use mirror to install packages
------------------------------
`What to do when PyPI goes down <http://jacobian.org/writing/when-pypi-goes-down/>`_

Using Mirror Flag:

.. code-block:: bash

	pip install --use-mirrors Django==1.6

virtualenv
----------

install it:

.. code-block:: bash

	pip install virtualenv

make a virtualenv:

.. code-block:: bash

	cd /srv/www/project_name

	virtualenv --no-site-packages env

start it:

.. code-block:: bash

	source env/bin/activate

install package manager:

.. code-block:: bash

	pip install django==1.8


pyenv
-----

Install build packages:

.. code-block:: bash

	$ sudo apt-get install -y make build-essential libssl-dev zlib1g-dev libbz2-dev \
            libreadline-dev libsqlite3-dev wget curl llvm libncurses5-dev libncursesw5-dev xz-utils

https://github.com/yyuu/pyenv/wiki/Common-build-problems

Install `pyenv`:

.. code-block:: bash

	$ curl -L https://raw.githubusercontent.com/yyuu/pyenv-installer/master/bin/pyenv-installer | bash

Add pyenv configuration to ``~/.bashrc``:

.. code-block:: bash

	$ vim ~/.bash_profile

	# pyenv
	export PATH="/home/or/.pyenv/bin:$PATH"
	eval "$(pyenv init -)"
	eval "$(pyenv virtualenv-init -)"

Update it:

.. code-block:: bash

	$ Update

Uninstall it:

.. code-block:: bash

	$ rm -rf `pyenv root`


Walk through:

.. code-block:: bash

	$ pyenv global
	$ pyenv versions
	* system (set by ~/.pyenv/version)
	$ pyenv install 2.7.5
	$ pyenv global 2.7.5


Using pyenv virtualenv with pyenv:

.. code-block:: bash

	$ pyenv virtualenv 2.7.5 env2.7.5
	$ pyenv virtualenvs
	$ pyenv activate env2.7.5
	$ python -V
	$ pip list
	$ pyenv deactivate
	$ which python


Sets the location where python-build stores temporary files

.. code-block:: bash

	$ export TMPDIR="/tmp/pyenv"

See all available versions:

.. code-block:: bash

    $ pyenv install --list


https://github.com/yyuu/pyenv-virtualenv

https://github.com/yyuu/pyenv-installer

http://amaral-lab.org/resources/guides/pyenv-tutorial

https://github.com/yyuu/pyenv/blob/master/plugins/python-build/README.md#special-environment-variables




Fixed bz2 warnings
------------------

WARNING: The Python bz2 extension was not compiled. Missing the bzip2 lib?

.. code-block:: bash

    $ sudo apt-get install libbz2-dev


How do I install python-ldap in a virtualenv?
---------------------------------------------

.. code-block:: bash

    $ apt-get install libsasl2-dev python-dev libldap2-dev libssl-dev
    $ (env) pip install python-ldap



Install python modules without root access
------------------------------------------

.. code-block:: bash

    $ pip install --user package_name


Pip install from git repo branch
--------------------------------

.. code-block:: bash

    $ pip install git+https https://github.com/ansible/ansible


