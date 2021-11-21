package com.ppx.mall.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuccessResponse extends ResponseUtil{
    private Map<String,Object> messages;

    public SuccessResponse() {
        super.status="success";
        this.messages=new HashMap<>();
    }

    public SuccessResponse(String status) {
        super.status = "success";
        this.messages=new HashMap<>();
    }

    public String getStatus() {
        return super.status;
    }

    public void setStatus(String status) {
        super.status = status;
    }

    public Object getMessage(String key){
        return messages.get(key);
    }

    public void addMessage(String key, Object value){
        messages.put(key,value);
    }

    public Map<String,Object> getMessages(){
        return messages;
    }

    @Override
    public String toString() {
        return "ResponseUtil{" +
                "status='" + status + '\'' +
                ", messages=" + messages +
                '}';
    }
}
