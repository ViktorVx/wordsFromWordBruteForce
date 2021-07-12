package org.pva.wfwbf.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.CredentialProvider;
import org.pva.wfwbf.service.BruteForceService;
import org.pva.wfwbf.util.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@AllArgsConstructor
public class BruteForceBot extends TelegramLongPollingBot {

    private final CredentialProvider credentialProvider;
    private final BruteForceService bruteForceService;

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
            var word = update.getMessage().getText();
            var words = bruteForceService.bruteForce(word);
            var answers = MessageUtils.prepareMessage(words, word);

            try {
                for (String answer : answers) {
                    var message = new SendMessage(); // Create a SendMessage object with mandatory fields
                    message.setChatId(update.getMessage().getChatId().toString());
                    message.setText(answer);
                    execute(message); // Call method to send the message
                }
            } catch (TelegramApiException e) {
                log.error(e.toString());
            }
        }
    }
}