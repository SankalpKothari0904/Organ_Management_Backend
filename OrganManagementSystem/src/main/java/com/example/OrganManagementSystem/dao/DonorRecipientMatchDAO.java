package com.example.OrganManagementSystem.dao;

import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DonorRecipientMatchDAO extends CrudRepository<DonorRecipientMatch, UUID> {
    @Query("SELECT drm from DonorRecipientMatch drm where drm.donor.donorId = :id")
    DonorRecipientMatch getMatchByDonorId(@Param("id") UUID id);

    @Query("SELECT drm from DonorRecipientMatch drm where drm.recipient.recipientId = :id")
    DonorRecipientMatch getMatchByRecipientId(@Param("id") UUID id);

    @Query("SELECT drm from DonorRecipientMatch drm where drm.recipient.patientInformation.patientId = :id")
    List<DonorRecipientMatch> getMatchesByRecipientPatientId(@Param("id") UUID id);

    @Query("SELECT drm from DonorRecipientMatch drm where drm.donor.patientInformation.patientId = :id")
    List<DonorRecipientMatch> getMatchesByDonorPatientId(@Param("id") UUID id);
}
