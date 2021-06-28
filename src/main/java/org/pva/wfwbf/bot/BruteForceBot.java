package org.pva.wfwbf.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.CredentialProvider;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@AllArgsConstructor
public class BruteForceBot extends TelegramLongPollingBot {

    private final CredentialProvider credentialProvider;

    @Override
    public String getBotUsername() {
        return credentialProvider.getBotName();
    }

    @Override
    public String getBotToken() {
        return credentialProvider.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update != null && update.getMessage() != null) {
            log.info(update.getMessage().getText());
        }
    }
}