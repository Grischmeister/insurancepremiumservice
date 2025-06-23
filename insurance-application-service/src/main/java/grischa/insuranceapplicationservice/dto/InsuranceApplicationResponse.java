package grischa.insuranceapplicationservice.dto;

import lombok.Data;

@Data
public class InsuranceApplicationResponse {
    private Long id;
    private double premium;

    public InsuranceApplicationResponse(Long id, double premium) {
        this.id = id;
        this.premium = premium;
    }
}
