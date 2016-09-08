package com.Polodz;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.Polodz.View.MainWindow;
import com.Polodz.controller.MainController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@Configuration
@ImportResource("file:src/Grown-mainContext.xml")
public class GrownApplication {
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(GrownApplication.class)
                .headless(false)
                .web(true)
                .run(args);
    }
    
}	
