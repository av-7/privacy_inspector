# Privacy Inspector

The primary objective of this project is to enhance the Android platform, by implementing a new tool to monitor and log whenever applications use privacy-sensitive API. The Privacy Inspection tool enables the users to test, monitor, and analyze the behavior of any application on Android and recognize possible privacy loopholes. Privacy Inspector uses Aspect Oriented Programming to weave custom aspects into the Android platform. In this way our injected aspects blend with the platform and work together to alert users about privacy leaks.

#### To use this tool, you'll need to instrument the AOSP build using [Instrumentation_tool](https://github.com/poojakanchan/instrumentation_tool)

## Intial Setup
>Please refer to the project wiki, or click the links below for detailed description on getting started:

1. [Getting started with Android 2.3.7 Gingerbread Build][2.3.7]
2. [Procedure for instrumenting the source code and building custom Android emulator][emulator]

#### Mock-up data wrangling
1. [Inserting mock-up geo coordinates in the emulator using telnet][telnet]

[home]: https://github.com/av-7/privacy_protection/wiki
[2.3.7]: https://github.com/av-7/privacy_protection/wiki/Getting-started-with-Android-2.3.7-Gingerbread-Build
[emulator]: https://github.com/av-7/privacy_protection/wiki/Procedure-for-instrumenting-the-source-code-and-building-custom-Android-emulator
[telnet]: https://github.com/av-7/privacy_protection/wiki/Inserting-mock-up-geo-coordinates-in-the-emulator-using-telnet

## Source code walkthrough:

The source code can be found in ```instrumentation_tool/src```

First, let's glance over at the project structure

![Project Structure](https://github.com/av-7/privacy_protection/blob/master/Screens/setup_screens/SS1_Project_Structure.jpg?raw=true "structure")

Let's go over each package and look over the class diagram

### ```package observer;```

![observer](https://github.com/av-7/privacy_protection/blob/master/Screens/class_diagrams/CD2_Package_observer.jpg?raw=true "observer")

### ```package visitor;```

![visitor](https://github.com/av-7/privacy_protection/blob/master/Screens/class_diagrams/CD3_Package_visitor.jpg?raw=true "visitor")

### ```package util;```

![util](https://github.com/av-7/privacy_protection/blob/master/Screens/class_diagrams/CD1_Package_util.jpg?raw=true "util")

### Dependency diagram

![overall](https://github.com/av-7/privacy_protection/blob/master/Screens/class_diagrams/CD4_Overall_flow.jpg?raw=true "overall")

### Flow Diagram (Bottom-up Approach)

![Bottom-up](https://github.com/av-7/privacy_protection/blob/master/Screens/class_diagrams/Flow.jpg?raw=true "overall")

The project makes use of software design patterns like the Observer pattern, Visitor Pattern and the Singleton pattern. Whenever the analysis is run, the stream of bytes are pipelined through the Sliding Buffers looking for the mock-up data instrumented beforehand. The observers are notified, whenever the stream of byte changes, and the DataInspector class runs the analysis - first by detecting the Encoding of the stream, then returning the stream in proper format, if the mockup data is detected, it is logged in the ```adb logs``` while the app is run for testing.

## Sample Application:

For testing purposes, an APK file has been provided under the APKs/ folder for you to understand the working of the tool. The Secret-Spiller app deliberately requests the permission for IMEI, Geolocation, Contacts and then uploads it to an echo server.
