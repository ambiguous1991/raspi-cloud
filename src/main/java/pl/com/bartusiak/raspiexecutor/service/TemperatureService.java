package pl.com.bartusiak.raspiexecutor.service;

import java.util.List;

public interface TemperatureService {
    void measureTemp();
    List<Double> getMeasures();
}
