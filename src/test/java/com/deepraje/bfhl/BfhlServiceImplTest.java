package com.deepraje.bfhl;

import com.deepraje.bfhl.dto.BfhlRequestDTO;
import com.deepraje.bfhl.dto.BfhlResponseDTO;
import com.deepraje.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
    }

    // ---------------------------------------------------------------- Example A
    @Test
    @DisplayName("Example A: mixed input with numbers, alphabets, and special char")
    void testExampleA() {
        BfhlRequestDTO req = new BfhlRequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponseDTO res = service.processData(req);

        assertTrue(res.isSuccess());
        assertEquals("deepraje_sonone_08092004", res.getUserId());
        assertEquals("deeprajesonone230965@acropolis.in", res.getEmail());
        assertEquals("0827CS231072", res.getRollNumber());

        assertEquals(List.of("1"),         res.getOddNumbers());
        assertEquals(List.of("334", "4"),  res.getEvenNumbers());
        assertEquals(List.of("A", "R"),    res.getAlphabets());
        assertEquals(List.of("$"),         res.getSpecialCharacters());
        assertEquals("339",                res.getSum());
        assertEquals("Ra",                 res.getConcatString());
    }

    // ---------------------------------------------------------------- Example B
    @Test
    @DisplayName("Example B: multiple numbers and special chars")
    void testExampleB() {
        BfhlRequestDTO req = new BfhlRequestDTO(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponseDTO res = service.processData(req);

        assertTrue(res.isSuccess());
        assertEquals(List.of("5"),              res.getOddNumbers());
        assertEquals(List.of("2", "4", "92"),   res.getEvenNumbers());
        assertEquals(List.of("A", "Y", "B"),    res.getAlphabets());
        assertEquals(List.of("&", "-", "*"),    res.getSpecialCharacters());
        assertEquals("103",                     res.getSum());
        assertEquals("ByA",                     res.getConcatString());
    }

    // ---------------------------------------------------------------- Example C
    @Test
    @DisplayName("Example C: only multi-char alphabet strings")
    void testExampleC() {
        BfhlRequestDTO req = new BfhlRequestDTO(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponseDTO res = service.processData(req);

        assertTrue(res.isSuccess());
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertEquals(List.of("A", "ABCD", "DOE"), res.getAlphabets());
        assertTrue(res.getSpecialCharacters().isEmpty());
        assertEquals("0",        res.getSum());
        assertEquals("EoDdCbAa", res.getConcatString());
    }

    // ---------------------------------------------------------------- Edge: empty data
    @Test
    @DisplayName("Edge case: empty data array")
    void testEmptyData() {
        BfhlRequestDTO req = new BfhlRequestDTO(Collections.emptyList());
        BfhlResponseDTO res = service.processData(req);

        assertTrue(res.isSuccess());
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertTrue(res.getAlphabets().isEmpty());
        assertTrue(res.getSpecialCharacters().isEmpty());
        assertEquals("0", res.getSum());
        assertEquals("",  res.getConcatString());
    }

    // ---------------------------------------------------------------- Edge: only numbers
    @Test
    @DisplayName("Edge case: only numeric strings")
    void testOnlyNumbers() {
        BfhlRequestDTO req = new BfhlRequestDTO(Arrays.asList("3", "6", "11"));
        BfhlResponseDTO res = service.processData(req);

        assertTrue(res.isSuccess());
        assertEquals(List.of("3", "11"), res.getOddNumbers());
        assertEquals(List.of("6"),       res.getEvenNumbers());
        assertTrue(res.getAlphabets().isEmpty());
        assertEquals("20", res.getSum());
        assertEquals("",   res.getConcatString());
    }

    // ---------------------------------------------------------------- Edge: only special chars
    @Test
    @DisplayName("Edge case: only special characters")
    void testOnlySpecialChars() {
        BfhlRequestDTO req = new BfhlRequestDTO(Arrays.asList("@", "#", "!"));
        BfhlResponseDTO res = service.processData(req);

        assertTrue(res.isSuccess());
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertTrue(res.getAlphabets().isEmpty());
        assertEquals(List.of("@", "#", "!"), res.getSpecialCharacters());
        assertEquals("0", res.getSum());
        assertEquals("",  res.getConcatString());
    }

    // ---------------------------------------------------------------- user_id format check
    @Test
    @DisplayName("Verify user_id is in correct format: fullname_ddmmyyyy")
    void testUserIdFormat() {
        BfhlRequestDTO req = new BfhlRequestDTO(List.of("1"));
        BfhlResponseDTO res = service.processData(req);
        assertEquals("deepraje_sonone_08092004", res.getUserId());
    }
}
