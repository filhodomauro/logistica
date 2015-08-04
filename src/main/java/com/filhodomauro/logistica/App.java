package com.filhodomauro.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author maurofilho
 *
 */
@SpringBootApplication
@EnableCaching
public class App {

	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
	
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("byMapaPonto");
	}
}
