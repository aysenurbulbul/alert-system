package yte.intern.alertsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class AlertSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertSystemApplication.class, args);
	}

	//to create Bean for restTemplate to auto wiring the restTemplate object
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}
