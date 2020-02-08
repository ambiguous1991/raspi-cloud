package pl.com.bartusiak.raspiexecutor.dto;

import lombok.Data;

@Data
public class Payload {
    private String command;
    private boolean elevate;
}
