package com.white.meta.utils;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author: tmind
 * @Date: 2024/9/19 17:30
 * @Description:
 */
public class ExcelCalculator {

    public static void main(String[] args) {
        String filePath = "D:\\excel.xlsx"; // Excel文件路径
        String sheetName = "Sheet1"; // 工作表名称

        // 指定多个单元格坐标
        List<Integer[]> cellCoordinates = new ArrayList<>();
        cellCoordinates.add(new Integer[]{0, 0}); // 第1行第1列
        cellCoordinates.add(new Integer[]{0, 1}); // 第1行第2列
        cellCoordinates.add(new Integer[]{1, 0}); // 第2行第1列
        try {
            // 读取Excel文件并执行运算
            Map<List<Integer[]>, Double> results = performOperations(filePath, sheetName, cellCoordinates, "add");
            // 输出结果
            printResults(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Excel文件并执行指定的数学运算
     *
     * @param filePath 文件路径
     * @param sheetName 工作表名称
     * @param cellCoordinates 单元格坐标列表
     * @param operation 运算列表（"add", "subtract", "multiply", "divide"）
     * @return 运算结果（Map<Coordinate, Result>）
     * @throws IOException 如果读取文件失败
     */
    public static Map<List<Integer[]>, Double> performOperations(String filePath, String sheetName,
                                                           List<Integer[]> cellCoordinates, String operation) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            List<Double> values = new ArrayList<>();
            for (Integer[] coordinate : cellCoordinates) {
                int row = coordinate[0];
                int col = coordinate[1];

                Row r = sheet.getRow(row);
                if (r == null) {
                    throw new IllegalArgumentException("Row not found: " + row);
                }

                Cell c = r.getCell(col, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (c == null) {
                    throw new IllegalArgumentException("Cell not found: (" + row + ", " + col + ")");
                }

                double value = getCellValue(c);
                values.add(value);
            }

            // 执行指定的运算
            double result = calculate(values, operation);

            Map<List<Integer[]>, Double> resultMap = new HashMap<>();
            // key是所有列的列及值
            resultMap.put(cellCoordinates, result); // 结果与第一个单元格关联，仅为示例
            return resultMap;
        }
    }

    /**
     * 根据指定的运算符列表计算数值列表的结果
     *
     * @param values 数值列表
     * @param operation 运算符列表
     * @return 计算结果
     */
    private static double calculate(List<Double> values, String operation) {
        double total = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            switch (operation) {
                case "add":
                    total += values.get(i);
                    break;
                case "subtract":
                    total -= values.get(i);
                    break;
                case "multiply":
                    total *= values.get(i);
                    break;
                case "divide":
                    total /= values.get(i);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operation: " + operation);
            }
        }
        return total;
    }

    /**
     * 获取单元格的值
     *
     * @param cell 单元格对象
     * @return 单元格的值
     */
    private static double getCellValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }

    /**
     * 输出结果
     *
     * @param results 运算结果（Map<Row, Map<Column, Double>>）
     */
    private static void printResults(Map<List<Integer[]>, Double> results) {
        System.out.println("运算结果：");
        for (Map.Entry<List<Integer[]>, Double> entry : results.entrySet()) {
            List<Integer[]> coordinates = entry.getKey();
            double result = entry.getValue();
            System.out.println("  Row " + JSON.toJSONString(coordinates) + "Result " + ": " + result);
        }
    }
}
