Cloudflare
==========


Zone ID
-------

https://developers.cloudflare.com/fundamentals/get-started/basic-tasks/find-account-and-zone-ids/


cloudflared tunnel
-------------------

.. code-block:: bash

    cloudflared tunnel login
    cloudflared tunnel create sample
    # It will add a new record:
    #    Type: CNAME
    #    Value: app
    #    Content uuid.cfargotunnel.com
    #    Proxy: Orange
    cloudflared tunnel route dns sample app
    cloudflared tunnel list
    cloudflared tunnel run sample
    cloudflared tunnel delete sample

.. code-block:: bash

    vim ~/.cloudflared/config.yaml
        url: http://192.168.60.10:80
        tunnel: uuid
        credentials-file: ~/.cloudflared/uuid.json


https://developers.cloudflare.com/cloudflare-one/connections/connect-networks/get-started/create-local-tunnel/
