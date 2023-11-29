package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.DoctorInfoDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.DoctorInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.OrganManagementSystem.entity.PatientInformation;

@Service
public class AdminService {

    private PatientInfoDAO patientInfoDAO;
    private DoctorInfoDAO doctorInfoDAO;


    @Autowired
    public AdminService(PatientInfoDAO patientInfoDAO, DoctorInfoDAO doctorInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
        this.doctorInfoDAO = doctorInfoDAO;
    }

    public List<PatientInformation> showPatients() {
        return (List<PatientInformation>) patientInfoDAO.findAll();
    }

    public List<DoctorInformation> showDoctors() {
        return (List<DoctorInformation>) doctorInfoDAO.findAll();
    }

    public Optional<PatientInformation> showPatientById(UUID id) {
        return patientInfoDAO.findById(id);
    }

    public Optional<DoctorInformation> showDoctorById(UUID id) {
        return doctorInfoDAO.findById(id);
    }
}

