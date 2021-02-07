
package org.springframework.samples.petclinic.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreSearch {

    public CoreSearch() {
		super();
		this.id = null;
		this.result = null;
		this.error = null;
		this.additionalProperties = null;
	}

	private Integer id;
    private List<Result> result = null;
    private Object error;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "CoreSearch [id=" + id + ", result=" + result + ", error=" + error + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
