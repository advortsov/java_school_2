package com.tsystems.javaschool.dto;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 26.03.2016
 */
public class ClientDTO {
    private String clientName;
    private String clientSurname;
    private String clientUserName;
    private String clientEmail;
    private int totalSumm;
    private int percentage;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public int getTotalSumm() {
        return totalSumm;
    }

    public void setTotalSumm(int totalSumm) {
        this.totalSumm = totalSumm;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
