Open shift
==========


Installing the OpenShift Client Tools
-------------------------------------

https://developers.openshift.com/en/getting-started-client-tools.html

http://appsembler.com/blog/django-deployment-using-openshift/


.. code-block:: bash

    $ sudo apt-get install ruby-full rubygems git-core


.. code-block:: bash

    $ sudo gem install rhc

.. code-block:: bash

    $ sudo rhc setup

    OpenShift Client Tools (RHC) Setup Wizard

    This wizard will help you upload your SSH keys, set your application namespace, and check that other programs like Git are properly installed.

    If you have your own OpenShift server, you can specify it now. Just hit enter to use the server for OpenShift Online: openshift.redhat.com.
    Enter the server hostname: |openshift.redhat.com|

    You can add more servers later using 'rhc server'.

    Login to openshift.redhat.com: or@*****.com
    Password: *********************

    OpenShift can create and store a token on disk which allows to you to access the server without using your password. The key is stored in your home directory and should be kept secret.  You can
    delete the key at any time by running 'rhc logout'.
    Generate a token now? (yes|no) yes
    Generating an authorization token for this client ... lasts 30 days

    Saving configuration to /home/or/.openshift/express.conf ... done

    Checking for git ... found git version 2.1.1

    Checking common problems .

    An SSH connection could not be established to django-*****.rhcloud.com. Your SSH configuration may not be correct, or the application may not be responding. Authentication failed for user
    *****@django-*****.rhcloud.com (Net::SSH::AuthenticationFailed)

    Checking for a domain ... or

    Checking for applications ... found 1

      django http://django-*****.rhcloud.com/

      You are using 2 of 3 total gears
      The following gear sizes are available to you: small

    Your client tools are now configured.

.. code-block:: bash

    or@debian:~$ ssh-add ~/.ssh/id_rsa

    Identity added: /home/or/.ssh/id_rsa (/home/or/.ssh/id_rsa)

.. code-block:: bash

    or@debian:~$ ssh *****@django-*****.rhcloud.com

        *********************************************************************

        You are accessing a service that is for use only by authorized users.
        If you do not have authorization, discontinue use at once.
        Any use of the services is subject to the applicable terms of the
        agreement which can be found at:
        https://www.openshift.com/legal

        *********************************************************************

        Welcome to OpenShift shell

        This shell will assist you in managing OpenShift applications.

        !!! IMPORTANT !!! IMPORTANT !!! IMPORTANT !!!
        Shell access is quite powerful and it is possible for you to
        accidentally damage your application.  Proceed with care!
        If worse comes to worst, destroy your application with "rhc app delete"
        and recreate it
        !!! IMPORTANT !!! IMPORTANT !!! IMPORTANT !!!

        Type "help" for more info.


    [django-****.rhcloud.com ****]\> ls
    app-deployments  app-root  gear-registry  git  haproxy	python
    [django-****.rhcloud.com ****]\> exit
    exit
    Connection to django-****.rhcloud.com closed.
    or@debian:~$


.. code-block:: bash

    $ rhc deployment-list django

    # Tail the logs of an application
    $ rhc tail django

.. code-block:: bash

    [openshift-server]\>ls -la  app-root/data

    [openshift-server]\>gear deploy


Django admin pass
-----------------

.. code-block:: bash

    [openshift-server]\> python app-root/repo/wsgi/my_prj/manage.py syncdb

    [openshift-server]\> cp app-root/repo/wsgi/my_prj/sqlite3.db app-root/data



Openshift Environment Variables List
------------------------------------


https://developers.openshift.com/en/managing-environment-variables.html


Update rhc
----------

.. code-block:: bash

    $ gem update rhc httpclient



How to create and unset environment variables on the server ?
-------------------------------------------------------------

https://help.openshift.com/hc/en-us/articles/202399310-How-to-create-and-use-environment-variables-on-the-server-

https://blog.openshift.com/taking-advantage-of-environment-variables-in-openshift-php-apps/

.. code-block:: bash

        $ rhc set-env My_VAR_1=my_val_1 My_VAR_2=my_val_2 -a app_name

        $ rhc env set My_VAR_1=my_val_1 -a app_name


.. code-block:: bash

        $ rhc env unset My_VAR_1 -a app_name



Restart the application
-----------------------

.. code-block:: bash

    $ rhc app restart -a app_name

    $ rhc app stop -a app_name
    $ rhc app start -a app_name



Using redmine on openshift
--------------------------

https://www.openshift.com/quickstarts/redmine-24

https://github.com/openshift/openshift-redmine-quickstart

https://forums.openshift.com/how-to-install-redmine-plugins-on-openshift


Payment
-------

https://help.openshift.com/hc/en-us/articles/202525320-What-are-the-payment-methods-for-OpenShift-Online-

https://www.openshift.com/products/pricing

http://www.tehranpayment.com/%d8%aa%d9%85%d8%a7%d8%b3-%d8%a8%d8%a7-%d9%85%d8%a7


To see where an existing application is being hosting
-----------------------------------------------------

https://developers.openshift.com/en/overview-platform-features.html#scaling

.. code-block:: bash

    $ rhc app show --gears -a django


ID    State   Cartridges             Size  Region        Zone           SSH URL
--- ------- ---------------------- ----- ------------- -------------- -------
*** started python-2.7 haproxy-1.4 small aws-us-east-1 aws-us-east-1e ***.rhcloud.com
*** started postgresql-9.2         small aws-us-east-1 aws-us-east-1e ***.rhcloud.com


