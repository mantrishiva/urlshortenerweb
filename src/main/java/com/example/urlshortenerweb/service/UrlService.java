package com.example.urlshortenerweb.service;

import com.example.urlshortenerweb.entity.UrlMapping;
import com.example.urlshortenerweb.repository.UrlMappingRepository;
import com.example.urlshortenerweb.util.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UrlService {
    private static final Duration TTL = Duration.ofDays(30);

    @Autowired
    private UrlMappingRepository repo;

    @Autowired
    private StringRedisTemplate redis;

    public String shortenUrl(String longUrl) {
        long id = System.nanoTime(); // Replace with distributed ID generator in production
        String shortUrl = Base62.encode(id);

        UrlMapping mapping = new UrlMapping();
        mapping.setShortUrl(shortUrl);
        mapping.setLongUrl(longUrl);
        mapping.setCreatedAt(LocalDateTime.now());

        repo.save(mapping);
        redis.opsForValue().set(shortUrl, longUrl, TTL);

        return shortUrl;
    }

    public String resolveUrl(String shortUrl) {
        String longUrl = redis.opsForValue().get(shortUrl);
        if (longUrl != null) return longUrl;

        UrlMapping mapping = repo.findById(shortUrl).orElse(null);
        if (mapping == null || mapping.isExpired()) return null;

        return mapping.getLongUrl();
    }

}
