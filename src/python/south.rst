South
=====

Enable South for django apps(Converting existing apps)
------------------------------------------------------

Edit your `settings.py` and put `south` into `INSTALLED_APPS` (assuming you’ve installed it to the right place)

Run `python manage.py syncdb` to load the South table into the database. Note that syncdb looks different now - South modifies it.

Run `python manage.py convert_to_south myapp` - South will automatically make and pretend to apply your first migration.


Example of using South for a model
----------------------------------

Change the model field.
run following cmd to detect and create a new migration file such as 001

.. code-block:: python

	$ python manage.py schemamigration  myapp --auto

run following cmd to update migration table (south_migrationhistory) in db, and update myapp model in db

.. code-block:: python

	$ manage.py migrate myapp


