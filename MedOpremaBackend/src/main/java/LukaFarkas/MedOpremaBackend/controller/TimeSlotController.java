package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.service.EquipmentService;
import LukaFarkas.MedOpremaBackend.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping("timeslots/bulk")
    public ResponseEntity<List<TimeSlotDto>> createTimeSlots(@RequestBody List<TimeSlotDto> timeSlotDtos) {
        List<TimeSlotDto> createdTimeSlots = timeSlotService.createTimeSlots(timeSlotDtos);
        return ResponseEntity.ok(createdTimeSlots);
    }

    @PostMapping("/timeslots")
    public TimeSlotDto createTimeSlot(@RequestBody TimeSlotDto timeSlotDTO) {
        return timeSlotService.saveTimeSlot(timeSlotDTO);
    }

    @GetMapping("/available/{equipmentId}")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlots(@PathVariable Long equipmentId) {
        List<TimeSlotDto> availableTimeSlots = timeSlotService.findAvailableTimeSlotsByEquipmentId(equipmentId);
        return ResponseEntity.ok(availableTimeSlots);
    }

    @GetMapping("/timeslots/{id}")
    public ResponseEntity<TimeSlotDto> getTimeSlot(@PathVariable Long id) {
        TimeSlotDto timeSlotDto = timeSlotService.getTimeSlot(id);
        if(timeSlotDto != null){
            return ResponseEntity.ok(timeSlotDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
