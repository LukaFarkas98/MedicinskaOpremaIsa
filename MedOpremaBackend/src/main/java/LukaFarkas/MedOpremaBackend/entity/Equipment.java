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
@Table(name = "Equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipment_id;
    @Column(name = "equipment_name")
    private String equipment_name;
    @Column(name = "equipment_type")
    private String equipment_type;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
