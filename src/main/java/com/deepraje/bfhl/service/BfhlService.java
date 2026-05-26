package com.deepraje.bfhl.service;

import com.deepraje.bfhl.dto.BfhlRequestDTO;
import com.deepraje.bfhl.dto.BfhlResponseDTO;

public interface BfhlService {

    /**
     * Processes the input data array and returns categorized results.
     *
     * @param requestDTO the request containing the data array
     * @return BfhlResponseDTO with all processed fields
     */
    BfhlResponseDTO processData(BfhlRequestDTO requestDTO);
}
