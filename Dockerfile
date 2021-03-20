FROM gradle:6.8.3-jdk8
ADD ./ /tmp/app
WORKDIR /tmp/app
RUN ./gradlew installDist
RUN cp -r build/install/spring-boot-soap-example /opt/soap
EXPOSE 8080
WORKDIR /opt/soap
CMD ["./bin/spring-boot-soap-example"]
