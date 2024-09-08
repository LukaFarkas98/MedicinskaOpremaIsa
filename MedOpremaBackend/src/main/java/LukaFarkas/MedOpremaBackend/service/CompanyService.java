package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.Company;

import java.util.List;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto);

    List<CompanyDto> createCompanies(List<CompanyDto> companies, Long adminID);

    List<EquipmentDto> getAllEquipmentByCompanyId(Long companyId);

    Company findById(Long companyId);

    List<CompanyDto> getAllCompanies();
}
