Vagrant
=======


Quick Guide to Vagrant on Amazon EC2
------------------------------------

https://github.com/mitchellh/vagrant-aws

http://www.cantoni.org/2014/09/22/quick-guide-vagrant-amazon-ec2


http://www.devopsdiary.com/blog/2013/05/07/automated-deployment-of-aws-ec2-instances-with-vagrant-and-puppet/


How to use vagrant in a proxy enviroment?
-----------------------------------------



.. code-block:: bash

    $ vagrant plugin install vagrant-proxyconf
    $ vim   ~/.vagrant.d/Vagrantfile
        Vagrant.configure("2") do |config|
          if Vagrant.has_plugin?("vagrant-proxyconf")
            config.proxy.http     = "http:///192.168.1.234:8080/"
            config.proxy.https    = "http://192.168.1.234:8080/"
            config.proxy.no_proxy = "localhost,127.0.0.1,.example.com"
          end
          # ... other stuff
        end


https://github.com/tmatilai/vagrant-proxyconf

http://stackoverflow.com/questions/19872591/how-to-use-vagrant-in-a-proxy-enviroment


Disable or remove the proxy
---------------------------

..  code-block:: bash

    $ VAGRANT_HTTP_PROXY="" VAGRANT_HTTPS_PROXY="" vagrant up --no-provision
    $ vagrant ssh
    $ curl 'https://api.ipify.org?format=json'


Install ubuntu
--------------

..  code-block:: bash

    $ vagrant init ubuntu/trusty64
    $ vagrant init ubuntu/xenial64
    $ vagrant up --provider virtualbox
    $ vagrant ssh


Multi-Machine
-------------

https://atlas.hashicorp.com/ubuntu

https://www.vagrantup.com/docs/multi-machine/

CPU and Memory
--------------

https://www.vagrantup.com/docs/virtualbox/configuration.html


.. code-block:: bash

    config.vm.provider "virtualbox" do |v|
      v.memory = 1024
      v.cpus = 2
    end


Update plugin
-------------

Plugin Update

.. code-block:: bash

    vagrant plugin update [<name>]
