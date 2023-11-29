package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.DoctorInfoDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.entity.PatientInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorService {

    private PatientInfoDAO patientInfoDAO;
    private DoctorInfoDAO doctorInfoDAO;

    @Autowired
    public DoctorService(PatientInfoDAO patientInfoDAO, DoctorInfoDAO doctorInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
        this.doctorInfoDAO = doctorInfoDAO;
    }

    public DoctorInformation getDoctorByUserId(UUID id){
        return doctorInfoDAO.getDoctorByUserId(id);
    }

    public DoctorInformation addDocInfo(DoctorInformation doctorInformation) {
        return doctorInfoDAO.save(doctorInformation);
    }

    public List<PatientInformation> showPatients() {
        return (List<PatientInformation>) patientInfoDAO.findAll();
    }

    public Optional<PatientInformation> showPatientById(UUID id) {
        return patientInfoDAO.findById(id);
    }

    public Optional<DoctorInformation> viewMyInfo(UUID Id){
        return doctorInfoDAO.findById(Id);
    }

    public DoctorInformation updateMyInfo(DoctorInformation doctorInformation){
        return doctorInfoDAO.save(doctorInformation);
    }

}

