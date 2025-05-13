How to Run Cursor as an Application in Ubuntu
=============================================

Running a portable application like the Cursor AppImage on Ubuntu involves several steps,
from downloading the AppImage to creating a desktop shortcut for easy access. This guide will walk you through the entire process, making it easier to launch Cursor like any other application on your Ubuntu system.

Step 1: Download the AppImage
-----------------------------

Start by downloading the Cursor AppImage. You can find it on the official website or other trusted repositories.
For this guide, we’ll assume you've already downloaded the `Cursor-0.49.6-x86_64.AppImage` version.

Step 2: Move the AppImage to the Applications Folder
----------------------------------------------------

Once you've downloaded the AppImage, move it to the `~/Applications` directory,
which is the standard directory for user-specific applications in Ubuntu. If the folder does not exist, you can create it.

.. code-block:: bash

    mkdir -p ~/Applications
    mv Cursor-0.49.6-x86_64.AppImage ~/Applications/

Step 3: Extract the AppImage
-----------------------------

AppImages are self-contained packages, but they can be extracted to their component files for easier management.
To extract the contents of the Cursor AppImage, use the following command:

.. code-block:: bash

    ~/Applications/Cursor-0.49.6-x86_64.AppImage --appimage-extract

This will create a folder named `squashfs-root` in the current directory, containing the application files.

Step 4: Copy the Icon
----------------------

For the AppImage to look like a typical application in the Ubuntu menu, you’ll need an icon.
After extraction, you can find the icon in the `squashfs-root/usr/share/icons/hicolor/256x256/apps/` directory. Copy it to your `~/Applications` folder.

.. code-block:: bash

    cp squashfs-root/usr/share/icons/hicolor/256x256/apps/cursor.png ~/Applications/

Step 5: Remove the Extracted Folder
-----------------------------------

Once you’ve copied the icon, you can remove the extracted folder to save space.

.. code-block:: bash

    rm -r squashfs-root

Step 6: Create a Desktop Entry
------------------------------

Now, let’s create a desktop entry that will allow you to launch Cursor directly from your application menu.
You’ll need to create a `.desktop` file.

Open the file with a text editor:

.. code-block:: bash

    vim ~/.local/share/applications/cursor.desktop

In the file, add the following content:

.. code-block::

    [Desktop Entry]
    Name=Cursor
    Comment=Cursor AppImage Launcher
    Exec=/home/<USERNAME>/Applications/Cursor-0.49.6-x86_64.AppImage --no-sandbox
    Icon=/home/<USERNAME>/Applications/cursor.png
    Terminal=false
    Type=Application
    Categories=Utility;Development;

Make sure to replace `/home/<USERNAME>/` with your actual username. This will create a shortcut to the AppImage
with the appropriate icon and categories in the application menu.

Step 7: Make the `.desktop` File Executable
-------------------------------------------

After creating the `.desktop` file, you need to make it executable so that Ubuntu can recognize it as an application.
Run the following command:

.. code-block:: bash

    chmod +x ~/.local/share/applications/cursor.desktop

Step 8: Update the Desktop Database
-----------------------------------

To ensure that the system recognizes the new desktop entry, update the application database by running:

.. code-block:: bash

    update-desktop-database ~/.local/share/applications

Step 9: Launch the Application
------------------------------

Now that you’ve set everything up, you can find "Cursor" in your application menu, ready to be launched just
like any other native Ubuntu application. Alternatively, you can run it directly from the terminal with the command:

.. code-block:: bash

    bash -c '/home/<USERNAME>/Applications/Cursor-0.49.6-x86_64.AppImage --no-sandbox'

Conclusion
----------

By following these steps, you've successfully installed and configured the Cursor AppImage as a regular application in Ubuntu.
This method can be used for any AppImage, allowing you to run portable applications with ease without the need for complex installations or package management.

