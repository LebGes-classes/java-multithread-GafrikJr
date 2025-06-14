package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelCalculatorBot extends TelegramLongPollingBot {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/result")) {
                result(chatId, messageText);
            } else {
                sendText(chatId, "Доступные команды:\n/result" +
                        "\nВводите числа (например : 1 2 43 132 2322 11) и бот возвращает вам сумму, макс.число, мин число.");
            }
        }
    }

    private void result(long chatId, String message) {
        executor.execute(() -> calculateSum(chatId, message));
        executor.execute(() -> findMax(chatId, message));
        executor.execute(() -> findMin(chatId, message));
    }

    private void calculateSum(long chatId, String message) {
        try {
            String[] parts = message.split(" ");
            int sum = Arrays.stream(parts, 1, parts.length)
                    .mapToInt(Integer::parseInt)
                    .sum();
            sendText(chatId, "Сумма: " + sum);
        } catch (Exception e) {
            sendText(chatId, "Ошибка! Пример: /result 1 2 3");
        }
    }

    private void findMax(long chatId, String message) {
        try {
            String[] parts = message.split(" ");
            int max = Arrays.stream(parts, 1, parts.length)
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElseThrow();
            sendText(chatId, "Максимум: " + max);
        } catch (Exception e) {
            sendText(chatId, "Ошибка! Пример: /result 5 10 2");
        }
    }

    private void findMin(long chatId, String message) {
        try {
            String[] parts = message.split(" ");
            int min = Arrays.stream(parts, 1, parts.length)
                    .mapToInt(Integer::parseInt)
                    .min()
                    .orElseThrow();
            sendText(chatId, "Минимум: " + min);
        } catch (Exception e) {
            sendText(chatId, "Ошибка! Пример: /result 5 10 2");
        }
    }

    private void sendText(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "ParallelNumbersBot";
    }

    @Override
    public String getBotToken() {
        return "7946698777:AAGVaDxh5kNxgF-l5nQ7NgKrAjjQpcXdYh4";
    }
}
