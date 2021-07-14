package org.pva.wfwbf.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.CredentialProvider;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@AllArgsConstructor
public class TerminalBot extends TelegramLongPollingBot {

    private final CredentialProvider credentialProvider;
    private static Boolean schedulerStarted = false;

    @Override
    public String getBotUsername() {
        return credentialProvider.getTerminalBotName();
    }

    @Override
    public String getBotToken() {
        return credentialProvider.getTerminalBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!schedulerStarted) {
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
            exec.scheduleAtFixedRate(this::wakeUpWbf, 0, 1, TimeUnit.MINUTES);
            schedulerStarted = true;
        }
    }

    private void wakeUpWbf() {
        try {
            var message = new SendMessage();
            message.setChatId("-593702521");
            message.setText("Ок");
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.toString());
        }
    }
}
