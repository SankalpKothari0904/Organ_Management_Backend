package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.PatientInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.Optional;

@Service
public class PatientService {
    private PatientInfoDAO patientInfoDAO;

    @Autowired
    public PatientService(PatientInfoDAO patientInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
    }

    @Transactional
    public PatientInformation updatePatientInfo(PatientInformation patientInformation) {
        return patientInfoDAO.save(patientInformation);
    }

    @Transactional
    public PatientInformation addPatientInfo(PatientInformation patientInformation) {
        return patientInfoDAO.save(patientInformation);
    }

    public PatientInformation viewPatientByUserId(UUID id) {
        return patientInfoDAO.getPatientByUserId(id);
    }

    public Optional<PatientInformation> viewPatientInfo(UUID id) {
        return patientInfoDAO.findById(id);
    }
}

