Modules
=======

Dict Validator
--------------

https://pypi.python.org/pypi/voluptuous/

    https://github.com/alecthomas/voluptuous

https://github.com/j2labs/schematics

    https://github.com/dfm/schematics https://github.com/j2labs/dictshield    https://github.com/exfm/dictshield

http://notario.cafepais.com/docs/index.html

https://github.com/greenwoodcm/dict-validator

https://github.com/halst/schema

https://github.com/onyxfish/jsonschema

https://github.com/Deepwalker/procrustes


Schematics
----------
converter: initial simple value converter, for example "180" to 180, or to 180.0, and or "" to None

filter(s): convert value to another (complex) format value, for example "22/5/2013" to datetime(22,5,2013)

validator: validate a value and return value or raise exception

serialization: serialize value.

Order of converter, filters and validators?

validators and filters and converter may raise exceptions


step 1: SampleModel(data)

Model get raw_data as a dict and then pass the value of each key of dict to a corresponding field of Model.

every field try to convert given value to a python data object.

for example for this input {'service_name': 123} for service_name field as StringType in Model,

it will converted to {'service_name': u'123'}

also exceptions may be raised here, for example if we given {'service_name': 12.3} to Model, then it will

raise schematics.exceptions.ModelConversionError(u"Couldn't interpret value as string.") exception.

at end, converted data is accessible from _data attribute of Model instance: SampleModel(data)._data

step 2: SampleModel(data).validate()
The converted data that is in `_data` pass to each validator or validators of field.

in evey validation, before validate a value, the value will pass to convert method of filed,
and `_data` will update for that value, if validation passed.

looks at: `/usr/local/lib/python2.7/dist-packages/schematics/validate.py:52`


libnotify
---------
https://wiki.archlinux.org/index.php/libnotify#Python

install libnotify, python-gobject packages.

.. code-block:: python

	#!/usr/bin/python
	from gi.repository import Notify
	Notify.init ("Hello world")
	Hello=Notify.Notification.new ("Hello world","This is an example notification.","dialog-information")
	Hello.show ()

You may also use notify-send (on Debian-based systems, install the libnotify-bin package):

.. code-block:: bash

	notify-send -i 'dialog-information' 'Summary' '<b><font color=red>Message body.'

also kdialog's passive popup option can be used:

.. code-block:: bash

	kdialog --passivepopup <text> <timeout>
	kdialog --passivepopup 'This is a notification' 5


Terminal
--------
npyscreen https://pypi.python.org/pypi/npyscreen/
Urwid http://urwid.org/index.html
blessed https://github.com/jquast/blessed


Install lxml on pyenv (virtual env)
-----------------------------------

.. code-block::bash

    $ sudo apt-get install libxml2-dev libxslt1-dev
    $ pip install lxml
