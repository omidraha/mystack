Install
=======


Install aircrack-ng 1.2-beta1 from source
-----------------------------------------

``http://www.aircrack-ng.org/doku.php?id=install_aircrack#installing_aircrack-ng_from_source``

``https://github.com/aircrack-ng/aircrack-ng``


.. code-block:: bash

    apt-get install build-essential
    wget http://download.aircrack-ng.org/aircrack-ng-1.2-beta1.tar.gz
    tar -zxvf aircrack-ng-1.2-beta1.tar.gz
    cd aircrack-ng-1.2-beta1
    make sqlite=true unstable=true
    make sqlite=true unstable=true install
    airodump-ng-oui-update  # to install or update Airodump-ng OUI file
