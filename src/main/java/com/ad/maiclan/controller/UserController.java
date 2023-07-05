package com.ad.maiclan.controller;

import com.ad.maiclan.dto.AddUserRequestDto;
import com.ad.maiclan.dto.GetAllClanRequestDto;
import com.ad.maiclan.dto.GetAllUserRequestDto;
import com.ad.maiclan.dto.RemoveUserRequestDto;
import com.ad.maiclan.model.Clan;
import com.ad.maiclan.model.User;
import com.ad.maiclan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user")
    public ResponseEntity<Object> addUserToClan(@RequestBody final AddUserRequestDto addUserRequestDto) {
        userService.addUserToClan(addUserRequestDto.serverId(), addUserRequestDto.userId(), addUserRequestDto.clanName());
        return new ResponseEntity<>("User is added to clan.", HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Object> addUserToClan(@RequestBody final RemoveUserRequestDto removeUserRequestDto) {
        userService.removeUserFromClan(removeUserRequestDto.serverId(), removeUserRequestDto.userId(), removeUserRequestDto.clanName());
        return new ResponseEntity<>("User is removed from clan.", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllClan(@RequestBody final GetAllUserRequestDto getAllUserRequestDto) {
        List<User> users = userService.getAllUser(getAllUserRequestDto.serverId(), getAllUserRequestDto.clanName());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
