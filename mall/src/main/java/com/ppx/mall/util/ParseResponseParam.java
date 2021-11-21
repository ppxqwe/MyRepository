package com.ppx.mall.util;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.ppx.mall.bean.User;

public class ParseResponseParam {
    public static SuccessResponse parse(User user){
        String userString= Base64.encode(JSONObject.toJSONString(user));
        SuccessResponse r=new SuccessResponse();
        r.addMessage("user",userString);
        return r;
    }
}
