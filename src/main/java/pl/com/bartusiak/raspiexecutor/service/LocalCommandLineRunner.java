package pl.com.bartusiak.raspiexecutor.service;

import org.springframework.util.StopWatch;
import pl.com.bartusiak.raspiexecutor.dto.Payload;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalCommandLineRunner implements CommandLineService {

    public ResultWrapper executePayload(Payload payload) {
        final ResultWrapper.ResultWrapperBuilder builder = ResultWrapper.builder();
        final StopWatch stopWatch = new StopWatch();
        String command = payload.getCommand();
        builder.command(command);
        try {
            stopWatch.start();
            Process exec = Runtime.getRuntime().exec(command);
            stopWatch.stop();
            String result =  new String(exec.getInputStream().readAllBytes());
            builder.result(List.of(result.split("\n"))).exception(false).tta(stopWatch.getTotalTimeMillis());
        }
        catch (Exception e) {
            stopWatch.stop();
            List<String> stack = Stream.of(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
            builder.result(stack).exception(true).tta(stopWatch.getTotalTimeMillis());
        }
        return builder.build();
    }

}
