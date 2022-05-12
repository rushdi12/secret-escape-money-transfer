FROM openjdk:8-jdk-alpine
MAINTAINER rushdi hoosain
COPY /target/account-transfer-api-1.0.0.jar account-transfer-api-1.0.0.jar
ENTRYPOINT ["java","-jar","/account-transfer-api-1.0.0.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
