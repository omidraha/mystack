ReplicaSet
==========

http://blog.mongodb.org/post/53841037541/real-time-profiling-a-mongodb-cluster

MongoDB provides two flavors of replication: master-slave replication and replica sets. 
For both, a single primary node receives all writes, and then all secondary nodes read and apply those writes to themselves asynchronously.

Master-slave replication and replica sets use the same replication mechanism, 
but replica sets additionally ensure automated failover: if the primary node goes offline for any reason, then one of the secondary nodes will automatically be promoted to primary, if possible. 
Replica sets provide other enhancements too, such as easier recovery and more sophistical deployment topologies

The only time you should opt for MongoDB’s master-slave replication is when you’d require more than 11 slave nodes, since a replica set can have no more than 12 members.

In addition to protecting against external failures, replication has been important for MongoDB in particular for durability. 
When running without journaling enabled, MongoDB’s data files aren’t guaranteed to be free of corruption in the event of an unclean shutdown. 

Without journaling, replication must always be run to guarantee a clean copy of the data files if a single node shuts down hard.
Of course, replication is desirable even when running with journaling. After all, you still want high availability and fast failover. 

In this case, journaling expedites recovery because it allows you to bring failed nodes back online simply by replaying the journal. 
This is much faster than resyncing from an existing replica or copying a replica’s data files manually.

Journaled or not, MongoDB’s replication greatly increases the reliability of the overall database deployments and is highly recommended.

replicas aren’t a replacement for backups. A backup represents a snapshot of the database at a particular time in the past, whereas a replica is always up to date.

Scaling reads with secondaries isn’t practical if any of the following apply
----------------------------------------------------------------------------------
1. The allotted hardware can’t process the given workload. As an example, If your working data set is much larger than the available RAM, 
	then sending random reads to the secondaries is still likely to result in excessive disk access, and thus slow queries.
2. The ratio of writes to reads exceeds 50%. This is an admittedly arbitrary ratio, but it’s a reasonable place to start. 
	The issue here is that every write to the primary must eventually be written to all the secondaries as well. Therefore directing reads to secondaries that are already processing a lot of writes can sometimes slow the replication process and may not result in increased read throughput.
3. The application requires consistent reads. Secondary nodes replicate asynchronously and therefore aren’t guaranteed to reflect the latest writes to the primary node. 
	In pathological cases, secondaries can run hours behind.


Configure a Delayed Replica Set Member
--------------------------------------

To configure a delayed secondary member, set its priority value to 0, its hidden value to true, and its slaveDelay value to the number of seconds to delay.

.. code-block:: bash

	cfg = rs.conf()
	cfg.members[0].priority = 0
	cfg.members[0].hidden = true
	cfg.members[0].slaveDelay = 3600
	rs.reconfig(cfg)

The length of the secondary slaveDelay must fit within the window of the oplog. 
If the oplog is shorter than the slaveDelay window, the delayed member cannot successfully replicate operations.
After the replica set reconfigures, the delayed secondary member cannot become primary and is hidden from applications. 

http://docs.mongodb.org/manual/tutorial/configure-a-delayed-replica-set-member/

http://docs.mongodb.org/manual/core/replica-set-delayed-member/


Replica sets Setup
------------------

The minimum recommended replica set configuration consists of three nodes. 
Two of these nodes serve as first-class, persistent mongod instances. 

Either can act as the replica set primary, and both have a full copy of the data. 
The third node in the set is an arbiter, which doesn’t replicate data, but merely acts as a kind of neutral observer. 

As the name suggests, the arbiter arbitrates: when failover is required, the arbiter helps to elect a new primary node

Start by creating a data directory for each replica set member:

.. code-block:: bash

	mkdir /data/mongo-sample/node1
	mkdir /data/mongo-sample/node2
	mkdir /data/mongo-sample/arbiter

Next, start each member as a separate mongod. Since you’ll be running each process on the same machine, 
it’s probably easiest to start each mongod in a separate terminal window:

.. code-block:: bash

	mongod --replSet myapp --dbpath /data/mongo-sample/node1 --port 40000
	mongod --replSet myapp --dbpath /data/mongo-sample/node2 --port 40001
	mongod --replSet myapp --dbpath /data/mongo-sample/arbiter --port 40002

