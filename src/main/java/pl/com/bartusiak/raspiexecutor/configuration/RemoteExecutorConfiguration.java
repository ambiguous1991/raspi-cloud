package pl.com.bartusiak.raspiexecutor.configuration;

import org.apache.sshd.client.SshClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.com.bartusiak.raspiexecutor.datastores.EvictionQueue;
import pl.com.bartusiak.raspiexecutor.service.CommandLineService;
import pl.com.bartusiak.raspiexecutor.service.RemoteCommandLineRunner;
import pl.com.bartusiak.raspiexecutor.service.TemperatureService;
import pl.com.bartusiak.raspiexecutor.service.TemperatureServiceImpl;

@Configuration
@Profile("remote")
public class RemoteExecutorConfiguration {
    @Bean
    public CommandLineService remoteCommandLineRunner(SshClient sshClient,
                                                      @Value("${application.ssh.hostname}") String hostname,
                                                      @Value("${application.ssh.username}") String username,
                                                      @Value("${application.ssh.password}") String password) {
        return new RemoteCommandLineRunner(sshClient, hostname, username, password);
    }

    @Bean
    public SshClient sshClient() {
        return SshClient.setUpDefaultClient();
    }

    @Bean
    public TemperatureService temperatureService(CommandLineService commandLineService,
                                                 @Value("${application.temp.capacity}") Integer capacity,
                                                 @Value("${application.temp.command}") String command) {
        return new TemperatureServiceImpl(commandLineService, new EvictionQueue<>(capacity), command);
    }
}
