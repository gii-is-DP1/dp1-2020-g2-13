package org.springframework.samples.petclinic.model;


import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CoreSearch {



    private Integer id;

    private List<Survey> result;

    private Object error;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Survey> getResult() {
        return result;
    }

    public void setResult(List<Survey> result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }


	@Override
	public String toString() {
		return "CoreSearch [id=" + id + ", result=" + result + ", error=" + error + "]";
	}

    

}

