FROM openjdk:21
VOLUME /tmp
ADD ./target/Tour_guide-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
