Celery
======


How to disallow pickle serialization in celery?
-----------------------------------------------

Add these lines to celery config file:

.. code-block:: python

    CELERY_ACCEPT_CONTENT = ['json']
    CELERY_TASK_SERIALIZER = 'json'
    CELERY_RESULT_SERIALIZER = 'json'

If not work, try this:

.. code-block:: python

    CELERY_ACCEPT_CONTENT = ['json']
    from kombu import serialization
    serialization.registry._decoders.pop("application/x-python-serialize")


http://stackoverflow.com/questions/6628016/how-to-disallow-pickle-serialization-in-celery


Is CELERY_RESULT_BACKEND necessary?
-----------------------------------


If you want to get the result of a task back, or you want to know when
the task is completed, then you need a result backend.

.. code-block:: python

    @task
    def x():
       pass

    t = x.delay()
    t.state # always PENDING unless you have a RESULT_BACKEND and not ignore_result.
    t.result # always None unless...


http://docs.celeryproject.org/en/latest/configuration.html#std:setting-CELERY_RESULT_BACKEND

https://groups.google.com/forum/#!topic/celery-users/3OBTaaoKsTU

Using Amazon SQS
----------------

http://docs.celeryproject.org/en/latest/getting-started/brokers/sqs.html

Use celery with different code base in API and workers
------------------------------------------------------


http://stackoverflow.com/a/36977126

http://docs.celeryproject.org/en/latest/userguide/canvas.html#signatures


Chain tasks on celery
---------------------



.. code-block:: bash

    t1 = my_task_01.subtask((arg1,), immutable=True)
    t2 = my_task_02.subtask((arg1, arg2), immutable=True)
    t3 = my_task_01.subtask((arg1,), immutable=True)
    task_list = [t1, t2, t3]
    tasks = chain(task_list)
    tasks.apply_async()

The next task will run, if previous task run successfully.

The `immutable` option is set to `True`, so the result of each task won't  send to the next task.
