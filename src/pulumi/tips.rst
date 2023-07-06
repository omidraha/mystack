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
    kubectl get nodes -A -o wide
    kubectl get pods -A -o wide
    kubectl get service -A -o wide
    kubectl get namespaces -A -o wide
    kubectl get deployment -A -o wide
    kubectl describe nodes
    kubectl describe pods
    kubectl describe service

Pulumi Kubernetes: API Docs
---------------------------

https://www.pulumi.com/registry/packages/kubernetes/api-docs/

State
------

Manually delete a resource that no longer exists because of dependents

.. code-block:: bash

    pulumi refresh
    pulumi stack export --file stack.json
    # Lookup `urn:pulumi` in resources section
    cat stack.json | grep -i urn:pulumi
    pulumi state delete 'urn:pulumi:dev::****' --target-dependents -y

.. code-block:: bash

    # Get the current stack as json:
    pulumi stack export --file stack.json
    # Delete what you don't want from your stack file and then:
    pulumi stack import --file stack.json

https://www.pulumi.com/docs/cli/commands/pulumi_state_delete/

https://stackoverflow.com/a/68651488

https://artifacthub.io/packages/helm/bitnami/external-dns


aws Load balancer
-----------------

The `Load balancer` file:

.. code-block:: bash

    kubernetes.helm.v3.Chart(
            "lb",
            kubernetes.helm.v3.ChartOpts(
                chart="aws-load-balancer-controller",
                fetch_opts=kubernetes.helm.v3.FetchOpts(
                    repo="https://aws.github.io/eks-charts"
                ),
                namespace=namespace.metadata["name"],
                values={
                    "logLevel": "debug",
                    "region": "us-west-2",
                    "replicaCount": "1",
                    "serviceAccount": {
                        "name": "aws-lb-controller-serviceaccount",
                        "create": False,
                    },
                    "vpcId": vpc.vpc_id,
                    "clusterName": cluster_name,
                    "podLabels": {
                        "app": "aws-lb-controller"
                    },
                    "autoDiscoverAwsRegion": "true",
                    "autoDiscoverAwsVpcID": "true",
                    "keepTLSSecret": True,
                },
            ),
            pulumi.ResourceOptions(
                provider=provider,
                parent=namespace
            )
        )

The `Ingress` file:

.. code-block:: python

    kubernetes.networking.v1.Ingress(
        "ingress",
        metadata=kubernetes.meta.v1.ObjectMetaArgs(
            name='ingress',
            namespace=namespace.metadata["name"],
            annotations={
                "kubernetes.io/ingress.class": "alb",
                "alb.ingress.kubernetes.io/target-type": "instance",
                "alb.ingress.kubernetes.io/scheme": "internet-facing",
                'external-dns.alpha.kubernetes.io/hostname': 'app1.example.com,app2.example.com',
                # 'alb.ingress.kubernetes.io/listen-ports': '[{"HTTPS":443}, {"HTTP":80}]',
            },
            labels={
                'app': 'ingress'
            },
        ),
        spec=kubernetes.networking.v1.IngressSpecArgs(
            rules=[
                kubernetes.networking.v1.IngressRuleArgs(
                    host='app1.example.com',
                    http=kubernetes.networking.v1.HTTPIngressRuleValueArgs(
                        paths=[
                            kubernetes.networking.v1.HTTPIngressPathArgs(
                                path="/",
                                path_type="Prefix",
                                backend=kubernetes.networking.v1.IngressBackendArgs(
                                    service=kubernetes.networking.v1.IngressServiceBackendArgs(
                                        name=service_app_01.metadata.name,
                                        port=kubernetes.networking.v1.ServiceBackendPortArgs(
                                            number=80,
                                        ),
                                    ),
                                ),
                            ),
                        ],
                    ),
                ),
                kubernetes.networking.v1.IngressRuleArgs(
                    host='app2.example.com',
                    http=kubernetes.networking.v1.HTTPIngressRuleValueArgs(
                        paths=[
                            kubernetes.networking.v1.HTTPIngressPathArgs(
                                path="/",
                                path_type="Prefix",
                                backend=kubernetes.networking.v1.IngressBackendArgs(
                                    service=kubernetes.networking.v1.IngressServiceBackendArgs(
                                        name=service_app_02.metadata.name,
                                        port=kubernetes.networking.v1.ServiceBackendPortArgs(
                                            number=80,
                                        ),
                                    ),
                                ),
                            ),
                        ],
                    ),
                )
            ],
        ),
        opts=pulumi.ResourceOptions(provider=provider)
    )

