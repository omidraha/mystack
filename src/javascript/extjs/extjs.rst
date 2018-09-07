ExtJS
=====


Sencha Cmd
----------

Upgrading Sencha Cmd

.. code-block:: bash

	$ sencha upgrade --check
	$ sencha upgrade

	$ sencha upgrade --check --beta
	$ sencha upgrade --beta


Generating Your Application

.. code-block:: bash

	$ sencha -sdk /path/to/sdk generate app MyApp /path/to/MyApp
	$ sencha -sdk /home/omidraha/Tools/js/Sencha/Sencha\ Ext\ JS/ext-4.2.1.883/ generate app hello /home/omidraha/Prj/Sencha/ExtJS/hello

Building Your Application

.. code-block:: bash

	$ sencha app build


Generating a Workspace

.. code-block:: bash

	$ sencha generate workspace /path/to/workspace

Generating Pages

.. code-block:: bash

	$ sencha -sdk /path/to/ext generate app ExtApp /path/to/workspace/extApp
	$ sencha -sdk /path/to/touch generate app TouchApp /path/to/workspace/touchApp


Generate Local Packages

.. code-block:: bash

	sencha generate package -type code foo

Then add "common" as a requirement to your application's "app.json":

.. code-block:: bash

	{
		"name": "MyApp",
		"requires": [
			"foo"
		]
	}

Building The Package

.. code-block:: bash

	$ sencha package build
