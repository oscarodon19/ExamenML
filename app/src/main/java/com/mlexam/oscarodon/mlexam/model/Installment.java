package com.mlexam.oscarodon.mlexam.model;

/**
 * Created by oscarodon on 4/9/17.
 */

public class Installment {

    private int installments;
    private double installmentsRate;
    private double discountRate;
    private int minAllowedAmount;
    private int maxAllowedAmount;
    private String recommendedMessage;
    private int instalmentAmount;
    private int totalAmount;

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public String getRecommendedMessage() {
        return recommendedMessage;
    }

    public void setRecommendedMessage(String recommendedMessage) {
        this.recommendedMessage = recommendedMessage;
    }
}
