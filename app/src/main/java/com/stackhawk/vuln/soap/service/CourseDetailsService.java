package com.stackhawk.vuln.soap.service;

import com.stackhawk.vuln.soap.bean.Course;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	// course - 1
	public List<Course> findByName(String name) {
		System.out.println("searching for course " + name);
		final Session session = (Session) entityManager.unwrap(Session.class);
		return session.doReturningWork(new ReturningWork<List<Course>>() {
			@Override
			public List<Course> execute(Connection connection) throws SQLException {
				List<Course> items = new ArrayList<>();
				ResultSet rs = connection
						.createStatement()
						.executeQuery(
								"SELECT * FROM course WHERE name = '" + name + "'"
						);
				while (rs.next()) {
					System.out.println("Found record " + rs.getString("name"));
					items.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
				}
				return items;
			}
		});
	}

	// courses
	public List<Course> findAll() {
		final Session session = (Session) entityManager.unwrap(Session.class);
		System.out.println("searching for courses..");
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
					System.out.println("Found record " + rs.getString("name"));
					items.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
				}
				return items;
			}
		});
		return items;
	}

	public Status deleteById(int id) {
		final Session session = (Session) entityManager.unwrap(Session.class);
		return session.doReturningWork(new ReturningWork<Status>() {
			@Override
			public Status execute(Connection connection) throws SQLException {
				ResultSet rs = connection
				.createStatement()
				.executeQuery(
						"DELETE FROM course WHERE id = " + id
				);
				if (rs.next()) {
					if (rs.rowDeleted())
						return com.stackhawk.vuln.soap.service.CourseDetailsService.Status.SUCCESS;
				}
				return com.stackhawk.vuln.soap.service.CourseDetailsService.Status.FAILURE;
			}
			});
	}

	// updating course & new course
}
