package com.reedcwilson.amica.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Job {
    private String company;
    private Date startDate;
}
