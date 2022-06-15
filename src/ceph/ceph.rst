Ceph
====


Default crush rule is set to the host level, not the osd level, to change it to osd level, add this to global section of
ceph.conf file:

.. code-block:: bash

	osd crush chooseleaf type = 0

osd crush chooseleaf type

Description:	The bucket type to use for chooseleaf in a CRUSH rule. Uses ordinal rank rather than name.

Type:	32-bit Integer

Default:	1. Typically a host containing one or more Ceph OSD Daemons.


Status of the PGS: STUCK UNCLEAN, ACTIVE+CLEAN

.. code-block:: bash

	ceph -s
	ceph osd tree
