package pl.com.bartusiak.raspiexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class RaspiExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaspiExecutorApplication.class, args);
	}

}
