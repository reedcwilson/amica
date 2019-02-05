package com.reedcwilson.amica.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LovedOne implements Serializable {
    private String name;
    private Date birthday;
}
