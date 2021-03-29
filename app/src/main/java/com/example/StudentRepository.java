package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.example.school.Student;

@Component
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findStudentByName(String name);

    Student findAllByNameOrAddress(String name, String address);

}