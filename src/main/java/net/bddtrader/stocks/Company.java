package net.bddtrader.stocks;

import java.util.ArrayList;

public class Company {

    private String symbol;
    private String companyName;
    private String exchange;
    private String industry;
    private String website;
    private String description;
    private String ceo;
    private String securityName;
    private String issueType;
    private String sector;
    private float primarySicCode;
    private float employees;
    ArrayList <String> tags = new ArrayList<>();
    private String address;
    private String address2 = null;
    private String state;
    private String city;
    private String zip;
    private String country;
    private String phone;

    // Getter Methods

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public String getIndustry() {
        return industry;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public String getCeo() {
        return ceo;
    }

    public String getSecurityName() {
        return securityName;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getSector() {
        return sector;
    }

    public float getPrimarySicCode() {
        return primarySicCode;
    }

    public float getEmployees() {
        return employees;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    // Setter Methods

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setPrimarySicCode(float primarySicCode) {
        this.primarySicCode = primarySicCode;
    }

    public void setEmployees(float employees) {
        this.employees = employees;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

}
