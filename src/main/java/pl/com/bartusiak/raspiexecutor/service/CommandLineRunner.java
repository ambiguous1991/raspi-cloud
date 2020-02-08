package pl.com.bartusiak.raspiexecutor.service;

import org.springframework.util.StopWatch;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;

public class CommandLineRunner implements CommandLineService {

    public ResultWrapper executePayload(String command) {
        final ResultWrapper.ResultWrapperBuilder builder = ResultWrapper.builder();
        final StopWatch stopWatch = new StopWatch();
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
            builder.result(e.getMessage()).exception(true).tta(stopWatch.getTotalTimeMillis());
        }
        return builder.build();
    }

}
