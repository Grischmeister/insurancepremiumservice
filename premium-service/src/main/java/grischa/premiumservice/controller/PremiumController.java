package grischa.premiumservice.controller;

import grischa.premiumservice.model.PremiumRequest;
import grischa.premiumservice.model.PremiumResponse;
import grischa.premiumservice.service.PostcodeRegionService;
import grischa.premiumservice.service.PremiumCalculator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
public class PremiumController {

    private final PremiumCalculator calculator;
    private final PostcodeRegionService postcodeService;

    public PremiumController(PremiumCalculator calculator, PostcodeRegionService postcodeService) {
        this.calculator = calculator;
        this.postcodeService = postcodeService;
    }

    @PostMapping
    public PremiumResponse calculatePremium(@RequestBody PremiumRequest request) {
        String bundesland = postcodeService.getBundeslandByPlz(request.getZipcode());
        double result = calculator.calculate(request.getKilometers(), request.getCarType(), bundesland);
        return new PremiumResponse(result);
    }
}
