
package com.mlexam.oscarodon.mlexam.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethod {


    private String id;
    private String name;
    private String paymentTypeId;
    private String status;
    private String secureThumbnail;
    private String thumbnail;
    private String deferredCapture;
    private List<Setting> settings = null;
    private List<String> additionalInfoNeeded = null;
    private Integer minAllowedAmount;
    private Integer maxAllowedAmount;
    private Integer accreditationTime;
    private List<Object> financialInstitutions = null;
    private List<String> processingModes = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSecureThumbnail() {
        return secureThumbnail;
    }

    public void setSecureThumbnail(String secureThumbnail) {
        this.secureThumbnail = secureThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDeferredCapture() {
        return deferredCapture;
    }

    public void setDeferredCapture(String deferredCapture) {
        this.deferredCapture = deferredCapture;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public List<String> getAdditionalInfoNeeded() {
        return additionalInfoNeeded;
    }

    public void setAdditionalInfoNeeded(List<String> additionalInfoNeeded) {
        this.additionalInfoNeeded = additionalInfoNeeded;
    }

    public Integer getMinAllowedAmount() {
        return minAllowedAmount;
    }

    public void setMinAllowedAmount(Integer minAllowedAmount) {
        this.minAllowedAmount = minAllowedAmount;
    }

    public Integer getMaxAllowedAmount() {
        return maxAllowedAmount;
    }

    public void setMaxAllowedAmount(Integer maxAllowedAmount) {
        this.maxAllowedAmount = maxAllowedAmount;
    }

    public Integer getAccreditationTime() {
        return accreditationTime;
    }

    public void setAccreditationTime(Integer accreditationTime) {
        this.accreditationTime = accreditationTime;
    }

    public List<Object> getFinancialInstitutions() {
        return financialInstitutions;
    }

    public void setFinancialInstitutions(List<Object> financialInstitutions) {
        this.financialInstitutions = financialInstitutions;
    }

    public List<String> getProcessingModes() {
        return processingModes;
    }

    public void setProcessingModes(List<String> processingModes) {
        this.processingModes = processingModes;
    }

}
