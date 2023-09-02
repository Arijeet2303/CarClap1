FROM openjdk:8
EXPOSE 8080
ADD target/carclap-0.0.1-SNAPSHOT.jar carclap-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/carclap-0.0.1-SNAPSHOT.jar"]