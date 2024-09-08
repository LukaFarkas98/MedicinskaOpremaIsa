package LukaFarkas.MedOpremaBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "complaints")
public class Complaint {
    @Id
    @Column(name = "id")
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

    @Column(nullable = true)
    private String response;  // Field to store the super admin's response

    // Getters and Setters
}
