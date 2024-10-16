ARG JAVA_VERSION=11
FROM openjdk:11
COPY target/cloudstorage-0.0.1-SNAPSHOT.jar springboot-demo-docker.jar
EXPOSE 8080
CMD ["java","-jar","/springboot-demo-docker.jar"]