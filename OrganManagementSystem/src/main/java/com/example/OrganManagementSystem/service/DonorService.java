package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.DonorDAO;
import com.example.OrganManagementSystem.entity.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DonorService {
    private DonorDAO donorDAO;

    @Autowired
    public DonorService(DonorDAO donorDAO) {
        this.donorDAO = donorDAO;
    }

    public List<Donor> getDonorByPatientId(UUID id){
        return donorDAO.getDonorByPatientId(id);
    }

    public Donor addInfo(Donor donor){
        return donorDAO.save(donor);
    }

    public Donor updateInfo(Donor donor){
        return donorDAO.save(donor);
    }

    public Optional<Donor> viewInfoById(UUID id){
        return donorDAO.findById(id);
    }
}
