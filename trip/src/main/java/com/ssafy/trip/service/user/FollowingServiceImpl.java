package com.ssafy.trip.service.user;

import com.ssafy.trip.dto.user.FollowingDto;
import com.ssafy.trip.repository.user.FollowingRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl implements FollowingService {

    private FollowingRepository followingRepository;

    public FollowingServiceImpl(FollowingRepository followingRepository) {
        this.followingRepository = followingRepository;
    }

    @Override
    public void follow(FollowingDto followingDto) {
        followingRepository.follow(followingDto);
    }

    @Override
    public void unfollow(FollowingDto followingDto) {
        followingRepository.unfollow(followingDto);
    }

    @Override
    public int followCheck(FollowingDto followingDto) {
        return followingRepository.followCheck(followingDto);
    }
}
