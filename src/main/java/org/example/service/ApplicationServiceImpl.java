package org.example.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duyenthai
 */
public class ApplicationServiceImpl implements ApplicationService {
    private static final List<ApplicationService> SERVICES = new ArrayList<>();

    static {
        SERVICES.add(new ApplicationServiceLoggerImpl());
        SERVICES.add(new ApplicationServiceDbImpl());
    }

    @Override
    public void process() {
        SERVICES.forEach(ApplicationService::process);
    }
}
