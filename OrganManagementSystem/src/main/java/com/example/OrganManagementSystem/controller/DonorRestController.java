package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import com.example.OrganManagementSystem.entity.User;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.UnauthorisedUserException;
import com.example.OrganManagementSystem.service.DonorService;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import com.example.OrganManagementSystem.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/donor")
public class DonorRestController {
    private DonorService donorService;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private MatchService matchService;

    @Autowired
    public DonorRestController(DonorService donorService, JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil, MatchService matchService){
        this.donorService = donorService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.matchService = matchService;
    }

    @GetMapping("/viewInfo")
    public List<Donor> viewDonor(@RequestHeader String Authorization){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        return donorService.getDonorByPatientId(user.getPatientInformation().getPatientId());
    }

    @GetMapping("/viewInfo/{id}")
    public Optional<Donor> viewDonorById(@PathVariable UUID id, @RequestHeader String Authorization) throws DonorNotFoundException, UnauthorisedUserException {
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        Optional<Donor> donor = donorService.viewInfoById(id);

        if (donor.isEmpty()){
            throw new DonorNotFoundException();
        }else if (donor.get().getPatientInformation().getPatientId() != user.getPatientInformation().getPatientId()){
            throw new UnauthorisedUserException();
        }

        return donor;
    }

    @PostMapping("/addInfo")
    public DonorRecipientMatch addDonorInfo(@RequestHeader String Authorization, @RequestBody Donor donor){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        donor.setPatientInformation(user.getPatientInformation());
        Donor d = donorService.addInfo(donor);
        return matchService.matchDonorToRecipient(d);
    }

    @PutMapping("/updateInfo")
    public Donor updateDonorInfo(@RequestHeader String Authorization, @RequestBody Donor donor){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        donor.setPatientInformation(user.getPatientInformation());
        return donorService.updateInfo(donor);
    }
}
