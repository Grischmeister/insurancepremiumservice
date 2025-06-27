package grischa.premiumservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PostcodeRegionServiceTest {

    private PostcodeRegionService service;

    @BeforeEach
    void setup() {
        String csvContent = """
            POSTLEITZAHL,REGION1
            10115,BERLIN
            80331,BAYERN
            """;

        InputStream testStream = new ByteArrayInputStream(csvContent.getBytes());
        service = new PostcodeRegionService(testStream);
        service.loadCSV();
    }

    @Test
    void shouldReturnCorrectRegionForKnownPlz() {
        assertEquals("BERLIN", service.getBundeslandByPlz("10115"));
        assertEquals("BAYERN", service.getBundeslandByPlz("80331"));
    }

    @Test
    void shouldReturnUnknownForUnknownPlz() {
        assertEquals("UNKNOWN", service.getBundeslandByPlz("99999"));
    }
}