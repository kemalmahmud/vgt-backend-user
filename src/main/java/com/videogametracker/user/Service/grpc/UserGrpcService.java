package com.videogametracker.user.Service.grpc;

import com.proto.*;
import com.videogametracker.user.Model.dto.request.RegisterRequest;
import com.videogametracker.user.Service.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserService userService;

    @Override
    public void getUserAuthGrpc(UserAuthRequestGrpc request, StreamObserver<UserAuthResponseGrpc> responseObserver) {
        var user = userService.getLoginInfo(request.getUsername());
        assert user != null;
        responseObserver.onNext(UserAuthResponseGrpc.newBuilder()
                .setUserId(user.getUserId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void registerUserGrpc(UserRegisterRequestGrpc request, StreamObserver<UserRegisterResponseGrpc> responseObserver) {
        var userRequest = RegisterRequest.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .name(request.getFullname())
                .password(request.getPassword())
                .description(request.getDescription()).build();

        var newUser = userService.registerUser(userRequest);
        responseObserver.onNext(UserRegisterResponseGrpc.newBuilder()
                .setUserId(newUser.getUserId()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void checkUsernameExistGrpc(checkUsernameRequestGrpc request, StreamObserver<checkUsernameRequestGrpc> responseObserver) {
        super.checkUsernameExistGrpc(request, responseObserver);
    }
}
