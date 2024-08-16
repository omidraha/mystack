Managing and Switching Between Multiple AWS Profiles on Ubuntu
==============================================================

Introduction
------------

When working with AWS CLI, your access keys and configuration settings are stored locally in specific files within your home directory. Understanding where these keys are stored and how to manage multiple profiles is crucial for securely handling multiple AWS accounts.

Storage Location of AWS Keys and Settings
-----------------------------------------

AWS CLI stores your access keys and configuration settings in two main files located in the ``~/.aws`` directory:

- **``~/.aws/credentials``**: This file contains your AWS Access Key ID and Secret Access Key for different profiles.
- **``~/.aws/config``**: This file holds configuration settings like the default region and output format for each profile.

These files allow AWS CLI to authenticate your requests and configure how those requests are handled. It is essential to secure these files, as they contain sensitive information that grants access to your AWS resources.

Configuring Multiple AWS Profiles
---------------------------------

To set up multiple AWS profiles, you can use the ``aws configure`` command with the ``--profile`` option:

.. code-block:: bash

    aws configure --profile profile_name

Replace ``profile_name`` with a name of your choice. This command prompts you to enter your AWS Access Key ID, Secret Access Key, region, and output format specific to the profile.

Alternatively, you can manually edit the ``~/.aws/credentials`` and ``~/.aws/config`` files to manage multiple profiles:

**``~/.aws/credentials``**:

.. code-block:: ini

    [default]
    aws_access_key_id = YOUR_DEFAULT_ACCESS_KEY
    aws_secret_access_key = YOUR_DEFAULT_SECRET_KEY

    [profile_name]
    aws_access_key_id = YOUR_PROFILE_ACCESS_KEY
    aws_secret_access_key = YOUR_PROFILE_SECRET_KEY

**``~/.aws/config``**:

.. code-block:: ini

    [default]
    region = us-west-2
    output = json

    [profile profile_name]
    region = us-east-1
    output = json

Switching Between Profiles
--------------------------

To switch between AWS profiles without specifying the profile in every command, you can use the ``AWS_PROFILE`` environment variable. This sets a default profile for all AWS CLI commands in your session:

For Bash or Zsh:

.. code-block:: bash

    export AWS_PROFILE=profile_name

For Fish:

.. code-block:: fish

    set -x AWS_PROFILE profile_name

To return to the default profile or disable the profile setting, use:

.. code-block:: bash

    unset AWS_PROFILE

Verifying Profile Usage
-----------------------

To ensure that your AWS CLI commands are using the correct profile, you can perform the following checks:

1. **Check the Active Profile**:

   .. code-block:: bash

       echo $AWS_PROFILE

   This command displays the currently active profile. If it's empty, the default profile is in use.

2. **Run a Simple Command**:

   Execute a simple command like listing your S3 buckets to verify access:

   .. code-block:: bash

       aws s3 ls

3. **Use ``sts get-caller-identity``**:

   This command shows which AWS account and identity are being used:

   .. code-block:: bash

       aws sts get-caller-identity

   The output will include ``Account``, ``UserId``, and ``Arn``, confirming the profile in use.

4. **Inspect Credentials and Config Files**:

   Manually check the ``~/.aws/credentials`` and ``~/.aws/config`` files to ensure the correct profiles are set up.

Conclusion
----------

By following these steps, you can easily switch between multiple AWS accounts and ensure that your commands are executed using the intended profile, making your workflow more efficient and secure.
