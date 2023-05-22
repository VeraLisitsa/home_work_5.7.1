package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTests {

    @ParameterizedTest
    @MethodSource("methodSource")
    public void testSend(String ip, String expected){
        GeoService geoservice = Mockito.mock(GeoService.class);
        Mockito.when(geoservice.byIp(Mockito.anyString()))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localService = Mockito.mock(LocalizationService.class);
        Mockito.when(localService.locale(Mockito.any()))
                .thenReturn(expected);
        MessageSender messageSender = new MessageSenderImpl(geoservice, localService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String preferences = messageSender.send(headers);

        Assertions.assertEquals(preferences, expected);
    }

    public static Stream<Arguments> methodSource(){
        return Stream.of(
                Arguments.of("172.123.12.19","Добро пожаловать"),
                Arguments.of("96.44.1.1","Welcome"),
                Arguments.of(null,"Welcome"),
                Arguments.of("", "Welcome"));
    }
}
