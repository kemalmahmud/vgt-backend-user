package com.videogametracker.user.Service;

import com.videogametracker.user.Model.dto.request.UserDetailRequest;
import com.videogametracker.user.Model.dto.response.BaseResponse;
import com.videogametracker.user.Model.dto.response.UserDetailResponse;
import com.videogametracker.user.Repository.UserDetailRepository;
import com.videogametracker.user.Repository.UserRepository;
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

}
