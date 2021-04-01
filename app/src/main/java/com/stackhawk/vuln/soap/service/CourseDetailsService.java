package com.stackhawk.vuln.soap.service;

import com.stackhawk.vuln.soap.bean.Course;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseDetailsService {

	@Autowired
	EntityManager entityManager;

	public enum Status {
		SUCCESS, FAILURE;
	}

	private static List<Course> courses = new ArrayList<>();

	static {
		Course course1 = new Course(1, "Spring", "10 Steps");
		courses.add(course1);

		Course course2 = new Course(2, "Spring MVC", "10 Examples");
		courses.add(course2);

		Course course3 = new Course(3, "Spring Boot", "6K Students");
		courses.add(course3);

		Course course4 = new Course(4, "Maven", "Most popular maven course on internet!");
		courses.add(course4);
	}

	// course - 1
	public Course findById(int id) {
		final Session session = (Session) entityManager.unwrap(Session.class);
		return session.doReturningWork(new ReturningWork<Course>() {
			@Override
			public Course execute(Connection connection) throws SQLException {
				for (Course course1 : courses) {
					if (course1.getId() == id)
						return course1;
				}
				return null;
			}
		});
	}

	// courses
	public List<Course> findAll() {
		final Session session = (Session) entityManager.unwrap(Session.class);
		List items = session.doReturningWork(new ReturningWork<List<Course>>() {
			@Override
			public List<Course> execute(Connection connection) throws SQLException {
				List<Course> items = new ArrayList<>();
				ResultSet rs = connection
						.createStatement()
						.executeQuery(
								"SELECT * FROM course"
						);
				//or description like '%" + search.getSearchText() + "%'
				while (rs.next()) {
					items.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
				}
				return items;
			}
		});
		return items;
	}

	public com.stackhawk.vuln.soap.service.CourseDetailsService.Status deleteById(int id) {
		Iterator<Course> iterator = courses.iterator();
		while (iterator.hasNext()) {
			Course course = iterator.next();
			if (course.getId() == id) {
				iterator.remove();
				return com.stackhawk.vuln.soap.service.CourseDetailsService.Status.SUCCESS;
			}
		}
		return com.stackhawk.vuln.soap.service.CourseDetailsService.Status.FAILURE;
	}

	// updating course & new course
}
