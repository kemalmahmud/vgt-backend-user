package com.videogametracker.user.Service;

import com.videogametracker.user.Model.User;
import com.videogametracker.user.Model.UserDetail;
import com.videogametracker.user.Model.dto.request.UserDetailRequest;
import com.videogametracker.user.Model.dto.request.RegisterRequest;
import com.videogametracker.user.Model.dto.response.BaseResponse;
import com.videogametracker.user.Model.dto.response.UserDetailResponse;
import com.videogametracker.user.Model.dto.response.RegisterResponse;
import com.videogametracker.user.Repository.UserDetailRepository;
import com.videogametracker.user.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    public ResponseEntity<BaseResponse> getUserDetail(UserDetailRequest request) {
        BaseResponse resp = new BaseResponse();
        try {
            var userId = request.getUserId();
            var userInfo = userDetailRepository.findByUserId(userId).orElse(null);
            if(userInfo == null) throw new Exception("User profile not found");
            UserDetailResponse userDetailResponse = UserDetailResponse.builder()
                    .name(userInfo.getName())
                    .email(userInfo.getEmail())
                    .userId(userInfo.getUser().getUserId())
                    .description(userInfo.getDescription())
                    .username(userInfo.getUser().getUsername())
                    .build();
            resp.setData(userDetailResponse);
            resp.setMessage("Success retrieving user profile");
            resp.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok(resp);
        }
        catch (Exception e) {
            resp.setMessage(e.getMessage());
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        try {
            // save to users table
            var newUser = User.builder()
                    .userId(UUID.randomUUID().toString())
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .isDeleted(false).build();
            userRepository.save(newUser);

            //save to user detail
            var newUserDetail = UserDetail.builder()
                    .userDetailId(UUID.randomUUID().toString())
                    .user(newUser)
                    .name(request.getName())
                    .email(request.getEmail())
                    .description(request.getDescription()).build();
            userDetailRepository.save(newUserDetail);

            log.info("Register success");

            return RegisterResponse.builder().userId(newUser.getUserId()).username(newUser.getUsername()).build();
        }
        catch (Exception e) {
            log.error("error when register user: " + e.getMessage());
            throw e;
        }
    }

}
