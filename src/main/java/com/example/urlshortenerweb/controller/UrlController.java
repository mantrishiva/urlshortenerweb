package com.example.urlshortenerweb.controller;

import com.example.urlshortenerweb.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody String longUrl) {
        String shortUrl = urlService.shortenUrl(longUrl);
        return ResponseEntity.ok("http://short.ly/" + shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        String longUrl = urlService.resolveUrl(shortUrl);
        if (longUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(java.net.URI.create(longUrl)).build();
    }
}