The `External dns` file:

.. code-block:: python

    kubernetes.helm.v3.Chart(
        "external-dns",
        kubernetes.helm.v3.ChartOpts(
            chart="external-dns",
            fetch_opts=kubernetes.helm.v3.FetchOpts(
                repo="https://charts.bitnami.com/bitnami"
            ),
            namespace=namespace.metadata["name"],
            values={
                "logLevel": "debug",
                'provider': 'cloudflare',
                'sources': ['ingress'],
                'domainFilters': ['example.com'],
                "cloudflare": {
                    "apiToken": 'token',
                    "email": 'email',
                    'cloudflare-dns-records-per-page': '5000',
                    'proxied': False,
                },
                "replicaCount": "1",
                "region": "us-west-2",
            },
        ),
        pulumi.ResourceOptions(
            provider=provider,
            parent=namespace
        )
    )



Create AWS Certificate Manager and DNS Validation with Cloud flare
==================================================================


.. code-block:: python

    import pulumi
    import pulumi_aws as aws
    import pulumi_cloudflare as cloudflare

    # Create Certificate for primary domain and wild subdomain
    certificate = aws.acm.Certificate("cert",
        domain_name="example.com",
        validation_method="DNS",
        subjectAlternativeNames=["*.example.com"])

    # Create a Cloudflare Record
    record = cloudflare.Record("record",
        type="CNAME",
        name="_example",
        value=f"{certificate.domain_validation_options[0].resource_record_name}.cloudflare.com",
        zone_id="abcdef")

    # Create Certificate Validation
    certificate_validation = aws.acm.CertificateValidation("certificateValidation",
        certificate_arn=certificate.arn,
        validation_record_fqdns=[])

    # Export the ARN of the validation certificate
    pulumi.export('certificateValidationArn', certificate_validation.certificate_arn)


https://us-west-2.console.aws.amazon.com/acm/home

https://www.pulumi.com/registry/packages/aws/api-docs/acm/certificatevalidation/

https://github.com/kubernetes-sigs/aws-load-balancer-controller/blob/main/docs/guide/ingress/annotations.md

https://kubernetes-sigs.github.io/aws-load-balancer-controller/v2.2/guide/ingress/annotations/

https://kubernetes-sigs.github.io/aws-load-balancer-controller/v2.4/guide/tasks/ssl_redirect/


Stack
*****

.. code-block:: bash

    pulumi stack ls
    pulumi stack select dev

https://www.pulumi.com/docs/concepts/stack/


Set secret
**********

Enter your passphrase to unlock config/secrets
    (set PULUMI_CONFIG_PASSPHRASE or PULUMI_CONFIG_PASSPHRASE_FILE to remember):

.. code-block:: bash

     export PULUMI_CONFIG_PASSPHRASE=''

Custom timeout
**************

.. code-block:: bash

    from pulumi import CustomTimeouts

    kubernetes.helm.v3.Chart(
        "lb",
        kubernetes.helm.v3.ChartOpts(
            chart="aws-load-balancer-controller",
            fetch_opts=kubernetes.helm.v3.FetchOpts(
                repo="https://aws.github.io/eks-charts"
            ),
            namespace=namespace.metadata["name"],
            values={
                "logLevel": "debug",
            },
        ),
        pulumi.ResourceOptions(
            provider=provider,
            parent=namespace,
            custom_timeouts=CustomTimeouts(create='3m')
        )
    )


