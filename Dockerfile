FROM gradle:6.8.3-jdk8
ADD ./ /opt/soap
WORKDIR /opt/soap
RUN ./gradlew clean build
EXPOSE 8080
CMD ["./gradlew bootRun"]
