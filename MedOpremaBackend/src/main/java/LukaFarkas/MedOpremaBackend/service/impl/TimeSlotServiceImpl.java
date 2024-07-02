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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

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
                .map(timeSlot -> new TimeSlotDto(timeSlot.getId(), timeSlot.getStartTime().toString(), timeSlot.getEndTime().toString(), timeSlot.getEquipment().getEquipment_id(), timeSlot.isBooked()))
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
                .map(timeSlot -> new TimeSlotDto(timeSlot.getId(), timeSlot.getStartTime().toString(), timeSlot.getEndTime().toString(), timeSlot.getEquipment().getEquipment_id(), timeSlot.isBooked()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TimeSlotDto> findAvailableTimeSlotsByEquipmentId(Long equipmentId) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAllByEquipmentId(equipmentId);
        return timeSlots.stream()
                .filter(timeSlot -> !timeSlot.isBooked())
                .map(timeSlot -> new TimeSlotDto(timeSlot.getId(), timeSlot.getStartTime().toString(), timeSlot.getEndTime().toString(), timeSlot.getEquipment().getEquipment_id(), timeSlot.isBooked()))
                .collect(Collectors.toList());
    }

    @Override
    public TimeSlotDto getTimeSlot(Long id) {
        Optional<TimeSlot> timeSlot = timeSlotRepository.findById(id);
        if (timeSlot.isPresent()) {
            TimeSlot ts = timeSlot.get();
            TimeSlotDto dto = new TimeSlotDto(ts.getId(), ts.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), ts.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), ts.getEquipment().getEquipment_id(), ts.isBooked());
            return dto;
        } else {
            return null;
        }
    }


}
