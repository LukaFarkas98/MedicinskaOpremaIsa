package LukaFarkas.MedOpremaBackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Companies")
public class Company {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipment> equipment;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    // Add the relationship to the User entity for the admin
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;  // Assuming that User entity exists with an admin role

}
