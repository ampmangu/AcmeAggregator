package com.amp.acmeaggregator.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeeResponse {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("fees")
    @Expose
    private List<String> fees = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getFees() {
        return fees;
    }

    public void setFees(List<String> fees) {
        this.fees = fees;
    }
}
