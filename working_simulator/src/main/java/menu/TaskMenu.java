package menu;

import models.Clock;
import models.Employee;
import service.ExcelService;
import service.HelpService;
import java.util.Scanner;

public class TaskMenu {
    public static void showMenu() {
        HelpService.clearConsole();
        Clock.showTime();
        for (Employee employee: ExcelService.getStaff()) {
            if (employee.getStatus().equals("Свободен")) {
                employee.printInfo();
            }
        }
        System.out.println("\nВведите id сотрудника из списка: ");
        long employeeId = new Scanner(System.in).nextLong();
        Employee employee = ExcelService.getEmployeeById(employeeId);
        employee.start();
        System.out.println(employee.getFio() + " начал(а) выполнять задачу.");

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        MainMenu.showMenu();
    }




}
