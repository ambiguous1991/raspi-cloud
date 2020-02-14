package pl.com.bartusiak.raspiexecutor.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import pl.com.bartusiak.raspiexecutor.datastores.EvictionQueue;
import pl.com.bartusiak.raspiexecutor.dto.Payload;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

    private CommandLineService commandLineService;
    private EvictionQueue<Double> evictionQueue;
    private String tempCommand;

    @Scheduled(fixedRate = 5000)
    @Override
    public void measureTemp() {
        Optional<String> reduce = commandLineService
                .executePayload(new Payload(tempCommand, false))
                .getResult().stream().reduce(String::concat);

        if (reduce.isPresent()) {
            Pattern p = Pattern.compile("(\\d+\\.\\d+)");
            Matcher m = p.matcher(reduce.get());
            if(m.find()) {
                evictionQueue.add(Double.parseDouble(m.group()));
            }
        }
    }

    @Override
    public List<Double> getMeasures() {
        return evictionQueue.get();
    }
}
