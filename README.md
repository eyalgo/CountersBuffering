# CountersBuffering
A simple library to use for counters insertion. Using a buffer in the process. 

## Gradle tasks
* *oneJar* will create fat jar
* *run* (in eclipse) will start the server
* *startManagedMongoDb test* runs all tests with mongo plugin
* *startMongoDb* starts mongo so we can run tests independently
* *stopMongoDb*

## Dropwizrd
### Links
[http://dropwizard.io/getting-started.html](http://dropwizard.io/getting-started.html)

[https://github.com/gini/dropwizard-gradle](https://github.com/gini/dropwizard-gradle)

[https://github.com/rholder/gradle-one-jar](https://github.com/rholder/gradle-one-jar)

### Executing
After running *oneJar* task, you'll have *counters.jar* file under *build/lib* directory.

Go to that directory and run: `java -jar counters.jar server ../resources/main/config/helloworld.yml`