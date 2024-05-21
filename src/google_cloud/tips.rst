Tips
====


Init
-------------

.. code-block:: bash

    $ gcloud init

Authenticate
-------------

.. code-block:: bash

    $ gcloud auth login


Set project
-----------

.. code-block:: bash

    gcloud config set project PROJECT_ID

List project
------------

    .. code-block:: bash

        gcloud projects list

Enabling and disabling debug mode
---------------------------------

To disable debug mode:

.. code-block:: bash

    $ gcloud app --project PROJECT-ID instances disable-debug


https://cloud.google.com/appengine/docs/flexible/debugging-an-instance?_ga=2.76983445.-1020793264.1638435237#enabling_and_disabling_debug_mode


Google bucket set CORS
-----------------------

The `cors.json` file:

.. code-block:: bash

    [
        {
          "origin": ["https://example.com", "https://example.io"],
          "method": ["GET", "OPTION"],
          "responseHeader": ["Content-Type", "Access-Control-Allow-Origin"],
          "maxAgeSeconds": 3600
        }
    ]


Commands

.. code-block:: bash

    gcloud storage buckets update gs://BUCKET-NAME --cors-file=cors.json

    gcloud storage buckets describe gs://BUCKET-NAME --format="default(cors_config)"

Sample js console code to test:

.. code-block:: bash

        fetch('https://storage.googleapis.com/BUCKET-NAME/FOLDER/sample.json')
          .then(response => response.blob())
          .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'meta.json';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
          })
          .catch(error => console.error('Error downloading file:', error));
