FROM eclipse-temurin:17-jre as build

MAINTAINER edeesan

COPY build/libs/loans-0.0.1-SNAPSHOT.jar loans-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/loans-0.0.1-SNAPSHOT.jar"]

