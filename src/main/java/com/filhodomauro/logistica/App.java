package com.filhodomauro.logistica;

import java.util.Arrays;

import  org.springframework.cache.Cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author maurofilho
 *
 */
@SpringBootApplication
public class App {

	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
	
	@Bean
	public CacheManager cacheManager() {

		Cache cache = new ConcurrentMapCache("byMapaPonto");

		SimpleCacheManager manager = new SimpleCacheManager();
		manager.setCaches(Arrays.asList(cache));

		return manager;
	}
}
