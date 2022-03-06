package com.smartdev.activemq.userservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageModel implements Serializable {
    public static final long serialVersionUID = 42L;
    private String name;
    private String content;
}
