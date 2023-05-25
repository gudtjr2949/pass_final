package com.ssafy.trip.dto.place;

import lombok.Data;

@Data
public class PlaneDto {

    private int plan_id;
    private String departure;
    private String arrival;
    private String price;
    private String carrierCode;
    private String numberOfBookableSeats;
    private String numberOfStops;
    private int sequence;
    private String start;
    private String end;


}
