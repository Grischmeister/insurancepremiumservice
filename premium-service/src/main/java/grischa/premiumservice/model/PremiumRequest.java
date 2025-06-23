package grischa.premiumservice.model;

public class PremiumRequest {
    private int kilometers;
    private String carType;
    private String zipcode;

    // Getters and setters
    public int getKilometers() { return kilometers; }
    public void setKilometers(int kilometers) { this.kilometers = kilometers; }
    public String getCarType() { return carType; }
    public void setCarType(String carType) { this.carType = carType; }
    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
}