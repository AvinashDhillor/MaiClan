package com.ad.maiclan.controller;

import com.ad.maiclan.dto.ClanScoreRequestDto;
import com.ad.maiclan.dto.CreateClanRequestDto;
import com.ad.maiclan.dto.GetAllClanRequestDto;
import com.ad.maiclan.model.Clan;
import com.ad.maiclan.service.ClanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClanController {

    private final ClanService clanService;

    @Autowired
    public ClanController(final ClanService clanService) {
        this.clanService = clanService;
    }

    @PostMapping("/clan")
    public ResponseEntity<Object> createClan(@RequestBody final CreateClanRequestDto createClanRequestDto) {
        clanService.addClan(createClanRequestDto.serverId(), createClanRequestDto.clanName());
        return new ResponseEntity<>("Clan is created.", HttpStatus.OK);
    }

    @GetMapping("/clan/score")
    public ResponseEntity<Object> clanScore(@RequestBody final ClanScoreRequestDto clanScoreRequestDto) {
        double score = clanService.clanScore(clanScoreRequestDto.serverId(), clanScoreRequestDto.clanName());
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @GetMapping(value = "/clan", produces = "application/json")
    public ResponseEntity<List<Clan>> getAllClan(@RequestBody final GetAllClanRequestDto getAllClanRequestDto) {
        List<Clan> clans = clanService.getAllClan(getAllClanRequestDto.serverId());
        return new ResponseEntity<>(clans, HttpStatus.OK);
    }
}
