package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;

import java.util.List;

public interface EquipmentService {
    Equipment findById(Long equipmentId);

    List<TimeSlot> getAllAvailableTimeSlots(Long equipmentId);

    EquipmentDto getEquipmentDtoByAppointmentId(Long appointmentId);
}
