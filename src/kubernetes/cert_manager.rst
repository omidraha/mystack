Cert manager
============


Steps:

1. Install Cert-manager onto your cluster
2. Add LetsEncrypt as an Issuer (or ClusterIssuer)
3. Update ingress to use certificate


cert-manager
------------

The `cert-manager` is a Kubernetes tool that issues certificates from various certificate providers,
including Let’s Encrypt.



Issuers
-------

Issuers (and ClusterIssuers) represent a certificate authority from which signed x509 certificates can be obtained,
such as Let’s Encrypt.

You will need at least one Issuer or ClusterIssuer to begin issuing certificates within your cluster.

An Issuer is a namespace resource,
you will need to create an Issuer in each namespace you wish to obtain Certificates in.



https://dev.to/ileriayo/adding-free-ssltls-on-kubernetes-using-certmanager-and-letsencrypt-a1l

https://medium.com/avmconsulting-blog/encrypting-the-certificate-for-kubernetes-lets-encrypt-805d2bf88b2a

https://cert-manager.io/docs/configuration/

https://cert-manager.io/docs/configuration/acme/dns01/cloudflare/

https://cert-manager.io/docs/configuration/acme/dns01/


Issuer vs ClusterIssuer
-----------------------

The main difference between the two is that an Issuer can only issue certificates within the same namespace!

If you want your CA to issue certificates in other namespaces as well, you will have to use the ClusterIssuer.


https://kubernetes.io/docs/concepts/services-networking/ingress/#tls

https://cert-manager.io/docs/usage/ingress/

Let's Encrypt ACME
--------------------

Production Environment:

https://acme-v02.api.letsencrypt.org/directory:

This is the URL for the production environment of Let's Encrypt.
When you use this URL, you are interacting with Let's Encrypt's live and official infrastructure.
Certificates issued here are valid and trusted by major browsers.

Staging Environment:

https://acme-staging-v02.api.letsencrypt.org/directory:

This is the URL for the staging environment of Let's Encrypt.

The staging environment is a testing environment provided by Let's Encrypt for developers to test their ACME client implementations without affecting the production infrastructure.

Certificates issued in the staging environment are not trusted by browsers and are meant for testing purposes only.


Let's Encrypt - Best Practice - Keep Port 80 Open
-------------------------------------------------

https://letsencrypt.org/docs/allow-port-80/


Renew Let’s Encrypt Certificates Managed by cert-manager on Kubernetes
----------------------------------------------------------------------


.. code-block:: bash

    kubectl get  certificates -A
    kubectl describe certificates -n ns-name cert-name

    Status:
      Conditions:
        Last Transition Time:  2024-05-17T18:31:26Z
        Message:               Certificate is up to date and has not expired
        Observed Generation:   1
        Reason:                Ready
        Status:                True
        Type:                  Ready
      Not After:               2024-08-15T17:31:25Z
      Not Before:              2024-05-17T17:31:26Z
      Renewal Time:            2024-07-16T17:31:25Z
      Revision:                1
