Longhorn
========

Documentation links:

https://longhorn.io/docs/1.5.1/deploy/install/#installing-open-iscsi
https://raw.githubusercontent.com/longhorn/longhorn/v1.5.1/deploy/prerequisite/longhorn-iscsi-installation.yaml

https://longhorn.io/docs/1.5.1/deploy/install/#installing-nfsv4-client
https://raw.githubusercontent.com/longhorn/longhorn/v1.5.1/deploy/prerequisite/longhorn-nfs-installation.yaml

https://longhorn.io/docs/1.5.1/deploy/install/#using-the-environment-check-script

.. code-block:: bash

    curl -sSfL https://raw.githubusercontent.com/longhorn/longhorn/v1.5.1/scripts/environment_check.sh | bash

Helm
****

https://longhorn.io/docs/1.5.1/advanced-resources/deploy/customizing-default-settings/#using-helm

https://staging.artifacthub.io/packages/helm/longhorn/longhorn/1.5.1


Set up Node Selector During installing Longhorn with Helm
*********************************************************

https://github.com/longhorn/longhorn/issues/7407#issuecomment-1867014096

https://github.com/longhorn/longhorn/blob/3e9742772d957990916d4bbac3a5353eac95166a/chart/values.yaml#L233

https://longhorn.io/docs/1.5.3/references/settings/#system-managed-components-node-selector
