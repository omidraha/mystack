Tips
====


Send SMS with account sid and auth token
---------------------------------------

https://www.twilio.com/docs/messaging/tutorials/how-to-send-sms-messages/python

Send SMS with API Key and API Secret
-----------------------------------------


.. code-block:: python

    from twilio.rest import Client
    username = "SK***"
    password="***"
    account_sid="AC***"
    messaging_service_sid = 'MG***'
    client = Client(username=username, password=password, account_sid=account_sid)
    # By using from number
    message = client.messages.create(body='Sample sms',from_='+1234',to='+156789',)
    # By using messaging service sid
    message = client.messages.create(body='Sample sms',messaging_service_sid=messaging_service_sid, to='+156789',)


https://www.twilio.com/docs/iam/keys/api-key

Different SIDs mean
-------------------

https://www.twilio.com/blog/programmable-messaging-sids

