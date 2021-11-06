package pl.com.bartusiak.raspiexecutor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;
import pl.com.bartusiak.raspiexecutor.service.CommandLineService;
import pl.com.bartusiak.raspiexecutor.service.TemperatureService;

import java.util.Collections;
import java.util.List;

@Configuration
@Profile("noop")
public class NoopExecutorConfiguration {
    @Bean
    public CommandLineService noopCommandLineRunner() {
        return command -> ResultWrapper
                .builder()
                .command(command.getCommand())
                .tta(0)
                .exception(false)
                .result(List.of("Command line runner disabled"))
                .build();
    }

    @Bean
    public TemperatureService temperatureService() {
        return new TemperatureService() {
            @Override
            public void measureTemp() {
            }

            @Override
            public List<Double> getMeasures() {
                return Collections.emptyList();
            }
        };
    }
}
