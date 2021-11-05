package pl.com.bartusiak.raspiexecutor.configuration;

import org.apache.sshd.client.SshClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.com.bartusiak.raspiexecutor.datastores.EvictionQueue;
import pl.com.bartusiak.raspiexecutor.dto.Payload;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;
import pl.com.bartusiak.raspiexecutor.service.*;

import java.util.Collections;
import java.util.List;

@Configuration
public class ExecutorConfiguration {
    @Bean
    @Profile("noop")
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
    @Profile("noop")
    public TemperatureService temperatureService() {
        return new TemperatureService() {
            @Override
            public void measureTemp() {}

            @Override
            public List<Double> getMeasures() {
                return Collections.emptyList();
            }
        };
    }

    @Bean
    @Profile("remote")
    public CommandLineService remoteCommandLineRunner(SshClient sshClient,
                                                      @Value("${application.ssh.hostname}") String hostname,
                                                      @Value("${application.ssh.username}") String username,
                                                      @Value("${application.ssh.password}") String password) {
        return new RemoteCommandLineRunner(sshClient, hostname, username, password);
    }

    @Bean
    @Profile("remote")
    public SshClient sshClient() {
        return SshClient.setUpDefaultClient();
    }

    @Bean
    @Profile("local")
    public CommandLineService localCommandLineRunner() {
        return new LocalCommandLineRunner();
    }

    @Bean
    @Profile("local")
    public TemperatureService temperatureService(CommandLineService commandLineService,
                                                 @Value("${application.temp.capacity}") Integer capacity,
                                                 @Value("${application.temp.command}") String command) {
        return new TemperatureServiceImpl(commandLineService, new EvictionQueue<>(capacity), command);
    }
}
