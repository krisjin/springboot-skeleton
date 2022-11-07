package org.yeyu.springboot.gateway.provider.model;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class ResponseData<T> {
    private String code;
    private String message;
    private T data;

    public static <T> ResponseData<T> buildSuccess(T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setCode("0");
        responseData.setMessage("调用成功!");
        responseData.setData(data);
        return responseData;
    }

    public static <T> ResponseData<T> buildSuccess(JSONObject data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setCode("0");

        if (data != null) {
            String code = data.getString("code");
            if (null != code) {
                responseData.setCode(code);
            }
            if (null != data.get("message")) {
                responseData.setMessage(data.getString("message"));
            }
            if (null != data.get("data")) {
                responseData.setData((T) data.get("data"));
            }
            if (code == null && data.get("data") == null && data.get("message") == null) {
                responseData.setData((T) data);
            }
        }
        return responseData;
    }


}
