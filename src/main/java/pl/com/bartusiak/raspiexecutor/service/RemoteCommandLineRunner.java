package pl.com.bartusiak.raspiexecutor.service;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;
import org.springframework.util.StopWatch;
import pl.com.bartusiak.raspiexecutor.dto.Payload;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoteCommandLineRunner implements CommandLineService {

    private final SshClient sshClient;
    private final String hostname;
    private final String username;
    private final String password;
    private static int defaultTimeoutSeconds = 10;

    public RemoteCommandLineRunner(SshClient sshClient, String hostname, String username, String password) {
        this.sshClient = sshClient;
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    public ResultWrapper executePayload(Payload payload) {
        final ResultWrapper.ResultWrapperBuilder builder = ResultWrapper.builder();
        final StopWatch stopWatch = new StopWatch();
        String command = payload.getCommand();
        builder.command(command);

        sshClient.start();

        try (ClientSession session = getSession();
             ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
             ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {
            channel.setOut(responseStream);
            stopWatch.start();
            System.out.println("Opening ");
            channel.open();
            channel.waitFor(EnumSet.of(ClientChannelEvent.OPENED), TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
            responseStream.flush();
            try (OutputStream pipedIn = channel.getInvertedIn()) {
                pipedIn.write(command.getBytes());
                pipedIn.flush();
            }
            channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                    TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
            String responseString = responseStream.toString();
            System.out.println("Response: "+ responseString);
            stopWatch.stop();
            builder.result(List.of(responseString.split("\n"))).exception(false).tta(stopWatch.getTotalTimeMillis());
        } catch (IOException e) {
            stopWatch.stop();
            List<String> stack = Stream.of(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
            builder.result(stack).exception(true).tta(stopWatch.getTotalTimeMillis());
        }
        return builder.build();
    }

    private ClientSession getSession() throws IOException {
        ClientSession session =
                sshClient.connect(this.username, this.hostname, 22).verify(defaultTimeoutSeconds, TimeUnit.SECONDS)
                        .getSession();
        session.addPasswordIdentity(password);
        session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
        return session;
    }
}
