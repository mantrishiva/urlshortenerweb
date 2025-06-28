package com.example.urlshortenerweb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UrlMapping {
    @Id
    private String shortUrl;
    private String longUrl;
    private LocalDateTime createdAt;
    public boolean isExpired() {
        return createdAt.plusDays(30).isBefore(LocalDateTime.now());
    }

}
