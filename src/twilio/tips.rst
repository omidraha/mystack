Tips
====


Send SMS with account sid and auth token
---------------------------------------

.. code-block:: python

    from twilio.rest import Client
    account_sid = "SK***"
    auth_token="***"
    client = Client(username=username, password=password)
    message = client.messages.create(body='Sample sms',from_='+1234',to='+156789',)


https://www.twilio.com/docs/messaging/tutorials/how-to-send-sms-messages/python

Send SMS with API Key and API Secret
-----------------------------------------


.. code-block:: python

    from twilio.rest import Client
    username = "SK***"
    password="***"
    # @note: The `account_sid`: Account SID, required if using api_key to authenticate
    account_sid="AC***"
    client = Client(username=username, password=password, account_sid=account_sid)
    # By using from number
    message = client.messages.create(body='Sample sms',from_='+1234',to='+156789',)
    # By using messaging service sid
    message = client.messages.create(body='Sample sms',messaging_service_sid=messaging_service_sid, to='+156789',)


https://www.twilio.com/docs/iam/keys/api-key

https://www.twilio.com/docs/iam/keys/api-key?code-sample=code-authenticate-with-api-key-and-api-secret&code-language=Python&code-sdk-version=8.x

Send SMS by using messaging service sid
-----------------------------------------

.. code-block:: python

    # By using from number
    message = client.messages.create(body='Sample sms',from_='+1234',to='+156789',)
    # By using messaging service sid
    message = client.messages.create(body='Sample sms',messaging_service_sid=messaging_service_sid, to='+156789',)

Different SIDs mean
-------------------

https://www.twilio.com/blog/programmable-messaging-sids

