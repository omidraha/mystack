Ethereum
========



https://www.infura.io/register


.. code-block:: python

    from web3 import Web3


    api_key = '****'

    api_secret = '****'

    endpoint_url = 'https://mainnet.infura.io/v3/********'


    web3 = Web3(Web3.HTTPProvider(endpoint_url))

    print(web3.eth.blockNumber)
    print(web3.eth.getBlock('latest'))





