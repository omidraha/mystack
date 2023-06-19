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


Configure Python virtual environment
------------------------------------

https://www.pulumi.com/docs/languages-sdks/python/

The `Pulumi.yaml` file

.. code-block:: yaml

    name: devops
    runtime:
      name: python
      options:
        virtualenv: /home/or/ws/tools/env/devops
    description: A minimal AWS Python Pulumi program


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


Diagnostics
------------


Unable to validate AWS credentials
-----------------------------------

.. code-block:: bash

  aws:iam:Role (cluster-eksRole-role):
    error: unable to validate AWS credentials.
    Details: validating provider credentials:
    retrieving caller identity from STS: operation error STS: GetCallerIdentity,
    exceeded maximum number of attempts, 3, https response error StatusCode: 0, RequestID: ,
    request send failed, Post "https://sts.us-east-1a.amazonaws.com/":
    dial tcp: lookup sts.us-east-1a.amazonaws.com on 127.0.0.53:53: no such host
    Make sure you have set your AWS region, e.g. `pulumi config set aws:region us-west-2`.


.. code-block:: bash

    pulumi config set aws:region us-east-1a


Instance type is not supported in your requested Availability Zone
------------------------------------------------------------------

.. code-block:: bash

  aws:autoscaling:Group (fixedNodeGroup):
    error: 1 error occurred:
    * updating urn:pulumi:dev::devops::eks:index:NodeGroupV2$aws:autoscaling/group:Group::fixedNodeGroup:
    1 error occurred:
    * waiting for Auto Scaling Group (fixedNodeGroup) capacity satisfied: 1 error occurred:
    * Scaling activity (67962305-f219-b4e1-71a7-74c10480fb32):
    Failed: Your requested instance type (t2.micro) is not supported in your requested Availability Zone (us-west-2d).
    Please retry your request by not specifying an Availability Zone or choosing us-west-2a, us-west-2b, us-west-2c.
    Launching EC2 instance failed.


https://stackoverflow.com/questions/63047064/ec2-launch-failed-your-requested-instance-type-t2-micro-is-not-supported-in

https://repost.aws/knowledge-center/ec2-instance-type-not-supported-az-error

UnsupportedAvailabilityZoneException
------------------------------------

https://github.com/pulumi/pulumi-eks/issues/95#issuecomment-1595237039


View the dependency graph
-------------------------

.. code-block:: bash

    pulumi stack graph g.txt


https://sketchviz.com/new


.. code-block:: bash

    pulumi stack select
    export PULUMI_CONFIG_PASSPHRASE=""
    pulumi stack output kubeconfig > kubeconfig.yml
    export KUBECONFIG=./kubeconfig.yml
    kubectl get nodes
    kubectl get pods
    kubectl get service
    kubectl describe nodes
    kubectl describe pods
    kubectl describe service

Pulumi Kubernetes: API Docs
---------------------------

https://www.pulumi.com/registry/packages/kubernetes/api-docs/

