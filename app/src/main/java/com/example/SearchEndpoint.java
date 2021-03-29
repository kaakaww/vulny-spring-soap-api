package com.example;

import com.example.school.StudentSearchRequest;
import com.example.school.StudentSearchResponse;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SearchEndpoint {
    private static final String NAMESPACE_URI = "http://localhost/xml/school";

    private StudentRepository StudentRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public SearchEndpoint(StudentRepository StudentRepository) {
        this.StudentRepository = StudentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentSearchResponse")
    @ResponsePayload
    public StudentSearchResponse searchStudent(@RequestPayload StudentSearchRequest request) {
        StudentSearchResponse response = new StudentSearchResponse();
        response.setStudent(StudentRepository.findAllByNameOrAddress(request.getName(), request.getAddress()));
        return response;
    }


}