If you examine the mongod log output, the first thing you’ll notice are error messages saying that the configuration can’t be found. 

The is completely normal:

.. code-block:: bash

	Sun Jul 14 09:58:55.576 [initandlisten] allocator: tcmalloc
	Sun Jul 14 09:58:55.576 [initandlisten] options: { dbpath: "/data/mongo-sample/node1", port: 4000, replSet: "myapp" }
	Sun Jul 14 09:58:55.651 [FileAllocator] allocating new datafile /data/mongo-sample/node1/local.ns, filling with zeroes...
	Sun Jul 14 09:58:55.651 [FileAllocator] creating directory /data/mongo-sample/node1/_tmp
	Sun Jul 14 09:58:55.706 [FileAllocator] done allocating datafile /data/mongo-sample/node1/local.ns, size: 16MB,  took 0.022 secs
	Sun Jul 14 09:58:55.707 [FileAllocator] allocating new datafile /data/mongo-sample/node1/local.0, filling with zeroes...
	Sun Jul 14 09:58:55.729 [FileAllocator] done allocating datafile /data/mongo-sample/node1/local.0, size: 16MB,  took 0.022 secs
	Sun Jul 14 09:58:55.734 [initandlisten] waiting for connections on port 4000
	Sun Jul 14 09:58:55.734 [websvr] admin web console waiting for connections on port 5000
	Sun Jul 14 09:58:55.739 [rsStart] replSet can't get local.system.replset config from self or any seed (EMPTYCONFIG)
	Sun Jul 14 09:58:55.739 [rsStart] replSet info you may need to run replSetInitiate -- rs.initiate() in the shell -- if that is not already done
	Sun Jul 14 09:59:05.739 [rsStart] replSet can't get local.system.replset config from self or any seed (EMPTYCONFIG)
	Sun Jul 14 09:59:15.739 [rsStart] replSet can't get local.system.replset config from self or any seed (EMPTYCONFIG)

To proceed, you need to configure the replica set. 

Do so by first connecting to one of the non-arbiter mongods just started.

Connect, and then run the rs.initiate() command:

.. code-block:: bash

	omidraha@debian:~$ mongo --port 4000
	> rs.status()
	{
	   "startupStatus" : 3,
	   "info" : "run rs.initiate(...) if not yet done for the set",
	   "ok" : 0,
	   "errmsg" : "can't get local.system.replset config from self or any seed (EMPTYCONFIG)"
	}
	> rs.initiate()
	{
	   "info2" : "no configuration explicitly specified -- making one",
	   "me" : "debian:4000",
	   "info" : "Config now saved locally.  Should come online in about a minute.",
	   "ok" : 1
	}

mongo node1 log:

.. code-block:: bash

	Sun Jul 14 10:11:02.875 [conn2] replSet replSetInitiate admin command received from client
	Sun Jul 14 10:11:02.877 [conn2] replSet info initiate : no configuration specified.  Using a default configuration for the set
	Sun Jul 14 10:11:02.877 [conn2] replSet created this configuration for initiation : { _id: "myapp", members: [ { _id: 0, host: "debian:4000" } ] }
	Sun Jul 14 10:11:02.877 [conn2] replSet replSetInitiate config object parses ok, 1 members specified
	Sun Jul 14 10:11:02.878 [conn2] replSet replSetInitiate all members seem up
	Sun Jul 14 10:11:02.878 [conn2] ******
	Sun Jul 14 10:11:02.878 [conn2] creating replication oplog of size: 50MB...
	Sun Jul 14 10:11:02.878 [FileAllocator] allocating new datafile /data/mongo-sample/node1/local.1, filling with zeroes...
	Sun Jul 14 10:11:02.923 [FileAllocator] done allocating datafile /data/mongo-sample/node1/local.1, size: 64MB,  took 0.044 secs
	Sun Jul 14 10:11:03.068 [conn2] ******
	Sun Jul 14 10:11:03.068 [conn2] replSet info saving a newer config version to local.system.replset
	Sun Jul 14 10:11:03.168 [conn2] replSet saveConfigLocally done
	Sun Jul 14 10:11:03.168 [conn2] replSet replSetInitiate config now saved locally.  Should come online in about a minute.
	Sun Jul 14 10:11:03.168 [conn2] command admin.$cmd command: { replSetInitiate: undefined } ntoreturn:1 keyUpdates:0 locks(micros) W:291798 reslen:195 292ms
	Sun Jul 14 10:11:05.765 [rsStart] replSet I am debian:4000
	Sun Jul 14 10:11:05.766 [rsStart] replSet STARTUP2
	Sun Jul 14 10:11:06.767 [rsSync] replSet SECONDARY
	Sun Jul 14 10:11:06.767 [rsMgr] replSet info electSelf 0
	Sun Jul 14 10:11:07.767 [rsMgr] replSet PRIMARY
	Sun Jul 14 10:12:48.733 [conn2] replSet replSetReconfig config object parses ok, 2 members specified
	Sun Jul 14 10:12:48.734 [conn2] replSet replSetReconfig [2]
	Sun Jul 14 10:12:48.734 [conn2] replSet info saving a newer config version to local.system.replset
	Sun Jul 14 10:12:48.807 [conn2] replSet saveConfigLocally done
	Sun Jul 14 10:12:48.808 [conn2] replSet info : additive change to configuration
	Sun Jul 14 10:12:48.808 [conn2] replSet replSetReconfig new config saved locally

