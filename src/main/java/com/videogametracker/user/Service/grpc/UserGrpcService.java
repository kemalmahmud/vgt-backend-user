//package com.videogametracker.user.Service.grpc;
//
//import com.proto.*;
//import com.videogametracker.user.Model.dto.request.RegisterRequest;
//import com.videogametracker.user.Repository.UserRepository;
//import com.videogametracker.user.Service.UserService;
//import io.grpc.stub.StreamObserver;
//import net.devh.boot.grpc.server.service.GrpcService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@GrpcService
//public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void getUserAuth(UserAuthRequest request, StreamObserver<UserAuthResponse> responseObserver) {
//        var user = userRepository.findById(request.getUserId()).orElse(null);
//        assert user != null;
//        responseObserver.onNext(UserAuthResponse.newBuilder()
//                .setUsername(user.getUsername()).setPassword(user.getPassword()).build());
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void registerUser(UserRegisterRequest request, StreamObserver<UserRegisterResponse> responseObserver) {
//        var userRequest = RegisterRequest.builder()
//                .username(request.getUsername())
//                .email(request.getEmail())
//                .name(request.getFullname())
//                .password(request.getPassword())
//                .description(request.getDescription()).build();
//
//        var newUser = userService.registerUser(userRequest);
//        responseObserver.onNext(UserRegisterResponse.newBuilder()
//                .setUserId(newUser.getUserId()).build());
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void checkEmailExist(CheckEmailRequest request, StreamObserver<CheckEmailResponse> responseObserver) {
//        super.checkEmailExist(request, responseObserver);
//    }
//}
