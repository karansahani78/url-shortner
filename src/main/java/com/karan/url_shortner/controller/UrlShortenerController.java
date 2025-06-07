package com.karan.url_shortner.controller;

import com.karan.url_shortner.service.UrlShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody String originalUrl) {
        try {
            // Clean the URL (remove quotes and extra spaces if present)
            originalUrl = originalUrl.trim()
                    .replaceAll("^\"|\"$", "")
                    .replaceAll("\\s+", "");

            // Add protocol if missing
            if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
                originalUrl = "https://" + originalUrl;
            }

            String shortCode = urlShortenerService.shortenUrl(originalUrl);
            String shortLink = "http://localhost:8080/" + shortCode;

            return ResponseEntity.ok(Map.of(
                    "originalUrl", originalUrl,
                    "shortUrl", shortLink,
                    "shortCode", shortCode,
                    "message", "URL shortened successfully"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "An error occurred while shortening the URL"
            ));
        }
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        return urlShortenerService.getOriginalUrl(shortCode)
                .map(url -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create(url));
                    return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Additional endpoint to get URL info without redirecting (for testing)
    @GetMapping("/info/{shortCode}")
    public ResponseEntity<?> getUrlInfo(@PathVariable String shortCode) {
        return urlShortenerService.getOriginalUrl(shortCode)
                .map(url -> ResponseEntity.ok(Map.of(
                        "shortCode", shortCode,
                        "originalUrl", url,
                        "shortUrl", "http://localhost:8080/" + shortCode
                )))
                .orElse(ResponseEntity.status(404).body(Map.of(
                        "error", "Short code not found"
                )));
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "URL Shortener"
        ));
    }
}