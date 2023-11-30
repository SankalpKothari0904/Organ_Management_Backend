package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import com.example.OrganManagementSystem.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/match")
public class MatchRestController {

    private MatchService matchService;

    @Autowired
    public MatchRestController(MatchService matchService){
        this.matchService = matchService;
    }

    @GetMapping("/donor/{id}")
    public DonorRecipientMatch getByDonorId(@PathVariable UUID id){
        return matchService.getMatchByDonorId(id);
    }

    @GetMapping("/recipient/{id}")
    public DonorRecipientMatch getByRecipientId(@PathVariable UUID id){
        return matchService.getMatchByRecipientId(id);
    }

    @GetMapping("/patient/donor/{id}")
    public List<DonorRecipientMatch> getByDonorPatientId(@PathVariable UUID id){
        return matchService.getMatchesByDonorPatientId(id);
    }

    @GetMapping("/patient/recipient/{id}")
    public List<DonorRecipientMatch> getByRecipientPatientId(@PathVariable UUID id){
        return matchService.getMatchesByRecipientPatientId(id);
    }

    @PostMapping("/add")
    public DonorRecipientMatch addMatch(@RequestBody DonorRecipientMatch donorRecipientMatch){
        return matchService.addMatch(donorRecipientMatch);
    }

    @PutMapping("/update")
    public DonorRecipientMatch updateMatch(@RequestBody DonorRecipientMatch donorRecipientMatch){
        return matchService.updateMatch(donorRecipientMatch);
    }
}
