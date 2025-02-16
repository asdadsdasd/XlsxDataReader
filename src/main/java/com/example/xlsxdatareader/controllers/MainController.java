package com.example.xlsxdatareader.controllers;

import com.example.xlsxdatareader.services.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainController {
    @Autowired
    private final MainService mainService;

    @Operation(summary = "Получить максимальное число", description = "Возвращает N-е максимальное число из xlsx файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные (например, N больше количества элементов)"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера или проблемы с файлом")
    })
    @GetMapping("/getMax")
    public long findMaxNumber(@RequestParam String filePath, @RequestParam int n) throws IOException {
        return mainService.findMaxNumberInXlsx(filePath, n);
    }
}
