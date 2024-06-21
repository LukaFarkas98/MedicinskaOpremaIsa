package LukaFarkas.MedOpremaBackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Complaint {

    public enum ComplaintType {
        SERVICE_QUALITY,
        EQUIPMENT_ISSUE,
        BILLING_PROBLEM,
        OTHER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    private ComplaintType complaintType;

    @Column(nullable = false)
    private String complaintText;

    private LocalDateTime timestamp;

    public Complaint() {}

    public Complaint(User user, Company company, ComplaintType complaintType, String complaintText, LocalDateTime timestamp) {
        this.user = user;
        this.company = company;
        this.complaintType = complaintType;
        this.complaintText = complaintText;
        this.timestamp = timestamp;
    }


    // Getters and Setters
}


