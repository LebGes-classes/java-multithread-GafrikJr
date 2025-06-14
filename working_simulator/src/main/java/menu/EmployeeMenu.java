package menu;

import models.Clock;
import models.Efficiency;
import models.Employee;
import models.Task;
import service.ExcelService;
import service.HelpService;

import java.util.Scanner;

public class EmployeeMenu {
    public static void showMenu() {
        HelpService.clearConsole();
        Clock.showTime();
        for (Employee employee: ExcelService.getStaff()) {
            employee.printInfo();
        }
        System.out.println("Введите id сотрудника из списка: ");
        long employeeId = new Scanner(System.in).nextLong();
        HelpService.clearConsole();
        for (Task task : ExcelService.getEmployeeTasks(employeeId)) {
            task.printInfo();
        }
        System.out.println("\nЕсли хотите посмотреть задачи других работников - нажмите 1" +
                "\nЕсли хотите вернуться назад - нажмите 2");
        int choice = new Scanner(System.in).nextInt();
        switch (choice) {
            case 1:
                showMenu();
                break;
            case 2:
                MainMenu.showMenu();
                break;
        }
    }
}
