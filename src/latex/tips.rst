Tips
====


.. code-block:: bash


    $ sudo apt-get install texlive-xetex
    $ sudo apt-get install texlive-lang-arabic



Write Persian in Latex
----------------------



Use `bidi` package and `RTL` :


.. code-block:: latex

    \documentclass[12pt,a4paper,oneside]{report}

    \usepackage[utf8]{inputenc}
    \usepackage{fontspec}
    \setmainfont{B Nazanin}
    \usepackage{bidi}
    \RTL
    \begin{document}
        \textbf{سلام لاتک! }
       فارسی نویسی در لاتک به این صورت است!
    \end{document}


Use `xepersian` package:


.. code-block:: latex

    \documentclass[12pt,a4paper,oneside]{report}

    \usepackage[utf8]{inputenc}
    \usepackage{fontspec}
    \usepackage{xepersian}
    \settextfont{B Nazanin}
    \setlatintextfont{Lato Thin}

    \begin{document}
        \textbf{سلام لاتک! }
       فارسی نویسی در لاتک به این صورت است!
    \end{document}


Generate pdf file:

.. code-block:: bash

    $ xelatex sample.tex

