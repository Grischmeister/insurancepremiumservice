package grischa.insuranceapplicationservice.service;

import grischa.insuranceapplicationservice.client.PremiumWebClient;
import grischa.insuranceapplicationservice.dto.InsuranceApplicationRequest;
import grischa.insuranceapplicationservice.dto.InsuranceApplicationResponse;
import grischa.insuranceapplicationservice.dto.PremiumRequest;
import grischa.insuranceapplicationservice.dto.PremiumResponse;
import grischa.insuranceapplicationservice.model.InsuranceApplication;
import grischa.insuranceapplicationservice.repository.InsuranceApplicationRepository;
import org.springframework.stereotype.Service;


@Service
public class InsuranceApplicationService {

    private final InsuranceApplicationRepository repository;
    private final PremiumWebClient premiumWebClient;

    public InsuranceApplicationService(InsuranceApplicationRepository repository, PremiumWebClient premiumWebClient) {
        this.repository = repository;
        this.premiumWebClient = premiumWebClient;
    }

    public InsuranceApplicationResponse create(InsuranceApplicationRequest request) {
        var premiumRequest = new PremiumRequest();
        premiumRequest.setCarType(request.getCarType());
        premiumRequest.setKilometers(request.getKilometers());
        premiumRequest.setZipcode(request.getZipcode());

        PremiumResponse premiumResponse = premiumWebClient.calculate(premiumRequest);

        var app = new InsuranceApplication();
        app.setCarType(request.getCarType());
        app.setKilometers(request.getKilometers());
        app.setZipcode(request.getZipcode());
        app.setPremium(premiumResponse.getPremium());

        app = repository.save(app);

        return new InsuranceApplicationResponse(app.getId(), app.getPremium());
    }
}

