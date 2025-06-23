package grischa.insuranceapplicationservice.client;

import grischa.insuranceapplicationservice.dto.PremiumRequest;
import grischa.insuranceapplicationservice.dto.PremiumResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PremiumWebClient {

    private final WebClient webClient;

    public PremiumWebClient(@Value("${premium-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public PremiumResponse calculate(PremiumRequest request) {
        // synchroner Aufruf mit block(), in realen Apps evtl. reaktiv arbeiten
        Mono<PremiumResponse> responseMono = webClient.post()
                .uri("/calculate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PremiumResponse.class);

        return responseMono.block();
    }
}
