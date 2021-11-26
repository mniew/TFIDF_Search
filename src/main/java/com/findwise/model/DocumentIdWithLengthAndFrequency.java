package com.findwise.model;

import lombok.Value;

@Value
public class DocumentIdWithLengthAndFrequency {
    String id;
    Integer length;
    Integer frequency;
}
