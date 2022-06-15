Firefox
=======


.. code-block:: bash

    $ aptitude install nss-passwords
    $ nss-passwords example.com
    | http://example.com | USERNAME | PASSWORD |



Disable Dns Cache
-----------------

Type in about:config in the address bar

Right click on the list of Properties and select New > Integer in the Context menu
Enter network.dnsCacheExpiration as the preference name and 0 as the integer value
When disabled,

Firefox will use the DNS cache provided by the OS.

http://en.kioskea.net/faq/555-disabling-the-dns-cache-in-mozilla-firefox


Increase download them all maximum segments
-------------------------------------------
Type in about:config in the address bar

Type `extensions.dta.maxchunks` and change number you want.

After that, don't change it from download them all panel.


Set security tls
----------------

Go to `about:config` , and set:

    security.tls.version.min 0
    security.tls.version.max 0 # default is 3


Disable automatic loading of Images in Firefox
----------------------------------------------

Go to about:config, search for this option "permissions.default.image" change to 1.

Possible values:

1 -- always load the images

2 -- never load the images

3 -- don't load third images

Fix Firefox Phishing
--------------------
The xn-- prefix is what is known as an ‘ASCII compatible encoding’ prefix.
It lets the browser know that the domain uses ‘punycode’ encoding to represent Unicode characters.
In non-techie speak, this means that if you have a domain name with Chinese or other international characters,
you can register a domain name with normal A-Z characters that can
allow a browser to represent that domain as international characters in the location bar.
What we have done above is used ‘e’ ‘p’ ‘i’ and ‘c’ unicode characters that look identical to the real characters
but are different unicode characters. In the current version of Chrome, as long as all characters are unicode,
it will show the domain in its internationalized form.

.. code-block:: bash

    about:config
    network.IDN_show_punycode = false

https://www.wordfence.com/blog/2017/04/chrome-firefox-unicode-phishing/
