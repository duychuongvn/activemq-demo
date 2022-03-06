# Build docker image:
```
./mvnw -Pprod verify jib:dockerBuild
```

```
 mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*"
 
```
