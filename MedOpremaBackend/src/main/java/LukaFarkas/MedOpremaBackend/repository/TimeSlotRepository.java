package LukaFarkas.MedOpremaBackend.repository;

import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    //nemoj prikazati timeslot za opremu ako se njegov id vec nalazi u nekom appointmentu
    @Query("SELECT ts FROM TimeSlot ts WHERE ts.id NOT IN (SELECT a.timeSlot.id FROM Appointment a WHERE a.equipment.equipment_id = :equipmentId)")
    List<TimeSlot> findAvailableTimeSlotsByEquipmentId(@Param("equipmentId") Long equipmentId);

    List<TimeSlot> findByEquipmentAndStartTimeAfterAndEndTimeBefore(Equipment equipment, LocalDateTime start, LocalDateTime end);

    @Query("SELECT ts FROM TimeSlot ts WHERE ts.equipment.equipment_id = :equipmentId")
    List<TimeSlot> findAllByEquipmentId(@Param("equipmentId") Long equipmentId);
}
