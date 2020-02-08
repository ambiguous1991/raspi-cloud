package pl.com.bartusiak.raspiexecutor.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultWrapper {
    private String command;
    private List<String> result;
    private long tta;
    private boolean exception;
}