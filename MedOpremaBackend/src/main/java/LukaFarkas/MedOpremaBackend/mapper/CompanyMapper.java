package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.Company;
import LukaFarkas.MedOpremaBackend.entity.Equipment;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static CompanyDto toDTO(Company company) {
        if (company == null) {
            return null;
        }

        List<EquipmentDto> equipmentDTOs = company.getEquipment().stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());

        return new CompanyDto(
                company.getCompany_id(),
                company.getCompanyName(),
                company.getAddress(),
                equipmentDTOs
        );
    }

    public static Company toEntity(CompanyDto companyDTO) {
        if (companyDTO == null) {
            return null;
        }

        List<Equipment> equipments = companyDTO.getEquipment().stream()
                .map(EquipmentMapper::toEntity)
                .collect(Collectors.toList());

        Company company = new Company();
        company.setCompany_id(companyDTO.getCompanyId());
        company.setCompanyName(companyDTO.getCompanyName());
        company.setAddress(companyDTO.getAddress());
        company.setEquipment(equipments);

        return company;
    }

}
