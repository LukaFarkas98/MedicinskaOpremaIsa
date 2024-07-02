package LukaFarkas.MedOpremaBackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true)
    private User admin; // Assuming admin is a type of User

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private LocalDateTime date;

    // Getters and Setters
}
