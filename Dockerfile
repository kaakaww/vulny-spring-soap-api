FROM gradle:6.8.3-jdk8
ADD ./app /opt/soap
WORKDIR /opt/soap
RUN gradle build
EXPOSE 8080
ENTRYPOINT ["gradle", "bootRun"]
