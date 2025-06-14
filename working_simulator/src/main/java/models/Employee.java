package models;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import service.ExcelService;
import service.HelpService;
import service.TaskService;

import java.util.Random;

@Entity
@Getter
@Setter
public class Employee extends Thread {
    private long id;
    private String fio;
    private String post;
    private String status;

    public Employee(long id, String fio, String post, String status) {
        this.id = id;
        this.fio = fio;
        this.post = post;
        this.status = status;
    }

    public void run() {
        ExcelService.updateEmployeeStatus(id, "Занят");
        int executionTime = new Random().nextInt(15);
        int notWorkingHours = new Random().nextInt(10);
        String dayOfStarting = Clock.getCurrentDate();
        Task task = new Task(HelpService.getRandomTaskName(), id, executionTime, dayOfStarting);
        TaskService.startDoingTheTask(task, executionTime, notWorkingHours, id);
        ExcelService.updateEmployeeStatus(id, "Свободен");
    }

    public void printInfo() {
        System.out.println(
                "id: " + id
                + " | ФИО: " + fio
                + " | Должность: " + post
                + " | Статус занятости: " + status
        );
    }
}
