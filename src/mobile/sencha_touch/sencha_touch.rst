Sencha Touch
============


Native Packaging for Mobile Devices
-----------------------------------

`Native Packaging <http://docs.sencha.com/touch/2.3.0/#!/guide/native_packaging>`_


Creating a New Application
--------------------------

`Using Sencha Cmd with Sencha Touch  <http://docs.sencha.com/touch/2.3.0/#!/guide/command_app>`_

.. code-block:: bash

	omidraha@debian:$ cd ~/Tools/js/Sencha/SenchaTouch/touch-2.3.0
	omidraha@debian:$ sencha generate app helloTouch /home/omidraha/Prj/Sencha/Touch/helloTouch/

Deploying Your Application
--------------------------

.. code-block:: bash

	omidraha@debian:~/Prj/Sencha/Touch/helloTouch$ sencha app build testing
	omidraha@debian:~/Prj/Sencha/Touch/helloTouch$ sencha app build production
	omidraha@debian:~/Prj/Sencha/Touch/helloTouch$ sencha app build native
	omidraha@debian:~/Prj/Sencha/Touch/helloTouch$ sencha app package run config.json

# sudo ln -s /home/omidraha/bin/Sencha/Cmd/4.0.0.203/stbuild/
# cp -R /home/omidraha/bin/Sencha/Cmd/4.0.0.203/stbuild/st-res/ ~/Prj/Sencha/Touch/helloTouch
