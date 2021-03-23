package com.stackhawk;

import com.stackhawk.school.StudentDetailsRequest;
import com.stackhawk.school.StudentDetailsResponse;
import com.stackhawk.school.StudentSearchRequest;
import com.stackhawk.school.StudentSearchResponse;
import com.stackhawk.school.repos.StudentRepo;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StudentEndpoint
{
    private static final String NAMESPACE_URI = "http://localhost/vulnysoap";

    private StudentRepo studentRepo;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public StudentEndpoint(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentDetailsRequest")
    @ResponsePayload
    public StudentDetailsResponse getStudent(@RequestPayload StudentDetailsRequest request) {
        StudentDetailsResponse response = new StudentDetailsResponse();
        response.setStudent(studentRepo.findStudentByName(request.getName()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentSearchRequest")
    @ResponsePayload
    public StudentSearchResponse searchStudents(@RequestPayload StudentSearchRequest request) {
        StudentSearchResponse response = new StudentSearchResponse();
        response.setStudents(studentRepo.findAllByName(request.getName()));
        return response;
/*        final Session session = (Session) entityManager.unwrap(Session.class);
        return session.doReturningWork(new ReturningWork<StudentSearchResponse>() {
            @Override
            public StudentSearchResponse execute(Connection connection) throws SQLException {
                StudentSearchResponse studentSearchResponse = new StudentSearchResponse();
                ResultSet rs = connection
                        .createStatement()
                        .executeQuery(
                                "select * from STUDENT where name like '%" + request.getName() + "%'"
                        );
                //or description like '%" + search.getSearchText() + "%'
                while (rs.next()) {
                    studentSearchResponse.addStudent(new Student(rs.getString("name"), rs.getDouble("gpa"), rs.getString("location")));
                }
                return studentSearchResponse;
            }
        });*/
    }
}
