package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("/equipment/{equipmentId}/timeslots")
    public List<TimeSlotDto> getAvailableTimeSlots(@PathVariable Long equipmentId,
                                                   @RequestParam String start,
                                                   @RequestParam String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return timeSlotService.getAvailableTimeSlots(equipmentId, startTime, endTime);
    }

    @PostMapping("/timeslots")
    public TimeSlotDto createTimeSlot(@RequestBody TimeSlotDto timeSlotDTO) {
        return timeSlotService.saveTimeSlot(timeSlotDTO);
    }

}
