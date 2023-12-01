package com.example.OrganManagementSystem.ServiceTests;
import com.example.OrganManagementSystem.dao.DonorDAO;
import com.example.OrganManagementSystem.dao.DonorRecipientMatchDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.RecipientDAO;
import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.exception.RecipientNotFoundException;
import com.example.OrganManagementSystem.service.MatchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {
    @Mock
    private DonorRecipientMatchDAO donorRecipientMatchDAO;

    @Mock
    private DonorDAO donorDAO;

    @Mock
    private RecipientDAO recipientDAO;

    @Mock
    private PatientInfoDAO patientInfoDAO;

    @Autowired @InjectMocks
    private MatchService matchService;

    private DonorRecipientMatch donorRecipientMatch;
    private List<DonorRecipientMatch> donorRecipientMatchList;
    private Donor donor;
    private Recipient recipient;
    private PatientInformation patientInformation;

    private List<Recipient> recipients;
    private List<Donor> donors;

    @BeforeEach
    public void setUp(){
        this.donor = new Donor("Kidney");
        this.donor.setDonorId(1);
        this.recipient = new Recipient("Kidney",10);
        this.recipient.setRecipientId(1);
        this.donorRecipientMatch = new DonorRecipientMatch(donor,recipient,0);
        this.donorRecipientMatch.setId(1);
        this.donorRecipientMatchList = new ArrayList<>();
        this.patientInformation = new PatientInformation("ABC","F","9090909090",33,"B+");
        this.patientInformation.setPatientId(1);

        this.recipients = new ArrayList<>();
        Recipient temp1 = new Recipient(10, "Kidney", 10);
        temp1.setPatientInformation(patientInformation);
        Recipient temp2 = new Recipient(11, "Eyes", 9);
        temp2.setPatientInformation(patientInformation);
        donor.setPatientInformation(patientInformation);
        recipients.add(temp1);
        recipients.add(temp2);

        this.donors = new ArrayList<>();
    }

    @AfterEach
    public void tearDown(){
        this.donor = null;
        this.recipient = null;
        this.donorRecipientMatch = null;
        this.patientInformation = null;
        this.donorRecipientMatchList = null;
    }

    @Test
    public void givenDonorIdReturnsMatchInfo(){
        when(donorDAO.findById(1)).thenReturn(Optional.of(donor));
        when(donorRecipientMatchDAO.getMatchByDonorId(1)).thenReturn(donorRecipientMatch);
        DonorRecipientMatch donorRecipientMatch1 = matchService.getMatchByDonorId(1);
        assertThat(donorRecipientMatch1).isEqualTo(donorRecipientMatch);
        verify(donorDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchByDonorId(1);
    }

    @Test
    public void givenRecipientIdReturnsMatchInfo(){
        when(recipientDAO.findById(1)).thenReturn(Optional.of(recipient));
        when(donorRecipientMatchDAO.getMatchByRecipientId(1)).thenReturn(donorRecipientMatch);
        DonorRecipientMatch donorRecipientMatch1 = matchService.getMatchByRecipientId(1);
        assertThat(donorRecipientMatch1).isEqualTo(donorRecipientMatch);
        verify(recipientDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchByRecipientId(1);
    }

    @Test
    public void givenDonorIdNullThrowsException(){
        when(donorDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(DonorNotFoundException.class, ()->matchService.getMatchByDonorId(1));
        verify(donorDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchByDonorId(any());
    }

    @Test
    public void givenRecipientIdNullThrowsException(){
        when(recipientDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(RecipientNotFoundException.class, ()->matchService.getMatchByRecipientId(1));
        verify(recipientDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchByRecipientId(any());
    }

    @Test
    public void givenDonorPatientIdReturnsAllMatches(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        when(donorRecipientMatchDAO.getMatchesByDonorPatientId(1)).thenReturn(donorRecipientMatchList);
        List<DonorRecipientMatch> donorRecipientMatchList1 = matchService.getMatchesByDonorPatientId(1);
        assertEquals(donorRecipientMatchList1,donorRecipientMatchList);
        verify(patientInfoDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchesByDonorPatientId(1);
    }

    @Test
    public void givenRecipientPatientIdReturnsAllMatches(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        when(donorRecipientMatchDAO.getMatchesByRecipientPatientId(1)).thenReturn(donorRecipientMatchList);
        List<DonorRecipientMatch> donorRecipientMatchList1 = matchService.getMatchesByRecipientPatientId(1);
        assertEquals(donorRecipientMatchList1,donorRecipientMatchList);
        verify(patientInfoDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchesByRecipientPatientId(1);
    }

    @Test
    public void givenPatientIdOfDonorNullThrowsException(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, ()->matchService.getMatchesByDonorPatientId(1));
        verify(patientInfoDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchesByDonorPatientId(any());
    }

    @Test
    public void givenPatientIdOfRecipientNullThrowsException(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, ()->matchService.getMatchesByRecipientPatientId(1));
        verify(patientInfoDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchesByRecipientPatientId(any());
    }

    @Test
    public void givenDonorAddedFindMatches(){
        when(donorDAO.findById(1)).thenReturn(Optional.of(donor));
        when(recipientDAO.getAll(any())).thenReturn(recipients);
        when(donorRecipientMatchDAO.getMatchByRecipientId(any())).thenReturn(null);
        when(recipientDAO.findById(any())).thenReturn(Optional.of(recipients.get(0)));

        DonorRecipientMatch donorRecipientMatch1 = this.matchService.matchDonorToRecipient(donor);
        verify(donorRecipientMatchDAO, times(1)).save(any());
    }
}
