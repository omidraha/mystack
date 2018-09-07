Flutter
=======

https://flutter.io/

https://github.com/flutter/flutter


Flutter with high performance compare with React Native and Native
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    Compiles to Native Code

    No reliance on OEM widgets

    No bridge needed

    Structural Repainting

https://medium.com/@nhancv/why-i-move-to-flutter-34c4005b96ef

Native apps (Java/Swift)
++++++++++++++++++++++++

How native Android/iOS code interacts with the platform


.. image:: images/mobile_native.png

Your app talks to the platform to create widgets,
or access services like the camera. The widgets are rendered to a screen canvas,
and events are passed back to the widgets. This is a simple architecture, bu
t you pretty much have to create separate apps for each platform because the widgets are different,
not to mention the native languages.


React Native apps (Javascript)
++++++++++++++++++++++++++++++

How React Native interacts with the platform


.. image:: images/mobile_react.png

React Native is very popular (and deserves to be),
but because the JavaScript realm accesses the OEM widgets in the native realm,
it has to go through the bridge for those as well.
Widgets are typically accessed quite frequently (up to 60 times a second during animations,
transitions, or when the user “swipes” something on the screen with their finger)
so this can cause performance problems. As one article about React Native puts it:

Here lies one of the main keys to understanding React Native performance. Each realm by itself is blazingly fast.
The performance bottleneck often occurs when we move from one realm to the other.
In order to architect performance React Native apps, we must keep passes over the bridge to a minimum.

Flutter apps (Dart)
+++++++++++++++++++

How Flutter interacts with the platform


.. image:: images/mobile_flutter.png

Flutter takes a different approach to avoiding performance problems caused by the need for a JavaScript bridge by using a compiled programming language,
namely Dart. Dart is compiled “ahead of time” (AOT) into native code for multiple platforms.
This allows Flutter to communicate with the platform without going through a JavaScript bridge that does a context switch.

Flutter has a new architecture that includes widgets that look and feel good,
are fast, and are customizable and extensible.
That’s right, Flutter does not use the OEM widgets (or DOM WebViews), it provides its own widgets.

Flutter moves the widgets and the renderer from the platform into the app,
which allows them to be customizable and extensible.
All that Flutter requires of the platform is a canvas in which to render the widgets so they can appear on the device screen,
and access to events (touches, timers, etc.) and services (location, camera, etc.).


https://hackernoon.com/why-native-app-developers-should-take-a-serious-look-at-flutter-e97361a1c073
