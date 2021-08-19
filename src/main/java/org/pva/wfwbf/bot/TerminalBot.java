package org.pva.wfwbf.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.ParamsProvider;
import org.pva.wfwbf.service.AuthService;
import org.pva.wfwbf.service.TerminalService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@AllArgsConstructor
public class TerminalBot extends TelegramLongPollingBot {

    private final ParamsProvider paramsProvider;
    private final TerminalService terminalService;
    private final AuthService authService;

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
        var userId = update.getMessage().getFrom().getId();
        log.info("User ID: {}", userId);
        if (!authService.isAdmin(userId)) {
            sendMessage(update, "Я тебя не знаю! Иди отседова)!");
            return;
        }

        sendMessage(update, "Привет, хозяин!");
        terminalService.runCircleHealthCheck();
        terminalService.logLastHealthCheck();
    }

    private void sendMessage(Update update, String msg) {
        try {
            var message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(msg);
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.toString());
        }
    }
}
