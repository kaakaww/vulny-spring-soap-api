FROM java:8-jre
ADD build/install/spring-boot-soap-example/. /opt/soap/
EXPOSE 8080
WORKDIR /opt/soap
CMD ["./bin/spring-boot-soap-example"]
