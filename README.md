# Spring boot SOAP example 

Simple SOAP API using Spring Boot. Based on this [tutorial](https://howtodoinjava.com/spring/spring-boot/spring-boot-soap-webservice-example/).

Requires:
* Java (8)
* Gradle (wrapper in source)

## Run

```
./gradlew run
```

View the WDSL at http://localhost:8080/service/studentDetailsWsdl.wsdl and post requests to http://localhost:8080/service/student-details.

Example request (with header "Content-Type:text/xml"):
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sch="https://www.howtodoinjava.com/xml/school">
   <soapenv:Header/>
   <soapenv:Body>
      <sch:StudentDetailsRequest>
         <sch:name>Sajal</sch:name>
      </sch:StudentDetailsRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

### Docker

```
# build install distribution
./gradlew installDist

# build image
docker build -t local/spring-boot-soap-example .

# run image
docker run -p 8080:8080 local/spring-boot-soap-example
```