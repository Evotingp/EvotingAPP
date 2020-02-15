package com.example.evoting.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedPostResponseVo {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("error")
@Expose
private Object error;
@SerializedName("response")
@Expose
private List<FeedPostResultVo> response = null;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Object getError() {
return error;
}

public void setError(Object error) {
this.error = error;
}

public List<FeedPostResultVo> getResponse() {
return response;
}

public void setResponse(List<FeedPostResultVo> response) {
this.response = response;
}

}