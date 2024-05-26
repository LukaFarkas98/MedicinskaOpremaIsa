package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;

import java.util.List;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto);

    List<CompanyDto> createCompanies(List<CompanyDto> companies);

    List<EquipmentDto> getAllEquipmentByCompanyId(Long companyId);
}
