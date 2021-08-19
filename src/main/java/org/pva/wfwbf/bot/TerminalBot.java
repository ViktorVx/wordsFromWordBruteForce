package org.pva.wfwbf.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.ParamsProvider;
import org.pva.wfwbf.service.TerminalService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@AllArgsConstructor
public class TerminalBot extends TelegramLongPollingBot {

    private final TerminalService terminalService;
    private final ParamsProvider paramsProvider;

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
        terminalService.runCircleHealthCheck();
    }
}
