Ansible
-------


Ansible’s unique feature set

 Based on an agent-less architecture (unlike Chef or Puppet).
 Accessed mostly through SSH (it also has local and paramiko modes).
 No custom security infrastructure is required.
 Configurations (playbooks, modules etc.) written in the easy-to-use YML format.
 Shipped with more than 250 built-in modules.
 Full configuration management, orchestration, and deployment capability.
 Ansible interacts with its clients either through playbooks or a command-line tool.


http://cloudacademy.com/blog/ansible-aws/

http://docs.ansible.com/ansible/guide_aws.html

http://docs.ansible.com/ansible/ec2_module.html

https://aws.amazon.com/blogs/apn/getting-started-with-ansible-and-dynamic-amazon-ec2-inventory-management/

Install
=======

.. code-block:: bash

    $ sudo pip install ansible



.. code-block:: bash

    $ sudo apt-add-repository -y ppa:ansible/ansible
    $ sudo apt-get update
    $ sudo apt-get install -y ansible

