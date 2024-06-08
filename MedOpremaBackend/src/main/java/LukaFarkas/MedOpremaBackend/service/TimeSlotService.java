package LukaFarkas.MedOpremaBackend.service;


import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotService {
    public List<TimeSlotDto> getAvailableTimeSlots(Long equipmentId, LocalDateTime start, LocalDateTime end);

    public TimeSlotDto saveTimeSlot(TimeSlotDto timeSlotDTO);

    public TimeSlotDto findById(Long timeSlotId);
}
