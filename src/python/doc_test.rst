Doctest
=======

`http://docs.python.org/2/library/doctest.html`

Directives
----------

`http://docs.python.org/2/library/doctest.html#option-flags`

`http://docs.python.org/2/library/doctest.html#directives`


# doctest: +ELLIPSIS

.. code-block:: python

    Next up, we are exploring the ellipsis.
    >>> import sys
    >>> sys.modules # doctest: +ELLIPSIS
    {...'sys': <module 'sys' (built-in)>...}
    >>> 'This is an expression that evaluates to a string'
    ... # doctest: +ELLIPSIS
    'This is ... a string'
    >>> 'This is also a string' # doctest: +ELLIPSIS
    'This is ... a string'
    >>> import datetime
    >>> datetime.datetime.now().isoformat() # doctest: +ELLIPSIS
    '...-...-...T...:...:...'

doctest: +NORMALIZE_WHITESPACE

.. code-block:: python

    Next, a demonstration of whitespace normalization.
    >>> [1, 2, 3, 4, 5, 6, 7, 8, 9]
    ... # doctest: +NORMALIZE_WHITESPACE
    [1,
    2, 3,
    4,
    5, 6,
    7,
    8, 9]
    >>> import sys
    >>> sys.stdout.write("This text\n contains weird    spacing.")
    ... # doctest: +NORMALIZE_WHITESPACE
    This text contains weird spacing.


doctest: +SKIP

.. code-block:: python

    Now we are telling doctest to skip a test
    >>> 'This test would fail.' # doctest: +SKIP
    If it were allowed to run.


This "test a little, code a little" style of programming is called Test-Driven Development,

and you'll find that it's very productive.
