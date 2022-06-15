Tips
====

Install Gunicorn
----------------

.. code-block:: bash

    $ pip install gunicorn
    # or
    $ sudo apt-get install -y gunicorn


Gunicorn config settings
------------------------


http://docs.gunicorn.org/en/stable/settings.html


How Many Workers?
-----------------

DO NOT scale the number of workers to the number of clients you expect to have.

Gunicorn should only need 4-12 worker processes to handle hundreds or thousands of requests per second.

Gunicorn relies on the operating system to provide all of the load balancing when handling requests.

Generally we recommend ``(2 x $num_cores) + 1`` as the number of workers to start off with. While not overly scientific,

the formula is based on the assumption that for a given core,

one worker will be reading or writing from the socket while the other worker is processing a request.

Obviously, your particular hardware and application are going to affect the optimal number of workers.

Our recommendation is to start with the above guess and tune using TTIN and TTOU signals

while the application is under load.

Always remember, there is such a thing as too many workers.

After a point your worker processes will start thrashing system resources decreasing the throughput of

the entire system.

http://docs.gunicorn.org/en/latest/design.html#how-many-workers

Choosing a Worker Type
----------------------
The default synchronous workers assume that your application is resource bound in terms of CPU and network bandwidth.

Generally this means that your application shouldn’t do anything that takes an undefined amount of time.

For instance, a request to the internet meets this criteria.

At some point the external network will fail in such a way that clients will pile up on your servers.

This resource bound assumption is why we require a buffering proxy in front of a default configuration Gunicorn.

If you exposed synchronous workers to the internet,

a DOS attack would be trivial by creating a load that trickles data to the servers.

For the curious, Boom is an example of this type of load.

Some examples of behavior requiring asynchronous workers:

* Applications making long blocking calls (Ie, external web services)
* Serving requests directly to the internet
* Streaming requests and responses
* Long polling
* Web sockets
* Comet

http://docs.gunicorn.org/en/latest/design.html#choosing-a-worker-type

Worker Processes
----------------

http://docs.gunicorn.org/en/develop/configure.html#worker-processes

Running Django with Gunicorn - Best Practice
--------------------------------------------

http://stackoverflow.com/questions/16857955/running-django-with-gunicorn-best-practice

https://docs.djangoproject.com/en/dev/howto/deployment/wsgi/gunicorn/#running-django-in-gunicorn-as-a-generic-wsgi-application

http://docs.gunicorn.org/en/0.17.2/deploy.html

http://docs.gunicorn.org/en/0.17.2/run.html


Can't get access log to work for gunicorn
-----------------------------------------

http://docs.gunicorn.org/en/stable/settings.html#logging

http://stackoverflow.com/questions/13472842/cant-get-access-log-to-work-for-gunicorn


.. code-block:: bash

    $ gunicorn web_app.wsgi:application --bind 192.168.1.119:8001 --workers 3 --access-logfile -


Serving a gunicorn app with PyInstaller
---------------------------------------

.. code-block:: bash

    from gunicorn.app.base import Application, Config
    import gunicorn
    from gunicorn import glogging
    from gunicorn.workers import sync

    class GUnicornFlaskApplication(Application):
        def __init__(self, app):
            self.usage, self.callable, self.prog, self.app = None, None, None, app

        def run(self, **options):
            self.cfg = Config()
            [self.cfg.set(key, value) for key, value in options.items()]
            return Application.run(self)

        load = lambda self:self.app


    def app(environ, start_response):
        data = "Hello, World!\n"
        start_response("200 OK", [
            ("Content-Type", "text/plain"),
            ("Content-Length", str(len(data)))
        ])

        return iter(data)

    if __name__ == "__main__":
        gunicorn_app = GUnicornFlaskApplication(app)
        gunicorn_app.run(
            worker_class="gunicorn.workers.sync.SyncWorker",

https://github.com/benoitc/gunicorn/issues/669#issuecomment-31217831

Serving a pycnic app with gunicorn with PyInstaller
---------------------------------------------------


.. code-block:: bash

    import gunicorn
    from gunicorn.six import iteritems
    from pycnic.core import WSGI, Handler
    from gunicorn.app.base import Application
    from gunicorn.glogging import Logger  # needs by pyinstaller
    from gunicorn.workers import sync  # needs by pyinstaller


    class Hello(Handler):
        def get(self, name="World"):
            return {"message": "Hello, %s!" % (name)}

    class app(WSGI):
        routes = [
            ('/', Hello()),
            ('/([\w]+)', Hello())
        ]

    class StandaloneApplication(gunicorn.app.base.BaseApplication):
        def __init__(self, app, options=None):
            self.options = options or {}
            self.application = app
            super(StandaloneApplication, self).__init__()

        def load_config(self):
            config = dict([(key, value) for key, value in iteritems(self.options)
                           if key in self.cfg.settings and value is not None])
            for key, value in iteritems(config):
                self.cfg.set(key.lower(), value)

        def load(self):
            return self.application


    StandaloneApplication(app, {}).run()

http://docs.gunicorn.org/en/stable/custom.html

http://pycnic.nullism.com/docs/getting-started.html#hosting-with-gunicorn
