package pl.com.bartusiak.raspiexecutor.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.com.bartusiak.raspiexecutor.datastores.EvictionQueue;
import pl.com.bartusiak.raspiexecutor.service.CommandLineService;
import pl.com.bartusiak.raspiexecutor.service.LocalCommandLineRunner;
import pl.com.bartusiak.raspiexecutor.service.TemperatureService;
import pl.com.bartusiak.raspiexecutor.service.TemperatureServiceImpl;

@Configuration
@Profile("local")
public class LocalExecutorConfiguration {
    @Bean
    public CommandLineService localCommandLineRunner() {
        return new LocalCommandLineRunner();
    }

    @Bean
    public TemperatureService temperatureService(CommandLineService commandLineService,
                                                 @Value("${application.temp.capacity}") Integer capacity,
                                                 @Value("${application.temp.command}") String command) {
        return new TemperatureServiceImpl(commandLineService, new EvictionQueue<>(capacity), command);
    }
}
