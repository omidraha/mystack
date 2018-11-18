Tips
====


.. code-block:: bash


    $ sudo apt-get install texlive-xetex
    $ sudo apt-get install texlive-lang-arabic



User RTL and persian
--------------------


The `sample.tex` file:

.. code-block:: latex

    \documentclass[12pt,a4paper,oneside,english]{report}

    \usepackage[utf8]{inputenc}
    \usepackage{multicol}
    \usepackage{fontspec}
    \setmainfont{B Nazanin}
    \usepackage{bidi}
    \RTL
    \begin{document}
        \textbf{سلام لاتک! }
       فارسی نویسی در لاتک به این صورت است!
    \end{document}


Generate pdf file:

.. code-block:: bash

    $ xelatex sample.tex

