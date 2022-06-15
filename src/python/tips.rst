Tips
====

String in Python 2 and 3
------------------------

Python 2’s unicode() type was renamed str() in Python 3, str() was renamed bytes(), and basestring() disappeared

In Python 3 all strings are Unicode while in Python 2 strings are bytes by default

OAUTH
-----

.. code-block:: python

    from oauth2client.client import OAuth2WebServerFlow

    GOOGLE_CLIENT_ID = '****'
    GOOGLE_CLIENT_SECRET = '****'

    # server side
    def get_flow(redirect_url=None):
        return OAuth2WebServerFlow(client_id=GOOGLE_CLIENT_ID,
                                   client_secret=GOOGLE_CLIENT_SECRET,
                                   scope='profile email',
                                   redirect_uri=redirect_url)


    # Server create the oauth link with return url
    flow = get_flow('http://localhost:8000/auth/next/')
    url = flow.step1_get_authorize_url()
    # Client side
    # Client redirect user to this url:
    # https://accounts.google.com/o/oauth2/auth?scope=profile+email&redirect_uri=....
    # Internal Google redirection: https://accounts.google.com/ServiceLogin?passive=1209600
    #       &continue=https://accounts.google.com/o/oauth2/auth?access_type....
    # Google interactive login page
    # Google finally redirect user to the return url
    #  http://localhost:8000/auth/next/?code=******
    # Server get credentials from server
    code = '******'
    credentials = flow.step2_exchange(code)
    print(credentials.__dict__)

Simple HTTP Server with Python
------------------------------

.. code-block:: bash

    $ python -m SimpleHTTPServer 8002
    # Python 3
    $ python -m http.server 8002

What exactly does the T and Z mean in timestamp?
------------------------------------------------

The T doesn't really stand for anything.
It is just the separator that the ISO 8601 combined date-time format requires.
You can read it as an abbreviation for Time.

The Z stands for the Zero timezone,
as it is offset by 0 from the Coordinated Universal Time (UTC).

Both characters are just static letters in the format,
which is why they are not documented by the datetime.strftime() method.
You could have used Q or M or Monty Python and the method would have returned them unchanged as well;
the method only looks for patterns starting with % to replace those with information from the datetime object.


http://stackoverflow.com/a/29282022
