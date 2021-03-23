package com.stackhawk;

import com.stackhawk.school.entity.Student;
import com.stackhawk.school.repos.StudentRepo;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootVulnySoapApi {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVulnySoapApi.class, args);
	}

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx, StudentRepo repo) {

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
				repo.findAll().forEach(item -> System.out.println(String.format("item: %s", item.getName())));

				Stream.of("Peter", "Blandford", "Townsend").forEach(i -> {
					System.out.println(String.format("Adding student %s", i));
					repo.save(new Student(String.format("%s", i), 3.0, String.format("Anytown, G.B.", i)));
				});

				System.out.println(String.format("Items in DB %d", repo.count()));
				repo.findAll().forEach(item -> System.out.println(String.format("item: %s", item.getName())));
			}

		};
	}

}
