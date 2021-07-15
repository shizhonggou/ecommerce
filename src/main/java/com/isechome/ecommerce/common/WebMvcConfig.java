package com.isechome.ecommerce.common;

import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebMvcConfig {
//    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new DateConverter());
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }
}
