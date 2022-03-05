# build User service
```
cd user-service && ./mvnw -Pprod verify jib:dockerBuild
```

# build Message service
```
cd message-service && ./mvnw -Pprod verify jib:dockerBuild

```

#run User service instances

```
docker-compose -f user-service/src/main/docker/app.yml up
```
open other tab and execute command
```
docker-compose -f user-service/src/main/docker/app-2.yml up

```

#run Message service instances

```
docker-compose -f message-service/src/main/docker/app.yml up
```
open other tab and execute command
```
docker-compose -f message-service/src/main/docker/app-2.yml up

```

# Fire event 
```
 curl 'http://localhost:8080/api/users/greeting?name=SmartDev&type=virtual'
```
