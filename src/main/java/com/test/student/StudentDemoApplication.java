package com.test.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.test.student.model.Student;
import com.test.student.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class StudentDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(StudentDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StudentDemoApplication.class, args);
	}

	//Initial data load for testing
	@Bean
	public CommandLineRunner demo(StudentRepository repository) {
		return (args) -> {
			repository.save(new Student(57475L, "Umesh", "Pune"));
			repository.save(new Student(54872L, "Ashok", "Mumbai"));
			repository.save(new Student(14523L, "Rahul", "Nagpur"));

			for (Student student : repository.findAll()) {
				log.info("The Student is: " + student.toString());
			}
		};
	}

}
