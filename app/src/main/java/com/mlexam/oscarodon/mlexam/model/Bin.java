
package com.mlexam.oscarodon.mlexam.model;


public class Bin {

    private String pattern;
    private Object exclusionPattern;
    private String installmentsPattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Object getExclusionPattern() {
        return exclusionPattern;
    }

    public void setExclusionPattern(Object exclusionPattern) {
        this.exclusionPattern = exclusionPattern;
    }

    public String getInstallmentsPattern() {
        return installmentsPattern;
    }

    public void setInstallmentsPattern(String installmentsPattern) {
        this.installmentsPattern = installmentsPattern;
    }

}
