How to Creating a Self-Signed Certificate with a Full Certificate Chain
=======================================================================

This guide will walk you through the process of creating a self-signed certificate that includes a complete certificate chain.


Self-Sign Certificate
---------------------

To create a self-signed certificate, use the following commands:

.. code-block:: bash

    openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout example.key -out example.crt -config example.cnf -extensions req_ext
    openssl x509 -in example.crt -text -noout
    openssl x509 -in example.crt -text -noout | grep -A1 "Subject Alternative Name"

After generating the certificate, copy the key and certificate to the appropriate directories:

.. code-block:: bash

    cp example.key ../config/ingress/tls.key
    cp example.crt ../config/ingress/tls.crt

Configuration File (example.cnf)
---------------------------------

The `example.cnf` configuration file should look as follows:

.. code-block::

    [ req ]
    default_bits       = 2048
    prompt             = no
    default_md         = sha256
    req_extensions     = req_ext
    distinguished_name = dn

    [ dn ]
    commonName         = ExampleCommonName
    countryName        = ExampleCountryName
    stateOrProvinceName = ExampleStateOrProvinceName
    localityName       = ExampleLocalityName
    organizationName   = ExampleOrganizationName
    organizationalUnitName  = ExampleOrganizationalUnitName

    [ req_ext ]
    keyUsage           = critical,digitalSignature,keyEncipherment
    extendedKeyUsage   = critical,serverAuth,clientAuth
    subjectAltName     = @alt_names

    [ alt_names ]
    DNS.1 = example.com
    DNS.2 = *.example.com

Step 1: Create Configuration Files
-----------------------------------

Create the configuration files as shown below:

.. code-block:: bash

    # Create rootCA.cnf
    cat > rootCA.cnf << 'EOF'
    [ req ]
    default_bits       = 2048
    prompt             = no
    default_md         = sha256
    req_extensions     = v3_ca
    distinguished_name = dn

    [ dn ]
    commonName         = ExampleCommonName
    countryName        = ExampleCountryName
    stateOrProvinceName = ExampleStateOrProvinceName
    localityName       = ExampleLocalityName
    organizationName   = ExampleOrganizationName
    organizationalUnitName  = ExampleOrganizationalUnitName

    [ v3_ca ]
    basicConstraints = critical, CA:TRUE
    keyUsage = critical, digitalSignature, cRLSign, keyCertSign
    subjectKeyIdentifier = hash
    EOF

    # Create intermediateCA.cnf
    cat > intermediateCA.cnf << 'EOF'
    [ req ]
    default_bits       = 2048
    prompt             = no
    default_md         = sha256
    req_extensions     = v3_ca
    distinguished_name = dn

    [ dn ]
    commonName         = ExampleCommonName
    countryName        = ExampleCountryName
    stateOrProvinceName = ExampleStateOrProvinceName
    localityName       = ExampleLocalityName
    organizationName   = ExampleOrganizationName
    organizationalUnitName  = ExampleOrganizationalUnitName

    [ v3_ca ]
    basicConstraints = critical, CA:TRUE, pathlen:1
    keyUsage = critical, digitalSignature, cRLSign, keyCertSign
    subjectKeyIdentifier = hash
    EOF

    # Create intermediateCA_sign_simple.cnf
    cat > intermediateCA_sign_simple.cnf << 'EOF'
    [ v3_ca ]
    basicConstraints = critical, CA:TRUE, pathlen:1
    keyUsage = critical, digitalSignature, cRLSign, keyCertSign
    subjectKeyIdentifier = hash
    EOF

Step 2: Generate Root CA
-------------------------

Generate the Root CA:

.. code-block:: bash

    # Generate Root CA private key
    openssl genrsa -out rootCA.key 2048

    # Generate Root CA certificate
    openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 365 -out rootCA.crt -config rootCA.cnf

Step 3: Generate Intermediate CA
--------------------------------

Generate the Intermediate CA:

.. code-block:: bash

    # Generate Intermediate CA private key
    openssl genrsa -out intermediateCA.key 2048

    # Generate Intermediate CA CSR
    openssl req -new -key intermediateCA.key -out intermediateCA.csr -config intermediateCA.cnf

    # Sign Intermediate CA with Root CA
    openssl x509 -req -in intermediateCA.csr -CA rootCA.crt -CAkey rootCA.key -CAcreateserial -out intermediateCA.crt -days 365 -extensions v3_ca -extfile intermediateCA_sign_simple.cnf

Step 4: Generate Server Certificate
-----------------------------------

Generate the server certificate:

.. code-block:: bash

    # Generate server certificate CSR (assuming you have example.key and example.cnf)
    openssl req -new -key example.key -out example.csr -config example.cnf

    # Sign server certificate with Intermediate CA
    openssl x509 -req -in example.csr -CA intermediateCA.crt -CAkey intermediateCA.key -CAcreateserial -out example.crt -days 365 -extensions req_ext -extfile example.cnf

Step 5: Create Certificate Chain
---------------------------------

Combine all certificates into one chain file:

.. code-block:: bash

    cat example.crt intermediateCA.crt rootCA.crt > cert_chain.crt

Step 6: Verify the Chain
------------------------

Verify the certificate chain:

.. code-block:: bash

    # Verify intermediate CA against root CA
    openssl verify -CAfile rootCA.crt intermediateCA.crt

    # Verify server certificate against the chain
    openssl verify -CAfile rootCA.crt -untrusted intermediateCA.crt example.crt

    # Check the chain file
    openssl x509 -in cert_chain.crt -text -noout | head -20
