Tips
====



Optical mark recognition (OMR)
------------------------------

https://en.wikipedia.org/wiki/Optical_mark_recognition



openkm
++++++


.. code-block:: bash

    $ docker pull openkm/openkm
    $ docker run --name openkm -p8000:8080  openkm/openkm


Browse: http://127.0.0.1:8000

    username: okmAdmin

    password: admin

sads
++++

https://github.com/sdaps/sdaps

https://sdaps.org/Documentation/Tutorial/


.. code-block:: bash

    $ docker pull lsakalauskas/sdaps
    $ docker run --rm -v "$PWD/ws":/ws  lsakalauskas/sdaps /ws/sdaps setup_tex /ws/example.tex

https://github.com/tfitz/docker-sdaps

The `example.tex` file is here:

https://sdaps.org/LaTeX/#basic-example

https://sdaps.org/Documentation/Tutorial/example.tex

