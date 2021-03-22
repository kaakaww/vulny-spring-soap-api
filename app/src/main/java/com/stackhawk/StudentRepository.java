package com.stackhawk;

import com.stackhawk.school.Student;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class StudentRepository {
    private static final Map<String, Student> students = new HashMap<>();

    @PostConstruct
    public void initData() {

        Student student = new Student();
        student.setName("Terrence");
        student.setStandard(5);
        student.setAddress("Los Angeles, CA");
        students.put(student.getName(), student);

        student = new Student();
        student.setName("Jim");
        student.setStandard(5);
        student.setAddress("Miami, FL");
        students.put(student.getName(), student);

        student = new Student();
        student.setName("Aldous");
        student.setStandard(6);
        student.setAddress("Manchester, G.B.");
        students.put(student.getName(), student);

        student = new Student();
        student.setName("Ronald");
        student.setStandard(7);
        student.setAddress("Sioux Falls, SD");
        students.put(student.getName(), student);
    }

    public Student findStudent(String name) {
        Assert.notNull(name, "The Student's name must not be null");
        return students.get(name);
    }
}