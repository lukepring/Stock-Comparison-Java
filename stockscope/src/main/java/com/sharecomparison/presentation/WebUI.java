package com.sharecomparison.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sharecomparison")
public class WebUI {

    public static void main(String[] args) {
        SpringApplication.run(WebUI.class, args);
    }
}
