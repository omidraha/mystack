Tips
====

Install the Pulumi CLI
---------------------

.. code-block:: bash

    curl -fsSL https://get.pulumi.com | sh

    pulumi version


    mkdir devops && cd devops
    pulumi login --local
    pip install pulumi_aws
    pip install pulumi_eks

    pulumi new aws-python

https://www.pulumi.com/docs/install/

Install dependencies
--------------------

Notes
++++++

Creating virtual environment...
The virtual environment was not created successfully because ensurepip is not
available.  On Debian/Ubuntu systems, you need to install the python3-venv
package using the following command.

.. code-block:: bash

    sudo apt install python3.10-venv


Installing aws-python
----------------------

.. code-block:: bash

    pulumi new aws-python

Setup AWS Credentials
---------------------

.. code-block:: bash

    aws configure
    $ cat ~/.aws/credentials


https://www.pulumi.com/registry/packages/aws/installation-configuration/


Pulumi Amazon EKS guide
-----------------------

https://www.pulumi.com/registry/packages/eks/

https://www.pulumi.com/docs/clouds/aws/guides/eks/

https://www.learnaws.org/2021/06/22/aws-eks-alb-controller-pulumi/

https://www.learnaws.org/2021/06/23/python-app-eks-cluster/
