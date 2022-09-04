# app

Minimal Helidon MP project suitable to start from scratch.

## Build and run


With JDK17+
```bash
mvn package
java -jar target/app.jar
```

## Exercise the application
```
curl -X GET http://localhost:8080/simple-greet
{"message":"Hello World!"}
```


