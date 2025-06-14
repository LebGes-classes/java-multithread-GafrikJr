package models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import service.ExcelService;

@Entity
@Getter @Setter
public class Task {
    private int id;
    private String name;
    private long employeeId;
    private String status;
    private String dateOfStarting;
    private int executionTime;
    private String dateOfEnding;

    public Task(String name, long employeeId, int executionTime, String dateOfStarting) {
        this.id = ExcelService.getTaskId();
        this.name = name;
        this.employeeId = employeeId;
        this.status = "В процессе выполнения";
        this.executionTime = executionTime;
        this.dateOfStarting = dateOfStarting;
    }

    public Task(int id, String name, long employeeId, String status, String dateOfStarting, String dateOfEnding) {
        this.id = ExcelService.getTaskId();
        this.name = name;
        this.employeeId = employeeId;
        this.status = status;
        this.dateOfStarting = dateOfStarting;
        this.dateOfEnding = dateOfEnding;
    }

    public void printInfo() {
        System.out.println(
                "Название: " + name
                + " | id сотрудника: " + employeeId
                + " | Статус: " + status
                + " | Дата начала: " + dateOfStarting
                + " | Дата завершения: " + dateOfEnding
        );
    }
}
