package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.repository.EquipmentRepository;
import LukaFarkas.MedOpremaBackend.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment findById(Long equipmentId) {
        return equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }
}
