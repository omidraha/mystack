Tips
====


Install
-------

Setting up Conode on each server:

Servers:

* 192.168.0.180
* 192.168.0.181


.. code-block:: bash

    $ docker run -it --rm -p 6879-6880:6879-6880 --name conode -v ~/conode_data:/conode_data dedis/conode:latest ./conode setup

Output:

.. code-block:: bash

    Setting up a cothority-server.

    Please enter the [address:]PORT for incoming requests [6879]: 192.168.0.180:6879

    We now need to get a reachable address for other Servers
    and clients to contact you. This address will be put in a group definition
    file that you can share and combine with others to form a Cothority roster.
    Creating private and public keys for suite Ed25519.
    Public key: b097aca656644fc86eaade5f3e14d74a922e7d5e4d0e2f1a05d7a2750edfde02

    Give a description of the cothority [New cothority]: cothority-1

    Please enter a folder for the configuration files [/root/.config/conode]:
    Success! You can now use the conode with the config file /root/.config/conode/private.toml
    Saved a group definition snippet for your server at /root/.config/conode/public.toml
    [[servers]]
      Address = "tls://192.168.0.180:6879"
      Suite = "Ed25519"
      Public = "b097aca656644fc86eaade5f3e14d74a922e7d5e4d0e2f1a05d7a2750edfde02"
      Description = "New cothority"

    All configurations saved, ready to serve signatures now.

Check ``conode_data`` created directory:

.. code-block:: bash

    $ ls ~/conode_data/
        private.toml  public.toml

    $ cat ~/conode_data/public.toml
        [[servers]]
          Address = "tls://192.168.0.180:6879"
          Suite = "Ed25519"
          Public = "b097aca656644fc86eaade5f3e14d74a922e7d5e4d0e2f1a05d7a2750edfde02"
          Description = "cothority-1"


Starting Conode:

.. code-block:: bash


    $ docker run --rm -p 6879-6880:6879-6880 --name conode -v ~/conode_data:/conode_data dedis/conode:latest

Output:

.. code-block:: bash

    3 : (                  onet.newServiceManager: 241) - Starting service ftCoSiService
    3 : (                  onet.newServiceManager: 253) - Started Service ftCoSiService
    3 : (                  onet.newServiceManager: 241) - Starting service Status
    3 : (                  onet.newServiceManager: 253) - Started Service Status
    3 : (                  onet.newServiceManager: 241) - Starting service Skipchain
    3 : (            messaging.NewPropagationFunc: 103) - Registering new propagation for tls://192.168.0.180:6879 SkipchainPropagate 357a62ee-c495-365b-9aed-e781d5a8285e
    3 : (                  onet.newServiceManager: 253) - Started Service Skipchain
    3 : (                  onet.newServiceManager: 241) - Starting service PoPServer
    3 : (            messaging.NewPropagationFunc: 103) - Registering new propagation for tls://192.168.0.180:6879 PoPPropagateFinal 251a9e2d-b3a4-3a25-ac5b-e6b89d265be9
    3 : (            messaging.NewPropagationFunc: 103) - Registering new propagation for tls://192.168.0.180:6879 PoPPropagateDescription 2144ba19-9d1e-33a0-9353-c2940af373eb
    3 : (                  onet.newServiceManager: 253) - Started Service PoPServer
    3 : (                  onet.newServiceManager: 241) - Starting service Identity
    3 : (            messaging.NewPropagationFunc: 103) - Registering new propagation for tls://192.168.0.180:6879 IdentityPropagateID 7b7a50c0-7c42-3465-8fd7-42b5111c6e46
    3 : (            messaging.NewPropagationFunc: 103) - Registering new propagation for tls://192.168.0.180:6879 IdentityPropagateSB 6b35d1a9-5f89-39b7-bc64-d39424dad041
    3 : (            messaging.NewPropagationFunc: 103) - Registering new propagation for tls://192.168.0.180:6879 IdentityPropagateConf 816f3244-bae7-38ca-a916-7dca0c635d6a
    3 : (             identity.(*Service).tryLoad: 795) - Successfully loaded
    3 : (                  onet.newServiceManager: 253) - Started Service Identity
    3 : (                  onet.newServiceManager: 241) - Starting service evoting
    1 : (                             service.new: 659) - Pin: f810aac19b690830d3e0c79a6c00a279
    3 : (                  onet.newServiceManager: 253) - Started Service evoting
    3 : (                  onet.newServiceManager: 257) - tls://192.168.0.180:6879 instantiated all services
    1 : (                    onet.(*Server).Start: 203) - Starting server at 2019-02-04 07:53:05 on address tls://192.168.0.180:6879 with public key b097aca656644fc86eaade5f3e14d74a922e7d5e4d0e2f1a05d7a2750edfde02
    2 : (                 onet.(*WebSocket).start:  93) - Starting to listen on 0.0.0.0:6880



https://github.com/dedis/cothority/blob/master/conode/Docker.md#docker

Status of conodes
-----------------


Copy the ``public.toml`` file from servers to somewhere you want to run status command:

