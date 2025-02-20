package com.videogametracker.user.Controller;

import com.videogametracker.user.Model.dto.request.UserDetailRequest;
import com.videogametracker.user.Model.dto.response.BaseResponse;
import com.videogametracker.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/detail")
    public ResponseEntity<BaseResponse> getUserById(@RequestBody UserDetailRequest request) {
        return userService.getUserDetail(request);
    }

}
