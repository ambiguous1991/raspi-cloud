package pl.com.bartusiak.raspiexecutor.service;

import pl.com.bartusiak.raspiexecutor.dto.ResultWrapper;

public interface CommandLineService {
    ResultWrapper executePayload(String command) ;
}
