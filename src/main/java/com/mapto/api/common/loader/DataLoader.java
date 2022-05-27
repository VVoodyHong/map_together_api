package com.mapto.api.common.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements ApplicationRunner {

    @Value("${spring.profiles.active}")
    private String profileActivated;

    @Override
    public void run(ApplicationArguments args) {
        log.info("\nApplication Started\nActive profile:: {}", profileActivated);
    }
}
