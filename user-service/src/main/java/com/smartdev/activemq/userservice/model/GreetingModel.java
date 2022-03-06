package com.smartdev.activemq.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GreetingModel implements Serializable {
    public static final long serialVersionUID = 42L;

    private String name;
}
