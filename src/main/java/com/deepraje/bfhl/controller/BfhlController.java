package com.deepraje.bfhl.controller;

import com.deepraje.bfhl.dto.BfhlRequestDTO;
import com.deepraje.bfhl.dto.BfhlResponseDTO;
import com.deepraje.bfhl.service.BfhlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    // Constructor injection (best practice over @Autowired on field)
    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * POST /bfhl
     * Accepts a JSON body with a "data" array and returns the processed response.
     */
    @PostMapping
    public ResponseEntity<BfhlResponseDTO> processData(@RequestBody BfhlRequestDTO requestDTO) {

        if (requestDTO == null || requestDTO.getData() == null) {
            throw new IllegalArgumentException("Request body must contain a non-null 'data' array.");
        }

        BfhlResponseDTO response = bfhlService.processData(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
