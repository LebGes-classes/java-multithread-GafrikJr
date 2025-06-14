package service;

import models.Clock;
import models.Efficiency;
import models.Task;


public class TaskService {
    public static void startDoingTheTask(Task task, int executionTime, int notWorkingHours, long employeeId) {
        ExcelService.createTaskRecord(task);
        String currentDate = Clock.getCurrentDate();
        for (int i = 0; i < (executionTime + notWorkingHours); i++) {
            if (Clock.getCurrentHour() == 16) {
                notWorkingHours /= 2;
                executionTime = 8 - notWorkingHours;
                Efficiency efficiency = new Efficiency(currentDate, employeeId, task.getId(), executionTime, notWorkingHours);
                ExcelService.createEfficiencyRecord(efficiency);
            }
            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentDate = Clock.getCurrentDate();
        }
        task.setStatus("Завершена");
        String dateOfEnding = Clock.getCurrentDate();
        if (!dateOfEnding.equals(task.getDateOfStarting())) {
            Efficiency lastEfficiency = new Efficiency(dateOfEnding, employeeId, task.getId(), 16 - Clock.getCurrentHour(), 0);
            ExcelService.createEfficiencyRecord(lastEfficiency);
        }
        ExcelService.updateTaskEntity(task.getId(), dateOfEnding);
    }

}
