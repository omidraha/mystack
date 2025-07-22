Handling Expired Certificates in K3s: Step-by-Step Guide
=========================================================

Introduction
------------

When you encounter the following error while running `kubectl` commands:

.. code-block:: bash

    kubectl get pods -n example-ns
    error: You must be logged in to the server (the server has asked for the client to provide credentials)


This usually indicates that the client certificate for K3s has expired or is invalid.

To resolve this, you need to renew the expired certificates.

This guide provides a step-by-step process to check the certificate expiry dates, renew them, and ensure everything works correctly.

Prerequisites
-------------

- `k3s` installed and running
- Access to the K3s server
- Sudo privileges for system modifications

Step 1: Check the Current Certificate Expiry Date
-------------------------------------------------

To determine if the certificates have expired, you will first need to extract the relevant certificate data from the K3s configuration file.

1. **Extract the Certificate Authority Data**

.. code-block:: bash

   sudo cat /etc/rancher/k3s/k3s.yaml | grep 'certificate-authority-data' | awk '{print $2}' | base64 -d > /tmp/k3s-ca.pem


2. **Extract the Client Certificate**

.. code-block:: bash

   sudo cat /etc/rancher/k3s/k3s.yaml | grep 'client-certificate-data' | awk '{print $2}' | base64 -d > /tmp/k3s-client-cert.pem


3. **Extract the Client Key**

.. code-block:: bash

   sudo cat /etc/rancher/k3s/k3s.yaml | grep 'client-key-data' | awk '{print $2}' | base64 -d > /tmp/k3s-client-key.pem


4. **Check Expiry Date of the Certificates**

* To check the expiry date of the Certificate Authority:

.. code-block:: bash

     openssl x509 -in /tmp/k3s-ca.pem -noout -enddate


* To check the expiry date of the Client Certificate:

.. code-block:: bash

     openssl x509 -in /tmp/k3s-client-cert.pem -noout -enddate


* Example output:

.. code-block:: bash

     notAfter=May 19 18:28:11 2025 GMT


If any certificate has expired, proceed with the renewal process.

Step 2: Renew the Certificates
------------------------------

1. **Stop K3s Service**

   Before rotating the certificates, stop the K3s service:

.. code-block:: bash

   sudo systemctl stop k3s

2. **Rotate the Certificates**

   Use the `k3s certificate rotate` command to renew the certificates:

.. code-block:: bash

   sudo k3s certificate rotate

This command will regenerate all necessary certificates, including the client certificate, server certificate, and CA certificate.

3. **Start K3s Service**

Once the certificates have been rotated, restart the K3s service:

.. code-block:: bash

   sudo systemctl start k3s


Step 3: Verify the Renewal of Certificates
-------------------------------------------

After restarting the K3s service, check the expiry dates again to verify that the certificates have been successfully renewed:

1. **Extract the Certificate Authority Data**

.. code-block:: bash

   sudo cat /etc/rancher/k3s/k3s.yaml | grep 'certificate-authority-data' | awk '{print $2}' | base64 -d > /tmp/k3s-ca.pem

2. **Extract the Client Certificate**

.. code-block:: bash

   sudo cat /etc/rancher/k3s/k3s.yaml | grep 'client-certificate-data' | awk '{print $2}' | base64 -d > /tmp/k3s-client-cert.pem

3. **Extract the Client Key**

.. code-block:: bash

   sudo cat /etc/rancher/k3s/k3s.yaml | grep 'client-key-data' | awk '{print $2}' | base64 -d > /tmp/k3s-client-key.pem

4 **Check the CA Certificate Expiry Date**

.. code-block:: bash

   openssl x509 -in /tmp/k3s-ca.pem -noout -enddate

5. **Check the Client Certificate Expiry Date**

.. code-block:: bash

   openssl x509 -in /tmp/k3s-client-cert.pem -noout -enddate


6. **Check the Client Key Expiry Date**

.. code-block:: bash

   openssl x509 -in /tmp/k3s-client-key.pem -noout -enddate

The new certificates should show updated expiry dates that are far in the future.

Step 4: Test the Connection
---------------------------

Finally, test if you can successfully execute `kubectl` commands again:

.. code-block:: bash

    kubectl get pods -n example-ns


If everything is set up correctly, you should no longer see the "You must be logged in to the server" error,
and the command should return the expected pod information.

Conclusion
----------

By following these steps, you should have resolved the certificate expiry issue in K3s.

Rotating certificates periodically is important to ensure smooth communication between the K3s server and clients.

Always check the expiry of certificates regularly and rotate them as needed.
