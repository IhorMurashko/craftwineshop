package com.craftWine.shop.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SwitchCaseToCapitalizeTest {

    @ParameterizedTest
    @MethodSource("getWordForSwitchCaseToCapitalize")
    void switchCaseToCapitalize(String word) {

        Assertions.assertEquals(SwitchCaseToCapitalize.switchCaseToCapitalize(word), "Ihor");


    }


    static Stream<Arguments> getWordForSwitchCaseToCapitalize() {
        return Stream.of(
                Arguments.of("ihor"),
                Arguments.of("IHOR"),
                Arguments.of("iHOR"),
                Arguments.of("Ihor"),
                Arguments.of("IhOR"),
                Arguments.of("iHoR")

        );
    }
}