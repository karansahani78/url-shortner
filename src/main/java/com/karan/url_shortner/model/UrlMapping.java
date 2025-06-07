package com.karan.url_shortner.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "url_mapping")
@Data
public class UrlMapping {
    public Long getId() {
        return id;
    }

    public UrlMapping(Long id, String originalUrl, String shortCode) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UrlMapping() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originalUrl;
    @Column(nullable = false, unique = true)
    private String shortCode;
}
