Tips
====


Public IPFS Gateways
--------------------

https://ipfs.github.io/public-gateway-checker/


Docker usage
------------



.. code-block:: bash

    $ export ipfs_staging=</absolute/path/to/somewhere/>
    $ export ipfs_data=</absolute/path/to/somewhere_else/>
    $ docker run -d --name ipfs_host -v $ipfs_staging:/export -v $ipfs_data:/data/ipfs -p 4001:4001 -p 127.0.0.1:8080:8080 -p 127.0.0.1:5001:5001 ipfs/go-ipfs:latest


Watch log and Wait for ipfs to start:

.. code-block:: bash

    $ docker logs -f ipfs_host


The ipfs is running when you see this on the log:

.. code-block:: bash

        Gateway (readonly) server
        listening on /ip4/0.0.0.0/tcp/8080


Connect to peers:

.. code-block:: bash

    $ docker exec ipfs_host ipfs swarm peers


Add files:

.. code-block:: bash

    $ cp -r <something> $ipfs_staging
    $ docker exec ipfs_host ipfs add -r /export/<something>



https://github.com/ipfs/go-ipfs#docker-usage




Python usage
------------


.. code-block:: bash

    $ pip install ipfsapi


.. code-block:: python

    import ipfsapi

    api = ipfsapi.connect('127.0.0.1', 5001)
    res = api.add('test.txt')

    res
    {'Hash': 'QmWxS5aNTFEc9XbMX1ASvLET1zrqEaTssqt33rVZQCQb22', 'Name': 'test.txt'}

    api.cat(res['Hash'])
    'fdsafkljdskafjaksdjf\n'

    api.id()
    {'Addresses': ['/ip4/127.0.0.1/tcp/4001/ipfs/QmS2C4MjZsv2iP1UDMMLCYqJ4WeJw8n3vXx1VKxW1UbqHS',
                   '/ip6/::1/tcp/4001/ipfs/QmS2C4MjZsv2iP1UDMMLCYqJ4WeJw8n3vXx1VKxW1UbqHS'],
     'AgentVersion': 'go-ipfs/0.4.10',
     'ID': 'QmS2C4MjZsv2iP1UDMMLCYqJ4WeJw8n3vXx1VKxW1UbqHS',
     'ProtocolVersion': 'ipfs/0.1.0',
     'PublicKey': 'CAASpgIwgg ... 3FcjAgMBAAE='}

https://github.com/ipfs/py-ipfs-api#usage

