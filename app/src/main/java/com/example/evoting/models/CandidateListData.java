package com.example.evoting.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CandidateListData {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Election_Id")
    @Expose
    private Integer electionId;
    @SerializedName("Candidate_Id")
    @Expose
    private Integer candidateId;
    @SerializedName("Is_Approve")
    @Expose
    private Integer isApprove;
    @SerializedName("First_Name")
    @Expose
    private String firstName;
    @SerializedName("Middle_Name")
    @Expose
    private String middleName;
    @SerializedName("Last_Name")
    @Expose
    private String lastName;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Front_Id")
    @Expose
    private String frontId;
    @SerializedName("Back_Id")
    @Expose
    private String backId;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("ZipCode")
    @Expose
    private Integer zipCode;
    @SerializedName("Front_Address")
    @Expose
    private String frontAddress;
    @SerializedName("Back_Address")
    @Expose
    private String backAddress;
    @SerializedName("Party_Name")
    @Expose
    private String partyName;
    @SerializedName("Party_Logo")
    @Expose
    private String partyLogo;
    @SerializedName("Is_DocApprove")
    @Expose
    private Integer isDocApprove;
    @SerializedName("Cphoto")
    @Expose
    private String cphoto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Integer isApprove) {
        this.isApprove = isApprove;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getFrontId() {
        return frontId;
    }

    public void setFrontId(String frontId) {
        this.frontId = frontId;
    }

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(String frontAddress) {
        this.frontAddress = frontAddress;
    }

    public String getBackAddress() {
        return backAddress;
    }

    public void setBackAddress(String backAddress) {
        this.backAddress = backAddress;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyLogo() {
        return partyLogo;
    }

    public void setPartyLogo(String partyLogo) {
        this.partyLogo = partyLogo;
    }

    public Integer getIsDocApprove() {
        return isDocApprove;
    }

    public void setIsDocApprove(Integer isDocApprove) {
        this.isDocApprove = isDocApprove;
    }

    public String getCphoto() {
        return cphoto;
    }

    public void setCphoto(String cphoto) {
        this.cphoto = cphoto;
    }
}
