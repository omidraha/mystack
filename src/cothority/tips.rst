Tips
====


Install
-------

Setting up Conode on each of servers:

Servers:

* 192.168.0.180
* 192.168.0.181
* 192.168.0.182

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

Apps
----


Status of conodes
+++++++++++++++++


Copy the ``public.toml`` file from servers to somewhere you want to run status command:

.. code-block:: bash

    $ scp ubuntu@192.168.0.180:~/conode_data/public.toml  ct1_public.toml
    $ scp ubuntu@192.168.0.181:~/conode_data/public.toml  ct2_public.toml
    $ scp ubuntu@192.168.0.182:~/conode_data/public.toml  ct3_public.toml
    $ cat ct1_public.toml ct2_public.toml ct3_public.toml >  public.toml
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
    [[servers]]
      Address = "tls://192.168.0.182:6879"
      Suite = "Ed25519"
      Public = "2c84becca826a737560d572ce1f4e4bbda47f32044611e660dcf5b27cf2c30c2"
      Description = "cothority-3"



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
    Db.Tx.CursorCount: 32
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
    Db.TxN: 14
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
    Generic.Uptime: 2h32m46.804728179s
    Generic.Version: 2.0
    Skipblock.Blocks: 0
    Skipblock.Bytes: 0
    Db.FreeAlloc: 8192
    Db.FreePageN: 0
    Db.FreelistInuse: 32
    Db.Open: true
    Db.OpenTxN: 0
    Db.PendingPageN: 2
    Db.Tx.CursorCount: 27
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
    Db.TxN: 9
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
    Generic.Uptime: 1h50m37.783106977s
    Generic.Version: 2.0
    Skipblock.Blocks: 0
    Skipblock.Bytes: 0
    Db.FreeAlloc: 8192
    Db.FreePageN: 0
    Db.FreelistInuse: 32
    Db.Open: true
    Db.OpenTxN: 0
    Db.PendingPageN: 2
    Db.Tx.CursorCount: 24
    Db.Tx.NodeCount: 7
    Db.Tx.NodeDeref: 0
    Db.Tx.PageAlloc: 57344
    Db.Tx.PageCount: 14
    Db.Tx.Rebalance: 0
    Db.Tx.RebalanceTime: 0s
    Db.Tx.Spill: 7
    Db.Tx.SpillTime: 24.517µs
    Db.Tx.Split: 0
    Db.Tx.Write: 21
    Db.Tx.WriteTime: 14.166472ms
    Db.TxN: 6
    Generic.Available_Services: Identity,PoPServer,Skipchain,Status,evoting,ftCoSiService
    Generic.ConnType: tls
    Generic.Description: cothority-3
    Generic.GoModuleInfo:
    Generic.GoRelease: go1.10.1
    Generic.Host: 192.168.0.182
    Generic.Port: 6879
    Generic.RX_bytes: 0
    Generic.System: linux/amd64/go1.10.1
    Generic.TX_bytes: 0
    Generic.Uptime: 2m26.909041005s
    Generic.Version: 2.0
    Skipblock.Blocks: 0
    Skipblock.Bytes: 0




https://github.com/dedis/cothority#status

https://github.com/dedis/cothority/blob/master/status/README.md


Collective Signing
++++++++++++++++++

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



Evoting
+++++++

.. code-block:: bash


    $ go get github.com/dedis/cothority/evoting/evoting-admin/
    $ cd $GOPATH/src/github.com/dedis/cothority/evoting/evoting-admin/ && go build -o $GOPATH/bin/evoting ./...

    $ ~/go/bin/evoting-admin --help

     -admins string
            list of admin users
      -id string
            ID of the master chain to modify (optional)
      -key string
            public key of authentication server
      -pin string
            service pin
      -roster string
            path to roster toml file
      -show
            Show the current Master config
      -sig string
            A signature proving that you can login to Tequila with the given SCIPER.


Make a new master chain:

.. code-block:: bash

    $ cp public.toml  roster.toml
    $ evoting-admin -roster roster.toml -pin f810aac19b690830d3e0c79a6c00a279 -admins 0,1,2,3

        I : (main.main:  83) - Auth-server private key: 4fba8025c5ba783fe30bdb2bab653307a1fa23e29f9f42fe9fbaca93dbf05d09
        I : (main.main: 114) - Auth-server public  key: 87e1df80e37bd624c3a0a5852f28cf97d0705017c5da0bb7b0a047137db5d6ed
        I : (main.main: 115) - Master ID: cf5f2f9bc05fc115e4d2ef869405a3e0841dff80bc8b36183f5f9d4142470b0c


