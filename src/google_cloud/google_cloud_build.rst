Google Cloud Build
==================


Automate App Engine deployments with Cloud Build
------------------------------------------------

1. Enable API:

    https://console.cloud.google.com/flows/enableapi?apiid=appengine.googleapis.com,cloudbuild.googleapis.com

2. Set the status of the App Engine Admin role to Enable

    https://console.cloud.google.com/cloud-build/settings

3. Create a build trigger

    https://console.cloud.google.com/cloud-build/triggers

4. Create a `cloudbuild.yaml` file

.. code-block:: yaml

    steps:
    - name: "gcr.io/cloud-builders/gcloud"
      args: ["app", "deploy"]
    timeout: "1600s"




IAM
---

https://console.cloud.google.com/iam-admin/iam

.. code-block:: bash

    ERROR: (gcloud.app.deploy) A custom runtime must have exactly one of [Dockerfile] and [cloudbuild.yaml] in the source directory; [/workspace] contains both
    ERROR
    ERROR: build step 0 "gcr.io/cloud-builders/gcloud" failed: step exited with non-zero status: 1


https://console.cloud.google.com/iam-admin/iam

Roles
-----

Add these roles to this account <project-name@appspot.gserviceaccount.com> from here:

https://console.cloud.google.com/iam-admin/iam

.. code-block:: bash

    App Engine Deployer
    App Engine Service Admin
    Cloud Build Editor
    Service Account User
    Storage Object Creator
    Storage Object Viewer

If you are in flex env you need also this role:

Logs Writer

https://github.com/google-github-actions/setup-gcloud/issues/191


https://stackoverflow.com/a/59513568

.. code-block:: bash

    ERROR: (gcloud.app.deploy) PERMISSION_DENIED: You do not have permission to act as 'ID@appspot.gserviceaccount.com'
    - '@type': type.googleapis.com/google.rpc.ResourceInfo
      description: You do not have permission to act as this service account.
      resourceName: ID@appspot.gserviceaccount.com
      resourceType: serviceAccount
    ERROR
    ERROR: build step 0 "gcr.io/cloud-builders/gcloud" failed: step exited with non-zero status: 1

https://cloud.google.com/build/docs/securing-builds/configure-access-for-cloud-build-service-account?_ga=2.83847446.-1020793264.1638435237

https://stackoverflow.com/questions/64236468/cloud-build-fails-to-deploy-to-google-app-engine-you-do-not-have-permission-to#new-answer


.. code-block:: bash

    OR: (gcloud.app.deploy) Error Response: [7]

    The App Engine appspot and App Engine flexible environment service accounts
    must have permissions on the image [us.gcr.ioIDv/appengine/default.20220615t101656:latest].
    Please check that the
    App Engine default service account has the
    [Storage Object Viewer] role and the
    App Engine  Flexible service account has the App Engine Flexible Environment Service Agent role
    ERROR



App Engine Admin
Cloud Build Service Account
Service Account User


App Engine flexible environment Service Agent
Storage Object Viewer

App Engine Admin API
---------------------

.. code-block:: bash

    API [appengine.googleapis.com] not enabled on project [ID]. Would you
    Step #1: like to enable and retry (this will take a few minutes)? (y/N)?

https://console.cloud.google.com/apis/api/appengine.googleapis.com/overview

Permissions
------------

https://console.cloud.google.com/cloud-build/settings

.. code-block:: bash

    ERROR: (gcloud.app.deploy) Permissions error fetching application [ID].
    Please make sure that you have permission to view applications on the project
    and that ID@cloudbuild.gserviceaccount.com has the App Engine Deployer (roles/appengine.deployer) role.
