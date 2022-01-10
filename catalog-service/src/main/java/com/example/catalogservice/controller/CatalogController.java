package com.example.catalogservice.controller;


import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
    Environment environment;
    CatalogService catalogService;

    @Autowired
    public CatalogController(Environment environment, CatalogService catalogService) {
        this.environment = environment;
        this.catalogService = catalogService;
    }

    @GetMapping("/heath_check")
    public String status(HttpServletRequest request){
        return "It's Working Catalog Service PORT "+request.getLocalPort();
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
        Iterable<CatalogEntity> cataologList = catalogService.getAllCatalogs();
        List<ResponseCatalog> result = new ArrayList<>();

        cataologList.forEach( v -> {
            result.add(new ModelMapper().map(v,ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
