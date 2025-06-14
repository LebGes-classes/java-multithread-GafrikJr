package models;

public class Clock {
    private static String date;
    private static int day;
    private static int hours;
    private static int minutes;
    private static Thread clockThread;

    static {
        date = "01.01.2025"; // TODO: Загрузить из Excel
        day = 1;             // TODO: Загрузить из Excel
        hours = 8;
        minutes = 0;
        startClock();
    }

    private static void startClock() {
        clockThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("Clock interrupted!");
                    break;
                }
                updateTime();
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    private static void updateTime() {
        minutes += 15;

        if (minutes == 60) {
            hours++;
            minutes = 0;
        }

        if (hours == 16) {
            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException e)  {
                e.printStackTrace();
            }
            nextDay();
            hours = 8;
        }
    }

    private static void nextDay() {
        day++;
        if (day < 10) {
            date = "0" + day + ".01.2025";
        } else {
            date = day + ".01.2025";
        }
    }

    public static String getCurrentTime() {
        String time = (hours < 10) ? "0" + hours : String.valueOf(hours);
        time += ":";
        time += (minutes == 0) ? "00" : minutes;
        return date + " | " + time;
    }

    public static void showTime() {
        System.out.println(getCurrentTime());
    }

    public static int getCurrentDay() {
        return day;
    }

    public static int getCurrentHour() {
        return hours;
    }

    public static String getCurrentDate() {
        return date;
    }
}