Within a minute or so, you’ll have a one-member replica set. You can now add the other two members using rs.add():

.. code-block:: bash

	myapp:PRIMARY> rs.add("debian:4001")
	{ "ok" : 1 }

mongo node1 log:

.. code-block:: bash

	Sun Jul 14 10:12:48.808 [conn2] replSet info : additive change to configuration
	Sun Jul 14 10:12:48.808 [conn2] replSet replSetReconfig new config saved locally
	Sun Jul 14 10:12:48.809 [rsHealthPoll] replSet member debian:4001 is up
	Sun Jul 14 10:12:48.809 [rsMgr] replSet total number of votes is even - add arbiter or give one member an extra vote
	Sun Jul 14 10:12:58.049 [initandlisten] connection accepted from 127.0.0.1:40047 #3 (2 connections now open)
	Sun Jul 14 10:12:58.811 [rsHealthPoll] replset info debian:4001 thinks that we are down
	Sun Jul 14 10:12:58.811 [rsHealthPoll] replSet member debian:4001 is now in state STARTUP2
	Sun Jul 14 10:13:14.277 [initandlisten] connection accepted from 127.0.0.1:40050 #4 (3 connections now open)
	Sun Jul 14 10:13:14.434 [conn4] end connection 127.0.0.1:40050 (2 connections now open)
	Sun Jul 14 10:13:14.815 [rsHealthPoll] replSet member debian:4001 is now in state RECOVERING
	Sun Jul 14 10:13:15.117 [initandlisten] connection accepted from 127.0.0.1:40051 #5 (3 connections now open)
	Sun Jul 14 10:13:15.434 [initandlisten] connection accepted from 127.0.0.1:40052 #6 (4 connections now open)
	Sun Jul 14 10:13:16.451 [slaveTracking] build index local.slaves { _id: 1 }
	Sun Jul 14 10:13:16.453 [slaveTracking] build index done.  scanned 0 total records. 0.001 secs
	Sun Jul 14 10:13:16.816 [rsHealthPoll] replSet member debian:4001 is now in state SECONDARY

Within a minute or so, you’ll have a one-member replica set. 

You can now add the other two members using rs.add():


.. code-block:: bash

	myapp:PRIMARY> rs.add("debian:4002", {aribterOnly: true})
	{ "ok" : 1 }


mongo node 1 log

.. code-block:: bash

	Sun Jul 14 10:18:14.555 [conn2] replSet info : additive change to configuration
	Sun Jul 14 10:18:14.555 [conn2] replSet replSetReconfig new config saved locally
	Sun Jul 14 10:18:14.557 [rsHealthPoll] replSet member debian:4002 is up
	Sun Jul 14 10:18:21.957 [initandlisten] connection accepted from 127.0.0.1:36193 #17 (5 connections now open)
	Sun Jul 14 10:18:22.559 [rsHealthPoll] replset info debian:4002 thinks that we are down
	Sun Jul 14 10:18:22.559 [rsHealthPoll] replSet member debian:4002 is now in state STARTUP2
	Sun Jul 14 10:18:24.559 [rsHealthPoll] replSet member debian:4002 is now in state ARBITER

