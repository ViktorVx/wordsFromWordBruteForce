package org.pva.wfwbf.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.ParamsProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class TerminalService {

    private final ParamsProvider paramsProvider;

    private static String lastHealth;
    private static Date lastHealthDate;
    private static Boolean schedulerStarted = false;
    private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public void runCircleHealthCheck() {
        if (!schedulerStarted) {
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
            exec.scheduleAtFixedRate(this::healthCheck, 0, paramsProvider.getTerminalBotWakeUpTimeout(), TimeUnit.SECONDS);
            schedulerStarted = true;
        }
    }

    public void logLastHealthCheck() {
        if (lastHealthDate != null && lastHealth != null) {
            log.info(String.format("%s %s", df.format(lastHealthDate), lastHealth == null ? "FAIL" : lastHealth));
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
