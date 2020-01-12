
package com.gbsnowday.server.model.closings;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClosingRecord implements Serializable
{

    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("comments_line1")
    @Expose
    private String commentsLine1;
    @SerializedName("comments_line2")
    @Expose
    private String commentsLine2;
    @SerializedName("comments_line3")
    @Expose
    private String commentsLine3;
    @SerializedName("county_name1")
    @Expose
    private String countyName1;
    @SerializedName("expiration")
    @Expose
    private String expiration;
    @SerializedName("forced_category_name")
    @Expose
    private String forcedCategoryName;
    @SerializedName("forced_county_name")
    @Expose
    private String forcedCountyName;
    @SerializedName("forced_organization_name")
    @Expose
    private String forcedOrganizationName;
    @SerializedName("forced_status_name")
    @Expose
    private String forcedStatusName;
    @SerializedName("organization_homepage")
    @Expose
    private String organizationHomepage;
    @SerializedName("rec_id")
    @Expose
    private String recId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    private final static long serialVersionUID = -7122863387008016082L;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCommentsLine1() {
        return commentsLine1;
    }

    public void setCommentsLine1(String commentsLine1) {
        this.commentsLine1 = commentsLine1;
    }

    public String getCommentsLine2() {
        return commentsLine2;
    }

    public void setCommentsLine2(String commentsLine2) {
        this.commentsLine2 = commentsLine2;
    }

    public String getCommentsLine3() {
        return commentsLine3;
    }

    public void setCommentsLine3(String commentsLine3) {
        this.commentsLine3 = commentsLine3;
    }

    public String getCountyName1() {
        return countyName1;
    }

    public void setCountyName1(String countyName1) {
        this.countyName1 = countyName1;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getForcedCategoryName() {
        return forcedCategoryName;
    }

    public void setForcedCategoryName(String forcedCategoryName) {
        this.forcedCategoryName = forcedCategoryName;
    }

    public String getForcedCountyName() {
        return forcedCountyName;
    }

    public void setForcedCountyName(String forcedCountyName) {
        this.forcedCountyName = forcedCountyName;
    }

    public String getForcedOrganizationName() {
        return forcedOrganizationName;
    }

    public void setForcedOrganizationName(String forcedOrganizationName) {
        this.forcedOrganizationName = forcedOrganizationName;
    }

    public String getForcedStatusName() {
        return forcedStatusName;
    }

    public void setForcedStatusName(String forcedStatusName) {
        this.forcedStatusName = forcedStatusName;
    }

    public String getOrganizationHomepage() {
        return organizationHomepage;
    }

    public void setOrganizationHomepage(String organizationHomepage) {
        this.organizationHomepage = organizationHomepage;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
