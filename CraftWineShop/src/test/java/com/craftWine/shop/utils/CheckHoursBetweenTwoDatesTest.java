package com.craftWine.shop.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckHoursBetweenTwoDatesTest {

    @ParameterizedTest
    @MethodSource("getTrueTimeArguments")
    void getTrueIsTimeBetweenTwoDatesIsMoreThan23Hours(LocalDateTime time) {
        assertTrue(CheckHoursBetweenTwoDates.isTimeBetweenTwoDatesIsMoreThan23Hours(Optional.of(time)));
    }

    @ParameterizedTest
    @MethodSource("getFalseTimeArguments")
    void getFalseIsTimeBetweenTwoDatesIsMoreThan23Hours(LocalDateTime time) {
        assertFalse(CheckHoursBetweenTwoDates.isTimeBetweenTwoDatesIsMoreThan23Hours(Optional.of(time)));
    }

    static Stream<Arguments> getTrueTimeArguments() {

        return Stream.of(
                Arguments.of(LocalDateTime.of(2020, 1, 1, 12, 0)),
                Arguments.of(LocalDateTime.of(2022, 6, 4, 23, 0)),
                Arguments.of(LocalDateTime.of(1970, 1, 1, 12, 0)),
                Arguments.of(LocalDateTime.of(2023, 1, 1, 12, 0)),
                Arguments.of(LocalDateTime.of(2023, 12, 21, 12, 0))
        );

    }

    static Stream<Arguments> getFalseTimeArguments() {

        return Stream.of(
                Arguments.of(LocalDateTime.now().minusHours(20)),
                Arguments.of(LocalDateTime.now().minusHours(21)),
                Arguments.of(LocalDateTime.now().minusHours(3)),
                Arguments.of(LocalDateTime.now().minusHours(18)),
                Arguments.of(LocalDateTime.now().minusHours(22))
        );

    }


}