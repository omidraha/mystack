Android
=======

Get the Android SDK
-------------------

http://developer.android.com/sdk/index.html

Android NDK
-----------

http://developer.android.com/tools/sdk/ndk/index.html


API Level
---------

`API Level <http://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels>`_



Using adb command
-----------------

.. code-block:: bash

    root@debian:/path/to/android_sdk//adt-bundle-linux-x86-20131030/sdk/platform-tools# ./adb push /path/of/file/in/pc/file.zip  /path/of/file/in/mobile/new_file.zip
    root@debian:/path/to/android_sdk//adt-bundle-linux-x86-20131030/sdk/platform-tools# ./adb shell


Android Virtual Devices (AVDs)

http://www.techotopia.com/index.php/Android_4_App_Development_Essentials


To change the screen orientation on the emulator, use Crtl-F11.

Android introduced fragments in Android 3.0 (API level 11)

http://developer.android.com/training/multiscreen/screensizes.html

http://developer.android.com/guide/components/fragments.html

http://developer.android.com/tools/support-library/features.html#v7-appcompat

http://developer.android.com/training/basics/actionbar/index.html

http://stackoverflow.com/questions/20720667/where-is-the-option-to-add-a-new-preference-screen-in-android-studio-0-4-0


Using Android in Eclipse
------------------------

https://dl-ssl.google.com/android/eclipse


Click Help. Select Install New Software. This will open the Available Software screen,
with a list of your available software from the repository you have selected.

Click the “Add” button. This is located to the right of the “Work with” field.

Clicking this button will open the “Add Repository” dialog box.

Here you will enter the information to download the ADT plugin.

    In the “Name” field, enter “ADT Plugin”
    In the “Location” field, enter “https://dl-ssl.google.com/android/eclipse/”
    Click OK.

    Check the “Developer Tools” box. Click Next to display the list of tools that will be downloaded.

    Click Next again to open the license agreements. Read them and then click Finish.

    You may get a warning that the validity of the software cannot be established. It is OK to ignore this warning.


Force Android RTL
-----------------

http://android-developers.blogspot.com/2013/03/native-rtl-support-in-android-42.html

https://developer.android.com/about/versions/jelly-bean.html#42-native-rtl

https://developer.android.com/reference/android/view/View.html#attr_android:layoutDirection

https://developer.android.com/reference/android/view/View.html#attr_android:textDirection

https://developer.android.com/reference/android/view/View.html#attr_android:textAlignment

https://developer.android.com/reference/android/text/TextUtils.html#getLayoutDirectionFromLocale%28java.util.Locale%29


Before Using Android IDE
------------------------

.. code-block:: bash

	$ sudo apt-get install lib32z1 lib32ncurses5 lib32stdc++6



List_of custom Android distributions
-------------------------------------

https://en.wikipedia.org/wiki/List_of_custom_Android_distributions

https://www.geckoandfly.com/23320/custom-rom-firmware-android-smartphones/
