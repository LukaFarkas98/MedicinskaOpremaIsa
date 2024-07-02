package LukaFarkas.MedOpremaBackend.service;


import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotService {
    public List<TimeSlotDto> getAvailableTimeSlots(Long equipmentId, LocalDateTime start, LocalDateTime end);

    public TimeSlotDto saveTimeSlot(TimeSlotDto timeSlotDTO);

    public TimeSlotDto findById(Long timeSlotId);

    public List<TimeSlotDto> getAvailableTimeSlotsByEquipmentId(Long equipmentId);

    public List<TimeSlotDto> createTimeSlots(List<TimeSlotDto> timeSlotDtos);


    public List<TimeSlotDto> findAvailableTimeSlotsByEquipmentId(Long equipmentId);

    public TimeSlotDto getTimeSlot(Long id);
}
