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

Auto Scaling groups
-------------------


.. code-block:: bash
    aws configure
        # AWS Access Key ID [****************]:
        # AWS Secret Access Key [****************]:
        # Default region name [None]: us-west-2
        # Default output format [json]:
    aws autoscaling describe-launch-configurations
    aws autoscaling delete-launch-configuration --launch-configuration-name  eks-cluster-nodeLaunchConfiguration-example


https://us-east-2.console.aws.amazon.com/ec2/home?region=us-east-2#AutoScalingGroups:

https://docs.aws.amazon.com/autoscaling/ec2/userguide/launch-templates.html?icmpid=docs_ec2as_lc_banner

https://docs.aws.amazon.com/autoscaling/ec2/userguide/launch-configurations.html?icmpid=docs_ec2as_help_panel


Traceback

.. code-block:: bash

    An error occurred (ResourceInUse) when calling the DeleteLaunchConfiguration operation:
    Cannot delete launch configuration eks-cluster-nodeLaunchConfiguration-example
    because it is attached to AutoScalingGroup eks-cluster-example-NodeGroup-EXAMPLE


How to Set Up Ingress Controller in AWS EKS
-------------------------------------------

When you try to create a Service with an Ingress to receive traffic from the internet.
Your Service resource should be of type NodePort for the Ingress Controller
to be able to create the TargetGroupBindings.
It makes sense in AWS's world because if the Service is only exposed as a ClusterIP,
the AWS Load Balancer cannot talk to that since it's only exposed inside the cluster and is effectively inaccessible
from even the host worker node itself.


https://towardsdatascience.com/how-to-set-up-ingress-controller-in-aws-eks-d745d9107307

https://towardsdatascience.com/manage-your-aws-eks-load-balancer-like-a-pro-7ca599e081ca


Creating an IAM OIDC provider for your cluster
----------------------------------------------

Your cluster has an OpenID Connect (OIDC) issuer URL associated with it.
To use AWS Identity and Access Management (IAM) roles for service accounts,
an IAM OIDC provider must exist for your cluster's OIDC issuer URL


.. code-block:: bash

    aws iam list-open-id-connect-providers

https://docs.aws.amazon.com/eks/latest/userguide/enable-iam-roles-for-service-accounts.html


Ingress vs Load balancer
------------------------

https://www.nginx.com/blog/aws-alb-vs-nginx-plus/

https://stackoverflow.com/questions/45079988/ingress-vs-load-balancer/45084511#45084511

NodePort vs ClusterIP for services using in Load balancer with ingress
----------------------------------------------------------------------

https://kubernetes-sigs.github.io/aws-load-balancer-controller/v2.4/guide/ingress/annotations/#target-type

https://towardsdatascience.com/how-to-set-up-ingress-controller-in-aws-eks-d745d9107307

https://medium.com/google-cloud/kubernetes-nodeport-vs-loadbalancer-vs-ingress-when-should-i-use-what-922f010849e0


IngressGroup
************

https://kubernetes-sigs.github.io/aws-load-balancer-controller/v2.1/guide/ingress/annotations/#ingressgroup

https://repost.aws/questions/QUEyFKpZCBR_OTFMQlJNypaQ/ingress-annotations-only-for-a-specific-path

https://github.com/kubernetes-sigs/aws-load-balancer-controller/issues/914

TargetGroupBinding
******************

https://kubernetes-sigs.github.io/aws-load-balancer-controller/v2.4/guide/targetgroupbinding/targetgroupbinding/

use-annotation
**************

https://kubernetes-sigs.github.io/aws-load-balancer-controller/v2.4/guide/ingress/annotations/

.. code-block:: yaml

    spec:
      rules:
      - http:
          paths:
          - pathType: Prefix
            path: /
            backend:
              service:
                name: ssl-redirect
                port:
                  name: use-annotation


CustomResource
***************

.. code-block:: bash

    $ kubectl get customresourcedefinition -A -o wide
    $ kubectl get crd -A -o wide

    NAME                                         CREATED AT
    eniconfigs.crd.k8s.amazonaws.com             2023-07-05T20:38:05Z
    securitygrouppolicies.vpcresources.k8s.aws   2023-07-05T20:38:08Z
    targetgroupbindings.elbv2.k8s.aws            2023-07-06T19:23:52Z

    $ kubectl delete crd targetgroupbindings.elbv2.k8s.aws


https://docs.aws.amazon.com/elasticloadbalancing/latest/network/delete-target-group.html

https://us-west-2.console.aws.amazon.com/ec2/home?region=us-west-2#TargetGroups:

https://stackoverflow.com/questions/52009124/not-able-to-completely-remove-kubernetes-customresource

https://github.com/kubernetes/kubernetes/issues/60538


