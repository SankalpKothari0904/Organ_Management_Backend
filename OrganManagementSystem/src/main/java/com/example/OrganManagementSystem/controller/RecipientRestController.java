package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.entity.User;
import com.example.OrganManagementSystem.exception.RecipientNotFoundException;
import com.example.OrganManagementSystem.exception.UnauthorisedUserException;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import com.example.OrganManagementSystem.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/recipient")
public class RecipientRestController {
    private RecipientService recipientService;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public RecipientRestController(RecipientService recipientService, JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil){
        this.recipientService = recipientService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @GetMapping("/viewInfo")
    public List<Recipient> viewRecipient(@RequestHeader String Authorization){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        return recipientService.getRecipientByPatientId(user.getPatientInformation().getPatientId());
    }

    @GetMapping("/viewInfo/{id}")
    public Optional<Recipient> viewRecipientById(@PathVariable UUID id, @RequestHeader String Authorization) throws RecipientNotFoundException, UnauthorisedUserException {
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        Optional<Recipient> recipient = recipientService.viewInfoById(id);

        if (recipient.isEmpty()){
            throw new RecipientNotFoundException();
        }else if (recipient.get().getPatientInformation().getPatientId() != user.getPatientInformation().getPatientId()){
            throw new UnauthorisedUserException();
        }

        return recipient;
    }

    @PostMapping("/addInfo")
    public Recipient addRecipientInfo(@RequestHeader String Authorization, @RequestBody Recipient recipient){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        recipient.setPatientInformation(user.getPatientInformation());
        return recipientService.addInfo(recipient);
    }

    @PostMapping("/updateInfo")
    public Recipient updateRecipientInfo(@RequestHeader String Authorization, @RequestBody Recipient recipient){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        recipient.setPatientInformation(user.getPatientInformation());
        return recipientService.updateInfo(recipient);
    }
}

