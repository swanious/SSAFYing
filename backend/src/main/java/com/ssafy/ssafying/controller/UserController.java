package com.ssafy.ssafying.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafying.config.JwtTokenUtil;
import com.ssafy.ssafying.model.dto.FollowDto;
import com.ssafy.ssafying.model.dto.UserDto;
import com.ssafy.ssafying.model.service.CustomUserDetailsService;
import com.ssafy.ssafying.model.service.EmailService;
import com.ssafy.ssafying.model.service.FollowService;
import com.ssafy.ssafying.model.service.JwtService;
import com.ssafy.ssafying.model.service.S3Service;
import com.ssafy.ssafying.model.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("UserController V1")
@RestController
// @CrossOrigin(origins = { "http://localhost:8080" })
@RequestMapping("/user")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JwtService jwtService;

    // @Autowired
    // private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailService;

    @ApiOperation(value = "모든 유저 정보 조회", notes = "모든 유저 정보를 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping(value = "/admin")
    public ResponseEntity<Map<String, Object>> userSelectAll(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("userDtoList", userService.userSelectAll());
            
            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("모든 유저 정보 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();

            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("모든 유저정보 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원탈퇴", notes = "유저 삭제를 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping(value = "/admin/{userId}")
    public ResponseEntity<Map<String, Object>> adminUserDelete(@PathVariable("userId") String userId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            userService.userDelete(userId);

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("가입 거절 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("가입 거절 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원 수정", notes = "유저 정보를 수정한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping(value="/admin")
    public ResponseEntity<Map<String, Object>> adminUserUpdate(
        @RequestBody @ApiParam(value = "유저 정보", required = true) UserDto userDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            userDto.setRole("ROLE_USER");
            userService.userUpdate(userDto);

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("가입 승인 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("가입 승인 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "인증메일", notes = "email을 받아 uuid를 보낸다. 그리고 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping(value="/email")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody @ApiParam(value = "email : ssafy@ssafy.com", required = true) Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;        
        
        try {
            String email = (String)map.get("email");
            if(userService.userSelectByKeyWord("email", email) != null){
                resultMap.put("message", "fail");
            } else {
                resultMap.put("uuid", emailService.sendSimpleMessage(email));
                resultMap.put("message", "success");
            }

            status = HttpStatus.CREATED;
            logger.info("이메일 전송 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("이메일 전송 실패 : 에러");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "이메일 중복 확인", notes = "이메일의 중복을 확인한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmailUnique(@PathVariable("email") @ApiParam(value = "이메일", required = true) String email){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            if(userService.userSelectByKeyWord("email", email) != null) throw new Exception();

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("타인의 유저 정보 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("타인의 유저 정보 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원가입", notes = "유저 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping(value="/")
    public ResponseEntity<Map<String, Object>> userInsert(@RequestBody @ApiParam(value = "유저 정보", required = true) UserDto userDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;        
        
        try {
            if( userDto.getProfileImage() != null && !userDto.getProfileImage().equals("")){
                userDto.setProfileImage(s3Service.upload(userDto.getProfileImage(), Long.toString(new Date().getTime())));
            }
            userService.userInsert(userDto);

            resultMap.put("message", "success");
            status = HttpStatus.CREATED;
            logger.info("회원가입 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("회원가입 실패 : 에러");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "자신 유저 정보 요청", notes = "자신의 유저 정보를 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping(value = "/")
    public ResponseEntity<Map<String, Object>> userSelectOne(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("userDto", userService.userSelectByKeyWord("userId", jwtService.getUserId()));

            List<FollowDto> followerDtoList = followService.followSelectByKeyWord("followerId", jwtService.getUserId()); // 자신이 팔로우하는 유저들
            resultMap.put("followerCount", followerDtoList.size());
            List<FollowDto> followingDtoList = followService.followSelectByKeyWord("followingId", jwtService.getUserId()); // 자신을 팔로우하는 유저들
            resultMap.put("followingCount", followingDtoList.size());

            List<UserDto> followerUserDtoList = new ArrayList<>();
            for(FollowDto followDto : followerDtoList) {
                UserDto userDto = userService.userSelectOne(followDto.getFollowingId());

                userDto.setUserId(""); userDto.setPassword("");
                followerUserDtoList.add(userDto);
            }
            resultMap.put("followerUserDtoList", followerUserDtoList);

            List<UserDto> followingUserDtoList = new ArrayList<>();
            for(FollowDto followDto : followingDtoList) {
                UserDto userDto = userService.userSelectOne(followDto.getFollowerId());

                userDto.setUserId(""); userDto.setUserId("");
                followingUserDtoList.add(userDto);
            }
            resultMap.put("followingUserDtoList", followingUserDtoList);

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("자신의 유저 정보 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("자신의 유저 정보 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "타인 유저 정보 요청", notes = "닉네임에 해당하는 타인의 유저 정보를 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping(value = "/{nickname}")
    public ResponseEntity<Map<String, Object>> userSelectOne(@PathVariable("nickname") @ApiParam(value = "타인의 닉네임", required = true) String nickname){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            UserDto userDto = userService.userSelectByKeyWord("nickname", nickname);

            List<FollowDto> followerDtoList = followService.followSelectByKeyWord("followerId", userDto.getUserId()); // 타인을 팔로우하는 유저들
            resultMap.put("followerCount", followerDtoList.size());

            List<FollowDto> followingDtoList = followService.followSelectByKeyWord("followingId", userDto.getUserId()); // 타인이 팔로우하는 유저들
            resultMap.put("followingCount", followingDtoList.size());

            userDto.setUserId(""); userDto.setUserId("");
            resultMap.put("userDto", userDto);

            List<UserDto> followerUserDtoList = new ArrayList<>();
            for(FollowDto followDto : followerDtoList) {
                UserDto follwerUserDto = userService.userSelectOne(followDto.getFollowingId());

                follwerUserDto.setUserId(""); userDto.setUserId("");
                followerUserDtoList.add(follwerUserDto);
            }
            resultMap.put("followerUserDtoList", followerUserDtoList);

            List<UserDto> followingUserDtoList = new ArrayList<>();
            for(FollowDto followDto : followingDtoList) {
                UserDto followingUserDto = userService.userSelectOne(followDto.getFollowerId());

                followingUserDto.setUserId(""); userDto.setUserId("");
                followingUserDtoList.add(followingUserDto);
            }
            resultMap.put("followingUserDtoList", followingUserDtoList);

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("타인의 유저 정보 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("타인의 유저 정보 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "타인 유저 정보 요청", notes = "닉네임에 해당하는 타인의 유저 정보를 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping(value = "/duplicate")
    public ResponseEntity<Map<String, Object>> userDuplicate(@RequestBody @ApiParam(value = "유저 정보", required = true) UserDto userDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            if(userService.userSelectByKeyWord("nickname", userDto.getNickname()) != null) throw new Exception();

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("타인의 유저 정보 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("타인의 유저 정보 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원 수정", notes = "유저 정보를 수정한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping(value="/")
    public ResponseEntity<Map<String, Object>> userUpdate(
        @RequestBody @ApiParam(value = "유저 정보", required = true) UserDto userDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;


        try {
            if( userDto.getProfileImage() != null && !userDto.getProfileImage().equals("")){
                userDto.setProfileImage(s3Service.upload(userDto.getProfileImage(), Long.toString(new Date().getTime())));
            }
            userDto.setUserId(jwtService.getUserId());
            userService.userUpdate(userDto);

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("회원 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("회원 수정 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호를 수정한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping(value="/password")
    public ResponseEntity<Map<String, Object>> userUpdatePassword(
        @RequestBody @ApiParam(value = "기존 비밀번호, 새로운 비밀번호", required = true) Map<String, Object> map) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            userService.userUpdatePassword(jwtService.getUserId(), (String)map.get("oldPassword"), (String)map.get("newPassword"));

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("비밀번호 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("비밀번호 수정 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원탈퇴", notes = "유저 삭제를 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping(value = "/")
    public ResponseEntity<Map<String, Object>> userDelete(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            userService.userDelete(jwtService.getUserId());

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("회원탈퇴 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("회원탈퇴 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "로그인", notes = "로그인을 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody @ApiParam(value = "이메일, 비밀번호", required = true) UserDto userDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {            
            final UserDto userAuth = userDetailService.authenticateByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userAuth.getUserId());
            
            resultMap.put("access-token", jwtTokenUtil.generateToken(userAuth.getEmail(), map));
                
            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("로그인 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("로그인 실패");
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃을 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping(value = "/logout")
    public ResponseEntity<Map<String, Object>> userLogout(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            userService.userLogout(jwtService.getUserId());

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("로그아웃 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("로그아웃 아웃 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @ApiOperation(value = "팔로우 입력", notes = "팔로우를 입력 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping(value = "/follow/{following_nickname}")
    public ResponseEntity<Map<String, Object>> followInsert(
        @PathVariable("following_nickname") @ApiParam(value = "타인의 닉네임", required = true) String following_nickname){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            followService.followInsert(jwtService.getUserId(), userService.userSelectByKeyWord("nickname", following_nickname).getUserId());

            resultMap.put("message", "success");
            status = HttpStatus.CREATED;
            logger.info("팔로우 입력 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("팔로우 입력 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "팔로우 삭제", notes = "팔로우를 삭제 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping(value = "/follow/{following_nickname}")
    public ResponseEntity<Map<String, Object>> followDelete(
        @PathVariable("following_nickname") @ApiParam(value = "타인의 닉네임", required = true) String following_nickname){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            followService.followDelete(jwtService.getUserId(), userService.userSelectByKeyWord("nickname", following_nickname).getUserId());

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("팔로우 삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("팔로우 삭제 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "팔로우 조회", notes = "팔로우를 조회하여 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping(value = "/follow/{following_nickname}")
    public ResponseEntity<Map<String, Object>> followSelectOne(
        @PathVariable("following_nickname") @ApiParam(value = "타인의 닉네임", required = true) String following_nickname){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            resultMap.put(
                "IsFollow", 
                followService.followSelectOne(jwtService.getUserId(), userService.userSelectByKeyWord("nickname", following_nickname).getUserId()) == null ? 0 : 1
            );

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("팔로우 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("팔로우 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "전체 팔로우 조회", notes = "팔로우를 조회하여 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping(value = "/follow/admin")
    public ResponseEntity<Map<String, Object>> followSelectAll(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            resultMap.put("followDtoList", followService.followSelectAll());

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("전체 팔로우 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("전체 팔로우 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 이 컨트롤러는 지움..
    @ApiOperation(value = "자신의 팔로워, 팔로잉 조회", notes = "팔로우를 조회하여 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @GetMapping(value = "/follow")
    public ResponseEntity<Map<String, Object>> followSelectOne(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            List<UserDto> followerUserDtoList = new ArrayList<>();
            for(FollowDto followDto : followService.followSelectByKeyWord("followerId", jwtService.getUserId())) {
                UserDto userDto = userService.userSelectOne(followDto.getFollowerId());

                userDto.setUserId(""); userDto.setUserId("");
                followerUserDtoList.add(userDto);
            }
            resultMap.put("followerUserDtoList", followerUserDtoList);

            List<UserDto> followingUserDtoList = new ArrayList<>();
            for(FollowDto followDto : followService.followSelectByKeyWord("followingId", jwtService.getUserId())) {
                UserDto userDto = userService.userSelectOne(followDto.getFollowingId());

                userDto.setUserId(""); userDto.setUserId("");
                followingUserDtoList.add(userDto);
            }
            resultMap.put("followingUserDtoList", followingUserDtoList);

            resultMap.put("message", "success");
            status = HttpStatus.ACCEPTED;
            logger.info("자신의 팔로워, 팔로잉 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // resultMap.put("message", e.getMessage());
            resultMap.put("message", "fail");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info("자신의 팔로워, 팔로잉 조회 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}