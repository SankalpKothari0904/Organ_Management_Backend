package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.RecipientDAO;
import com.example.OrganManagementSystem.entity.Recipient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipientService {
    private RecipientDAO recipientDAO;

    public RecipientService(RecipientDAO recipientDAO) {
        this.recipientDAO = recipientDAO;
    }

    public List<Recipient> getRecipientByPatientId(UUID id){
        return recipientDAO.getRecipientByPatientId(id);
    }

    public Recipient addInfo(Recipient recipient){
        return recipientDAO.save(recipient);
    }

    public Recipient updateInfo(Recipient recipient){
        return recipientDAO.save(recipient);
    }

    public Optional<Recipient> viewInfoById(UUID id){
        return recipientDAO.findById(id);
    }
}
