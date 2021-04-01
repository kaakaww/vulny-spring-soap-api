package com.stackhawk.vuln.soap;

import com.stackhawk.vuln.soap.bean.Course;
import org.springframework.data.repository.CrudRepository;


public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findCourseByName(String name);

    Course findCourseByDescription(String description);

}