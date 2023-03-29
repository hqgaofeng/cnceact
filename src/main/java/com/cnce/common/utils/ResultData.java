package com.cnce.common.utils;

public class ResultData<T> {

    private String code;
    private boolean result;
    private String msg;
    private T data;

    public ResultData (String code, boolean result, T data) {
        this.code=code;
        this.result=result;
        this.data=data;
    }
    public ResultData (String code, boolean result, T data,String msg) {
        this.code=code;
        this.result=result;
        this.data=data;
        this.msg = msg;
    }
    public ResultData (String code, boolean result, String msg) {
        this.code=code;
        this.result=result;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
