package model;

public class InterestRule {
    private final String date;
    private final String ruleId;
    private final double interestRate;

    public InterestRule(String date, String ruleId, double interestRate) {
        this.date = date;
        this.ruleId = ruleId;
        this.interestRate = interestRate;
    }

    public String getDate() {
        return date;
    }

    public String getRuleId() {
        return ruleId;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
