PyInstaller
-----------

https://github.com/pyinstaller/pyinstaller




Package Windows binaries while running under Linux
--------------------------------------------------


Sample (`sample.py`) python code, It's rest application that run with `waitress` wsgi server

.. code-block:: bash

    $ cat ~/ws/wine/sample.py

.. code-block:: python

        from pycnic.core import WSGI, Handler
        from waitress import serve


        class Hello(Handler):
            def get(self, name="World"):
                return {"message": "Hello, %s!" % (name)}


        class app(WSGI):
            routes = [
                ('/', Hello()),
                ('/([\w]+)', Hello())
            ]


        serve(app, host='0.0.0.0', port=9999)


Install Wine
++++++++++++

.. code-block:: bash

    $ sudo apt-get install wine
    $ winecfg


Install Python
++++++++++++++

.. code-block:: bash

    # note: Download 32bit of python from python.org
    $ wine msiexec -i ~/ws/tools/windows/python/python-2.7.13.msi  ALLUSERS=1
    # @note: Install VCForPython27 if we want complie some python package from source code
    $ wine msiexec /i ~/ws/tools/windows/python/VCForPython27.msi ALLUSERS=1
    # Install python dependency of sample program by using pip
    $ cd ~/ws/wine/
    $ wine ~/.wine/drive_c/Python27/python.exe ~/.wine/drive_c/Python27/Scripts/pip.exe install waitress
    $ wine ~/.wine/drive_c/Python27/python.exe ~/.wine/drive_c/Python27/Scripts/pip.exe install pycnic
    # Install pyinstaller
    $ wine ~/.wine/drive_c/Python27/python.exe ~/.wine/drive_c/Python27/Scripts/pip.exe install pyinstaller
    $ cp ~/ws/wine/sample.py  ~/.wine/drive_c/users/$USER/Desktop/sample.py
    $ wine ~/.wine/drive_c/Python27/Scripts/pyinstaller.exe --onefile  ~/.wine/drive_c/users/$USER/Desktop/sample.py
    $ ls dist/
    sample.exe


https://github.com/pyinstaller/pyinstaller/wiki/FAQ

https://github.com/paulfurley/python-windows-packager

https://www.paulfurley.com/packaging-python-for-windows-pyinstaller-wine/

https://stackoverflow.com/a/35605479

https://milkator.wordpress.com/2014/07/19/windows-executable-from-python-developing-in-ubuntu/

https://pythonhosted.org/PyInstaller/installation.html#installing-in-windows