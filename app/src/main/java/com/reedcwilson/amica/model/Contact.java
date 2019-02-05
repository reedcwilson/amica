package com.reedcwilson.amica.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {
    private String name;
    private Date birthday;
    private String notes;
    private List<LovedOne> lovedOnes;
    private List<Job> jobs;
    private List<Favorite> favorites;
    private List<Interaction> interactions;
    private List<String> todos;
}
