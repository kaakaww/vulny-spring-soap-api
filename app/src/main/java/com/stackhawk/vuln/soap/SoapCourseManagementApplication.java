package com.stackhawk.vuln.soap;

import com.stackhawk.vuln.soap.example.bean.Course;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoapCourseManagementApplication {


	@Value("${spring.datasource.url}")
	private String dbUrl;

	public static void main(String[] args) {
		SpringApplication.run(SoapCourseManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx, CourseRepository repo) {

		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

			System.out.println(String.format("Load some fixture data %s", dbUrl));

			System.out.println(String.format("Items in DB %d", repo.count()));

			if (repo.count() == 0) {
				repo.findAll().forEach(item -> System.out.println(String.format("item: %s", item.toString())));
				repo.save(new Course(1,"History", "History of Science"));
				repo.save(new Course(2,"Science", "Science of Literature"));
				repo.save(new Course(3,"Literature", "British Literature"));
				System.out.println(String.format("Items in DB %d", repo.count()));
				repo.findAll().forEach(item -> System.out.println(String.format("item: %s", item.toString())));
				System.out.println(String.format("Items in DB %d", repo.count()));
				repo.findAll().forEach(item -> System.out.println(String.format("item: %s", item.toString())));
			}

		};
	}
}
