package com.example.blogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@PropertySource({
		"classpath:application.properties",
		"classpath:error-message.properties"
})
public class BlogApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}
}
