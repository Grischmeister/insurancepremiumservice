package grischa.premiumservice.model;

public class PremiumResponse {

    private double premium;

    public PremiumResponse(double premium) {
        this.premium = premium;
    }

    public double getPremium() { return premium; }
    public void setPremium(double premium) { this.premium = premium; }
}