package pl.com.bartusiak.raspiexecutor.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultWrapper {
    private String command;
    private String result;
    private long tta;
    private boolean exception;
}