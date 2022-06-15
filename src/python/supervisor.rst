Supervisor
==========


Step by step example
--------------------

Install and run supervisor

.. code-block:: bash

    # apt-get install supervisor
    # service supervisor status
    # service supervisor restart

we'll assume we have a shell script we wish to keep persistently running,
that we have saved at ``~/workspace/script/run/dstat_network_bandwidth_usage.sh`` and looks like the following:

.. code-block:: bash

    #!/bin/bash
    dstat -tn  --output ~/workspace/script/report/dstat_network_bandwidth_usage.csv  --noupdate 3600

Make this script executable:

.. code-block:: bash

    $ chmod +x /usr/local/bin/long.sh

The program configuration files for Supervisor programs are found in the ``/etc/supervisor/conf.d`` directory,
normally with one program per file and a ``.conf`` extension.
A simple configuration for our script, saved at ``/etc/supervisor/conf.d/dstat.conf``, would look like so:

.. code-block:: bash

    [program:dstat_network_bandwidth_usage]
    command=~/workspace/script/run/dstat_network_bandwidth_usage.sh
    autostart=true
    autorestart=true
    stderr_logfile=/var/log/dstat_network_bandwidth_usage.err.log
    stdout_logfile=/var/log/dstat_network_bandwidth_usage.out.log
    user=or

Once our configuration file is created and saved,
we can inform Supervisor of our new program through the ``supervisorctl`` command.
First we change directory path to ``/etc/supervisor/`` and tell `Supervisor` to look for
any new or changed program configurations in the ``/etc/supervisor/conf.d`` directory with:

.. code-block:: bash

    # cd /etc/supervisor/
    # supervisorctl reread

Followed by telling it to enact any changes with:

.. code-block:: bash

    # supervisorctl update

To enter the interactive mode, start supervisorctl with no arguments:


.. code-block:: bash

    # supervisorctl
        dstat_network_bandwidth_usage    RUNNING   pid 13405, uptime 0:14:49
        $ supervisor> status
        dstat_network_bandwidth_usage    RUNNING   pid 13405, uptime 0:14:52
        $ supervisor>
        supervisor> restart dstat_network_bandwidth_usage
        dstat_network_bandwidth_usage: stopped
        dstat_network_bandwidth_usage: started
        $ supervisor> status
        dstat_network_bandwidth_usage    RUNNING   pid 13720, uptime 0:00:06
        $ supervisor>


Links
-----

https://serversforhackers.com/monitoring-processes-with-supervisord

https://www.digitalocean.com/community/tutorials/how-to-install-and-manage-supervisor-on-ubuntu-and-debian-vps



