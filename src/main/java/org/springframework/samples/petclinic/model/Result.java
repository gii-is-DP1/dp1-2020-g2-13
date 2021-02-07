package org.springframework.samples.petclinic.model;

import com.google.gson.annotations.SerializedName;

public class Result {


    private String sid;

    private String surveyls_title;

    private Object startdate;

    private Object expires;

    private String active;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSurveylsTitle() {
        return surveyls_title;
    }

    public void setSurveylsTitle(String surveylsTitle) {
        this.surveyls_title = surveylsTitle;
    }

    public Object getStartdate() {
        return startdate;
    }

    public void setStartdate(Object startdate) {
        this.startdate = startdate;
    }

    public Object getExpires() {
        return expires;
    }

    public void setExpires(Object expires) {
        this.expires = expires;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Result [sid=" + sid + ", surveylsTitle=" + surveyls_title + ", startdate=" + startdate + ", expires="
                + expires + ", active=" + active + "]";
    }


}