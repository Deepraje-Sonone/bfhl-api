package com.deepraje.bfhl.controller;

import com.deepraje.bfhl.dto.BfhlRequestDTO;
import com.deepraje.bfhl.dto.BfhlResponseDTO;
import com.deepraje.bfhl.service.BfhlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponseDTO> processData(@RequestBody BfhlRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getData() == null) {
            throw new IllegalArgumentException("Request body must contain a non-null 'data' array.");
        }
        BfhlResponseDTO response = bfhlService.processData(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "bfhl-api",
                "user_id", "deepraje_sonone_08092004"
        ));
    }
}
