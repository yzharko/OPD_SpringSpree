package ru.goth.deliveryTimeCalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DGISGeocoderTest {

    private static final String TEST_ADDRESS = "Москва";
    private static final String TEST_WRONG_ADDRESS = "Бубубе";
    private static final String TEST_APIKEY = "832149c7-1ea2-4b10-a5b6-be1cdb412d83";
    private static final String TEST_WRONG_APIKEY = "ooooooo";

    @Test
    void testGetCoordinates_Success() throws Exception {

        String lat = Double.toString(DGISGeocoder.getCoordinates(TEST_ADDRESS, TEST_APIKEY)[0]);
        String lon = Double.toString(DGISGeocoder.getCoordinates(TEST_ADDRESS, TEST_APIKEY)[1]);

        assertEquals("37.617835", lat);
        assertEquals("55.7588", lon);
    }

    @Test
    void testGetCoordinates_InvalidApiResponse(){
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            DGISGeocoder.getCoordinates(TEST_ADDRESS, TEST_WRONG_APIKEY);
        });
        assertEquals("Неверный формат ответа API", thrown.getMessage());
    }

    @Test
    void testGetCoordinates_AddressNotFound() throws Exception {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            DGISGeocoder.getCoordinates(TEST_WRONG_ADDRESS, TEST_APIKEY);
        });
        assertEquals("Неверный формат ответа API", thrown.getMessage());
    }
}
