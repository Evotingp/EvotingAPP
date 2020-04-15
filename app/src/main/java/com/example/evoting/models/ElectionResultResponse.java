package com.example.evoting.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElectionResultResponse {

    @SerializedName("response")
    @Expose
    private List<ElectionResultVo> response = null;

    public List<ElectionResultVo> getResponse() {
        return response;
    }

    public void setResponse(List<ElectionResultVo> response) {
        this.response = response;
    }

}