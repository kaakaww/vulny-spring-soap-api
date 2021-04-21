package com.stackhawk.vuln.soap;

import com.stackhawk.vuln.soap.bean.Course;
import com.stackhawk.vuln.soap.bean.User;
import com.stackhawk.vuln.soap.repo.CourseRepository;

import com.stackhawk.vuln.soap.repo.UserRepository;
import com.stackhawk.vuln.soap.service.CourseDetailsService;
import com.stackhawk.vulnsoap.Status;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoapCourseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapCourseManagementApplication.class, args);
	}

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	EntityManager entityManager;

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx, CourseRepository repo, UserRepository userRepo) {

		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

			System.out.println(String.format("Load some fixture data %s", dbUrl));
			System.out.println(String.format("Courses in DB %d", repo.count()));
			System.out.println(String.format("Users in DB %d", userRepo.count()));

			if (repo.count() == 0) {
				repo.save(new Course(0, "History", "History of Science"));
				repo.save(new Course(1, "Science", "Science of Literature"));
				repo.save(new Course(2, "Literature", "British Literature"));
				System.out.println(String.format("Courses in DB %d", repo.count()));
				repo.findAll().forEach(item -> System.out.println(String.format("course: %s", item.toString())));
				userRepo.save(new User(0, "user1", "P@ssw0rd"));
				userRepo.save(new User(1, "secret_admin", "So0p3r_$3cre7"));
				System.out.println(String.format("Users in DB %d", userRepo.count()));
				userRepo.findAll().forEach(item -> System.out.println(String.format("user: %s", item.toString())));

				/*final Session session = (Session) entityManager.unwrap(Session.class);
				session.doReturningWork(new ReturningWork<Status>() {
					@Override
					public Status execute(Connection connection) throws SQLException {
						connection
								.createStatement()
								.execute(
										"CREATE TABLE user (id INTEGER PRIMARY KEY NOT NULL, username NVARCHAR(1024) NOT NULL, password TEXT NOT NULL)"
								);
						connection
								.createStatement()
								.execute(
										"CREATE TABLE course (id INTEGER PRIMARY KEY NOT NULL, name NVARCHAR(1024) NOT NULL, description TEXT NOT NULL)"
								);
						connection
								.createStatement()
								.execute(
										"INSERT INTO course VALUES(0, 'History', 'History of Science')"
								);
						connection
								.createStatement()
								.execute(
										"INSERT INTO course VALUES(1, 'Science', 'Science of Literature')"
								);
						connection
								.createStatement()
								.execute(
										"INSERT INTO course VALUES(2, 'Literature', 'British Literature')"
								);
						connection
								.createStatement()
								.execute(
										"INSERT INTO user VALUES(0, 'user1', 'P@ssw0rd')"
								);
						connection
								.createStatement()
								.execute(
										"INSERT INTO user VALUES(1, 'secret_admin', 'So0p3r_$3cre7')"
								);

						return Status.SUCCESS;
					}
				});
				session.close();*/
			}
		};
	}
}
