package com.craftWine.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true) // отключение планирования во время тестов
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class);

    }
}