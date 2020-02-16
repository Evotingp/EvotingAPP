package com.example.evoting.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedPostResultVo {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("Cid")
    @Expose
    private Integer cid;
    @SerializedName("Cname")
    @Expose
    private String cname;
    @SerializedName("Cemail")
    @Expose
    private String cemail;
    @SerializedName("Cph")
    @Expose
    private String cph;
    @SerializedName("Caddress")
    @Expose
    private String caddress;
    @SerializedName("Ccity")
    @Expose
    private String ccity;
    @SerializedName("Cstate")
    @Expose
    private String cstate;
    @SerializedName("Cpassword")
    @Expose
    private String cpassword;
    @SerializedName("Cdob")
    @Expose
    private String cdob;
    @SerializedName("Cstatus")
    @Expose
    private String cstatus;
    @SerializedName("Cphoto")
    @Expose
    private String cphoto;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String getCcity() {
        return ccity;
    }

    public void setCcity(String ccity) {
        this.ccity = ccity;
    }

    public String getCstate() {
        return cstate;
    }

    public void setCstate(String cstate) {
        this.cstate = cstate;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getCdob() {
        return cdob;
    }

    public void setCdob(String cdob) {
        this.cdob = cdob;
    }

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

    public String getCphoto() {
        return cphoto;
    }

    public void setCphoto(String cphoto) {
        this.cphoto = cphoto;
    }


}
