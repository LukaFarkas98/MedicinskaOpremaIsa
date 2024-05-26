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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long company_id;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "address")
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_company_id", referencedColumnName = "company_id")
    private List<Equipment> equipment;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}
