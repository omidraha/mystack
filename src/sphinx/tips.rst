Tips
====

How do we embed images in sphinx docs?
--------------------------------------

.. code-block:: bash

    .. image:: example.png
        :width: 480pt

The path to the image is relative to the file.

http://sphinx-doc.org/rest.html?highlight=image#images

http://openalea.gforge.inria.fr/doc/openalea/doc/_build/html/source/sphinx/rest_syntax.html#directives

Document your Django projects: reStructuredText and Sphinx
-----------------------------------------------------------

http://www.marinamele.com/2014/03/document-your-django-projects.html


Generating Code Documentation With Pycco
----------------------------------------

.. code-block:: bash

    $ pip install pycco
    $ cd to/root/of/project
    $ pycco **/*.py   -p -i

https://realpython.com/blog/python/generating-code-documentation-with-pycco/


First steps with Sphinx
-----------------------


.. code-block:: bash

    $ pip install Sphinx
    $ sphinx-quickstart


http://www.sphinx-doc.org/en/stable/tutorial.html


Build docs
-----------

.. code-block:: bash

    $ sphinx-build -b html . build