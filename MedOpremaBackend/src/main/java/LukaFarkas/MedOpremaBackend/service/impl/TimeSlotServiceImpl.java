package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;
import LukaFarkas.MedOpremaBackend.mapper.TimeSlotMapper;
import LukaFarkas.MedOpremaBackend.repository.EquipmentRepository;
import LukaFarkas.MedOpremaBackend.repository.TimeSlotRepository;
import LukaFarkas.MedOpremaBackend.service.EquipmentService;
import LukaFarkas.MedOpremaBackend.service.TimeSlotService;
import jakarta.transaction.Transactional;
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

    @Autowired
    private EquipmentRepository equipmentRepository;



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

    @Override
    public List<TimeSlotDto> getAvailableTimeSlotsByEquipmentId(Long equipmentId) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAllByEquipmentId(equipmentId);
        return timeSlots.stream()
                .map(timeSlot -> new TimeSlotDto(timeSlot.getId(), timeSlot.getStartTime().toString(), timeSlot.getEndTime().toString(), timeSlot.getEquipment().getEquipment_id()))
                .collect(Collectors.toList());
    }


    @Transactional
    public List<TimeSlotDto> createTimeSlots(List<TimeSlotDto> timeSlotDtos) {
        List<TimeSlot> timeSlots = timeSlotDtos.stream()
                .map(dto -> {
                    Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                            .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
                    return new TimeSlot(LocalDateTime.parse(dto.getStartTime()), LocalDateTime.parse(dto.getEndTime()), equipment);
                })
                .collect(Collectors.toList());
        timeSlots = timeSlotRepository.saveAll(timeSlots);
        return timeSlots.stream()
                .map(timeSlot -> new TimeSlotDto(timeSlot.getId(), timeSlot.getStartTime().toString(), timeSlot.getEndTime().toString(), timeSlot.getEquipment().getEquipment_id()))
                .collect(Collectors.toList());
    }


}
