package com.example.OrganManagementSystem.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "matches")
public class DonorRecipientMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "match_id")
    private UUID id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    @JoinColumn(name = "completed")
    private Integer completed;

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public DonorRecipientMatch(UUID id, Donor donor, Recipient recipient, Integer completed) {
        this.id = id;
        this.donor = donor;
        this.recipient = recipient;
        this.completed = completed;
    }

    public DonorRecipientMatch(Donor donor, Recipient recipient, Integer completed) {
        this.donor = donor;
        this.recipient = recipient;
        this.completed = completed;
    }

    public DonorRecipientMatch() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
