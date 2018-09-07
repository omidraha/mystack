Introduction
============

Let’s think about Rabbit as a delivery service. Your app can send and receive packages. 
The server with the data you need can send and receive too. The role RabbitMQ plays is as the router between your app and the “server” it's talking to. 
So when your app connects to RabbitMQ, it has a decision to make: am I sending or receiving? Or in AMQP talk, am I a producer or a consumer?

Producers create messages and publish (send) them to a broker server (RabbitMQ).

What’s a message? A message has two parts: a payload and a label. The payload is the data you want to transmit. 
It can be anything from a JSON array to an MPEG-4 of your favorite iguana Ziggy. RabbitMQ doesn’t care. 
The label is more interesting. It describes the payload, and is how RabbitMQ will determine who should get a copy of your message. 
Unlike, for example, TCP, where you specify a specific sender and a specific receiver, 
AMQP only describes the message with a label (an exchange name and optionally a topic tag) and leaves it to Rabbit to send it to interested receivers based on that label. 

The communication is fire-and-forget and one-directional.
Consumers are just as simple. They attach to a broker server and subscribe to a queue. 

 
Docker
======

Run RabbitMQ

.. code-block:: bash


    $ docker run --hostname my-r1 --name r1 -p4369:4369 -p5671:5671 -p5672:5672 -p25672:25672 rabbitmq:3

Add RabbitMQ virtual host:

.. code-block:: bash

    $ docker exec -it <CONTAINER_NAME> /bin/bash
    root@my-r1:/# rabbitmqctl add_vhost new_vhost
    root@my-r1:/# rabbitmqctl set_user_tags guest my_new_tag
    root@my-r1:/# rabbitmqctl set_permissions -p new_vhost guest ".*" ".*" ".*"

http://docs.celeryproject.org/en/latest/getting-started/brokers/rabbitmq.html#setting-up-rabbitmq

Pika
====

Exchange
---------

A default exchange, identify by the empty string ("") will be used.
The default exchange means that messages are routed to the queue with the name specified by routing_key,
if it exists. (The default exchange is a direct exchange with no name).

.. code-block:: python

    import pika
    # Open a connection to RabbitMQ on localhost using all default parameters
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='127.0.0.1', virtual_host='sample_vhost', heartbeat_interval=0))
    # Open the channel
    channel = connection.channel()
    channel.exchange_declare(exchange='exh_01', durable=True, type='fanout')


Publish
-------

.. code-block:: python

    channel.basic_publish(exchange='exh_01', routing_key='', body="message_01")
    channel.basic_publish(exchange='exh_01', routing_key='', body="message_02")


Subscribe
+++++++++

Queue
-----

.. code-block:: python

    result = channel.queue_declare(queue="queue_01", durable=True, exclusive=False, auto_delete=False)

Bindings
--------

We've already created a fanout exchange and a queue.
Now we need to tell the exchange to send messages to our queue.
That relationship between exchange and a queue is called a binding.


.. code-block:: python

    channel.queue_bind(exchange="exh_01", queue=result.method.queue)



Subscribe
+++++++++

.. code-block:: python

    def callback(ch, method, properties, body):
        print(" [x] %r" % body)

    queue_name = result.method.queue
    channel.basic_consume(callback, queue=queue_name, no_ack=True)
    channel.start_consuming()


http://stackoverflow.com/questions/10620976/rabbitmq-amqp-single-queue-multiple-consumers-for-same-message


Delete Queue
------------

.. code-block:: bash

    $ rabbitmqctl list_queues

.. code-block:: python

    import pika
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost', virtual_host='sample_vhost'))
    channel = connection.channel()
    channel.queue_delete(queue='hello')
    connection.close()


https://pika.readthedocs.io/en/latest/modules/channel.html#pika.channel.Channel.queue_delete

Delete Exchange
---------------

.. code-block:: bash

    $ rabbitmqctl list_exchanges

.. code-block:: python

    import pika
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost', virtual_host='sample_vhost'))
    channel = connection.channel()
    channel.exchange_delete(exchange='hello')
    connection.close()

http://pika.readthedocs.io/en/latest/modules/channel.html#pika.channel.Channel.exchange_delete
