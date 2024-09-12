Tips
====

GKE with external load balancer
-------------------------------

.. code-block:: bash

    annotations

        {
            "kubernetes.io/ingress.class": "gce",
            "ingress.gcp.kubernetes.io/global-static-ip-name": "app-lb-ip-external",
            "ingress.gcp.kubernetes.io/pre-shared-cert": "app-cert",
            "ingress.gcp.kubernetes.io/healthcheck-path": "/healthz",
        }



GKE with internal load balancer
-------------------------------

.. code-block:: bash

    annotations

        {
            "kubernetes.io/ingress.class": "gce-internal",
            "kubernetes.io/ingress.regional-static-ip-name": "app-lb-ip-internal",
            "ingress.gcp.kubernetes.io/healthcheck-path": "/healthz",
        }
