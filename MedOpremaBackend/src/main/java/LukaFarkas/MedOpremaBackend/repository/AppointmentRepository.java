package LukaFarkas.MedOpremaBackend.repository;

import LukaFarkas.MedOpremaBackend.entity.Appointment;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.company.id = :companyId")
    List<Appointment> findByCompanyId(@Param("companyId") Long companyId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Appointment a WHERE a.equipment.equipment_id = :equipmentId AND a.timeSlot.id = :timeSlotId AND a.company.id = :companyId")
    Optional<Appointment> findByEquipmentAndTimeSlotAndCompany(@Param("equipmentId") Long equipmentId, @Param("timeSlotId") Long timeSlotId, @Param("companyId") Long companyId);


    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId")
    List<Appointment> findAllByUserId(@Param("userId") Long userId);

    Appointment findByUserIdAndCompany_Id(Long userId, Long companyId);

    List<Appointment> findByUserIdAndCompanyId(Long userId, Long companyId);
}
