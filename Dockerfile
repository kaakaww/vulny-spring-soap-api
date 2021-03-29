FROM gradle:6.8.3-jdk8
ADD ./app /opt/soap
WORKDIR /opt/soap
RUN gradle clean build
EXPOSE 9000
ENTRYPOINT ["gradle", "clean", "bootRun"]
