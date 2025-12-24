package com.spotifybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spotify Bot Provider - Spring Boot Application.
 * 
 * SMM Panel API v2 implementation for Spotify stream automation.
 * Clean Architecture: Presentation → Application → Domain → Infrastructure
 * 
 * @author RWTH MATSE Research Project
 */
@SpringBootApplication
@EnableAsync
public class SpotifyBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyBotApplication.class, args);
    }
}
