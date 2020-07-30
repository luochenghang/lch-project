package com.lch.utils;

import lombok.Data;

@Data
public class Result {

    private String title;

    private Location location;

    private  double similarity;

    private  Integer deviation;

    private Integer reliability;

    private  Integer level;



}
