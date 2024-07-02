package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;

import java.time.LocalDateTime;

public class TimeSlotMapper {
    public static TimeSlotDto toDTO(TimeSlot timeSlot) {
        return new TimeSlotDto(
                timeSlot.getId(),
                timeSlot.getStartTime().toString(),
                timeSlot.getEndTime().toString(),
                timeSlot.getEquipment().getEquipment_id(),
                timeSlot.isBooked()
        );
    }

    public static TimeSlot toEntity(TimeSlotDto timeSlotDTO, Equipment equipment) {
        return new TimeSlot(
                timeSlotDTO.getId(),
                LocalDateTime.parse(timeSlotDTO.getStartTime()),
                LocalDateTime.parse(timeSlotDTO.getEndTime()),
                equipment,
                timeSlotDTO.isBooked()
        );
    }
}
