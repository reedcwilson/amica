package com.reedcwilson.amica.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Interaction {
    private Date date;
    private InteractionType type;
    private String notes;
}
