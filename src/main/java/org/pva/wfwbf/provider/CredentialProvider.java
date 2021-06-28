package org.pva.wfwbf.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CredentialProvider {

    @Value("${telegram.bot.wfwbf.name}")
    private String botName;
    @Value("${telegram.bot.wfwbf.token}")
    private String botToken;

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }
}
