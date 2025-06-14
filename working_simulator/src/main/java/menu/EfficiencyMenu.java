package menu;

import models.Clock;
import models.Efficiency;
import models.Employee;
import service.ExcelService;
import service.HelpService;

import java.util.Scanner;

public class EfficiencyMenu {
    public static void showMenu() {
        HelpService.clearConsole();
        Clock.showTime();
        for (Employee employee: ExcelService.getStaff()) {
            employee.printInfo();
        }
        System.out.println("Введите id сотрудника из списка: ");
        long employeeId = new Scanner(System.in).nextLong();
        HelpService.clearConsole();
        for (Efficiency efficiency : ExcelService.parseEfficiencyFromExcel()) {
            if (efficiency.getEmployeeId() == employeeId) {
                efficiency.printInfo();
            }
        }
        System.out.println("Если хотите посмотреть статистику других работников - нажмите 1" +
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
