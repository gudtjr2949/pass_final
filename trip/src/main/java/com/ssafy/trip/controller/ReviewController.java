package com.ssafy.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.dto.review.ImageDto;
import com.ssafy.trip.dto.review.ReviewCommentDto;
import com.ssafy.trip.dto.review.ReviewDto;
import com.ssafy.trip.service.review.ReviewCommentService;
import com.ssafy.trip.service.review.ReviewImageService;
import com.ssafy.trip.service.review.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = {})
public class ReviewController {

    private ReviewService reviewService;
    private ReviewCommentService reviewCommentService;
    private ReviewImageService reviewImageService;

    ObjectMapper objectMapper = new ObjectMapper();

    public ReviewController(ReviewService reviewService, ReviewCommentService reviewCommentService, ReviewImageService reviewImageService) {
        this.reviewService = reviewService;
        this.reviewCommentService = reviewCommentService;
        this.reviewImageService = reviewImageService;
    }

    @PostMapping("/api/write")
    public ResponseEntity<Map<String, String>> write(@RequestParam(value = "image[]", required = false) List<MultipartFile> images,
                                                     @RequestParam("user_id") String user_id,
                                                     @RequestParam("title") String title,
                                                     @RequestParam("content") String content,
                                                     @RequestParam(value = "plan_id", required = false) int plan_id) {
    	
        Map<String, String> res = new HashMap<>();

//        String uploadPath = "C:\\Users\\SSAFY\\Desktop\\new\\pass_final\\plan\\src\\assets\\save_image";
        String uploadPath = "/Users/leehyungseok/Desktop/SSAFY/pass_final/pass_final/plan/src/assets/save_image";
        try {
            ReviewDto reviewDto = new ReviewDto();

            reviewDto.setUser_id(user_id);
            reviewDto.setTitle(title);
            reviewDto.setContent(content);
            
            reviewDto.setPlan_id(plan_id);

            reviewDto.setFirst_image(images.get(0).getOriginalFilename());

            reviewService.write(reviewDto);

            int review_id = reviewService.getMaxReviewId();

            uploadPath += "/" + review_id;

            File folder = new File(uploadPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            List<ImageDto> list = new ArrayList<>();

            for (int i = 0; i < images.size(); i++) {
                String originalFilename = images.get(i).getOriginalFilename();
                String filePath = uploadPath + File.separator + originalFilename;

                images.get(i).transferTo(new File(filePath));

                ImageDto imageDto = new ImageDto();

                imageDto.setReview_id(review_id);
                imageDto.setImage_path(uploadPath + "/" + originalFilename);
                imageDto.setImage_name(originalFilename);

                list.add(imageDto);
            }

            reviewImageService.insert(list);

            res.put("resmsg", "후기 게시글 작성 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 작성 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/list")
    public ResponseEntity<Map<String, Object>> list() {
        Map<String, Object> res = new HashMap<>();

        Map<String, String> map = new HashMap<>();

        try {
            res.put("list", reviewService.list(map));

            res.put("resmsg", "후기 게시글 전체 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 전체 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/detail/{review_id}")
    public ResponseEntity<Map<String, Object>> select(@PathVariable("review_id") int review_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            ReviewDto reviewDto = reviewService.select(review_id);
            res.put("review", reviewDto);
            // 댓글 가져오기
            List<ReviewCommentDto> commentList = reviewCommentService.list(review_id);
            res.put("comment", commentList);
            res.put("image", reviewImageService.list(review_id));
            reviewService.updateHit(review_id);
            res.put("resmsg", "후기 게시글 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    // 내 글만 조회
    @GetMapping("/api/my_review/{user_id}")
    public ResponseEntity<Map<String, Object>> my_review(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            res.put("review", reviewService.my_list(user_id));

            res.put("resmsg", "내 후기 게시글 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "내 후기 게시글 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    // 팔로워 글 조회
    @GetMapping("/api/following_review/{user_id}")
    public ResponseEntity<Map<String, Object>> following_review(@PathVariable("user_id") String user_id) {
        Map<String, Object> res = new HashMap<>();

        try {
            List<ReviewDto> list = reviewService.following_list(user_id);

            res.put("review", list);

            res.put("resmsg", "팔로잉 후기 게시글 조회 성공");
        } catch (Exception e) {
            res.put("resmsg", "팔로잉 후기 게시글 조회 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/modify")
    public ResponseEntity<Map<String, String>> modify(@RequestParam(value = "image[]", required = false) List<MultipartFile> images,
                                                      @RequestParam("review_id") int review_id,
                                                      @RequestParam("title") String title,
                                                      @RequestParam("content") String content
    ) {
        Map<String, String> res = new HashMap<>();

//        String uploadPath = "C:\\Users\\SSAFY\\Desktop\\new\\pass_final\\plan\\src\\assets\\save_image\\" + review_id;
        String uploadPath = "/Users/leehyungseok/Desktop/SSAFY/pass_final/pass_final/plan/src/assets/save_image" + review_id;

        try {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReview_id(review_id);
            reviewDto.setTitle(title);
            reviewDto.setContent(content);
            reviewDto.setFirst_image(images.get(0).getOriginalFilename());

            reviewService.modify(reviewDto);

            List<ImageDto> list = new ArrayList<>();

            // 폴더 지우기
            File folder = new File(uploadPath);
            File[] deleteFolderList = folder.listFiles();

            for (int i = 0; i < deleteFolderList.length; i++) {
            	deleteFolderList[i].delete();
            }

            for (int i = 0; i < images.size(); i++) {
                String originalFilename = images.get(i).getOriginalFilename();
                
                String filePath = uploadPath + File.separator + originalFilename;
                
                ImageDto imageDto = new ImageDto();

                imageDto.setReview_id(review_id);
                imageDto.setImage_path(uploadPath + "/" + originalFilename);
                imageDto.setImage_name(originalFilename);
                
                images.get(i).transferTo(new File(filePath));

                list.add(imageDto);
            }
            
            reviewImageService.modify(list);

            res.put("resmsg", "후기 게시글 수정 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 수정 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/like/{review_id}")
    public ResponseEntity<Map<String, String>> like(@RequestBody Map<String, String> map, @PathVariable("review_id") int review_id) {
        Map<String, String> res = new HashMap<>();
        try {
            reviewService.updateHit(review_id);
            res.put("resmsg", "후기 게시글 좋아요 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 좋아요 실패");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/api/delete/{review_id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("review_id") int review_id) {
        Map<String, String> res = new HashMap<>();
        System.out.println(review_id);
        
        try {
            reviewService.delete(review_id);
            res.put("resmsg", "후기 게시글 삭제 성공");
        } catch (Exception e) {
            res.put("resmsg", "후기 게시글 삭제 실패");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }
}
