package LukaFarkas.MedOpremaBackend.entity;

import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointment_id;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


    @ElementCollection
    @CollectionTable(name = "appointment_times",
            joinColumns = @JoinColumn(name = "appointment_id"))
    @Column(name = "time_slot")
    private List<LocalDateTime> timeSlots;


}
