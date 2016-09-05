package com.Polodz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.SpringApplicationContextLoader;

@SuppressWarnings("deprecation")
public class CustomSpringApplicationContextLoader extends SpringApplicationContextLoader {

    @Override
    protected SpringApplication getSpringApplication() {
        return new SpringApplicationBuilder().headless(false).build();
    }

}
