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

    helm list --all --all-namespaces
    helm ls --all-namespaces
    helm ls -A


linkerd
-------


Install

.. code-block:: bash

    helm repo add linkerd https://helm.linkerd.io/stable
    helm install linkerd-crds linkerd/linkerd-crds -n linkerd --create-namespace
    helm install linkerd-control-plane -n linkerd --set clusterNetworks="10.0.0.0/8\,11.0.0.0/8\,12.0.0.0/8" --set-file identityTrustAnchorsPEM=ca.crt --set-file identity.issuer.tls.crtPEM=issuer.crt --set-file identity.issuer.tls.keyPEM=issuer.key linkerd/linkerd-control-plane
    helm install linkerd-viz linkerd/linkerd-viz
    curl -sL https://run.linkerd.io/install | sh
    export PATH=$PATH:~/.linkerd2/bin

Uninstall

.. code-block:: bash

    helm uninstall linkerd-viz
    helm uninstall linkerd-control-plane -n linkerd
    helm uninstall linkerd-crds -n linkerd
    kubectl delete ns linkerd
    linkerd viz dashboard
    kubectl delete all --all -n linkerd
    kubectl delete ClusterRole  linkerd-linkerd-metrics-api linkerd-linkerd-prometheus linkerd-linkerd-tap linkerd-linkerd-tap-admin linkerd-linkerd-web-api linkerd-linkerd-web-check linkerd-tap-injector
    linkerd-linkerd-metrics-api linkerd-linkerd-prometheus linkerd-linkerd-tap linkerd-linkerd-tap-auth-delegator linkerd-linkerd-web-admin linkerd-linkerd-web-api linkerd-linkerd-web-check linkerd-tap-injector


Diagnostic

.. code-block:: bash

    linkerd check
    kubectl -n linkerd logs deploy/linkerd-destination -c policy
    linkerd diagnostics controller-metrics
    kubectl get validatingwebhookconfigurations
    kubectl get mutatingwebhookconfigurations
    kubectl get ClusterRole
    kubectl get ClusterRoleBinding
    kubectl get -A secret
    kubectl get -A cm


List all resources managed by the helm
**************************************

.. code-block:: bash

    kubectl get all --all-namespaces -l='app.kubernetes.io/managed-by=Helm'

https://stackoverflow.com/a/65774255

List all resources in all namespaces
**************************************

.. code-block:: bash

    kubectl api-resources --verbs=list -o name | xargs -n 1 kubectl get

.. code-block:: bash

    vim res.sh
    for i in $(kubectl api-resources --verbs=list -o name | sort | uniq); do
        echo "*************"
        echo "Resource:" $i
        kubectl get $i -A
      done
    chmod +x res.sh
    ./res.sh
