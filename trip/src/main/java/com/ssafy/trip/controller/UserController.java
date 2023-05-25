package com.ssafy.trip.controller;

import com.ssafy.trip.dto.user.FollowingDto;
import com.ssafy.trip.dto.user.UserDto;
import com.ssafy.trip.service.user.FollowingService;
import com.ssafy.trip.service.user.OAuthService;
import com.ssafy.trip.service.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {})
public class UserController {

    private UserService userService;
    private OAuthService oAuthService;
    private FollowingService followingService;


    public UserController(UserService userService, OAuthService oAuthService, FollowingService followingService) {
        this.userService = userService;
        this.oAuthService = oAuthService;
        this.followingService = followingService;
    }

    @GetMapping("/api/idCheck/{user_id}")
    public ResponseEntity<Map<String, Object>> idCheck(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("check", userService.idCheck(user_id));
            res.put("resmsg", "아이디 체크 성공");
        } catch (Exception e) {
            res.put("resmsg", "아이디 체크 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/join")
    public ResponseEntity<Map<String, String>> join(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            UserDto userDto = new UserDto();

            userDto.setUser_id(map.get("user_id"));
            userDto.setUser_pw(map.get("user_pw"));
            userDto.setUser_name(map.get("user_name"));
            userDto.setUser_email(map.get("user_email"));
            userDto.setUser_domain(map.get("user_domain"));

            userService.join(userDto);

            res.put("resmsg", "회원가입 성공");
        } catch (Exception e) {
            res.put("resmsg", "회원가입 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        String user_id = map.get("user_id");
        String user_pw = map.get("user_pw");

        try {
            UserDto userDto = userService.login(user_id, user_pw);

            if (userDto != null) {
                res.put("user_id", userDto.getUser_id());
                res.put("user_name", userDto.getUser_name());
                res.put("resmsg", "로그인 성공");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            res.put("resmsg", "로그인 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/kakao/callback")
    public ResponseEntity<Map<String, String>> kakaoCallback(@RequestParam String code) throws UnsupportedEncodingException {
        Map<String, String> res = new HashMap<>();

        UserDto userDto = oAuthService.getKakaoAccessToken(code);

        try {
            if (userService.idCheck(userDto.getUser_id())) { // 카카오 회원 정보를 DB에 넣어야 함
                userService.join(userDto);
            }

            res.put("user_id", userDto.getUser_id());
            res.put("user_name", userDto.getUser_name());
            res.put("resmsg", "카카오 로그인 성공");
        } catch (Exception e) {
            res.put("resmsg", "카카오 로그인 실패");
            return ResponseEntity.notFound().build();
        }

        // Vue.js의 Main 페이지로 리다이렉트
        String redirectUrl = "http://192.168.208.54:8080?user_id=" + userDto.getUser_id() + "&user_name=" +URLEncoder.encode(userDto.getUser_name(), String.valueOf(StandardCharsets.UTF_8));
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .body(res);
    }

    @GetMapping("/api/mypage/{user_id}")
    public ResponseEntity<Map<String, Object>> mypage(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("user", userService.mypage(user_id));
            res.put("resmsg", "마이페이지 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "마이페이지 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/follower_list/{user_id}")
    public ResponseEntity<Map<String, Object>> follower_list(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            List<UserDto> list = userService.follower_list(user_id);

            res.put("list", list);

            res.put("resmsg", "날 팔로우하는 사람 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "날 팔로우하는 사람 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/following_list/{user_id}")
    public ResponseEntity<Map<String, Object>> following_list(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            List<UserDto> list = userService.following_list(user_id);

            res.put("list", list);
            res.put("resmsg", "내가 팔로우하는 사람 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "내가 팔로우하는 사람 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/modify")
    public ResponseEntity<Map<String, String>> modify(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            UserDto userDto = new UserDto();

            userDto.setUser_id(map.get("user_id"));
            userDto.setUser_pw(map.get("user_pw"));
            userDto.setUser_name(map.get("user_name"));
            userDto.setUser_email(map.get("user_email"));
            userDto.setUser_domain(map.get("user_domain"));

            userService.modify(userDto);

            System.out.println(userDto);

            res.put("resmsg", "회원정보 수정 성공");
        } catch (Exception e) {
            res.put("resmsg", "회원정보 수정 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/api/delete/{user_id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("user_id") String user_id, HttpSession session) {
        Map<String, String> res = new HashMap<>();

        try {
            userService.delete(user_id);

            session.invalidate();
            res.put("resmsg", "회원정보 삭제 성공");
        } catch (Exception e) {
            res.put("resmsg", "회원정보 삭제 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/follow")
    public ResponseEntity<Map<String, String>> follow(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            FollowingDto followingDto = new FollowingDto();

            followingDto.setUser_id(map.get("user_id"));
            followingDto.setFollowing_id(map.get("following_id"));

            followingService.follow(followingDto);

            res.put("resmsg", "팔로우 성공");
        } catch (Exception e) {
            res.put("resmsg", "팔로우 실패");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/unfollow")
    public ResponseEntity<Map<String, String>> unfollow(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();

        try {
            FollowingDto followingDto = new FollowingDto();

            followingDto.setUser_id(map.get("user_id"));
            followingDto.setFollowing_id(map.get("following_id"));

            followingService.unfollow(followingDto);

            res.put("resmsg", "언팔로우 성공");
        } catch (Exception e) {
            res.put("resmsg", "언팔로우 실패");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/checkFollow")
    public ResponseEntity<Map<String, Object>> checkFollow(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();

        try {
            FollowingDto followingDto = new FollowingDto();

            followingDto.setUser_id(map.get("user_id"));
            followingDto.setFollowing_id(map.get("following_id"));

            res.put("check", followingService.followCheck(followingDto));

            res.put("resmsg", "팔로우 체크 성공");
        } catch (Exception e) {
            res.put("resmsg", "팔로우 체크 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/checkAdmin/{user_id}")
    public ResponseEntity<Map<String, Object>> checkAdmin(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("check", userService.checkAdmin(user_id));
            res.put("resmsg", "Admin 여부 확인 성공");
        } catch (Exception e) {
            res.put("resmsg", "Admin 여부 확인 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }
}