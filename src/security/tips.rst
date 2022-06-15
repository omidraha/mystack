Tips
====


`WebAppSec Secure Coding Guidelines <https://wiki.mozilla.org/WebAppSec/Secure_Coding_Guidelines>`_

`Django Secure <http://django-secure.readthedocs.org/en/v0.1.2/index.html>`_


List of Secure Email Providers that take Privacy Serious
--------------------------------------------------------

http://freedomhacker.net/list-of-secure-email-providers-that-take-privacy-serious/


* ShazzleMail
    One of the most secure email providers.

*  Hushmail.com
    Canadian based company, really great and respects privacy. The leading provider in secure email. Get %25 off paid plans with code FREEDOMHACKER.

* StartMail.com

*  Lavabit
    Shut down as of August 2013 (the owner cannot state why, but many reliable sources say whistle blower Edward Snowden was using it, and the court made him shut the service down.)

*  SilentCircle.com
    Mail Shut down as of August 2013. All of their other services still work.

* TorGuard.net

* RiseUp.net
    Great secure offshore provider. Non profit and fights for digital freedoms.

* OpaqueMail.org

* Autistici/Inventati

* NeoMailBox.com

* 4SecureMail.com

* CounterMail.cm

* CryptoHeaven.com

* S-Mail.com

* Securenym.net

* Safe-Mail.net

* KeptPrivate.com

* Novo-Ordo.com

* LockBin.com

* AES.io

* SendINC.com
    Sends encrypted emails, not actually a provider, but allows full communication through your email but encrypting your email/emails.

* Opolis.eu

* OneShar.es – Self destructing emails

* BitMessage.ch

* MaskMe - Great service, its not a provider, not a throwaway email provider either. Read on their page what they are about. I would recommend using this everyday with Hushmail.

* TorMail.org – Service now dead.


How to create your own root key and root certificate
----------------------------------------------------

https://jamielinux.com/articles/2013/08/act-as-your-own-certificate-authority/


Copy default ``/etc/ssl/openssl.cnf`` sample file  to your custom directory:

.. code-block:: bash

    $ cp /etc/ssl/openssl.cnf ~/my_crt

Ensure that your OpenSSL configuration file (``~/my_crt/openssl.cnf``) specifies ``dir=~/my_crt``
within the ``[ CA_default ]`` section.

.. code-block:: bash

    [ CA_default ]

    dir = ~/my_crt

The very first cryptographic pair we generate includes what is known as a root certificate.

The root key (``ca.key.pem``) generated in this step should be kept extremely secure,

Otherwise an attacker can issue valid certificates for themselves.

We'll therefore protect it with ``AES 256-bit`` encryption and a strong password just in case it falls into the wrong hands:

.. code-block:: bash

    $ openssl genrsa -aes256 -out ca.key.pem 4096 -config ~/my_crt/openssl.cnf

    Enter pass phrase for ca.key.pem: secretpassword
    Verifying - Enter pass phrase for ca.key.pem: secretpassword

Open your OpenSSL configuration file (``~/my_crt/openssl.cnf``) and look for the ``[ usr_cert ]``
and ``[ v3_ca ]`` sections.

Make sure they contain the following options:

.. code-block:: bash

    [ usr_cert ]
    # These extensions are added when 'ca' signs a request.
    basicConstraints=CA:FALSE
    keyUsage = nonRepudiation, digitalSignature, keyEncipherment
    nsComment = "OpenSSL Generated Certificate"
    subjectKeyIdentifier=hash
    authorityKeyIdentifier=keyid,issuer

    [ v3_ca ]
    # Extensions for a typical CA
    subjectKeyIdentifier=hash
    authorityKeyIdentifier=keyid:always,issuer
    basicConstraints = CA:true
    keyUsage = cRLSign, keyCertSign

Now you can use the root key above to issue a root certificate (``ca.cert.pem``).

In this example, the certificate is set to expire in ten years. As this is a CA certificate,
use the ``v3_ca`` extension. You will be prompted for some responses, which you can fill with whatever you like.

