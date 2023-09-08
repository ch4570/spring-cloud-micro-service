package com.example.catalogservice.controller;

import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;
    private final ModelMapper mapper;


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on Port %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        List<ResponseCatalog> catalogList = catalogService.getAllCatalogs()
                .stream()
                .map(catalog -> mapper.map(catalog, ResponseCatalog.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(catalogList);
    }
}
