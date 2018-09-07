tips
====

Cmds
++++

.. code-block:: bash

	$ ffmpeg -formats # print the list of supported file formats
	$ ffmpeg -codecs  # print the list of supported codecs (E=encode,D=decode)

.. code-block:: bash

	$ ffmpeg --help
	-i         set the input file. Multiple -i switchs can be used
	-f         set video format (for the input if before of -i, for output otherwise)
	-an        ignore audio
	-vn        ignore video
	-ar        set audio rate (in Hz)
	-ac        set the number of channels
	-ab        set audio bitrate
	-acodec    choose audio codec or use “copy” to bypass audio encoding
	-vcodec    choose video codec or use “copy” to bypass video encoding
	-r         video fps. You can also use fractional values like 30000/1001 instead of 29.97
	-s         frame size (w x h, ie: 320x240)
	-aspect    set the aspect ratio i.e: 4:3 or 16:9
	-sameq     ffmpeg tries to keep the visual quality of the input
	-t N       encode only N seconds of video (you can use also the hh:mm:ss.ddd format)
	-croptop, -cropleft, -cropright, -cropbottom   crop input video frame on each side
	-y         automatic overwrite of the output file
	-ss        select the starting time in the source file
	-vol       change the volume of the audio
	-g         Gop size (distance between keyframes)
	-b         Video bitrate
	-bt        Video bitrate tolerance
	-metadata  add a key=value metadata


Compile
+++++++

Compile `FFMPEG`

Requirement libraries:

.. code-block:: bash

    $ aptitude install libfdk-aac-dev  \
                        libmp3lame-dev \
                        libtheora-dev \
                        libvorbis-dev \
                        libvpx-dev \
                        libx264-dev


Also you can download `libfdk_aac` from ``http://sourceforge.net/projects/opencore-amr/?source=dlp`` and compile it:

.. code-block:: bash

	$ ./configure
	$ make
	$ make install

then download ffpmeg from ``http://www.ffmpeg.org/download.html#releases``


.. code-block:: bash

	$./configure --enable-gpl --enable-nonfree \
		 --enable-libmp3lame \
		 --enable-libfdk_aac \
		 --enable-libvorbis \
		 --enable-libtheora \
		 --enable-libx264 \
		 --enable-libvpx
	$ make
	$ make install


FFmpeg Static Builds
--------------------

    `<http://johnvansickle.com/ffmpeg/>`_


    `<http://ffmpeg.gusari.org/static/>`_

Links
+++++

https://sonnati.wordpress.com/2011/07/11/ffmpeg-the-swiss-army-knife-of-internet-streaming-part-i/

FFmpeg on Windows
-----------------

http://ffmpeg.zeranoe.com/

How to Watermark an image into the video
++++++++++++++++++++++++++++++++++++++++


.. code-block:: bash

    # Top left
    $ ffmpeg –i source.avi -vf "movie=watermark.png [watermark]; [in][watermark] overlay=10:10 [out]" output.flv

    # Top right
    $ ffmpeg –i source.avi -vf "movie=watermark.png [watermark]; [in][watermark] overlay=main_w-overlay_w-10:10 [out]" output.flv

    # Bottom left
    $ ffmpeg –i source.avi -vf "movie=watermark.png [watermark]; [in][watermark] overlay=10:main_h-overlay_h-10 [out]" output.flv

    # Bottom right
    $ ffmpeg –i source.avi -vf "movie=watermark.png [watermark]; [in][watermark] overlay=main_w-overlay_w-10:main_h-overlay_h-10 [out]" output.flv

    # Center
    $ ffmpeg –i source.avi -vf "movie=watermark.png [watermark]; [in][watermark] overlay=main_w/2-overlay_w/2:main_h/2-overlay_h/2 [out]" output.flv


http://www.digitalwhores.net/ffmpeg/ffmpeg-watermark-positions/

Burn subtitles into video
-------------------------

.. code-block:: bash

    $ ffmpeg -i video.avi -vf subtitles=subtitle.srt out.avi


https://trac.ffmpeg.org/wiki/HowToBurnSubtitlesIntoVideo


Reduce the size of a video
--------------------------

.. code-block:: bash

    $ ffmpeg -i input.mp4 -r 30 -s 960x540 output.mp4


GUI Video editor
----------------

.. code-block:: bash

    $ apt-get install kdenlive

