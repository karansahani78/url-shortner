package com.karan.url_shortner.service;

import com.karan.url_shortner.model.UrlMapping;
import com.karan.url_shortner.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {
    private final UrlMappingRepository urlMappingRepository;
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SHORT_CODE_LENGTH = 6;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public String shortenUrl(String originalUrl) {
        // Validate URL format
        if (!isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL format: " + originalUrl);
        }

        String shortCode = generateRandomCode();

        // Ensure uniqueness
        while (urlMappingRepository.findByShortCode(shortCode).isPresent()) {
            shortCode = generateRandomCode();
        }

        UrlMapping urlMapping = new UrlMapping(null, originalUrl, shortCode);
        urlMappingRepository.save(urlMapping);
        return shortCode;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode)
                .map(UrlMapping::getOriginalUrl);
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        Random random = new Random();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}