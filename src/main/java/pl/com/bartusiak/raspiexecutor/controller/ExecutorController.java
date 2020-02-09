package pl.com.bartusiak.raspiexecutor.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bartusiak.raspiexecutor.dto.Payload;
import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;
import pl.com.bartusiak.raspiexecutor.service.CommandLineService;

@CrossOrigin(origins = "*")
@RestController
public class ExecutorController {

    private final CommandLineService commandLineService;

    public ExecutorController(CommandLineService commandLineService) {
        this.commandLineService=commandLineService;
    }

    @PostMapping(value = "/execute", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultWrapper> execute(@RequestBody Payload payload) {
        return ResponseEntity.ok(commandLineService.executePayload(payload));
    }
}
