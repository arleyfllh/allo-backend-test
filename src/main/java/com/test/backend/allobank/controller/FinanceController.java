package com.test.backend.allobank.controller;

import com.test.backend.allobank.service.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    @Autowired
    private DataStore dataStore;

    @GetMapping("/data/{resourceType}")
    public ResponseEntity<List<Map<String, Object>>> getData(@PathVariable String resourceType) {
        List<Map<String, Object>> data = dataStore.getData(resourceType);
        if (data == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(data);
    }
}
