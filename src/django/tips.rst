Tips
====

Create new project
------------------

.. code-block:: python

	django-admin startproject mysite
	python manage.py startapp polls


Using django in python cmd
--------------------------

.. code-block:: python

	from django.conf import settings
	django.setup() # or settings.configure()



Migration
---------

.. code-block:: bash

    $ python manage.py  makemigrations
    $ python manage.py  migrate
    $ python manage.py  syncdb


Django dump data of django.contrib.auth.Group
---------------------------------------------

Note: deprecated from Django 1.7 !

.. code-block:: bash

    $ python manage.py dumpdata --format=json auth.Group > fixtures.json

Django migration for auth
-------------------------


.. code-block:: bash

    $ python manage.py makemigrations account
    $ python manage.py makemigrations --empty account

Now adding your code to load initial data in RunPython section:

.. code-block:: python

    # -*- coding: utf-8 -*-
    from __future__ import unicode_literals
    from django.db import models, migrations


    def forwards_func(apps, schema_editor):
        Group = apps.get_model("auth", "Group")
        db_alias = schema_editor.connection.alias
        Group.objects.using(db_alias).bulk_create([
            Group(name='Viewer')
            Group(name='Editor'),
            Group(name='Admin'),
        ])


    class Migration(migrations.Migration):
        dependencies = [
            ('account', '0001_initial')
        ]

        operations = [
            migrations.RunPython(
                forwards_func,
            ),
        ]

Then:

.. code-block:: bash

    $ python manage.py syncdb


The ``'__first__'`` and ``'__latest__'`` can use in `dependencies` section.

https://code.djangoproject.com/ticket/23422

https://docs.djangoproject.com/en/1.7/topics/migrations/#dependencies

https://docs.djangoproject.com/en/1.7/ref/migration-operations/

https://docs.djangoproject.com/en/dev/ref/django-admin/#loaddata-fixture-fixture



how to reset django admin password?
-----------------------------------

.. code-block:: bash

    $ manage.py changepassword <user_name>

Run server from Python script
-----------------------------


.. code-block:: bash

    #!/usr/bin/env python
    import os
    from django.core.management import call_command
    from django.core.wsgi import get_wsgi_application

    os.environ.setdefault("DJANGO_SETTINGS_MODULE", "settings")

    application = get_wsgi_application()
    call_command('runserver', '127.0.0.1:8000')

Static files handling
---------------------


.. code-block:: bash

    $ vim settings.py

    # static files configs
    STATIC_ROOT = os.path.join(BASE_DIR, 'collected_static')
    STATIC_URL = '/st/'
    STATICFILES_DIRS = (
        os.path.join(BASE_DIR, 'static'),
    )
    STATICFILES_FINDERS = (
        'django.contrib.staticfiles.finders.FileSystemFinder',
        'django.contrib.staticfiles.finders.AppDirectoriesFinder',
    )

    INSTALLED_APPS = (
        'django.contrib.staticfiles',
        )

Get the static files URL in view
--------------------------------

.. code-block:: python

    from django.contrib.staticfiles.templatetags.staticfiles import static
    url = static('x.jpg')
    # url now contains '/static/x.jpg', assuming a static path of '/static/

https://docs.djangoproject.com/en/1.8/hkowto/static-files/

https://docs.djangoproject.com/en/1.8/ref/contrib/staticfiles/

http://stackoverflow.com/questions/11721818/django-get-the-static-files-url-in-view

Testing email sending
---------------------

.. code-block:: python

    from django.core import mail
    settings.EMAIL_USE_TLS = False
    settings.EMAIL_PORT = 25
    settings.EMAIL_HOST = 'mail.example.com'
    settings.EMAIL_HOST_USER = 'from@example.com'
    settings.EMAIL_HOST_PASSWORD='<PASSWORD>'
    mail.send_mail('Subject', 'Body','from@example.com', ['to@example.com'], fail_silently=False)


Django rest
-----------

Documenting your API

http://www.django-rest-framework.org/topics/documenting-your-api/

https://github.com/ekonstantinidis/django-rest-framework-docs

https://github.com/marcgibbons/django-rest-swagger

Django supported versions
-------------------------

https://www.djangoproject.com/download/#supported-versions

Translation
-----------

.. code-block:: bash

    $ django-admin makemessages -a
    $ django-admin compilemessages

.. code-block:: python

    from django.utils.translation import ugettext
    from django.utils import translation

    translation.activate('fa')
    txt = 'Hello'
    ugettext(txt)
    'سلام'



Django User Group Object Permissions
------------------------------------


Add permission to user

.. code-block:: python

    from django.contrib.auth.models import User
    from django.contrib.auth.models import Permission
    usr = User.objects.first()
    perm = Permission.objects.get(name='Can edit org')
    usr.user_permissions.add(perm)


Add user to group

.. code-block:: python

    from django.contrib.auth.models import User
    from django.contrib.auth.models import Group
    usr = User.objects.first()
    grp = Group.objects.get(name='Operator')
    grp.user_set.add(usr)


Add group to user

.. code-block:: python

    from django.contrib.auth.models import User
    from django.contrib.auth.models import Group
    usr = User.objects.first()
    grp = Group.objects.get(name='Operator')
    usr.groups.add(grp)



Add permission to group

.. code-block:: python

    from django.contrib.auth.models import Group
    from django.contrib.auth.models import Permission
    perm = Permission.objects.get(name='can edit org')
    grp = Group.objects.get(name='Operator')
    grp.permissions.add(perm)


Create permission for an object

.. code-block:: python

    from django.contrib.auth.models import Group
    from django.contrib.auth.models import Permission
    from django.contrib.contenttypes.models import ContentType
    from sample.models import SampleObj
    ct = ContentType.objects.get_for_model(SampleObj)
    perm = Permission.objects.create(codename='can edit org', name='Can Edit Org', content_type=ct)
