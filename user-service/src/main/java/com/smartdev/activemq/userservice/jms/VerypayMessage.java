package com.smartdev.activemq.userservice.jms;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class VerypayMessage <T>{
    String header;
    @JsonTypeInfo(use  = JsonTypeInfo.Id.NAME, property = "@className")
    T body;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
