package pl.com.bartusiak.raspiexecutor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;
import pl.com.bartusiak.raspiexecutor.service.CommandLineService;

@RestController
public class ExecutorController {

    private final CommandLineService commandLineService;

    public ExecutorController(CommandLineService commandLineService) {
        this.commandLineService=commandLineService;
    }

    @PostMapping("/execute")
    public ResponseEntity<ResultWrapper> execute(@RequestBody String payload) {
        return ResponseEntity.ok(commandLineService.executePayload(payload));
    }
}
