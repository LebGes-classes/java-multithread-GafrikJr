package service;

import models.Efficiency;
import models.Employee;
import models.Task;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ExcelService {
    private static final File EXCEL_FILE = new File("C://Users/Тимур/Documents/GitHub/multithreading/DataBase.xlsx");

    public static <T> List<T> parseFromExcel(File excelFile, int sheetIndex, Function<Row, T> rowParser) {
        List<T> list = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int i = 1;
            while (i <= sheet.getLastRowNum() && (sheet.getRow(i).getCell(0).getCellType() != null) && (sheet.getRow(i).getCell(0).getCellType() != CellType.STRING)) {
                Row row = sheet.getRow(i);
                T item = rowParser.apply(row); // функциональный интерфейс для перехода от строки из Excel к объекту типа T
                list.add(item);
                i++;
            }
            workbook.close();
            return list;
        }
        catch (IOException | InvalidFormatException e) {
            throw new RuntimeException("Ошибка при чтении Excel-файла: " + excelFile.getAbsolutePath() + e);
        }

    }

    public static boolean updateEmployeeStatus(long employeeId, String newStatus) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell idCell = row.getCell(0);
                if (idCell != null && idCell.getCellType() == CellType.NUMERIC
                        && (long) idCell.getNumericCellValue() == employeeId) {

                    Cell statusCell = row.getCell(3);
                    if (statusCell == null) {
                        statusCell = row.createCell(3);
                    }
                    statusCell.setCellValue(newStatus);

                    try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE)) {
                        workbook.write(fos);
                    }
                    return true;
                }
            }
            workbook.close();
            return false; // Сотрудник не найден

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createTaskRecord(Task task) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(1);

            int newRowNum = sheet.getLastRowNum() + 1;
            Row newRow = sheet.createRow(newRowNum);

            newRow.createCell(0).setCellValue(task.getId());
            newRow.createCell(1).setCellValue(task.getName());
            newRow.createCell(2).setCellValue(task.getEmployeeId());
            newRow.createCell(3).setCellValue(task.getStatus());
            newRow.createCell(4).setCellValue(task.getDateOfStarting());
            newRow.createCell(5).setCellValue("Не завершена");

            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE)) {
                workbook.write(fos);
            }
            workbook.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateTaskEntity(int taskId, String dayOfEnding) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE);
             Workbook workbook = WorkbookFactory.create(fis))
        {
            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {
                Cell idCell = row.getCell(0);
                if (idCell != null && idCell.getCellType() == CellType.NUMERIC
                        && (int) idCell.getNumericCellValue() == taskId)
                {

                    Cell statusCell = row.getCell(3);
                    statusCell.setCellValue("Завершена");

                    Cell dayOfEndingCell = row.getCell(5);
                    dayOfEndingCell.setCellValue(dayOfEnding);

                    try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE)) {
                        workbook.write(fos);
                    }
                    return true;
                }
            }
            workbook.close();
            return false; // Задача не найдена
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean createEfficiencyRecord(Efficiency efficiency) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(2);

            int newRowNum = sheet.getLastRowNum() + 1;
            Row newRow = sheet.createRow(newRowNum);

            newRow.createCell(0).setCellValue(efficiency.getDate());
            newRow.createCell(1).setCellValue(efficiency.getEmployeeId());
            newRow.createCell(2).setCellValue(efficiency.getTaskId());
            newRow.createCell(3).setCellValue(efficiency.getWorkingTime());
            newRow.createCell(4).setCellValue(efficiency.getNotWorkingTime());

            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE)) {
                workbook.write(fos);
            }
            workbook.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Employee> getStaff() {
        return parseFromExcel(
                EXCEL_FILE,
                0,
                row -> new Employee(
                        (long) row.getCell(0).getNumericCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue()
                )
        );
    }

    public static List<Task> getEmployeeTasks(long employeeId) {
        List<Task> allTasks = parseFromExcel(
                EXCEL_FILE,
                1,
                row -> new Task(
                        (int) row.getCell(0).getNumericCellValue(),
                        row.getCell(1).getStringCellValue(),
                        (long) row.getCell(2).getNumericCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(5).getStringCellValue()
                )
        );
        List<Task> employeeTasks = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getEmployeeId() == employeeId) {
                employeeTasks.add(task);
            }
        }
        return employeeTasks;
    }

    public static List<Efficiency> parseEfficiencyFromExcel() {
        List<Efficiency> result = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(EXCEL_FILE)) {
            Sheet sheet = workbook.getSheetAt(2);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                result.add(new Efficiency(
                        row.getCell(0).getStringCellValue(),
                        (long) row.getCell(1).getNumericCellValue(),
                        (int) row.getCell(2).getNumericCellValue(),
                        (int) row.getCell(3).getNumericCellValue(),
                        (int) row.getCell(4).getNumericCellValue()
                ));
            }
            workbook.close();
        } catch (Exception e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return result;
    }

    public static Employee getEmployeeById(long employeeId) {
        Employee theEmployee = null;
        for (Employee employee : getStaff()) {
            if (employee.getId() == employeeId) {
                theEmployee = employee;
            }
        }
        return theEmployee;
    }

    public static int getTaskId() {
        try {
            return WorkbookFactory.create(EXCEL_FILE).getSheetAt(1).getLastRowNum() + 1;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
