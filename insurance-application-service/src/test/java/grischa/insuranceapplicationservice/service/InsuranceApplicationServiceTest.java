package grischa.insuranceapplicationservice.service;

import grischa.insuranceapplicationservice.client.PremiumWebClient;
import grischa.insuranceapplicationservice.dto.InsuranceApplicationRequest;
import grischa.insuranceapplicationservice.dto.InsuranceApplicationResponse;
import grischa.insuranceapplicationservice.dto.PremiumRequest;
import grischa.insuranceapplicationservice.dto.PremiumResponse;
import grischa.insuranceapplicationservice.model.InsuranceApplication;
import grischa.insuranceapplicationservice.repository.InsuranceApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceApplicationServiceTest {

    private InsuranceApplicationRepository repository;
    private PremiumWebClient premiumWebClient;
    private InsuranceApplicationService service;

    @BeforeEach
    void setUp() {
        repository = mock(InsuranceApplicationRepository.class);
        premiumWebClient = mock(PremiumWebClient.class);
        service = new InsuranceApplicationService(repository, premiumWebClient);
    }

    @Test
    void testCreate() {
        // Setup Request
        InsuranceApplicationRequest request = new InsuranceApplicationRequest();
        request.setCarType("SUV");
        request.setKilometers(15000);
        request.setZipcode("80331");

        // Setup mock PremiumResponse
        PremiumResponse premiumResponse = new PremiumResponse();
        premiumResponse.setPremium(123.45);
        when(premiumWebClient.calculate(any(PremiumRequest.class))).thenReturn(premiumResponse);

        // Setup mock Repository save to return saved entity with ID
        InsuranceApplication savedApp = new InsuranceApplication();
        savedApp.setId(42L);
        savedApp.setCarType(request.getCarType());
        savedApp.setKilometers(request.getKilometers());
        savedApp.setZipcode(request.getZipcode());
        savedApp.setPremium(premiumResponse.getPremium());

        when(repository.save(any(InsuranceApplication.class))).thenReturn(savedApp);

        // Call service method
        InsuranceApplicationResponse response = service.create(request);

        // Verify interaction with PremiumWebClient
        ArgumentCaptor<PremiumRequest> premiumRequestCaptor = ArgumentCaptor.forClass(PremiumRequest.class);
        verify(premiumWebClient).calculate(premiumRequestCaptor.capture());
        PremiumRequest capturedPremiumRequest = premiumRequestCaptor.getValue();

        assertEquals(request.getCarType(), capturedPremiumRequest.getCarType());
        assertEquals(request.getKilometers(), capturedPremiumRequest.getKilometers());
        assertEquals(request.getZipcode(), capturedPremiumRequest.getZipcode());

        // Verify interaction with repository
        ArgumentCaptor<InsuranceApplication> appCaptor = ArgumentCaptor.forClass(InsuranceApplication.class);
        verify(repository).save(appCaptor.capture());
        InsuranceApplication savedEntity = appCaptor.getValue();

        assertEquals(request.getCarType(), savedEntity.getCarType());
        assertEquals(request.getKilometers(), savedEntity.getKilometers());
        assertEquals(request.getZipcode(), savedEntity.getZipcode());
        assertEquals(premiumResponse.getPremium(), savedEntity.getPremium());

        // Verify response values
        assertEquals(savedApp.getId(), response.getId());
        assertEquals(savedApp.getPremium(), response.getPremium());
    }
}
