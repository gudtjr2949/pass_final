package com.ssafy.trip.repository.user;

import com.ssafy.trip.dto.user.FollowingDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowingRepository {
    void follow(FollowingDto followingDto);
    void unfollow(FollowingDto followingDto);
    int followCheck(FollowingDto followingDto);
}
