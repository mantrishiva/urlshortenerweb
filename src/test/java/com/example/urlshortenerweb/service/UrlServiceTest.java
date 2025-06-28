package com.example.urlshortenerweb.service;

import com.example.urlshortenerweb.entity.UrlMapping;
import com.example.urlshortenerweb.repository.UrlMappingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UrlServiceTest {

    @MockBean
    private UrlMappingRepository repo;

    @Autowired
    private UrlService urlService;

    @Test
    void shortenUrl_ShouldReturnShortUrl() {
        String longUrl = "https://example.com";
        
        String result = urlService.shortenUrl(longUrl);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(repo).save(any(UrlMapping.class));
    }

    @Test
    void resolveUrl_FromDatabase_ShouldReturnLongUrl() {
        String shortUrl = "abc123";
        String longUrl = "https://example.com";
        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setCreatedAt(LocalDateTime.now());
        
        when(repo.findById(shortUrl)).thenReturn(Optional.of(mapping));
        
        String result = urlService.resolveUrl(shortUrl);
        
        assertEquals(longUrl, result);
    }

    @Test
    void resolveUrl_NotFound_ShouldReturnNull() {
        String shortUrl = "notfound";
        
        when(repo.findById(shortUrl)).thenReturn(Optional.empty());
        
        String result = urlService.resolveUrl(shortUrl);
        
        assertNull(result);
    }
}