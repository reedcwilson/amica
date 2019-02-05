package com.reedcwilson.amica.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Favorite {
    private String key;
    private String value;
}
