package com.ad.maiclan.controller;

import com.ad.maiclan.dto.StartSessionRequestDto;
import com.ad.maiclan.dto.StopSessionRequestDto;
import com.ad.maiclan.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private final SessionService sessionService;

    public SessionController(final SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/session/start")
    public ResponseEntity<Object> startSession(@RequestBody final StartSessionRequestDto startSessionRequestDto) {
        sessionService.startUserSession(startSessionRequestDto.serverId(), startSessionRequestDto.userId());
        return new ResponseEntity<>("User session is started.", HttpStatus.OK);
    }

    @PostMapping("/session/stop")
    public ResponseEntity<Object> stopSession(@RequestBody final StopSessionRequestDto stopSessionRequestDto) {
        sessionService.stopUserSession(stopSessionRequestDto.serverId(), stopSessionRequestDto.userId());
        return new ResponseEntity<>("User session is stopped.", HttpStatus.OK);
    }
}