.. code-block:: bash

    $ scp ubuntu@192.168.0.180:~/conode_data/public.toml  ct1_public.toml
    $ scp ubuntu@192.168.0.181:~/conode_data/public.toml  ct2_public.toml
    $ cat ct1_public.toml ct2_public.toml >  public.toml
    $ cat public.toml

    [[servers]]
      Address = "tls://192.168.0.180:6879"
      Suite = "Ed25519"
      Public = "b097aca656644fc86eaade5f3e14d74a922e7d5e4d0e2f1a05d7a2750edfde02"
      Description = "cothority-1"
    [[servers]]
      Address = "tls://192.168.0.181:6879"
      Suite = "Ed25519"
      Public = "7a6e03ba71bd87aa1a62972eb20788ab21250ea23ad3166e995225278b227983"
      Description = "cothority-2"



To get the status of the conodes in the cothority:

.. code-block:: bash

    $ go get github.com/dedis/cothority/status
    # @note: You can use `DEDIS_GROUP` env and set path of `public.toml` and run `status` cmd like this:
    $ export DEDIS_GROUP=public.toml
    $ ~/go/bin/status --group $DEDIS_GROUP

    # @note: And also you can change the name of `public.toml` to `group.toml` and run `status` cmd like this:
    $ ~/go/bin/status


Output:

.. code-block:: bash

    Db.FreeAlloc: 8192
    Db.FreePageN: 0
    Db.FreelistInuse: 32
    Db.Open: true
    Db.OpenTxN: 0
    Db.PendingPageN: 2
    Db.Tx.CursorCount: 30
    Db.Tx.NodeCount: 7
    Db.Tx.NodeDeref: 0
    Db.Tx.PageAlloc: 57344
    Db.Tx.PageCount: 14
    Db.Tx.Rebalance: 0
    Db.Tx.RebalanceTime: 0s
    Db.Tx.Spill: 7
    Db.Tx.SpillTime: 68.734µs
    Db.Tx.Split: 0
    Db.Tx.Write: 21
    Db.Tx.WriteTime: 13.224429ms
    Db.TxN: 12
    Generic.Available_Services: Identity,PoPServer,Skipchain,Status,evoting,ftCoSiService
    Generic.ConnType: tls
    Generic.Description: cothority-1
    Generic.GoModuleInfo:
    Generic.GoRelease: go1.10.1
    Generic.Host: 192.168.0.180
    Generic.Port: 6879
    Generic.RX_bytes: 1322
    Generic.System: linux/amd64/go1.10.1
    Generic.TX_bytes: 2095
    Generic.Uptime: 1h6m30.427247895s
    Generic.Version: 2.0
    Skipblock.Blocks: 0
    Skipblock.Bytes: 0
    Db.FreeAlloc: 8192
    Db.FreePageN: 0
    Db.FreelistInuse: 32
    Db.Open: true
    Db.OpenTxN: 0
    Db.PendingPageN: 2
    Db.Tx.CursorCount: 25
    Db.Tx.NodeCount: 7
    Db.Tx.NodeDeref: 0
    Db.Tx.PageAlloc: 57344
    Db.Tx.PageCount: 14
    Db.Tx.Rebalance: 0
    Db.Tx.RebalanceTime: 0s
    Db.Tx.Spill: 7
    Db.Tx.SpillTime: 22.611µs
    Db.Tx.Split: 0
    Db.Tx.Write: 21
    Db.Tx.WriteTime: 14.09274ms
    Db.TxN: 7
    Generic.Available_Services: Identity,PoPServer,Skipchain,Status,evoting,ftCoSiService
    Generic.ConnType: tls
    Generic.Description: cothority-2
    Generic.GoModuleInfo:
    Generic.GoRelease: go1.10.1
    Generic.Host: 192.168.0.181
    Generic.Port: 6879
    Generic.RX_bytes: 2095
    Generic.System: linux/amd64/go1.10.1
    Generic.TX_bytes: 1322
    Generic.Uptime: 24m21.403615792s
    Generic.Version: 2.0
    Skipblock.Blocks: 0
    Skipblock.Bytes: 0


https://github.com/dedis/cothority#status


Collective Signing
------------------

.. code-block:: bash

    $ go get github.com/dedis/cothority/ftcosi
    $ date > /tmp/my_file
    # @note: You can change the name of `group.toml` to `public.toml`! and run `ftcosi` cmd like this:
    $ ~/go/bin/ftcosi sign  /tmp/my_file | tee sig.json
    # @note: And also you can use `DEDIS_GROUP` env and set path of `public.toml` and run `ftcosi` cmd like this:
    $ export DEDIS_GROUP=group.toml
    $ ~/go/bin/ftcosi sign --group $DEDIS_GROUP /tmp/my_file | tee sig.json

Output:

.. code-block:: bash

    {
        "Hash": "f28d7749dfd8dc2275345a134995e4b432fe051e56d1d2cac2d346cf475c5e52",
        "Signature": "ec4ccdb41c2c37caad5a26a0e575bff9aefea7f6993e3a47dc30ca8e888d73d9eb24289227e6cc9d699be407791da2e57ded947930ba4586baee3143918fc00203"
    }

Verify:

.. code-block:: bash

    $ ~/go/bin/ftcosi verify --group $DEDIS_GROUP --signature sig.json /tmp/my_file

        [+] OK: Signature is valid.

https://github.com/dedis/cothority#collective-signing

