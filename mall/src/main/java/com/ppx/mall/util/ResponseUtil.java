package com.ppx.mall.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtil {
    private String status;
    private Map<String,Object> messages;

    public ResponseUtil() {
        this.messages=new HashMap<>();
    }

    public ResponseUtil(String status) {
        this.status = status;
        this.messages=new HashMap<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String,Object> getMessages(){
        return messages;
    }

    public void addMessage(String key, Object value){
        messages.put(key,value);
    }

    @Override
    public String toString() {
        return "ResponseUtil{" +
                "status='" + status + '\'' +
                ", messages=" + messages +
                '}';
    }
}
