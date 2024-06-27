package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.repository.EquipmentRepository;
import LukaFarkas.MedOpremaBackend.repository.TimeSlotRepository;
import LukaFarkas.MedOpremaBackend.service.EquipmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Override
    public Equipment findById(Long equipmentId) {
        return equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    public List<TimeSlot> getAllAvailableTimeSlots(Long equipmentId) {
        // Assuming Equipment and TimeSlot entities are defined and there are corresponding repositories.
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + equipmentId));

        // Fetch all time slots associated with the given equipment
        return timeSlotRepository.findAllByEquipmentId(equipmentId);
    }
}
