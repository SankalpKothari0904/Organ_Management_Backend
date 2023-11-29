package com.example.OrganManagementSystem.dao;

import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipientDAO extends CrudRepository<Recipient, UUID> {
    @Query("SELECT r from Recipient r where r.patientInformation.patientId = :id")
    List<Recipient> getRecipientByPatientId(@Param("id") UUID id);
}
