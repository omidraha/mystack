Opera
=====

Add  Opera source list


.. code-block:: bash

    ## Add this line for Opera browser
    ## use "stable" instead of distribution name
    $ deb http://deb.opera.com/opera stable non-free

Now, we are going to trust Opera :

    $ sudo su
    $ wget -O - http://deb.opera.com/archive.key | apt-key add -


https://wiki.debian.org/Opera