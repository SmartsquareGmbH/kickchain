FROM openjdk:8-jre-alpine

EXPOSE 8080

ADD target/kickchain-0.0.1-SNAPSHOT.jar kickchain.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dconsensus.peers=127.0.0.1", "-jar", "/kickchain.jar"]