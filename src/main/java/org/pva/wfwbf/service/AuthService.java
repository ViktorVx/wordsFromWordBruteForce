package org.pva.wfwbf.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pva.wfwbf.provider.ParamsProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final ParamsProvider paramsProvider;

    public boolean isAdmin(Long id) {
        if (paramsProvider.getTerminalBotAdmins() == null)
            return false;
        return paramsProvider.getTerminalBotAdmins().contains(id);
    }
}
