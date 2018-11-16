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

    $ docker run --rm -v "$PWD":/ws/omr/  lsakalauskas/sdaps /ws/omr/sdaps setup_tex /ws/omr/example.tex

    $  docker run --rm -v "$PWD":/ws/omr/  lsakalauskas/sdaps /ws/omr/sdaps add /ws/omr/example.tif

    #   Processing /ws/omr/example.tif
    #   Done

    $ docker run --rm -v "$PWD":/ws/omr/  lsakalauskas/sdaps /ws/omr/sdaps annotate

    $ docker run --rm -v "$PWD":/ws/omr/  lsakalauskas/sdaps /ws/omr/sdaps recognize

    #   3 sheets
    #   0.493762 seconds per sheet

    $ docker run --rm -v "$PWD":/ws/omr/  lsakalauskas/sdaps /ws/omr/sdaps report

    $ docker run --rm -v "$PWD":/ws/omr/  lsakalauskas/sdaps /ws/omr/sdaps csv export

https://github.com/tfitz/docker-sdaps

The `example.tex` file is here:

https://sdaps.org/Documentation/Tutorial/example.tex


The `example.tif` file is here:

https://sdaps.org/Documentation/Tutorial/example.tif

Scanning:

https://sdaps.org/Documentation/Scanning/
