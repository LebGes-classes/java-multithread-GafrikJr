package menu;

import models.Clock;
import service.HelpService;

import java.util.Scanner;

public class MainMenu {
    public static void showMenu() {
        HelpService.clearConsole();
        Clock.showTime();
        System.out.println(
                "Выберите опцию:\n" +
                        "\n1) Добавить задачу" +
                        "\n2) Посмотреть информацию о сотруднике" +
                        "\n3) Посмотреть эффективность сотрудников по дням" +
                        "\n4) Закрыть программу"
        );
        int choice = new Scanner(System.in).nextInt();
        switch (choice) {
            case 1:
                TaskMenu.showMenu();
                break;
            case 2:
                EmployeeMenu.showMenu();
                break;
            case 3:
                EfficiencyMenu.showMenu();
                break;
            case 4:
                System.exit(0);
        }
    }
}
