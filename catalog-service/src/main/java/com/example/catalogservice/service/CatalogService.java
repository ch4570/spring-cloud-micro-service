package com.example.catalogservice.service;


import com.example.catalogservice.domain.CatalogEntity;

import java.util.List;

public interface CatalogService {

    List<CatalogEntity> getAllCatalogs();
}