Output of ``192.168.0.180`` conode server:

.. code-block:: bash


    2 : (                onet.wsHandler.ServeHTTP: 178) - ws request from 192.168.0.107:36154: evoting/Link
    2 : (     skipchain.(*Service).StoreSkipBlock: 177) - Creating new skipchain with roster [tls://192.168.0.180:6879 tls://192.168.0.181:6879 tls://192.168.0.182:6879]
    3 : (     skipchain.(*Service).StoreSkipBlock: 349) - Propagate 1 blocks
    3 : (   skipchain.(*Service).startPropagation: 1145) - Starting to propagate for service tls://192.168.0.180:6879
    3 : (      messaging.NewPropagationFunc.func2: 114) - tls://192.168.0.181:6879 Starting to propagate *skipchain.PropagateSkipBlocks
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (c687bde8-b612-577b-bc3c-dfb982382b64): SkipchainPropagate
    3 : (               network.(*Router).connect: 204) - tls://[::]:6879 Connecting to tls://192.168.0.180:6879
    2 : (                      network.NewTLSConn: 369) - NewTLSConn to: tls://192.168.0.180:6879
    2 : (network.NewTLSListenerWithListenAddr.func1: 243) - Got new connection request from: 172.17.0.1:37406
    3 : (            network.makeVerifier.func1.1: 276) - verify cert -> b097aca656644fc86eaade5f3e14d74a922e7d5e4d0e2f1a05d7a2750edfde02
    3 : (            network.makeVerifier.func1.1: 276) - verify cert -> b097aca656644fc86eaade5f3e14d74a922e7d5e4d0e2f1a05d7a2750edfde02
    3 : (               network.(*Router).connect: 210) - tls://[::]:6879 Connected to tls://192.168.0.180:6879
    3 : (            network.(*Router).handleConn: 273) - tls://[::]:6879 Handling new connection from tls://192.168.0.180:6879
    3 : (            network.(*Router).handleConn: 273) - tls://[::]:6879 Handling new connection from tls://192.168.0.180:6879
    3 : (         messaging.(*Propagate).Dispatch: 165) - tls://192.168.0.180:6879 Got data from tls://192.168.0.180:6879 and setting timeout to 15s
    3 : (         messaging.(*Propagate).Dispatch: 182) - tls://192.168.0.180:6879 Sending to children
    3 : (               network.(*Router).connect: 204) - tls://[::]:6879 Connecting to tls://192.168.0.181:6879
    2 : (                      network.NewTLSConn: 369) - NewTLSConn to: tls://192.168.0.181:6879
    3 : (               network.(*Router).connect: 204) - tls://[::]:6879 Connecting to tls://192.168.0.182:6879
    2 : (                      network.NewTLSConn: 369) - NewTLSConn to: tls://192.168.0.182:6879
    3 : (            network.makeVerifier.func1.1: 276) - verify cert -> 2c84becca826a737560d572ce1f4e4bbda47f32044611e660dcf5b27cf2c30c2
    3 : (            network.makeVerifier.func1.1: 276) - verify cert -> 7a6e03ba71bd87aa1a62972eb20788ab21250ea23ad3166e995225278b227983
    3 : (               network.(*Router).connect: 210) - tls://[::]:6879 Connected to tls://192.168.0.182:6879
    3 : (            network.(*Router).handleConn: 273) - tls://[::]:6879 Handling new connection from tls://192.168.0.182:6879
    3 : (               network.(*Router).connect: 210) - tls://[::]:6879 Connected to tls://192.168.0.181:6879
    3 : (            network.(*Router).handleConn: 273) - tls://[::]:6879 Handling new connection from tls://192.168.0.181:6879
    3 : (         messaging.(*Propagate).Dispatch: 215) - tls://192.168.0.180:6879 done, isroot: true
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (c687bde8-b612-577b-bc3c-dfb982382b64): SkipchainPropagate has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (c687bde8-b612-577b-bc3c-dfb982382b64): SkipchainPropagate
    3 : (         messaging.propagateStartAndWait: 142) - Finished propagation with 3 replies
    3 : (     skipchain.(*Service).StoreSkipBlock: 359) - Block added, replying. New latest is: cf5f2f9bc05fc115e4d2ef869405a3e0841dff80bc8b36183f5f9d4142470b0c, at index 0
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    2 : (     skipchain.(*Service).StoreSkipBlock: 206) - Adding block with roster [tls://192.168.0.180:6879 tls://192.168.0.181:6879 tls://192.168.0.182:6879] to cf5f2f9bc05fc115e4d2ef869405a3e0841dff80bc8b36183f5f9d4142470b0c
    3 : (     skipchain.(*Service).StoreSkipBlock: 315) - Checking if all nodes from roster accept block
    3 : (onet.(*TreeNodeInstance).RegisterHandler: 295) - Registered handler PTID(skipchain.ProtoExtendRoster:a8a68b7b918356b69ce0333776a166b0) with flags 0
    3 : (onet.(*TreeNodeInstance).RegisterHandler: 295) - Registered handler PTID(skipchain.ProtoExtendRosterReply:bb6cd0d0ac0b5a5c84a5279f0fc266a6) with flags 0
    3 : (         skipchain.(*ExtendRoster).Start:  90) - Starting Protocol ExtendRoster
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (d157abe9-6533-5490-a32a-8d206d8469d1): scExtendRoster
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (d157abe9-6533-5490-a32a-8d206d8469d1): scExtendRoster has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (d157abe9-6533-5490-a32a-8d206d8469d1): scExtendRoster
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (  skipchain.(*Service).forwardLinkLevel0: 825) - tls://192.168.0.180:6879 is adding forward-link to [tls://192.168.0.180:6879 tls://192.168.0.181:6879 tls://192.168.0.182:6879]: 0->1
    3 : (              byzcoinx.(*ByzCoinX).Start:  77) - Starting prepare phase
    3 : (                protocol.(*FtCosi).Start: 332) - Starting CoSi
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (d629e4fc-b3db-5a96-85f9-37d7fb49cd49): SkipchainBFTNew
    3 : (             protocol.(*FtCosi).Dispatch: 120) - leader protocol started
    3 : (onet.(*TreeNodeInstance).RegisterHandler: 295) - Registered handler PTID(protocol.Stop:9d8b4bf59a8a5ea79bfda30dbb7e4be8) with flags 0
    3 : (             protocol.(*SubFtCosi).Start: 277) - tls://192.168.0.180:6879 Starting subCoSi
    3 : (             protocol.(*FtCosi).Dispatch: 148) - tls://192.168.0.180:6879 all protocols started
    3 : (       protocol.(*FtCosi).Dispatch.func1: 124) - tls://192.168.0.180:6879 starting verification
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (eee01075-292f-5128-8bb0-c1b54565e9ae): SkipchainBFTNew_subcosi_prep
    3 : (          protocol.(*SubFtCosi).Dispatch: 105) - tls://192.168.0.180:6879 received announcement
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (26104825-e456-5c14-9d7c-91c42903402a): SkipchainBFTNew_cosi_prep
    3 : (          protocol.(*SubFtCosi).Dispatch: 167) - tls://192.168.0.180:6879 finished receiving commitments,  1 commitment(s) received
    3 : (             protocol.(*FtCosi).Dispatch: 164) - root-node generating global challenge
    3 : (          protocol.(*SubFtCosi).Dispatch: 220) - tls://192.168.0.180:6879 received challenge
    3 : (          protocol.(*SubFtCosi).Dispatch: 238) - tls://192.168.0.180:6879 received all 1 response(s)
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (eee01075-292f-5128-8bb0-c1b54565e9ae): SkipchainBFTNew_subcosi_prep has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (eee01075-292f-5128-8bb0-c1b54565e9ae): SkipchainBFTNew_subcosi_prep
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (               protocol.generateResponse: 111) - tls://192.168.0.180:6879 Verification successful
    3 : (               protocol.generateResponse: 120) - tls://192.168.0.180:6879 is done aggregating responses with total of 2 responses
    3 : (             protocol.(*FtCosi).Dispatch: 218) - tls://192.168.0.180:6879 starts final signature
    3 : (             protocol.(*FtCosi).Dispatch: 226) - Root-node is done without errors
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (26104825-e456-5c14-9d7c-91c42903402a): SkipchainBFTNew_cosi_prep has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (26104825-e456-5c14-9d7c-91c42903402a): SkipchainBFTNew_cosi_prep
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (           byzcoinx.(*ByzCoinX).Dispatch: 149) - Finished prepare phase
    3 : (           byzcoinx.(*ByzCoinX).Dispatch: 152) - Starting commit phase
    3 : (                protocol.(*FtCosi).Start: 332) - Starting CoSi
    3 : (             protocol.(*FtCosi).Dispatch: 120) - leader protocol started
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (5af294ee-859a-5df6-8b6d-118f54741ede): SkipchainBFTNew_cosi_commit
    3 : (       protocol.(*FtCosi).Dispatch.func1: 124) - tls://192.168.0.180:6879 starting verification
    3 : (onet.(*TreeNodeInstance).RegisterHandler: 295) - Registered handler PTID(protocol.Stop:9d8b4bf59a8a5ea79bfda30dbb7e4be8) with flags 0
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (f7176736-e73e-579d-a837-c0f0e9578d9a): SkipchainBFTNew_subcosi_commit
    3 : (             protocol.(*SubFtCosi).Start: 277) - tls://192.168.0.180:6879 Starting subCoSi
    3 : (             protocol.(*FtCosi).Dispatch: 148) - tls://192.168.0.180:6879 all protocols started
    3 : (          protocol.(*SubFtCosi).Dispatch: 105) - tls://192.168.0.180:6879 received announcement
    3 : (          protocol.(*SubFtCosi).Dispatch: 167) - tls://192.168.0.180:6879 finished receiving commitments,  1 commitment(s) received
    3 : (             protocol.(*FtCosi).Dispatch: 164) - root-node generating global challenge
    3 : (          protocol.(*SubFtCosi).Dispatch: 220) - tls://192.168.0.180:6879 received challenge
    3 : (          protocol.(*SubFtCosi).Dispatch: 238) - tls://192.168.0.180:6879 received all 1 response(s)
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (f7176736-e73e-579d-a837-c0f0e9578d9a): SkipchainBFTNew_subcosi_commit has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (f7176736-e73e-579d-a837-c0f0e9578d9a): SkipchainBFTNew_subcosi_commit
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (               protocol.generateResponse: 111) - tls://192.168.0.180:6879 Verification successful
    3 : (               protocol.generateResponse: 120) - tls://192.168.0.180:6879 is done aggregating responses with total of 2 responses
    3 : (             protocol.(*FtCosi).Dispatch: 218) - tls://192.168.0.180:6879 starts final signature
    3 : (             protocol.(*FtCosi).Dispatch: 226) - Root-node is done without errors
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (5af294ee-859a-5df6-8b6d-118f54741ede): SkipchainBFTNew_cosi_commit has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (5af294ee-859a-5df6-8b6d-118f54741ede): SkipchainBFTNew_cosi_commit
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (           byzcoinx.(*ByzCoinX).Dispatch: 166) - Finished commit phase
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (d629e4fc-b3db-5a96-85f9-37d7fb49cd49): SkipchainBFTNew has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (d629e4fc-b3db-5a96-85f9-37d7fb49cd49): SkipchainBFTNew
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (  skipchain.(*Service).forwardLinkLevel0: 845) - tls://192.168.0.180:6879 adds forward-link to [tls://192.168.0.180:6879 tls://192.168.0.181:6879 tls://192.168.0.182:6879]: 0->1 - fwlinks:[]
    3 : (   skipchain.(*Service).startPropagation: 1145) - Starting to propagate for service tls://192.168.0.180:6879
    3 : (      messaging.NewPropagationFunc.func2: 114) - tls://192.168.0.180:6879 Starting to propagate *skipchain.PropagateSkipBlocks
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (099668f4-049f-5d59-b850-6227e076ccd9): SkipchainPropagate
    3 : (         messaging.(*Propagate).Dispatch: 165) - tls://192.168.0.180:6879 Got data from tls://192.168.0.180:6879 and setting timeout to 15s
    3 : (         messaging.(*Propagate).Dispatch: 182) - tls://192.168.0.180:6879 Sending to children
    3 : (         messaging.(*Propagate).Dispatch: 215) - tls://192.168.0.180:6879 done, isroot: true
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (099668f4-049f-5d59-b850-6227e076ccd9): SkipchainPropagate has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (099668f4-049f-5d59-b850-6227e076ccd9): SkipchainPropagate
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (         messaging.propagateStartAndWait: 142) - Finished propagation with 3 replies
    3 : (     skipchain.(*Service).StoreSkipBlock: 329) - Asking forward-links from all linked blocks
    3 : (     skipchain.(*Service).StoreSkipBlock: 349) - Propagate 2 blocks
    3 : (   skipchain.(*Service).startPropagation: 1145) - Starting to propagate for service tls://192.168.0.180:6879
    3 : (      messaging.NewPropagationFunc.func2: 114) - tls://192.168.0.181:6879 Starting to propagate *skipchain.PropagateSkipBlocks
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 455) - Starting node tls://192.168.0.180:6879 (c545a7d1-af42-518b-a7e9-4538384620cf): SkipchainPropagate
    3 : (         messaging.(*Propagate).Dispatch: 165) - tls://192.168.0.180:6879 Got data from tls://192.168.0.180:6879 and setting timeout to 15s
    3 : (         messaging.(*Propagate).Dispatch: 182) - tls://192.168.0.180:6879 Sending to children
    3 : (         messaging.(*Propagate).Dispatch: 215) - tls://192.168.0.180:6879 done, isroot: true
    3 : (           onet.(*TreeNodeInstance).Done: 571) - tls://192.168.0.180:6879 (c545a7d1-af42-518b-a7e9-4538384620cf): SkipchainPropagate has finished. Deleting its resources
    3 : (  onet.(*TreeNodeInstance).closeDispatch: 328) - Closing node tls://192.168.0.180:6879 (c545a7d1-af42-518b-a7e9-4538384620cf): SkipchainPropagate
    3 : (onet.(*TreeNodeInstance).dispatchMsgReader: 459) - Closing reader
    3 : (         messaging.propagateStartAndWait: 142) - Finished propagation with 3 replies
    3 : (     skipchain.(*Service).StoreSkipBlock: 359) - Block added, replying. New latest is: 84291836f8e62e7f4b619de39d724eb5546ebecf24392155627181df3d24ffcc, at index 1


https://github.com/dedis/cothority/blob/master/evoting/README.md

https://github.com/dedis/cothority/tree/master/evoting/evoting-admin



CISC
++++

.. code-block:: bash

    $ cisc --help

     link, ln       create and use links with admin privileges
     skipchain, sc  work with the underlying skipchain
     data, cfg      updating and voting on data
     keyvalue, kv   storing and retrieving key/value pairs
     ssh            interacting with the ssh-keys stored in the skipchain
     follow, f      follow skipchains
     web, w         add a web-site to a skipchain
     cert, c        create and use links with admin privileges
     help, h        Shows a list of commands or help for one command



Connecting to one conode:

.. code-block:: bash

    $ cisc link pin 192.168.0.180:6879

        Please read PIN in server-log

Output of ``192.168.0.180`` conode server:

.. code-block:: bash

    2 : (onet.wsHandler.ServeHTTP: 178) - ws request from 192.168.0.107:37832: Identity/PinRequest
    3 : (identity.(*Service).PinRequest: 117) - PinRequest tls://192.168.0.180:6879
    I : (identity.(*Service).PinRequest: 121) - PIN: 494777
    3 : (onet.wsHandler.ServeHTTP: 188) - Got an error while executing Identity/PinRequest: Read PIN in server-log

.. code-block:: bash

    $ cisc link pin 192.168.0.180:6879 494777

        Successfully linked with tcp://192.168.0.180:6879

.. code-block:: bash

    $ ls  ~/.cisc/

    config.bin

Creating an identity:

.. code-block:: bash

    $ cisc skipchain create group.toml

        Found full link to conode: 192.168.0.180:6879 44251bc84a7fe20a2fe0064b4ff858a01a5c14a3d4196239ea5d29e8b5cde354
        Creating new blockchain-identity for omid in roster [tls://192.168.0.180:6879 tls://192.168.0.181:6879 tls://192.168.0.182:6879]
        New cisc-id is: c1d0e4ab9b91781101406687c8d72992039955b653dfdc037fbf5758ccbd2a8d

Storing a key/value pair

.. code-block:: bash

    $ cisc keyvalue add name omid

        Stored key-value pair

    $ cisc keyvalue add family raha

    Stored key-value pair

.. code-block:: bash

    $ cisc  keyvalue list

        family: raha
        name: omid

    $ cisc  keyvalue list c1d0e4ab9b91781101406687c8d72992039955b653dfdc037fbf5758ccbd2a8d

        family: raha
        name: omid

https://github.com/dedis/cothority/blob/master/cisc/CLI.md
