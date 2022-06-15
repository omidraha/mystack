Ethereum
========



https://www.infura.io/register


.. code-block:: python


    from eth_account.messages import defunct_hash_message
    from solc import compile_source
    from web3 import Web3
    from web3.providers.rpc import HTTPProvider

    endpoint_url = 'https://ropsten.infura.io/v3/549a3096fb7d410381ba844e3930cfc5'

    web3 = Web3(HTTPProvider(endpoint_url))


    # https://web3py.readthedocs.io/en/latest/web3.eth.account.html
    def create_keys():
        account = web3.eth.account.create()
        return account.address, account.privateKey


    def sign_message(text, private_key):
        message_hash = defunct_hash_message(text=text)
        signed_message = web3.eth.account.signHash(message_hash, private_key=private_key)
        return message_hash, signed_message


    def verify_message(text, signed_message):
        message_hash = defunct_hash_message(text=text)
        recover = web3.eth.account.recoverHash(message_hash, signature=signed_message.signature)
        return recover


    def verify_message_from_message_hash(message_hash, signature):
        recover = web3.eth.account.recoverHash(message_hash, signature=signature)
        return recover


    # Solidity source code
    contract_source_code = '''
    pragma solidity ^0.4.21;

    contract Greeter {
        string public greeting;

        function Greeter() public {
            greeting = 'Hello';
        }

        function setGreeting(string _greeting) public {
            greeting = _greeting;
        }

        function greet() view public returns (string) {
            return greeting;
        }
    }
    '''

    address, pk = create_keys()

    address = web3.toChecksumAddress(address)

    compiled_sol = compile_source(contract_source_code)  # Compiled source code
    contract_interface = compiled_sol['<stdin>:Greeter']

    print('web3.eth.blockNumber', web3.eth.blockNumber)

    abi, bytecode = contract_interface['abi'], contract_interface['bin']

    contract = web3.eth.contract(bytecode=bytecode, abi=abi)

    print('address', address)

    # sample valid address
    to_address = web3.toChecksumAddress('0x7f508c2666a3b40598572d7232ec4fb95162fd9a')

    transaction = {
        'to': to_address,
        'value': 1000000000000000000,
        'gas': 4700000,
        'gasPrice': web3.eth.gasPrice,
        'nonce': web3.eth.getTransactionCount(address)
    }

    tx = contract.buildTransaction(transaction).setGreeting('Omid')
    signed = web3.eth.account.signTransaction(tx, pk)

    final = web3.eth.sendRawTransaction(signed.rawTransaction)

    print('final', final)
    print('fin:', web3.toHex(final))

    ###############################


    transaction = {
        'to': to_address,
        'value': 1000000000000000000,
        'gas': 4700000,
        'gasPrice': web3.eth.gasPrice,
        'nonce': web3.eth.getTransactionCount(address),
        'data': b'A' * 32655
    }

    signed = web3.eth.account.signTransaction(transaction, pk)
    final = web3.eth.sendRawTransaction(signed.rawTransaction)

    print('final', final)
    print('fin:', web3.toHex(final))

    ###############################




Solidity
--------

https://github.com/ethereum/py-solc


.. code-block:: bash

    $ pip install py-solc
    $ python -m solc.install v0.4.21
    $ export PATH="~/.py-solc/solc-v0.4.21/bin/:$PATH"


Online solidity
---------------

https://remix.ethereum.org/#optimize=true&version=builtin

https://ethereum.github.io/browser-solidity/#optimize=false&version=soljson-v0.4.25-nightly.2018.9.10+commit.86d85025.js





Links
--------

https://ropsten.etherscan.io/tx/

https://hackernoon.com/ethereum-smart-contracts-in-python-a-comprehensive-ish-guide-771b03990988

https://chrome.google.com/webstore/detail/metamask/nkbihfbeogaeaoehlefnkodbefgpgknn?hl=en

https://ethereum.stackexchange.com/questions/11495/best-way-to-test-a-smart-contract


https://metamask.io/

https://faucet.metamask.io/

https://gist.github.com/Kcrong/1f832a2f4ab861da3d852c5b0a30ef47


http://justin.yackoski.name/winp/

https://web3py.readthedocs.io/en/stable/contracts.html#contract-deployment-example
