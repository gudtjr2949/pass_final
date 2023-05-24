package com.ssafy.trip.dto.place;

import lombok.Data;

@Data
public class RouteDto {

    private int plan_id;
    private String formatted_address;
    private String place_id;
    private String url;
    private String photo;
    private int sequence;
    private int idx;
}
