package com.stackhawk;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.stackhawk.school.Student;

@Component
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findStudentByName(String name);

    Student findAllByNameOrAddress(String name, String address);

}