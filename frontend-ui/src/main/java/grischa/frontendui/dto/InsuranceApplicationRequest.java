package grischa.frontendui.dto;

import lombok.Data;

@Data
public class InsuranceApplicationRequest {
    private int kilometers;
    private String zipcode;
    private String carType;
}
