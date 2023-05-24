package com.ssafy.trip.service.place;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.dto.place.PlaceDto;
import com.ssafy.trip.dto.place.PlanPlaceDto;
import com.ssafy.trip.dto.place.PlaneDto;
import com.ssafy.trip.dto.place.RouteDto;

public interface PlaceService {
    void placeInfo(PlaceDto placeDto); // 장소 기본 정보 입력
    PlaceDto infoByPlaceId(String place_id); // 장소 기본키로 기본, 세부 정보 조회
    List<String> placeIdByPlanId(int plan_id); // 여행 계획에 담겨있는 장소들 조회
    void deletePlanPlace(int plan_id);
    void addPlanPlace(PlanPlaceDto planPlaceDto); // 여행지 경로 설정
    boolean checkPlaceId(String place_id);
    Map<String, Object> findPath(Map<String, Object> map, int size);
    void insertPlane(PlaneDto planeDto);
    void insertRoute(RouteDto routeDto);
    void deletePlane(int plan_id);
    void updatePlane(PlaneDto planeDto);
    void deleteRoute(int plan_id);
    void updateRoute(RouteDto routeDto);

}