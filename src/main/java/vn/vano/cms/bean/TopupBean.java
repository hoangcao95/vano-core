package vn.vano.cms.bean;

public class TopupBean {
    private String msisdn;
    private String serviceCode;
    private String topupResult;
    private String createdDate;
    private String amount;
    private String note;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getTopupResult() {
        return topupResult;
    }

    public void setTopupResult(String topupResult) {
        this.topupResult = topupResult;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
