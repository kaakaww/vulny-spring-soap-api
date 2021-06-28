FROM gradle:6.8.3-jdk8
ADD ./app /opt/soap
WORKDIR /opt/soap
RUN gradle bootJar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/opt/soap/build/libs/vulnsoap.jar"]
