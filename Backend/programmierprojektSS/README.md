# building
1. Create jar file using maven (maven and jdk-16 must be installed)
```
mvn package
```
2. A runnable .jar file is now in target/. To run it use
```
java -jar target/programmierprojektSS-0.0.1-SNAPSHOT.jar
```
3. Or optionally with arguments 
```
java -jar target/programmierprojektSS-0.0.1-SNAPSHOT.jar -graph germany.fmi.txt -lon 9.098 -lat 48.746 -que Benchs\germany.que -s 638394
```



