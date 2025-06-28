package com.example.urlshortenerweb.repository;

import com.example.urlshortenerweb.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {
}

