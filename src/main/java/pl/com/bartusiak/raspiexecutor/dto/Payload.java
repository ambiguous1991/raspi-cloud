package pl.com.bartusiak.raspiexecutor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payload {
    private String command;
    private boolean elevate;
}
