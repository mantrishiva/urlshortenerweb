package com.example.urlshortenerweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shorten_ShouldReturnShortUrl() {
        String longUrl = "https://example.com";
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/shorten", 
            longUrl, 
            String.class
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().startsWith("http://short.ly/"));
    }

    @Test
    void redirect_InvalidShortUrl_ShouldReturnNotFound() {
        ResponseEntity<Void> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/notfound", 
            Void.class
        );
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}