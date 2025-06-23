package grischa.frontendui.controller;

import grischa.frontendui.dto.InsuranceApplicationRequest;
import grischa.frontendui.dto.InsuranceApplicationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class UIController {

    private final WebClient webClient;

    public UIController(@Value("${application-service.url}") String applicationServiceUrl) {
        System.out.println("Using base URL: " + applicationServiceUrl);
        this.webClient = WebClient.builder()
                .baseUrl(applicationServiceUrl)
                .build();
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("applicationRequest", new InsuranceApplicationRequest());
        return "form";
    }

    @PostMapping("/calculate")
    public String calculatePremium(@ModelAttribute InsuranceApplicationRequest request, Model model) {
        try {
            InsuranceApplicationResponse response = webClient.post()
                    .uri("/application")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(InsuranceApplicationResponse.class)
                    .block();

            model.addAttribute("result", response.getPremium());
        } catch (Exception e) {
            model.addAttribute("error", "Fehler bei der Anfrage: " + e.getMessage());
        }

        model.addAttribute("applicationRequest", request);

        return "form";
    }
}