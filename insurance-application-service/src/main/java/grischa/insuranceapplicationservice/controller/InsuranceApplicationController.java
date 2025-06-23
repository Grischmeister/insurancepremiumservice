package grischa.insuranceapplicationservice.controller;

import grischa.insuranceapplicationservice.dto.InsuranceApplicationRequest;
import grischa.insuranceapplicationservice.dto.InsuranceApplicationResponse;
import grischa.insuranceapplicationservice.service.InsuranceApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class InsuranceApplicationController {

    private final InsuranceApplicationService service;

    public InsuranceApplicationController(InsuranceApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public InsuranceApplicationResponse submit(@RequestBody InsuranceApplicationRequest request) {
        return service.create(request);
    }
}