Custom resources with finalizers can "deadlock"
***********************************************


.. code-block:: bash

    $ kubectl get customresourcedefinition -A -o wide
    $ kubectl get crd -A -o wide

    NAME                                         CREATED AT
    eniconfigs.crd.k8s.amazonaws.com             2023-07-05T20:38:05Z
    securitygrouppolicies.vpcresources.k8s.aws   2023-07-05T20:38:08Z
    targetgroupbindings.elbv2.k8s.aws            2023-07-06T19:23:52Z

    $ kubectl patch crd/targetgroupbindings.elbv2.k8s.aws -p '{"metadata":{"finalizers":[]}}' --type=merge
    $ kubectl delete crd targetgroupbindings.elbv2.k8s.aws


https://github.com/kubernetes/kubernetes/issues/60538

https://stackoverflow.com/questions/52009124/not-able-to-completely-remove-kubernetes-customresource

Ingres resources are not getting deleted even though the alb ingress controller deployment is deleted
======================================================================================================

.. code-block:: bash

    kubectl get ingress -A
        NAMESPACE   NAME                         CLASS    HOSTS   ADDRESS   PORTS   AGE
        default     app-ingress-dev-1-313614b6   <none>   *                 80      21h

    kubectl delete ingress app-ingress-dev-1-**
        ingress.networking.k8s.io "app-ingress-dev-1-313614b6" deleted

    kubectl patch ingress app-ingress-dev-1-**  -n default -p '{"metadata":{"finalizers":[]}}' --type=merge


https://github.com/kubernetes-sigs/aws-load-balancer-controller/issues/1629#issuecomment-731011683



CloudFormation
==============


https://us-west-2.console.aws.amazon.com/cloudformation/home?region=us-west-2

ALB Target type
================

The AWS Load Balancer Controller supports the following traffic modes:

    `alb.ingress.kubernetes.io/target-type: instance`

    `alb.ingress.kubernetes.io/target-type: ip`

Instance

    Registers nodes within your cluster as targets for the ALB.
    Traffic reaching the ALB is routed to NodePort for your service and then proxied to your Pods.
    This is the default traffic mode.
    You can also explicitly specify it with the `alb.ingress.kubernetes.io/target-type`: `instance` annotation.

Your Kubernetes service must specify the `NodePort` or `LoadBalancer` type to use this traffic mode.

IP

    Registers Pods as targets for the ALB.
    Traffic reaching the ALB is directly routed to Pods for your service.
    You must specify the `alb.ingress.kubernetes.io/target-type`: `ip` annotation to use this traffic mode.
    The IP target type is required when target Pods are running on Fargate.


https://docs.aws.amazon.com/eks/latest/userguide/alb-ingress.html


Increase the amount of available IP addresses for your Amazon EC2 nodes
=======================================================================

MINIMUM_IP_TARGET guarantees minimum number of IPs provisioned on the worker node
(these IPs can either be used by Pods or stay in the pool as available)
where as WARM_IP_TARGET number guarantees those many IPs are available
in the pool all the time (if there is enough IP space available in your subnet).

.. code-block:: bash

    aws ec2 describe-network-interfaces
    kubectl describe daemonset aws-node -n kube-system
    kubectl set env daemonset aws-node -n kube-system WARM_IP_TARGET=5
    kubectl describe ds aws-node -n kube-system | grep -i WARM_IP_TARGET


https://repost.aws/knowledge-center/vpc-find-owner-unknown-ip-addresses

https://docs.amazonaws.cn/en_us/eks/latest/userguide/cni-increase-ip-addresses.html

https://github.com/aws/amazon-vpc-cni-k8s/issues/853#issuecomment-591615159

https://medium.com/@maartenfuchs/ip-address-allocation-for-aws-eks-cc046310cdda

https://docs.aws.amazon.com/eks/latest/userguide/cni-increase-ip-addresses.html

https://github.com/aws/amazon-vpc-cni-k8s/blob/master/docs/prefix-and-ip-target.md


Bitbucket Pipelines Pipe: AWS ECR push image
============================================

https://bitbucket.org/atlassian/aws-ecr-push-image/src/master/


Share single ALB with multiple Ingress
======================================

https://catalog.workshops.aws/eks-immersionday/en-US/services-and-ingress/multi-ingress


The "webidentityerr" error when using AWS Load Balancer Controller in Amazon EKS
================================================================================

