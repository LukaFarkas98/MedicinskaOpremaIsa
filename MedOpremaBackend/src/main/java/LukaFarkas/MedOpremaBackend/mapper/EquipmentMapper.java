package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.Equipment;

public class EquipmentMapper {

    public static EquipmentDto toDTO(Equipment equipment) {
        if (equipment == null) {
            return null;
        }

        return new EquipmentDto(
                equipment.getEquipment_id(),
                equipment.getEquipment_name(),
                equipment.getEquipment_type(),
                equipment.getCompany().getId()
        );
    }

    public static Equipment toEntity(EquipmentDto equipmentDTO) {
        if (equipmentDTO == null) {
            return null;
        }

        Equipment equipment = new Equipment();
        equipment.setEquipment_id(equipmentDTO.getEquipmentId());
        equipment.setEquipment_name(equipmentDTO.getEquipmentName());
        equipment.setEquipment_type(equipmentDTO.getEquipmentType());

        return equipment;
    }
}
