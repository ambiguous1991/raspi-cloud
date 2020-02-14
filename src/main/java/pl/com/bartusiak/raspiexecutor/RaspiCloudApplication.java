package pl.com.bartusiak.raspiexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaServer
@EnableConfigServer
@EnableScheduling
@SpringBootApplication
public class RaspiCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaspiCloudApplication.class, args);
	}

}
