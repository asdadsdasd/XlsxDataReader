package com.example.xlsxdatareader.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    public long findMaxNumberInXlsx(String filePath, int n) throws IOException {
        List<Long> numbers = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                numbers.add((long) cell.getNumericCellValue());
            }
        }

        workbook.close();
        fis.close();

        if (numbers.size() < n) {
            throw new IllegalArgumentException("Количество элементов в файле меньше числа N");
        }

        long[] numbersArray = numbers.stream()
                .mapToLong(Long::longValue)
                .toArray();

        mergeSort(numbersArray, 0, numbersArray.length - 1);

        return numbersArray[numbersArray.length - n];
    }

    private void mergeSort (long[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            merge(array, left, mid, right);
        }
    }

    private void merge(long[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        long[] leftArr = new long[n1];
        long[] rightArr = new long[n2];

        System.arraycopy(array, left, leftArr, 0, n1);
        System.arraycopy(array, mid + 1, rightArr, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                array[k++] = leftArr[i++];
            } else {
                array[k++] = rightArr[j++];
            }
        }
        while (i < n1) array[k++] = leftArr[i++];
        while (j < n2) array[k++] = rightArr[j++];
    }
}
