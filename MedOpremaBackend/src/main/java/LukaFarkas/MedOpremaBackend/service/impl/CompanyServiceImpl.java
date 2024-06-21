package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.Company;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.mapper.CompanyMapper;
import LukaFarkas.MedOpremaBackend.mapper.EquipmentMapper;
import LukaFarkas.MedOpremaBackend.repository.CompanyRepository;
import LukaFarkas.MedOpremaBackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private CompanyRepository companyRepository;

    private CompanyMapper companyMapper;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = CompanyMapper.toEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return CompanyMapper.toDTO(savedCompany);
    }

    @Override
    public List<CompanyDto> createCompanies(List<CompanyDto> companyDtos) {
        List<Company> companies = companyDtos.stream()
                .map(CompanyMapper::toEntity)
                .collect(Collectors.toList());
        List<Company> savedCompanies = companyRepository.saveAll(companies);
        return savedCompanies.stream()
                .map(CompanyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentDto> getAllEquipmentByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        return company.getEquipment().stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Company findById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        return company;
    }


    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(CompanyMapper::toDTO).collect(Collectors.toList());
    }
}
