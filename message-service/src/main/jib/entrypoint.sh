#!/bin/sh
echo "The application will start in 20s..." && sleep 20

exec java ${JAVA_OPTS}  -Djava.library.path=/app/resources -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.smartdev.activemq.userservice.MessageServiceApplication"  "$@"
