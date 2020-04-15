package com.example.evoting.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElectionResultVo {

    @SerializedName("votecount")
    @Expose
    private Integer votecount;
    @SerializedName("Election_Id")
    @Expose
    private Integer electionId;
    @SerializedName("vote_data")
    @Expose
    private String voteData;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Start_Date")
    @Expose
    private String startDate;
    @SerializedName("End_Date")
    @Expose
    private String endDate;
    @SerializedName("Is_Active")
    @Expose
    private Integer isActive;
    @SerializedName("Min_Age")
    @Expose
    private Integer minAge;

    public Integer getVotecount() {
        return votecount;
    }

    public void setVotecount(Integer votecount) {
        this.votecount = votecount;
    }

    public Integer getElectionId() {
        return electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public String getVoteData() {
        return voteData;
    }

    public void setVoteData(String voteData) {
        this.voteData = voteData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

}