.. code-block:: bash

    $ kubectl get  deploy -A
    NAMESPACE                 NAME                                    READY   UP-TO-DATE   AVAILABLE   AGE
    aws-lb-controller-dev-1   external-dns-dev-1                      1/1     1            1           47h
    aws-lb-controller-dev-1   lb-dev-1-aws-load-balancer-controller   1/1     1            1           136m
    aws-lb-controller-dev-1   sso-1ae40dfe                            1/1     1            1           23h
    kube-system               coredns                                 2/2     2            2           47h

    $ kubectl describe deploy lb-dev-1-aws-load-balancer-controller  -n aws-lb-controller-dev-1 | grep -i "Service Account"
    Service Account:  aws-lb-controller-sa-dev-1-13d88aed

    $ kubectl get serviceaccount -n aws-lb-controller-dev-1 -o wide
    NAME                                  SECRETS   AGE
    aws-lb-controller-sa-dev-1-13d88aed   0         7m4s
    aws-load-balancer-controller          0         88m
    default                               0         47h
    external-dns-dev-1                    0         47h

    $ kubectl describe sa  aws-lb-controller-sa-dev-1-13d88aed   -n aws-lb-controller-dev-1
    Name:                aws-lb-controller-sa-dev-1-13d88aed
    Namespace:           aws-lb-controller-dev-1
    Labels:              app.kubernetes.io/managed-by=pulumi
    Annotations:         eks.amazonaws.com/role-arn: arn:aws:iam::146899233417:role/aws-loadbalancer-controller-role-dev-1-ef33856
                         pulumi.com/autonamed: true
    Image pull secrets:  <none>
    Mountable secrets:   <none>
    Tokens:              <none>
    Events:              <none>

https://repost.aws/knowledge-center/eks-load-balancer-webidentityerr


aws-load-balancer-type annotations
==================================

.. code-block:: bash

    service.beta.kubernetes.io/aws-load-balancer-type

The AWS Load Balancer Controller manages Kubernetes Services in a compatible way with the legacy aws cloud provider.
The annotation service.beta.kubernetes.io/aws-load-balancer-type is used to determine which controller reconciles the service.
If the annotation value is nlb-ip or external,
legacy cloud provider ignores the service resource (provided it has the correct patch)
so that the AWS Load Balancer controller can take over. For all other values of the annotation,
the legacy cloud provider will handle the service.
Note that this annotation should be specified during service creation and not edited later.


https://kubernetes-sigs.github.io/aws-load-balancer-controller/v2.2/guide/service/annotations/#lb-type

https://docs.aws.amazon.com/eks/latest/userguide/network-load-balancing.html

https://cloud.google.com/anthos/clusters/docs/multi-cloud/aws/how-to/network-load-balancing#internet-facing-nlb

Elastic network interfaces
==========================

https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-eni.html?shortFooter=true#AvailableIpPerENI

Increase the amount of available IP addresses for your Amazon EC2 nodes
=======================================================================

https://docs.aws.amazon.com/eks/latest/userguide/cni-increase-ip-addresses.html

Increase the number of pods limit per Kubernetes Node
=====================================================

https://github.com/terraform-aws-modules/terraform-aws-eks/issues/2297

https://github.com/pulumi/pulumi-eks/issues/611

https://github.com/pulumi/pulumi-eks/issues/633

https://github.com/pulumi/pulumi-eks/issues/609

.. code-block:: bash

        node_user_data="""
          #!/bin/bash
          set -o xtrace
          /etc/eks/bootstrap.sh --apiserver-endpoint '${var.cluster_endpoint}' --b64-cluster-ca '${var.cluster_ca_data}' '${var.cluster_name}' --use-max-pods false --kubelet-extra-args '--max-pods=6'
        """,

VPC
===

https://docs.aws.amazon.com/eks/latest/userguide/network_reqs.html

https://aws.github.io/aws-eks-best-practices/networking/subnets/

https://github.com/pulumi/examples/blob/master/aws-py-eks/vpc.py


List of all pods and its nodes
===============================

.. code-block:: bash

    kubectl get pod -o=custom-columns=NODE:.spec.nodeName,NAME:.metadata.name -A
    kubectl get pod -o=custom-columns=NAME:.metadata.name,STATUS:.status.phase,NODE:.spec.nodeName -A

If I need to expose services to the internet using ALB AWS,
such as app1.example.com, app2.example.com,
and these services also require access to the internet,
Do I need to have public subnet?
Yes you need public subnets,
because the ALB has to have a public IP address,
and the only way to get that public IP address is to be in a public subnet for a VPC-based service (like EKS)
All your EKS nodes should be in a private subnet tho. Nothing goes in a public subnet (usually, there are rare exceptions) but your load balancers. EKS can create and manage the LBs for you. That'll probably be easier.
A single NAT Gateway is good for cost savings if you're doing a proof of concept. For a production workload, you want 3 AZs (this is the default) and you'll want a NAT Gateway in each AZ. One per AZ (also the default) is more reliable. I A single NAT Gateway is cheaper.

