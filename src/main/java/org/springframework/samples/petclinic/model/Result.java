
package org.springframework.samples.petclinic.model;


import java.util.HashMap;
import java.util.Map;

public class Result {

    @Override
	public String toString() {
		return "Result [sid=" + sid + ", surveylsTitle=" + surveylsTitle + ", startdate=" + startdate + ", expires="
				+ expires + ", active=" + active + ", additionalProperties=" + additionalProperties + "]";
	}

	private String sid;
    private String surveylsTitle;
    private Object startdate;
    private Object expires;
    private String active;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSurveylsTitle() {
        return surveylsTitle;
    }

    public void setSurveylsTitle(String surveylsTitle) {
        this.surveylsTitle = surveylsTitle;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
