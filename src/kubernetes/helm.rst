Helm
====


Install and search a char on the repo
-------------------------------------

.. code-block:: bash

    helm repo add aws-ebs-csi-driver https://kubernetes-sigs.github.io/aws-ebs-csi-driver
    helm repo update
    helm search repo aws-ebs-csi-driver -l
    helm upgrade --install aws-ebs-csi-driver --namespace kube-system aws-ebs-csi-driver/aws-ebs-csi-driver

https://github.com/kubernetes-sigs/aws-ebs-csi-driver/blob/master/docs/install.md


List all installed helm
-----------------------

.. code-block:: bash

    helm ls --all-namespaces
    helm ls -A
