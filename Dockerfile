FROM openjdk:11-jre-slim-stretch
COPY ./target/raspi-exe-1.0.jar /usr/src/raspiexe/raspi-exe.jar
WORKDIR /usr/src/raspiexe/
ENTRYPOINT ["java", "-jar", "./raspi-exe.jar", "--spring.profiles.active=noop"]
