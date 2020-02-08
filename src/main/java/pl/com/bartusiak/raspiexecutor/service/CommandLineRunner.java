package pl.com.bartusiak.raspiexecutor.service;

import org.springframework.util.StopWatch;
import pl.com.bartusiak.raspiexecutor.dto.Payload;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;

public class CommandLineRunner implements CommandLineService {

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
            builder.result(result).exception(false).tta(stopWatch.getTotalTimeMillis());
        }
        catch (Exception e) {
            stopWatch.stop();
            builder.result(e.toString()).exception(true).tta(stopWatch.getTotalTimeMillis());
        }
        return builder.build();
    }

}
