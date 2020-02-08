package pl.com.bartusiak.raspiexecutor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.bartusiak.raspiexecutor.service.CommandLineRunner;
import pl.com.bartusiak.raspiexecutor.service.CommandLineService;

@Configuration
public class ExecutorConfiguration {

    @Bean
    public CommandLineService commandLineService() {
        return new CommandLineRunner();
    }
}
