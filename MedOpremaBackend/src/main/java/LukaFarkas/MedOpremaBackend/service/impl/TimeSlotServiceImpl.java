package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;
import LukaFarkas.MedOpremaBackend.mapper.TimeSlotMapper;
import LukaFarkas.MedOpremaBackend.repository.TimeSlotRepository;
import LukaFarkas.MedOpremaBackend.service.EquipmentService;
import LukaFarkas.MedOpremaBackend.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotServiceImpl implements TimeSlotService{

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EquipmentService equipmentService;



    @Override
    public List<TimeSlotDto> getAvailableTimeSlots(Long equipmentId, LocalDateTime start, LocalDateTime end) {
        Equipment equipment = equipmentService.findById(equipmentId);
        List<TimeSlot> timeSlots = timeSlotRepository.findByEquipmentAndStartTimeAfterAndEndTimeBefore(equipment, start, end);
        return timeSlots.stream().map(TimeSlotMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TimeSlotDto saveTimeSlot(TimeSlotDto timeSlotDTO) {
        Equipment equipment = equipmentService.findById(timeSlotDTO.getEquipmentId());
        TimeSlot timeSlot = TimeSlotMapper.toEntity(timeSlotDTO, equipment);
        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);
        return TimeSlotMapper.toDTO(savedTimeSlot);
    }

    @Override
    public TimeSlotDto findById(Long timeSlotId) {
        return TimeSlotMapper.toDTO(timeSlotRepository.findById(timeSlotId).orElseThrow(() -> new RuntimeException("TimeSlot not found")));
    }
}
