package com.ppx.mall.util;

public class ErrorResponse extends ResponseUtil{
    private Object reason;

    public ErrorResponse(String status, Object reason) {
        super.status = "error";
        this.reason = reason;
    }

    public ErrorResponse(String reason) {
        super.status="error";
        this.reason=reason;
    }

    public String getStatus() {
        return super.status;
    }

    public void setStatus(String status) {
        super.status = status;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }
}
