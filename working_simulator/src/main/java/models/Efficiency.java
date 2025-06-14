package models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Efficiency {
    private String date;
    private long employeeId;
    private int taskId;
    private int workingTime;
    private int notWorkingTime;

    public Efficiency(String date, long employeeId, int taskId, int workingTime, int notWorkingTime) {
        this.date = date;
        this.employeeId = employeeId;
        this.taskId = taskId;
        this.workingTime = workingTime;
        this.notWorkingTime = notWorkingTime;
    }

    public void printInfo() {
        System.out.println(
                "Дата: " + date
                + " | id сотрудника: " + employeeId
                + " | id задачи: " + taskId
                + " | Работал " + workingTime + " часов"
                + " | Простоя " + notWorkingTime + " часов"
        );
    }
}
