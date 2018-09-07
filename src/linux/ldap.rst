LDAP
====

``http://www.debian-administration.org/articles/585#ldap-test1``

Install LDAP packages
---------------------

.. code-block:: bash

    # apt-get install slapd ldap-utils  libdb4.6
    # dpkg-reconfigure slapd

Configure LDAP package
----------------------

.. code-block:: bash

    # dpkg-reconfigure slapd
    ┌───────────────────────────────────┤ Configuring slapd ├───────────────────────────────────┐
    │                                                                                           │
    │ If you enable this option, no initial configuration or database will be created for you.  │
    │                                                                                           │
    │ Omit OpenLDAP server configuration?                                                       │
    │                                                                                           │
    │                          <Yes>                              [<No>]                        │
    │                                                                                           │
    └───────────────────────────────────────────────────────────────────────────────────────────┘

   ┌───────────────────────────────────┤ Configuring slapd ├────────────────────────────────────┐
   │ The DNS domain name is used to construct the base DN of the LDAP directory. For example,   │
   │ 'foo.example.org' will create the directory with 'dc=foo, dc=example, dc=org' as base DN.  │
   │                                                                                            │
   │ DNS domain name:                                                                           │
   │                                                                                            │
   │ bws.example.com_____________________________________________________________________________ │
   │                                                                                            │
   │                                          <Ok>                                              │
   │                                                                                            │
   └────────────────────────────────────────────────────────────────────────────────────────────┘

    ┌──────────────────────────────────┤ Configuring slapd ├───────────────────────────────────┐
    │ Please enter the name of the organization to use in the base DN of your LDAP directory.  │
    │                                                                                          │
    │ Organization name:                                                                       │
    │                                                                                          │
    │ example.com_______________________________________________________________________________ │
    │                                                                                          │
    │                                          <Ok>                                            │
    │                                                                                          │
    └──────────────────────────────────────────────────────────────────────────────────────────┘


    ┌─────────────────────────┤ Configuring slapd ├──────────────────────────┐
    │ Please enter the password for the admin entry in your LDAP directory.  │
    │                                                                        │
    │ Administrator password:                                                │
    │                                                                        │
    │ ********______________________________________________________________ │
    │                                                                        │
    │                                 <Ok>                                   │
    │                                                                        │
    └────────────────────────────────────────────────────────────────────────┘

    ┌───────────────────────────┤ Configuring slapd ├───────────────────────────┐
    │ Please enter the admin password for your LDAP directory again to verify   │
    │ that you have typed it correctly.                                         │
    │                                                                           │
    │ Confirm password:                                                         │
    │                                                                           │
    │ ********_________________________________________________________________ │
    │                                                                           │
    │                                  <Ok>                                     │
    │                                                                           │
    └───────────────────────────────────────────────────────────────────────────┘


    ┌───────────────────────────┤ Configuring slapd ├───────────────────────────┐
    │ The HDB backend is recommended. HDB and BDB use similar storage formats,  │
    │ but HDB adds support for subtree renames. Both support the same           │
    │ configuration options.                                                    │
    │                                                                           │
    │ In either case, you should review the resulting database configuration    │
    │ for your needs. See /usr/share/doc/slapd/README.DB_CONFIG.gz for more     │
    │ details.                                                                  │
    │                                                                           │
    │ Database backend to use:                                                  │
    │                                                                           │
    │                                   [BDB]                                   │
    │                                    HDB                                    │
    │                                                                           │
    │                                                                           │
    │                                  <Ok>                                     │
    │                                                                           │
    └───────────────────────────────────────────────────────────────────────────┘


    ┌─────────────────────┤ Configuring slapd ├─────────────────────┐
    │                                                               │
    │                                                               │
    │                                                               │
    │ Do you want the database to be removed when slapd is purged?  │
    │                                                               │
    │                <Yes>                   [<No>]                 │
    │                                                               │
    └───────────────────────────────────────────────────────────────┘


    ┌───────────────────────────┤ Configuring slapd ├───────────────────────────┐
    │                                                                           │
    │ There are still files in /var/lib/ldap which will probably break the      │
    │ configuration process. If you enable this option, the maintainer scripts  │
    │ will move the old database files out of the way before creating a new     │
    │ database.                                                                 │
    │                                                                           │
    │ Move old database?                                                        │
    │                                                                           │
    │                    <Yes>                       [<No>]                     │
    │                                                                           │
    └───────────────────────────────────────────────────────────────────────────┘


    ┌───────────────────────────┤ Configuring slapd ├───────────────────────────┐
    │                                                                           │
    │ The obsolete LDAPv2 protocol is disabled by default in slapd. Programs    │
    │ and users should upgrade to LDAPv3.  If you have old programs which       │
    │ can't use LDAPv3, you should select this option and 'allow bind_v2' will  │
    │ be added to your slapd.conf file.                                         │
    │                                                                           │
    │ Allow LDAPv2 protocol?                                                    │
    │                                                                           │
    │                    <Yes>                       [<No>]                     │
    │                                                                           │
    └───────────────────────────────────────────────────────────────────────────┘


Initial LDAP configuration
--------------------------

.. code-block:: bash

    # vim /etc/ldap/ldap.conf

        BASE  dc=bws,dc=example,dc=com
        URI ldap://172.16.1.200/

    # vim /usr/share/slapd/slapd.conf

        loglevel 256
        index           objectClass eq
        index           uid         eq

    # invoke-rc.d slapd stop
    # slapindex
    # chown openldap:openldap /var/lib/ldap/*
    # invoke-rc.d slapd start


Initial test
------------

.. code-block:: bash

    #ldapsearch -x
    #sudo slapcat

Creating basic tree structure
-----------------------------

.. code-block:: bash

    # vim ou.ldif
        dn: ou=People,dc=bws,dc=example,dc=com
        ou: People
        objectClass: organizationalUnit


Load the LDIF file into the server
----------------------------------

.. code-block:: bash

    # invoke-rc.d slapd stop
    # slapadd -c -v -l ou.ldif
    # invoke-rc.d slapd start

Test LDIF
---------

.. code-block:: bash

    # ldapsearch -x ou=people


Creating user accounts
----------------------

.. code-block:: bash

    # vim users.ldif

        dn: cn=omidraha,dc=bws,dc=example,dc=com
        objectClass: person
        objectClass: top
        cn: omidraha
        sn: omidraha

Load the LDIF file into the server
----------------------------------

.. code-block:: bash

    # ldapadd -x -D "cn=admin,dc=bws,dc=example,dc=com" -W -f users.ldif


To define the new user's password
---------------------------------

.. code-block:: bash

    # ldappasswd -x -D cn=admin,dc=bws,dc=example,dc=com -W -S cn=omidraha,dc=bws,dc=example,dc=com


Verify the user entry has been created
--------------------------------------

.. code-block:: bash

    # ldapsearch -x cn=omidraha



Sample python code to test
--------------------------

.. code-block:: python

    def auth_by_ldap(username, password, domain='dc=bws,dc=example,dc=com', server='ldap://localhost/'):
        import ldap
        con = ldap.initialize(server)
        dn = 'cn={},{}'.format(username, domain)
        try:
            con.simple_bind_s(dn, password.encode('utf8'))
        except ldap.INVALID_CREDENTIALS:
            return False
        return True
