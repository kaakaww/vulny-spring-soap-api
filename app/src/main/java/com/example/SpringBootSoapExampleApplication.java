package com.example;

import com.example.school.Student;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootSoapExampleApplication {


	@Value("${spring.datasource.url}")
	private String dbUrl;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSoapExampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx, StudentRepository repo) {

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
				repo.save(new Student(1L,"Peter", "Manchester, G.B."));
				repo.save(new Student(2L,"Blandford", "London, G.B."));
				repo.save(new Student(3L,"Townsend", "Slough, G.B."));
				System.out.println(String.format("Items in DB %d", repo.count()));
				repo.findAll().forEach(item -> System.out.println(String.format("item: %s", item.toString())));
				System.out.println(String.format("Items in DB %d", repo.count()));
				repo.findAll().forEach(item -> System.out.println(String.format("item: %s", item.toString())));
			}

		};
	}
}
