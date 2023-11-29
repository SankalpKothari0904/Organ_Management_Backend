package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.exception.DoctorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private AdminService adminService;

    @Autowired
    public AdminRestController(AdminService theAdminService) {
        this.adminService = theAdminService;
    }

    @GetMapping("/doctors")
    public List<DoctorInformation> findAllDocs() {
        return adminService.showDoctors();
    }

    @GetMapping("/patients")
    public List<PatientInformation> findAllPatients() {
        return adminService.showPatients();
    }

    @GetMapping("/doctors/{doctorId}")
    public Optional<DoctorInformation> getDoctorInformation(@PathVariable UUID doctorId) throws DoctorNotFoundException {
        Optional<DoctorInformation> theDoctorInformation = adminService.showDoctorById(doctorId);

        if (theDoctorInformation.isEmpty()) {
            throw new DoctorNotFoundException();
        }

        return theDoctorInformation;
    }

    @GetMapping("/patients/{patientId}")
    public Optional<PatientInformation> getPatientInformation(@PathVariable UUID patientId) throws PatientNotFoundException{
        Optional<PatientInformation> thePatientInformation = adminService.showPatientById(patientId);

        if (thePatientInformation.isEmpty()) {
            throw new PatientNotFoundException();
        }

        return thePatientInformation;
    }
}
