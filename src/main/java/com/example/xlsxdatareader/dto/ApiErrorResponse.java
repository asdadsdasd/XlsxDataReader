package com.example.xlsxdatareader.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Ошибка API")
public class ApiErrorResponse {
    @Schema(description = "Сообщение об ошибке", example = "Некорректные входные данные")
    private String message;
}
