Tips
====

(JDK) Java Development Kit
(JRE)  Java Runtime Environment
Standard Edition (JavaSE)
Enterprise Edition (JavaEE, also known as J2EE),
Mobile Edition (JavaME)


How to install Oracle Java
--------------------------

Download and extract Oracle JDK to the `/opt/jdk` like path, and then:

.. code-block:: bash


    $ sudo update-alternatives --install /usr/bin/java java /opt/jdk/jre/bin/java 2000
    $ sudo update-alternatives --install /usr/bin/javac javac /opt/jdk/bin/javac 2000


Install Java on ubuntu
----------------------

.. code-block:: bash

    $ apt-get install default-jre

This will install the Java Runtime Environment (JRE).
If you instead need the Java Development Kit (JDK),
which is usually needed to compile Java applications (for example Apache Ant,
Apache Maven, Eclipse and IntelliJ IDEA execute the following command:

.. code-block:: bash

    $ apt-get install default-jdk


https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get


Switch between installed java
-----------------------------

Configures the default for the program "java". That's the Java VM

.. code-block:: bash


    $ sudo update-alternatives --config java

Configures the default Java compiler

.. code-block:: bash


    $ sudo update-alternatives --config javac



The $JAVA_HOME is empty
-----------------------

.. code-block:: bash

    $ whereis java
    java: /usr/bin/java /usr/share/java /usr/share/man/man1/java.1.gz


.. code-block:: bash

    $ ls -l /usr/bin/java
    /usr/bin/java -> /etc/alternatives/java

.. code-block:: bash

    $ ls -la /etc/alternatives/java
    /etc/alternatives/java -> /usr/lib/jvm/java-11-openjdk-amd64/bin/java

.. code-block:: bash

    $ sudo vim /etc/environment

    PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin"
    JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/
    export JAVA_HOME
