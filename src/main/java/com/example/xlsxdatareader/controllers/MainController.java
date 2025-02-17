package com.example.xlsxdatareader.controllers;

import com.example.xlsxdatareader.dto.ApiErrorResponse;
import com.example.xlsxdatareader.services.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/numbers")
@Tag(name = "Numbers API", description = "Операции с числами из XLSX файлов")
@Slf4j
@Validated
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @Operation(
            summary = "Получить N-е максимальное число из XLSX файла",
            description = "Принимает путь к файлу и номер N, возвращает N-е максимальное число"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(schema = @Schema(implementation = Long.class), examples = @ExampleObject(value = "89"))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка обработки файла",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/max")
    public ResponseEntity<Long> getNthMaxNumber(
            @Parameter(description = "Путь к XLSX файлу", example = "excels/data.xlsx")
            @RequestParam String filePath,

            @Parameter(description = "Порядковый номер максимального числа N", example = "5")
            @RequestParam @Min(1) int n
    ) {
        log.info("Получен запрос: filePath={}, n={}", filePath, n);
        return ResponseEntity.ok(mainService.findMaxNumberInXlsx(filePath, n));
    }
}
