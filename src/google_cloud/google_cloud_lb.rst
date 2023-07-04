
Google cloud load balancer
=========================


Links
-----

https://console.cloud.google.com/networking/addresses/list?project=example

https://console.cloud.google.com/net-services/loadbalancing/list?project=example


https://console.cloud.google.com/networking/addresses/list?project=example


https://console.cloud.google.com/net-services/loadbalancing/advanced/sslCertificates/list?_ga=2.125741386.1526449259.1655094178-286473084.1655094178&project=example

https://console.cloud.google.com/net-services/loadbalancing/advanced/sslCertificates/list?project=example

https://console.cloud.google.com/net-services/loadbalancing/add


Global HTTP(S) Load Balancer (classic)
--------------------------------------

The Classic Global HTTPS Load Balancer is the earlier version of the Global HTTPS Load Balancer,
and does not support features such as advanced traffic management. Learn more

https://cloud.google.com/load-balancing/docs/features?&_ga=2.71074960.-1020793264.1638435237#routing-traffic-management

Global HTTP(S) Load Balancer
----------------------------
The recommended load balancer for external HTTP(S) workloads with globally dispersed users or backend services in multiple regions.
This load balancer contains advanced traffic management features provide additional capabilities over the Classic HTTP(S) Load Balancer,
enabling fine-grained control over how traffic is handled. Learn more

https://cloud.google.com/load-balancing/docs/https/traffic-management-global?_ga=2.71132560.-1020793264.1638435237


cmds
----

.. code-block:: bash

    proxychains4 gcloud compute target-ssl-proxies list

    proxychains4 gcloud compute target-https-proxies list

    NAME                                        SSL_CERTIFICATES                   URL_MAP
    https-load-balancer-classic-target-proxy-2  sso-load-balancer-certificate-dev  https-load-balancer-classic

    proxychains4 gcloud compute ssl-certificates describe sso-load-balancer-certificate-dev --format="get(managed.domainStatus)"


