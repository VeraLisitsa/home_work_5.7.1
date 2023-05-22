package ru.netology.geo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;


public class GeoServiceImplTests {
    GeoServiceImpl geoservice;

    @BeforeEach
    public void beforeEach() {
        geoservice = new GeoServiceImpl();
    }

    @AfterEach
    public void afterEach() {
        geoservice = null;
    }

    @ParameterizedTest
    @MethodSource("methodSource")
    public void testByIp(String ip, Location expected) {
        Location result = geoservice.byIp(ip);
        Assertions.assertEquals(expected.getCountry(), result.getCountry());
        Assertions.assertEquals(expected.getCity(), result.getCity());
        Assertions.assertEquals(expected.getStreet(), result.getStreet());
        Assertions.assertEquals(expected.getBuiling(), result.getBuiling());

    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.12.34.0", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.67.88.0", new Location("New York", Country.USA, null, 0)));

    }

    @Test
    public void testByCoordinates() {
        double latitude = 1.1;
        double longitude = 2.2;
        Class<RuntimeException> expected = RuntimeException.class;
        Assertions.assertThrows(expected, () -> geoservice.byCoordinates(latitude, longitude));
    }
}

