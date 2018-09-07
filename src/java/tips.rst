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
