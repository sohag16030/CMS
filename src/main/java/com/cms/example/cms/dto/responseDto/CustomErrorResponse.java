package com.cms.example.cms.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime localDateTime = LocalDateTime.now();
    private HttpStatus status;
    private String error;
    private String path;

    public CustomErrorResponse(HttpStatus httpStatus, String forbidden, String path) {
        this.status = httpStatus;
        this.error = forbidden;
        this.path = path;
    }
}
