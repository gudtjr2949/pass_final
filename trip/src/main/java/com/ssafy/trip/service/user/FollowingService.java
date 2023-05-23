package com.ssafy.trip.service.user;

import com.ssafy.trip.dto.user.FollowingDto;

public interface FollowingService {
    void follow(FollowingDto followingDto);
    void unfollow(FollowingDto followingDto);
    int followCheck(FollowingDto followingDto);
}
