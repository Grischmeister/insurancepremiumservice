package grischa.insuranceapplicationservice.dto;

import lombok.Data;

@Data
public class PremiumRequest {
    private int kilometers;
    private String carType;
    private String zipcode;
}
