package org.pva.wfwbf.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.ParamsProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@AllArgsConstructor
public class TerminalBot extends TelegramLongPollingBot {

    private final ParamsProvider paramsProvider;
    private static Boolean schedulerStarted = false;
    private static String lastHealth;
    private static Date lastHealthDate;
    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Override
    public String getBotUsername() {
        return paramsProvider.getTerminalBotName();
    }

    @Override
    public String getBotToken() {
        return paramsProvider.getTerminalBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!schedulerStarted) {
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
            exec.scheduleAtFixedRate(this::healthCheck, 0, paramsProvider.getTerminalBotWakeUpTimeout(), TimeUnit.SECONDS);
            schedulerStarted = true;
        }
        try {
            if (lastHealthDate != null && lastHealth != null) {
                var message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText(String.format("%s %s", df.format(lastHealthDate), lastHealth == null ? "FAIL" : lastHealth));
                execute(message);
            }
        } catch (TelegramApiException e) {
            log.error(e.toString());
        }
    }

    private void healthCheck() {
        var responseSpec = WebClient.create()
                .get()
                .uri(String.format("%s/health", paramsProvider.getTerminalBotWakeUpUrl()))
                .retrieve();
        lastHealth = responseSpec.bodyToMono(String.class).block();
        lastHealthDate = new Date();
        log.info(lastHealth);
    }
}
