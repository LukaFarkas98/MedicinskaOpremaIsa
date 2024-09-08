package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.Company;
import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.User;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.mapper.CompanyMapper;
import LukaFarkas.MedOpremaBackend.mapper.EquipmentMapper;
import LukaFarkas.MedOpremaBackend.repository.CompanyRepository;
import LukaFarkas.MedOpremaBackend.repository.EquipmentRepository;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import LukaFarkas.MedOpremaBackend.service.CompanyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private CompanyMapper companyMapper;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = CompanyMapper.toEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return CompanyMapper.toDTO(savedCompany);
    }

    @Override
    @Transactional
    public List<CompanyDto> createCompanies(List<CompanyDto> companyDtos, Long adminId) {
        List<Company> savedCompanies = new ArrayList<>();

        // Fetch the admin user
        User admin = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));

        for (CompanyDto companyDto : companyDtos) {
            Company company = CompanyMapper.toEntity(companyDto);

            // Set the admin for the company
            company.setAdmin(admin);

            // Detach equipment from the company to avoid detached entity issues
            List<Equipment> equipmentList = company.getEquipment();
            company.setEquipment(new ArrayList<>());

            Company savedCompany = companyRepository.save(company);
            for (Equipment equipment : equipmentList) {
                equipment.setCompany(savedCompany);  // Set the persisted company
                savedCompany.getEquipment().add(equipment);
                equipmentRepository.save(equipment);
            }

            // Now save the company again with the equipment attached
            savedCompany = companyRepository.save(savedCompany);
            savedCompanies.add(savedCompany);
        }

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
