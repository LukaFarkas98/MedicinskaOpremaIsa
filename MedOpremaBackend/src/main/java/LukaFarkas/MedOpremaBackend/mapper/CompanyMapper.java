package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.Company;
import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.User;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    @Autowired
    private static UserRepository userRepository;
    public static CompanyDto toDTO(Company company) {
        if (company == null) {
            return null;
        }

        List<EquipmentDto> equipmentDTOs = company.getEquipment().stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());

        return new CompanyDto(
                company.getId(),
                company.getCompanyName(),
                company.getAddress(),
                equipmentDTOs,
                company.getAdmin().getId()
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
        company.setId(companyDTO.getId());
        company.setCompanyName(companyDTO.getCompanyName());
        company.setAddress(companyDTO.getAddress());
        company.setEquipment(equipments);
        if (companyDTO.getAdminId() != null) {
            User admin = userRepository.findById(companyDTO.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + companyDTO.getAdminId()));
            company.setAdmin(admin);
        }

        return company;
    }

}
