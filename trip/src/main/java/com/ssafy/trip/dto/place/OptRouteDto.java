package com.ssafy.trip.dto.place;

import lombok.Data;

@Data
public class OptRouteDto {

    private String orderByTime;
    private String orderByDis;
    private String orderBy_dis_max_dis;
    private String orderBy_dis_max_time;
    private String orderBy_time_max_dis;
    private String orderBy_time_max_time;


}