Note that for the second node, you specify the arbiterOnly option to create an arbiter. 

Within a minute, all members should be online

.. code-block:: bash

	myapp:PRIMARY> db.isMaster()
	{
	   "setName" : "myapp",
	   "ismaster" : true,
	   "secondary" : false,
	   "hosts" : [
					   "debian:4000",
					   "debian:4001"
	   ],
	   "arbiters" : [
					   "debian:4002"
	   ],
	   "primary" : "debian:4000",
	   "me" : "debian:4000",
	   "maxBsonObjectSize" : 16777216,
	   "maxMessageSizeBytes" : 48000000,
	   "localTime" : ISODate("2013-07-14T07:00:21.925Z"),
	   "ok" : 1
	}

A more detailed view of the system is provided by the rs.status() method. You’ll see state information for each node. Here’s the complete status listing:


.. code-block:: bash

	myapp:PRIMARY> rs.status()
	{
	   "set" : "myapp",
	   "date" : ISODate("2013-07-14T07:07:01Z"),
	   "myState" : 1,
	   "members" : [
				   {
					   "_id" : 0,
					   "name" : "debian:4000",
					   "health" : 1,
					   "state" : 1,
					   "stateStr" : "PRIMARY",
					   "uptime" : 466,
					   "optime" : {
									   "t" : 1373780894,
									   "i" : 1
					   },
					   "optimeDate" : ISODate("2013-07-14T05:48:14Z"),
					   "self" : true
				   },
				   {
					   "_id" : 1,
					   "name" : "debian:4001",
					   "health" : 1,
					   "state" : 2,
					   "stateStr" : "SECONDARY",
					   "uptime" : 458,
					   "optime" : {
									   "t" : 1373780894,
									   "i" : 1
					   },
						   "optimeDate" : ISODate("2013-07-14T05:48:14Z"),
						   "lastHeartbeat" : ISODate("2013-07-14T07:06:59Z"),
						   "lastHeartbeatRecv" : ISODate("2013-07-14T07:07:00Z"),
						   "pingMs" : 0,
						   "syncingTo" : "debian:4000"
				   },
				   {
					   "_id" : 2,
					   "name" : "debian:4002",
					   "health" : 1,
					   "state" : 7,
					   "stateStr" : "ARBITER",
					   "uptime" : 454,
					   "lastHeartbeat" : ISODate("2013-07-14T07:06:59Z"),
					   "lastHeartbeatRecv" : ISODate("2013-07-14T07:07:01Z"),
					   "pingMs" : 0
				   }
	   ],
	   "ok" : 1
	}



Unless your MongoDB database contains a lot of data, the replica set should come online within 30 seconds. 

During this time, the stateStr field of each node should transition from RECOVERING to PRIMARY, SECONDARY, or ARBITER.


Cmds
----

.. code-block:: bash

	use admin
	db.shutdownServer()

	db.getReplicationInfo()

	db.oplog.rs.find().sort({$natural: -1})



`<http://docs.mongodb.org/manual/core/replica-set-elections/#optime>`_

 A replica set member cannot become primary unless it has the highest (i.e. most recent) optime of any visible member in the set.

`<http://docs.mongodb.org/manual/reference/glossary/>`_

`<http://docs.mongodb.org/manual/reference/glossary/#term-strict-consistency>`_

A property of a distributed system that allows changes to the system to propagate gradually. In a database system,
this means that readable members are not required to reflect the latest writes at all times. In MongoDB, reads to a primary have strict consistency;
reads to secondaries have eventual consistency.

`<http://docs.mongodb.org/manual/reference/glossary/#term-eventual-consistency>`_

A property of a distributed system requiring that all members always reflect the latest changes to the system.
In a database system, this means that any system that can provide data must reflect the latest writes at all times.
In MongoDB, reads from a primary have strict consistency; reads from secondary members have eventual consistency.

`<http://docs.mongodb.org/manual/core/replica-set-sync/#validity-and-durability>`_

In a replica set, only the primary can accept write operations. Writing only to the primary provides strict consistency among members.

Journaling provides single-instance write durability. Without journaling,
if a MongoDB instance terminates ungracefully, you must assume that the database is in an invalid state.

While applying a batch, MongoDB blocks all reads. As a result, secondaries can never return data that reflects a state that never existed on the primary.
