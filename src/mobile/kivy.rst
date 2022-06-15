Kivy
====


Using kivy
----------

.. code-block:: bash

    $ git clone git://github.com/kivy/python-for-android

    $ cd python-for-android

    $ export ANDROIDSDK="/path/to/Android/sdk"
    $ export ANDROIDNDK="/path/to/Android/android-ndk-r10d"
    $ export ANDROIDNDKVER=r10d
    $ export ANDROIDAPI=14
    $ export PATH=$ANDROIDNDK:$ANDROIDSDK/platform-tools:$ANDROIDSDK/tools:$PATH

    $ ./distribute.sh -m "openssl pil kivy"

    $ cd dist/default

    $ ./build.py --package org.test.touchtracer --name touchtracer --version 1.0 --dir /path/to/android/demo/touchtracer debug

    $ ./build.py --package org.test.touchtracer --name touchtracer --version 1.0 --dir /path/to/android/demo/touchtracer release



Ebook
-----
    Creating Apps in Kivy

Resources
---------

http://kivy.org/docs/api-kivy.html



RTL support issues
------------------

https://groups.google.com/forum/#!msg/kivy-users/Isjopdt7HQM/pZU5KcQRLkkJ

https://github.com/kivy/kivy/issues/1570

https://github.com/kivy/kivy/issues/1619

https://github.com/kivy/kivy/pull/1739

https://github.com/kivy/kivy/pull/1614


APK big size issues
-------------------

    http://stackoverflow.com/questions/23464540/size-of-apk-when-coded-with-kivy-compared-to-the-one-in-java

    https://github.com/kivy/python-for-android/issues/202

