package com.stackhawk.school.repos;

import com.stackhawk.school.entity.Student;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {

    List<Student> findStudentByNameOrGpa(String name, double gpa);

    Student findStudentByName(String name);

    List<Student> findAllByName(String name);

}