package com.deepraje.bfhl.service.impl;

import com.deepraje.bfhl.dto.BfhlRequestDTO;
import com.deepraje.bfhl.dto.BfhlResponseDTO;
import com.deepraje.bfhl.service.BfhlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    // ---- Hardcoded user details ----
    private static final String USER_ID     = "deepraje_sonone_08092004";
    private static final String EMAIL       = "deeprajesonone230965@acropolis.in";
    private static final String ROLL_NUMBER = "0827CS231072";

    @Override
    public BfhlResponseDTO processData(BfhlRequestDTO requestDTO) {

        List<String> data            = requestDTO.getData();
        List<String> oddNumbers      = new ArrayList<>();
        List<String> evenNumbers     = new ArrayList<>();
        List<String> alphabets       = new ArrayList<>();
        List<String> specialChars    = new ArrayList<>();
        long         sumOfNumbers    = 0;

        // ---- Categorise every element ----
        for (String element : data) {

            if (isNumber(element)) {
                long num = Long.parseLong(element);
                sumOfNumbers += num;
                if (num % 2 == 0) {
                    evenNumbers.add(element);
                } else {
                    oddNumbers.add(element);
                }

            } else if (isAlphabet(element)) {
                // Each element could be a multi-char string like "ABCD" or "DOE"
                alphabets.add(element.toUpperCase());

            } else {
                // Anything that is not purely numeric and not purely alphabetic
                specialChars.add(element);
            }
        }

        // ---- Build concat_string ----
        // Collect every individual alphabetic character from the input (preserve order)
        // then reverse the full sequence and apply alternating caps (index 0 = uppercase)
        String concatString = buildConcatString(data);

        return BfhlResponseDTO.builder()
                .isSuccess(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(sumOfNumbers))
                .concatString(concatString)
                .build();
    }

    // ------------------------------------------------------------------ helpers

    /**
     * Returns true when the entire string represents an integer number
     * (handles leading minus sign too, just in case).
     */
    private boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        int start = (s.charAt(0) == '-') ? 1 : 0;
        if (start == s.length()) return false;
        for (int i = start; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Returns true when EVERY character in the string is an ASCII letter.
     * Handles multi-char strings like "ABCD".
     */
    private boolean isAlphabet(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Builds the concat_string as described in the problem:
     *  1. Collect all individual alphabetic characters from every element (in order).
     *  2. Reverse the collected sequence.
     *  3. Apply alternating caps: index-0 → uppercase, index-1 → lowercase, …
     *
     * Example: input ["a","y","b"]  → chars = [a, y, b]
     *          reversed             = [b, y, a]
     *          alternating caps     = "ByA"
     *
     * Example: input ["A","ABCD","DOE"] → chars = [A, A, B, C, D, D, O, E]
     *          reversed                 = [E, O, D, D, C, B, A, A]
     *          alternating caps         = "EoDdCbAa"
     */
    private String buildConcatString(List<String> data) {
        // Step 1 – collect all alpha chars in original order
        StringBuilder allChars = new StringBuilder();
        for (String element : data) {
            for (char c : element.toCharArray()) {
                if (Character.isLetter(c)) {
                    allChars.append(c);
                }
            }
        }

        // Step 2 – reverse
        String reversed = allChars.reverse().toString();

        // Step 3 – alternating caps
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }
}
