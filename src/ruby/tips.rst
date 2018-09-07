Tips
====

Ruby environment
----------------

How to Use `rbenv` to Manage Multiple Versions of `Ruby`

.. code-block:: bash

    $ aptitude install  rbenv
    $ aptitude install  ruby-build
    $ echo 'export PATH="$HOME/.rbenv/bin:$PATH"' >> ~/.bash_profile
    $ echo 'eval "$(rbenv init -)"' >> ~/.bash_profile
    $ exec $SHELL -l
    $ rbenv install 1.9.3-p545
    $ rbenv rehash
    $ mkdir ruby_1.9.3-p545
    $ cd ruby_1.9.3-p545
    $ rbenv local 1.9.3-p545
    $ ruby -v
    $ gem install bundler

