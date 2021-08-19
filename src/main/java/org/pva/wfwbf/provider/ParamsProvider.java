package org.pva.wfwbf.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ParamsProvider {

    @Value("${telegram.bot.wfwbf.name}")
    private String botName;
    @Value("${telegram.bot.wfwbf.token}")
    private String botToken;
    @Value("${telegram.bot.wfw-terminal.name}")
    private String terminalBotName;
    @Value("${telegram.bot.wfw-terminal.token}")
    private String terminalBotToken;
    @Value("${telegram.bot.wfw-terminal.wakeup-url}")
    private String terminalBotWakeUpUrl;
    @Value("${telegram.bot.wfw-terminal.wakeup-timeout}")
    private Integer terminalBotWakeUpTimeout;
    @Value("${telegram.bot.wfw-terminal.admins}")
    private String terminalBotAdminsString;
    private Set<Long> terminalBotAdmins = new HashSet<>();

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getTerminalBotName() {
        return terminalBotName;
    }

    public String getTerminalBotToken() {
        return terminalBotToken;
    }

    public String getTerminalBotWakeUpUrl() {
        return terminalBotWakeUpUrl;
    }

    public Integer getTerminalBotWakeUpTimeout() {
        return terminalBotWakeUpTimeout;
    }

    public Set<Long> getTerminalBotAdmins() {
        if (terminalBotAdmins.isEmpty()) {
            terminalBotAdmins = Arrays.stream(terminalBotAdminsString.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
        }
        return terminalBotAdmins;
    }
}
