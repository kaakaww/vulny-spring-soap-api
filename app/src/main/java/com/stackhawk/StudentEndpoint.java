package com.stackhawk;

import com.stackhawk.school.Student;
import com.stackhawk.school.StudentDetailsRequest;
import com.stackhawk.school.StudentDetailsResponse;
import com.stackhawk.school.StudentSearchRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StudentEndpoint
{
    private static final String NAMESPACE_URI = "https://www.stackhawk.com/xml/vulnysoap";

    private StudentRepository studentRepository;

    @Autowired
    public StudentEndpoint(StudentRepository StudentRepository) {
        this.studentRepository = StudentRepository;
    }

    @Autowired
    EntityManager entityManager;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentDetailsRequest")
    @ResponsePayload
    public StudentDetailsResponse getStudent(@RequestPayload StudentDetailsRequest request) {
        StudentDetailsResponse response = new StudentDetailsResponse();
        response.setStudent(studentRepository.findStudent(request.getName()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentSearchRequest")
    @ResponsePayload
    public List<StudentDetailsResponse> searchStudents(@RequestPayload StudentSearchRequest request) {
        final Session session = (Session) entityManager.unwrap(Session.class);
        List items = session.doReturningWork(new ReturningWork<List<Student>>() {
            @Override
            public List<Student> execute(Connection connection) throws SQLException {
                List<Student> items = new ArrayList<>();
                ResultSet rs = connection
                        .createStatement()
                        .executeQuery(
                                "select * from STUDENT where name like '%" + request.getName() + "%'"
                        );
                //or description like '%" + search.getSearchText() + "%'
                while (rs.next()) {
                    items.add(new Student(rs.getString("name"), rs.getLong("standard"), rs.getString("address")));
                }
                return items;
            }
        });
        return items;
    }
}
