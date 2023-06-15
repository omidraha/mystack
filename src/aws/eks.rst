EKS
===

Applications running on
Amazon EKS are fully compatible with applications running on any standard Kubernetes environment,
whether running in on-premises data centers or public clouds, easing porting from other Kubernetes environments to EKS.

Install kubectl
---------------

.. code-block:: bash

    curl -O https://s3.us-west-2.amazonaws.com/amazon-eks/1.27.1/2023-04-19/bin/linux/amd64/kubectl
    chmod +x ./kubectl
    kubectl version  --output=json


Install eksctl
--------------


https://github.com/weaveworks/eksctl/blob/main/README.md#for-unix

.. code-block:: bash

    # for ARM systems, set ARCH to: `arm64`, `armv6` or `armv7`
    ARCH=amd64
    PLATFORM=$(uname -s)_$ARCH
    curl -sLO "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$PLATFORM.tar.gz"
    tar -xzf eksctl_$PLATFORM.tar.gz -C /tmp && rm eksctl_$PLATFORM.tar.gz
    mv /tmp/eksctl  ~/ws/tools/k8s/bin/

    eksctl version

https://eksctl.io/introduction/


Install aws
------------


https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html

.. code-block:: bash

    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
    unzip awscliv2.zip
    sudo ./aws/install

    aws --version
    aws configure


Create AWS access key
----------------------


https://us-east-1.console.aws.amazon.com/iamv2/home#/users/details/dev/create-access-key


.. code-block:: bash

    aws sts get-caller-identity

    {
        "UserId": "XXX",
        "Account": "XXX",
        "Arn": "arn:aws:iam::XXX:user/XXX"
    }




Amazon Elastic Kubernetes Service
---------------------------------

Amazon Elastic Kubernetes Service (Amazon EKS) is a managed Kubernetes service
to run Kubernetes in the AWS cloud and on-premises data centers. In the cloud,
Amazon EKS automatically manages the availability and scalability of the Kubernetes control plane nodes responsible
for scheduling containers, managing application availability,
storing cluster data, and other key tasks. With Amazon EKS,
you can take advantage of all the performance, scale, reliability,
and availability of AWS infrastructure, as well as integrations with AWS networking and security services.
On-premises, EKS provides a consistent,
fully-supported Kubernetes solution with integrated tooling and simple deployment to AWS Outposts,
virtual machines, or bare metal servers.

https://aws.amazon.com/eks/

Amazon EKS Distro
-----------------

https://aws.amazon.com/eks/eks-distro/

https://aws.amazon.com/eks/eks-distro/faqs/

https://distro.eks.amazonaws.com/

https://github.com/aws/eks-distro

Amazon EKS Anywhere
-------------------

https://aws.amazon.com/eks/eks-anywhere/

https://aws.amazon.com/eks/eks-anywhere/faqs/

https://github.com/aws/eks-anywhere

Nodes type
-----------

Self-managed node groups

Managed node groups

AWS fargate

https://www.youtube.com/watch?v=cipDJwDWWbY

https://www.youtube.com/watch?v=p6xDCz00TxU


Links
-----

https://github.com/bottlerocket-os/bottlerocket

https://docs.aws.amazon.com/eks/latest/userguide/eks-compute.html
