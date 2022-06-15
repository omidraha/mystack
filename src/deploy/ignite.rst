Ignite
======


Run ignite as docker
--------------------

.. code-block:: bash

    $ docker pull apacheignite/ignite
    $ docker run -it --net=host -e "CONFIG_URI=https://raw.githubusercontent.com/apache/ignite/master/examples/config/example-cache.xml" apacheignite/ignite



.. code-block:: bash

    $ pip install pylibmc


Connect to ignite as memcache with python
-----------------------------------------

.. code-block:: python


    import pylibmc
    client = pylibmc.Client (["127.0.0.1:11211"], binary=True)
    client.set("key", 2**60)
    client.set("key", b'You need to send message as binary')
    print("Value for 'key': %s"%client.get("key"))



There is an error when try to set string value for keys instead of binary.

.. code-block:: python

    client.set("key", 'string message')

.. code-block:: bash

    Traceback (most recent call last):
      File "<stdin>", line 1, in <module>
    _pylibmc.ConnectionError: error 3 from memcached_set: (0x21104c0) CONNECTION FAILURE, ::rec() returned zero, server has disconnected,  host: 127.0.0.1:11211 -> libmemcached/io.cc:484

https://apacheignite.readme.io/docs/memcached-support#section-python


Enable HTTP rest API
--------------------

.. code-block:: bash

    $ docker run --name ignite --net=host -e "CONFIG_URI=https://raw.githubusercontent.com/apache/ignite/master/examples/config/example-cache.xml" apacheignite/ignite

https://apacheignite.readme.io/docs/docker-deployment


TO enable HTTP connectivity, make sure that ignite-rest-http module is in the classpath of your application.
With Ignite binary distribution,
this means copying ignite-rest-http module from IGNITE_HOME\libs\optional\ to IGNITE_HOME\libs folder.


https://apacheignite.readme.io/docs/rest-api#section-getting-started

.. code-block:: bash

    $ docker exec -it ignite bash
    bash-4.4# cp -r apache-ignite-fabric/libs/optional/ignite-rest-http/ apache-ignite-fabric/libs/

.. code-block:: bash

    $ docker stop ignite
    $ docker start ignite

.. code-block:: bash

    $ curl http://localhost:8080/ignite?cmd=version
    $ curl "http://localhost:8080/ignite?cmd=getorcreate&cacheName=default"


Ignite Configuration
--------------------

Persistence
+++++++++++

https://ignite.apache.org/arch/persistence.html

https://apacheignite.readme.io/docs/distributed-persistent-store

Memory
++++++

https://apacheignite.readme.io/docs/memory-configuration

https://apacheignite.readme.io/docs/cache-modes

http://apache-ignite-users.70518.x6.nabble.com/Best-practise-for-setting-Ignite-Active-to-true-when-using-persistence-layer-in-Ignite-2-1-td15839.html

<property name="cacheMode" value="LOCAL"/>

Evictions
+++++++++

https://apacheignite.readme.io/docs/evictions


Cluster is inactive
+++++++++++++++++++

.. code-block:: bash

    $ ./apache-ignite-fabric/bin/control.sh --activate

"""
[12:57:49,510][SEVERE][rest-#54][GridCacheCommandHandler] Failed to execute cache command: GridRestCacheRequest [cacheName=default, cacheFlags=0, ttl=null, super=GridRestRequest [destId=null, clientId=null, addr=/127.0.0.1:44546, cmd=GET_OR_CREATE_CACHE]]
class org.apache.ignite.IgniteException: Can not perform the operation because the cluster is inactive. Note, that the cluster is considered inactive by default if Ignite Persistent Store is used to let all the nodes join the cluster. To activate the cluster call Ignite.active(true).
"""


Sample Ingnite configuration file
---------------------------------

.. code-block:: xml


    <?xml version="1.0" encoding="UTF-8"?>


    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="
            http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd">
        <bean id="ignite.cfg" class="org.apache.ignite.configuration.IgniteConfiguration">
            <property name="dataStorageConfiguration">
                <bean class="org.apache.ignite.configuration.DataStorageConfiguration">
                    <property name="defaultDataRegionConfiguration">
                        <bean class="org.apache.ignite.configuration.DataRegionConfiguration">
                            <property name="name" value="region_20MB"/>
                            <property name="persistenceEnabled" value="true"/>
                            <!-- 20MB -->
                            <property name="initialSize" value="#{20L * 1024 * 1024}"/>
                            <!-- 20MB -->
                            <property name="maxSize" value="#{20L * 1024 * 1024}"/>
                            <property name="pageEvictionMode" value="RANDOM_LRU"/>
                        </bean>
                    </property>
                </bean>
            </property>
            <property name="cacheConfiguration">
                <list>
                    <!-- Partitioned cache example configuration (Atomic mode). -->
                    <bean class="org.apache.ignite.configuration.CacheConfiguration">
                        <property name="name" value="default"/>
                        <property name="dataRegionName" value="region_20MB"/>
                        <property name="atomicityMode" value="ATOMIC"/>
                        <property name="backups" value="1"/>
                    </bean>
                </list>
            </property>
            <property name="discoverySpi">
                <bean class="org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi">
                    <property name="ipFinder">
                        <bean class="org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder">
                            <property name="addresses">
                                <list>
                                    <!-- In distributed environment, replace with actual host IP address. -->
                                    <value>127.0.0.1:47500..47509</value>
                                </list>
                            </property>
                        </bean>
                    </property>
                </bean>
            </property>
        </bean>
    </beans>
