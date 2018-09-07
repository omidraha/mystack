Introduction to java programming language
=========================================

.. code-block:: bash

    $ javac HelloWorld.java
    $ java -cp . HelloWorld

    $ javac -d . SimpleExample.java
    $ java -cp . com.mynotes.examples.SimpleExample

With the java command, the -cp (class path) option is used to designate where the command should seek out the specified
class(es). In this Example. is used to designate that the root directory for the classes is
the current local directory.


Note the use of the -d parameter with the javac command, which tells the compiler
that the destination of the compiled class (SimpleExample) and its directory structure’s
root is the local directory in which the Java file is located. What this means is that a
directory named com will be created. Within this com directory, an examples directory
is placed, and within examples, SimpleExample.class is generated (see Figure 2-1). This


http://www.sergiy.ca/how-to-compile-and-launch-java-code-from-command-line/

    Package names are all lowercase.

    Packages in the Java language begin with “java” or “javax.”

    Generally, companies use their Internet domain in reverse order (so a company like
    oreilly.com would become com.oreilly, nonprofit.org would become org.nonprofit,
    etc.). If the domain contains some special characters (nonalphanumeric) or conflicts with a reserved Java keyword,
    it is either not used or an underscore (_) is used instead.


In Java, what's the difference between public, default, protected, and private?
-------------------------------------------------------------------------------

http://stackoverflow.com/questions/215497/in-java-whats-the-difference-between-public-default-protected-and-private

    Public are accessible from everywhere.
    Protected are accessible by the classes of the same package and the subclasses residing in any package.
    Default are accessible by the classes of the same package.
    private are accessible within the same class only.



When should I use “this” in a class?
------------------------------------

http://stackoverflow.com/questions/2411270/when-should-i-use-this-in-a-class


Understanding constructors
--------------------------

http://www.javaworld.com/article/2076204/core-java/understanding-constructors.html

http://www.javabeginner.com/learn-java/java-constructors
