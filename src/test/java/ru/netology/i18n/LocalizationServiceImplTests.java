package ru.netology.i18n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;


public class LocalizationServiceImplTests {
    LocalizationServiceImpl locService;

    @BeforeEach
    public void beforeEach() {
        locService = new LocalizationServiceImpl();
    }

    @AfterEach
    public void afterEach() {
        locService = null;
    }

    @ParameterizedTest
    @MethodSource("methodSource")
    public void testLocale(Country country, String expected) {
        String result = locService.locale(country);
        Assertions.assertEquals(expected, result);

    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.BRAZIL, "Welcome"));
    }

}
