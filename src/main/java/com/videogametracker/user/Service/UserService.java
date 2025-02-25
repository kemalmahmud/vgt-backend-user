package com.videogametracker.user.Service;

import com.videogametracker.user.Model.User;
import com.videogametracker.user.Model.UserDetail;
import com.videogametracker.user.Model.dto.request.RegisterRequest;
import com.videogametracker.user.Model.dto.request.UserDetailRequest;
import com.videogametracker.user.Model.dto.response.BaseResponse;
import com.videogametracker.user.Model.dto.response.LoginResponse;
import com.videogametracker.user.Model.dto.response.RegisterResponse;
import com.videogametracker.user.Model.dto.response.UserDetailResponse;
import com.videogametracker.user.Repository.UserDetailRepository;
import com.videogametracker.user.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            var userInfo = userRepository.findById(userId).orElse(null);
            if(userInfo == null) throw new Exception("User profile not found");
            UserDetailResponse userDetailResponse = UserDetailResponse.builder()
                    .name(userInfo.getUserDetail().getName())
                    .email(userInfo.getUserDetail().getEmail())
                    .userId(userInfo.getUserId())
                    .description(userInfo.getUserDetail().getDescription())
                    .username(userInfo.getUsername())
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

    public LoginResponse getLoginInfo(String username) {
        LoginResponse response = new LoginResponse();
        try {
            log.info("Start retrieving user");
            var user = userRepository.findByUsername(username).orElse(null);
            if(user == null) {
                response.setErrorMessage("User not found");
            } else {
                response.setUserId(user.getUserId());
                response.setUsername(user.getUsername());
                response.setPassword(user.getPassword());
            }

            log.info("Retrieving user success");
            return response;
        }
        catch(Exception e) {
            log.error("error in getUserByUsername: " + e.getMessage());
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        try {
            // save to users table
            var newUser = User.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .isDeleted(false).build();
            userRepository.save(newUser);

            //save to user detail
            var newUserDetail = UserDetail.builder()
                    .user(newUser)
                    .name(request.getName())
                    .email(request.getEmail())
                    .description(request.getDescription()).build();
            userDetailRepository.save(newUserDetail);

            log.info("Register success");

            return RegisterResponse.builder()
                    .userId(newUser.getUserId()).build();
        }
        catch (Exception e) {
            log.error("error in registerUser: " + e.getMessage());
            throw e;
        }
    }
}
