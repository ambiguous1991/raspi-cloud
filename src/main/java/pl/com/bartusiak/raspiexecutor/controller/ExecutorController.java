package pl.com.bartusiak.raspiexecutor.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.bartusiak.raspiexecutor.dto.Payload;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;
import pl.com.bartusiak.raspiexecutor.service.CommandLineService;
import pl.com.bartusiak.raspiexecutor.service.TemperatureService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ExecutorController {

    private final CommandLineService commandLineService;
    private final TemperatureService temperatureService;

    public ExecutorController(CommandLineService commandLineService, TemperatureService temperatureService) {
        this.commandLineService=commandLineService;
        this.temperatureService = temperatureService;
    }

    @PostMapping(value = "/execute", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultWrapper> execute(@RequestBody Payload payload) {
        return ResponseEntity.ok(commandLineService.executePayload(payload));
    }

    @GetMapping(value = "/temp")
    public ResponseEntity<List<Double>> getTemp() {
        return ResponseEntity.ok(temperatureService.getMeasures());
    }
}