For convenience, defaults can be set in the openssl configuration file.

The default digest is ``SHA-1``. ``SHA-1`` is considered insecure.
Pass the ``-sha256`` option to use a more secure digest.

.. code-block:: bash

    $ openssl req -new -x509 -days 365 -key ca.key.pem \
        -sha256 -extensions v3_ca -out ca.cert.pem -config ~/my_crt/openssl.cnf

    Enter pass phrase for ca.key.pem:
    You are about to be asked to enter information that will be incorporated
    into your certificate request.
    -----
    Country Name (2 letter code) [XX]:GB
    State or Province Name (full name) []:London
    Locality Name (eg, city) [Default City]:London
    Organization Name (eg, company) [Default Company Ltd]:Alice CA
    Organizational Unit Name (eg, section) []:Certificate Authority
    Common Name (eg, your name or your server's hostname) []:Alice CA
    Email Address []:alice@example.com

Armed with your root key (ca.key.pem) and root certificate (ca.cert.pem),
you are now ready to optionally create an intermediate certificate authority.


How to generate a certificate signing request (CSR)
---------------------------------------------------

https://gist.github.com/mtigas/952344

.. code-block:: bash

    $ openssl genrsa -aes256 -out client.key 4096 -config ~/my_crt/openssl.cnf
    $ openssl req -new -key client.key -out client.csr

    # self-signed
    $ openssl x509 -req -days 365 -in client.csr -CA ca.cert.pem -CAkey ca.key.pem -set_serial 01 -out client.crt


Convert Client Key to PKCS
--------------------------

.. code-block:: bash

    $ openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12


Mutual Authentication
---------------------

With ``des3``:

.. code-block:: bash

    $ openssl genrsa -des3 -out ca.key 4096
    $ openssl req -new -x509 -days 365 -key ca.key -out ca.crt
    $ openssl genrsa -des3 -out server.key 1024
    $ openssl req -new -key server.key -out server.csr
    $ openssl x509 -req -days 365 -in server.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out server.crt
    $ openssl genrsa -des3 -out client.key 1024
    $ openssl req -new -key client.key -out client.csr
    $ openssl x509 -req -days 365 -in client.csr -CA ca.crt -CAkey ca.key -set_serial 02 -out client.crt
    $ openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12

With ``aes256`` and some more options:

.. code-block:: bash

    $ openssl genrsa -aes256 -out ca.key 4096 -config openssl.cnf
    $ openssl req -new -x509 -days 365 -key ca.key -sha256 -extensions v3_ca -out ca.crt -config openssl.cnf
    $ openssl genrsa -aes256 -out server.key 4096 -config openssl.cnf
    $ openssl req -new -key server.key -sha256 -extensions v3_ca -out server.csr  -config openssl.cnf
    $ openssl x509 -req -days 365 -in server.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out server.crt  -sha256 -extfile server_extKey.cnf
    $ openssl genrsa -aes256 -out client.key 4096 -config openssl.cnf
    $ openssl req -new -key client.key -sha256 -extensions v3_ca -out client.csr  -config openssl.cnf
    $ openssl x509 -req -days 365 -in client.csr -CA ca.crt -CAkey ca.key -set_serial 02 -out client.crt -sha256 -extfile client_extKey.cnf
    $ openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12

.. code-block:: bash

    # client_extKey.cnf
    extendedKeyUsage = critical, clientAuth
    keyUsage=critical, digitalSignature, keyEncipherment

.. code-block:: bash

    # server_extKey.cnf
    keyUsage=critical, digitalSignature, keyEncipherment

.. code-block:: bash

    # Verify Server Certificate
    $ openssl verify -purpose sslserver -CAfile ca.crt server.crt
    # Verify Client Certificate
    $ openssl verify -purpose sslclient -CAfile ca.crt client.crt
    $ curl -v -s -k --key client.key --cert client.crt https://termestudio.com/

.. code-block:: bash

        server {
            server_name localhost;
	        root html;
	        index index.html index.htm;
            listen 443;
            ssl on;
            ssl_certificate /etc/nginx/certs/server.crt;
            ssl_certificate_key /etc/nginx/certs/server.key;
            ssl_client_certificate /etc/nginx/certs/ca.crt;
            ssl_verify_client on;
            ssl_verify_depth 2;
            ssl_session_timeout 5m;
            ssl_protocols  SSLv2 SSLv3 TLSv1;
            ssl_ciphers  ALL:!ADH:!EXPORT56:RC4+RSA:+HIGH:+MEDIUM:+LOW:+SSLv2:+EXP;
            ssl_prefer_server_ciphers on;
        }

.. code-block:: bash

    # openssl.cnf
    #
    # OpenSSL example configuration file.
    # This is mostly being used for generation of certificate requests.
    #

    # This definition stops the following lines choking if HOME isn't
    # defined.
    HOME			= .
    RANDFILE		= $ENV::HOME/.rnd

    # Extra OBJECT IDENTIFIER info:
    #oid_file		= $ENV::HOME/.oid
    oid_section		= new_oids

    # To use this configuration file with the "-extfile" option of the
    # "openssl x509" utility, name here the section containing the
    # X.509v3 extensions to use:
    # extensions		=
    # (Alternatively, use a configuration file that has only
    # X.509v3 extensions in its main [= default] section.)

    [ new_oids ]

    # We can add new OIDs in here for use by 'ca', 'req' and 'ts'.
    # Add a simple OID like this:
    # testoid1=1.2.3.4
    # Or use config file substitution like this:
    # testoid2=${testoid1}.5.6

    # Policies used by the TSA examples.
    tsa_policy1 = 1.2.3.4.1
    tsa_policy2 = 1.2.3.4.5.6
    tsa_policy3 = 1.2.3.4.5.7

    ####################################################################
    [ ca ]
    default_ca	= CA_default		# The default ca section

    ####################################################################
    [ CA_default ]

    dir		= /home/or/workspace/prj/me/TS/crt		# Where everything is kept
    certs		= $dir/certs		# Where the issued certs are kept
    crl_dir		= $dir/crl		# Where the issued crl are kept
    database	= $dir/index.txt	# database index file.
    #unique_subject	= no			# Set to 'no' to allow creation of
                        # several ctificates with same subject.
    new_certs_dir	= $dir/newcerts		# default place for new certs.

    certificate	= $dir/cacert.pem 	# The CA certificate
    serial		= $dir/serial 		# The current serial number
    crlnumber	= $dir/crlnumber	# the current crl number
                        # must be commented out to leave a V1 CRL
    crl		= $dir/crl.pem 		# The current CRL
    private_key	= $dir/private/cakey.pem# The private key
    RANDFILE	= $dir/private/.rand	# private random number file

    x509_extensions	= usr_cert		# The extentions to add to the cert

    # Comment out the following two lines for the "traditional"
    # (and highly broken) format.
    name_opt 	= ca_default		# Subject Name options
    cert_opt 	= ca_default		# Certificate field options

    # Extension copying option: use with caution.
    # copy_extensions = copy

    # Extensions to add to a CRL. Note: Netscape communicator chokes on V2 CRLs
    # so this is commented out by default to leave a V1 CRL.
    # crlnumber must also be commented out to leave a V1 CRL.
    # crl_extensions	= crl_ext

    default_days	= 365			# how long to certify for
    default_crl_days= 30			# how long before next CRL
    default_md	= default		# use public key default MD
    preserve	= no			# keep passed DN ordering

    # A few difference way of specifying how similar the request should look
    # For type CA, the listed attributes must be the same, and the optional
    # and supplied fields are just that :-)
    policy		= policy_match

    # For the CA policy
    [ policy_match ]
    countryName		= match
    stateOrProvinceName	= match
    organizationName	= match
    organizationalUnitName	= optional
    commonName		= supplied
    emailAddress		= optional

    # For the 'anything' policy
    # At this point in time, you must list all acceptable 'object'
    # types.
    [ policy_anything ]
    countryName		= optional
    stateOrProvinceName	= optional
    localityName		= optional
    organizationName	= optional
    organizationalUnitName	= optional
    commonName		= supplied
    emailAddress		= optional

    ####################################################################
    [ req ]
    default_bits		= 2048
    default_keyfile 	= privkey.pem
    distinguished_name	= req_distinguished_name
    attributes		= req_attributes
    x509_extensions	= v3_ca	# The extentions to add to the self signed cert

    # Passwords for private keys if not present they will be prompted for
    # input_password = secret
    # output_password = secret

    # This sets a mask for permitted string types. There are several options.
    # default: PrintableString, T61String, BMPString.
    # pkix	 : PrintableString, BMPString (PKIX recommendation before 2004)
    # utf8only: only UTF8Strings (PKIX recommendation after 2004).
    # nombstr : PrintableString, T61String (no BMPStrings or UTF8Strings).
    # MASK:XXXX a literal mask value.
    # WARNING: ancient versions of Netscape crash on BMPStrings or UTF8Strings.
    string_mask = utf8only

    # req_extensions = v3_req # The extensions to add to a certificate request

    [ req_distinguished_name ]
    countryName			= Country Name (2 letter code)
    countryName_default		= AU
    countryName_min			= 2
    countryName_max			= 2

    stateOrProvinceName		= State or Province Name (full name)
    stateOrProvinceName_default	= Some-State

    localityName			= Locality Name (eg, city)

    0.organizationName		= Organization Name (eg, company)
    0.organizationName_default	= Internet Widgits Pty Ltd

    # we can do this but it is not needed normally :-)
    #1.organizationName		= Second Organization Name (eg, company)
    #1.organizationName_default	= World Wide Web Pty Ltd

    organizationalUnitName		= Organizational Unit Name (eg, section)
    #organizationalUnitName_default	=

    commonName			= Common Name (e.g. server FQDN or YOUR name)
    commonName_max			= 64

    emailAddress			= Email Address
    emailAddress_max		= 64

    # SET-ex3			= SET extension number 3

    [ req_attributes ]
    challengePassword		= A challenge password
    challengePassword_min		= 4
    challengePassword_max		= 20

    unstructuredName		= An optional company name

    [ usr_cert ]

    # These extensions are added when 'ca' signs a request.

    # This goes against PKIX guidelines but some CAs do it and some software
    # requires this to avoid interpreting an end user certificate as a CA.

    basicConstraints=CA:FALSE

    # Here are some examples of the usage of nsCertType. If it is omitted
    # the certificate can be used for anything *except* object signing.

    # This is OK for an SSL server.
    # nsCertType			= server

    # For an object signing certificate this would be used.
    # nsCertType = objsign

    # For normal client use this is typical
    # nsCertType = client, email

    # and for everything including object signing:
    # nsCertType = client, email, objsign

    # This is typical in keyUsage for a client certificate.
    keyUsage = nonRepudiation, digitalSignature, keyEncipherment

    # This will be displayed in Netscape's comment listbox.
    nsComment			= "OpenSSL Generated Certificate"

    # PKIX recommendations harmless if included in all certificates.
    subjectKeyIdentifier=hash
    authorityKeyIdentifier=keyid,issuer

    # This stuff is for subjectAltName and issuerAltname.
    # Import the email address.
    # subjectAltName=email:copy
    # An alternative to produce certificates that aren't
    # deprecated according to PKIX.
    # subjectAltName=email:move

    # Copy subject details
    # issuerAltName=issuer:copy

    #nsCaRevocationUrl		= http://www.domain.dom/ca-crl.pem
    #nsBaseUrl
    #nsRevocationUrl
    #nsRenewalUrl
    #nsCaPolicyUrl
    #nsSslServerName

    # This is required for TSA certificates.
    extendedKeyUsage = critical,timeStamping



    [ v3_req ]

    # Extensions to add to a certificate request

    basicConstraints = CA:FALSE
    keyUsage = nonRepudiation, digitalSignature, keyEncipherment

    [ v3_ca ]


    # Extensions for a typical CA


    # PKIX recommendation.

    subjectKeyIdentifier=hash

    authorityKeyIdentifier=keyid:always,issuer

    # This is what PKIX recommends but some broken software chokes on critical
    # extensions.
    basicConstraints = critical,CA:true
    # So we do this instead.
    #basicConstraints = CA:true

    # Key usage: this is typical for a CA certificate. However since it will
    # prevent it being used as an test self-signed certificate it is best
    # left out by default.
    keyUsage = cRLSign, keyCertSign

    # Some might want this also
    # nsCertType = sslCA, emailCA

    # Include email address in subject alt name: another PKIX recommendation
    # subjectAltName=email:copy
    # Copy issuer details
    # issuerAltName=issuer:copy

    # DER hex encoding of an extension: beware experts only!
    # obj=DER:02:03
    # Where 'obj' is a standard or added object
    # You can even override a supported extension:
    # basicConstraints= critical, DER:30:03:01:01:FF
    #extendedKeyUsage = critical, clientAuth

    [ crl_ext ]

    # CRL extensions.
    # Only issuerAltName and authorityKeyIdentifier make any sense in a CRL.

    # issuerAltName=issuer:copy
    authorityKeyIdentifier=keyid:always

    [ proxy_cert_ext ]
    # These extensions should be added when creating a proxy certificate

    # This goes against PKIX guidelines but some CAs do it and some software
    # requires this to avoid interpreting an end user certificate as a CA.

    basicConstraints=CA:FALSE

    # Here are some examples of the usage of nsCertType. If it is omitted
    # the certificate can be used for anything *except* object signing.

    # This is OK for an SSL server.
    # nsCertType			= server

    # For an object signing certificate this would be used.
    # nsCertType = objsign

    # For normal client use this is typical
    # nsCertType = client, email

    # and for everything including object signing:
    # nsCertType = client, email, objsign

    # This is typical in keyUsage for a client certificate.
    # keyUsage = nonRepudiation, digitalSignature, keyEncipherment

    # This will be displayed in Netscape's comment listbox.
    nsComment			= "OpenSSL Generated Certificate"

    # PKIX recommendations harmless if included in all certificates.
    subjectKeyIdentifier=hash
    authorityKeyIdentifier=keyid,issuer

    # This stuff is for subjectAltName and issuerAltname.
    # Import the email address.
    # subjectAltName=email:copy
    # An alternative to produce certificates that aren't
    # deprecated according to PKIX.
    # subjectAltName=email:move

    # Copy subject details
    # issuerAltName=issuer:copy

    #nsCaRevocationUrl		= http://www.domain.dom/ca-crl.pem
    #nsBaseUrl
    #nsRevocationUrl
    #nsRenewalUrl
    #nsCaPolicyUrl
    #nsSslServerName

    # This really needs to be in place for it to be a proxy certificate.
    proxyCertInfo=critical,language:id-ppl-anyLanguage,pathlen:3,policy:foo

    ####################################################################
    [ tsa ]

    default_tsa = tsa_config1	# the default TSA section

    [ tsa_config1 ]

    # These are used by the TSA reply generation only.
    dir		= ./demoCA		# TSA root directory
    serial		= $dir/tsaserial	# The current serial number (mandatory)
    crypto_device	= builtin		# OpenSSL engine to use for signing
    signer_cert	= $dir/tsacert.pem 	# The TSA signing certificate
                        # (optional)
    certs		= $dir/cacert.pem	# Certificate chain to include in reply
                        # (optional)
    signer_key	= $dir/private/tsakey.pem # The TSA private key (optional)

    default_policy	= tsa_policy1		# Policy if request did not specify it
                        # (optional)
    other_policies	= tsa_policy2, tsa_policy3	# acceptable policies (optional)
    digests		= md5, sha1		# Acceptable message digests (mandatory)
    accuracy	= secs:1, millisecs:500, microsecs:100	# (optional)
    clock_precision_digits  = 0	# number of digits after dot. (optional)
    ordering		= yes	# Is ordering defined for timestamps?
                    # (optional, default: no)
    tsa_name		= yes	# Must the TSA name be included in the reply?
                    # (optional, default: no)
    ess_cert_id_chain	= no	# Must the ESS cert id chain be included?
                    # (optional, default: no)



Note about common name:

Common Name (e.g. server FQDN or YOUR name) []:example.local

The Common Name option is the most important, as your domain used with the certificate needs to match it.

If you use the "www" subdomain, this means specifying the "www" subdomain as well!

Note about serial number:

This error ``(Error code: sec_error_reused_issuer_and_serial)`` occurred in firefox page with this description:

Your certificate contains the same serial number as another certificate issued by the certificate authority.

Please get a new certificate containing a unique serial number.

When serial number for client and CA is the same.


Resources:

https://www.openssl.org/docs/apps/x509v3_config.html

https://tech.mendix.com/linux/2014/10/29/nginx-certs-sni/

https://serversforhackers.com/ssl-certs/

http://stackoverflow.com/questions/1402699/bad-openssl-certificate

http://stackoverflow.com/questions/19726138/openssl-error-18-at-0-depth-lookupself-signed-certificate

https://github.com/nategood/sleep-tight/blob/master/scripts/create-certs.sh

http://stackoverflow.com/questions/20767548/nginx-subdomain-ssl-redirect-redirects-top-level-domain


Self Sign Authentication
------------------------


.. code-block:: bash

    $ openssl genrsa -out ca.key 4096
    $ openssl req -new -x509 -days 365 -key ca.key -out ca.crt
    $ openssl genrsa -out client.key 4096
    $ openssl req -new -key client.key -out client.csr
    $ openssl x509 -req -days 365 -in client.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out client.crt
    $ openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12

.. code-block:: bash

    server {

        server_name localhost;
        root html;
        index index.html index.htm;

        listen 443;
        ssl on;
        ssl_certificate /etc/nginx/certs/ca.crt;
        ssl_certificate_key /etc/nginx/certs/ca.key;
        ssl_client_certificate /etc/nginx/certs/client.crt;
        ssl_verify_client on;

        ssl_session_timeout 5m;
        ssl_protocols SSLv3 TLSv1 TLSv1.1 TLSv1.2;
        ssl_ciphers "HIGH:!aNULL:!MD5 or HIGH:!aNULL:!MD5:!3DES";
        ssl_prefer_server_ciphers on;

        location / {
            try_files $uri $uri/ =404;
        }
     }




Resources:

https://gist.github.com/eliangcs/6316574

https://gist.github.com/twined/cfdaa968223c9e293b59


Can I build my own Extended Validation (EV) SSL certificate?
------------------------------------------------------------

http://serverfault.com/questions/48053/can-i-build-my-own-extended-validation-ssl-certificate

http://stackoverflow.com/questions/10950014/green-bar-for-self-made-ssl

https://www.sslshopper.com/cheapest-ev-ssl-certificates.html

http://en.wikipedia.org/wiki/Extended_Validation_Certificate

http://stackoverflow.com/questions/8455113/firefox-ssl-you-are-connected-to-whatever-com-which-is-run-by-unknown


Using shadowsocks
-----------------

https://github.com/shadowsocks/shadowsocks

https://xuri.me/2014/08/14/shadowsocks-setup-guide.html

JSON Web Token
--------------

JSON Web Token is a fairly new standard which can be used for token-based authentication.
Unlike the built-in TokenAuthentication scheme,
JWT Authentication doesn't need to use a database to validate a token.

http://www.django-rest-framework.org/api-guide/authentication/#json-web-token-authentication

JWT (Json web token) Vs Custom Token

http://stackoverflow.com/a/31737111

http://getblimp.github.io/django-rest-framework-jwt/

https://jwt.io/introduction/
