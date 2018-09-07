Scan Options
============


Find open Proxies
-----------------


.. code-block:: bash

	# nmap -iR 10000 -sS -p8000,8080,8123,8181,3128,1080 -PS8000,8080,8123,8181,3128,1080 -n --script=http-open-proxy,socks-open-proxy --open -v


