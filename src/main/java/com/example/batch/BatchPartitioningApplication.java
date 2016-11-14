package com.example.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class BatchPartitioningApplication {

	@Bean
	CommandLineRunner runner(UserRepository userRepository) {
		return args -> {
//			Stream.of("Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni","Alok,Kulkarni", "Sheetal,Kulkarni","Dhriti,Kulkarni","Avinash, Kulkarni","Sarthak,Kulkarni","Kanchan, Kulkarni")
//					.map(s -> s.split(","))
//						.forEach(tuple -> userRepository.save(new UserDetails(tuple[0],tuple[1])));

//			for(int i = 0; i<=50000;i++) {
//				userRepository.save(new UserDetails("Alok","Kulkarni"));
//			}
//
//
//			userRepository.findAll().forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BatchPartitioningApplication.class, args);
	}
}
