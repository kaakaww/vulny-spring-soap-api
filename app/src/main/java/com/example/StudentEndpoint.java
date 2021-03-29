package com.example;

import com.example.school.StudentSearchRequest;
import com.example.school.StudentSearchResponse;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.example.school.StudentDetailsRequest;
import com.example.school.StudentDetailsResponse;

@Endpoint
public class StudentEndpoint
{
    private static final String NAMESPACE_URI = "http://localhost/xml/school";

    private StudentRepository StudentRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public StudentEndpoint(StudentRepository StudentRepository) {
        this.StudentRepository = StudentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentDetailsRequest")
    @ResponsePayload
    public StudentDetailsResponse getStudent(@RequestPayload StudentDetailsRequest request) {
        StudentDetailsResponse response = new StudentDetailsResponse();
        response.setStudent(StudentRepository.findStudentByName(request.getName()));
        return response;
    }

}

