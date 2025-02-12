FROM alpine:latest

RUN apk update && apk add openjdk17-jre-headless && apk upgrade

ENV JAVA_HOME="/usr/lib/jvm/java-17-openjdk"
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /MoneyTransferService

EXPOSE 8080

ADD target/*-SNAPSHOT.jar MoneyTransferService.jar

ENTRYPOINT ["java", "-jar", "MoneyTransferService.jar"]