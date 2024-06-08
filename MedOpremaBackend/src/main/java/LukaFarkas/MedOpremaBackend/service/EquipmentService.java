package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.entity.Equipment;

public interface EquipmentService {
    Equipment findById(Long equipmentId);
}
