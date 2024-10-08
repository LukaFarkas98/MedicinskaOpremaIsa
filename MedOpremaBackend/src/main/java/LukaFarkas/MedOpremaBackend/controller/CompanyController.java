package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.entity.TimeSlot;
import LukaFarkas.MedOpremaBackend.mapper.CompanyMapper;
import LukaFarkas.MedOpremaBackend.repository.CompanyRepository;
import LukaFarkas.MedOpremaBackend.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyRepository companyRepository;
    private CompanyService companyService;


    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole = 'ADMIN'")
    @GetMapping("{companyId}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long companyId){
        CompanyDto company = CompanyMapper.toDTO(companyService.findById(companyId));
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<CompanyDto>> createCompanies(@RequestBody List<CompanyDto> companyDtos, Long adminID){
        List<CompanyDto> savedCompanies = companyService.createCompanies(companyDtos, adminID);
        return new ResponseEntity<>(savedCompanies, HttpStatus.CREATED);
    }

    @GetMapping("/{companyId}/equipment")
    public ResponseEntity<List<EquipmentDto>> getAllEquipmentByCompanyId(@PathVariable Long companyId) {
        List<EquipmentDto> equipmentList = companyService.getAllEquipmentByCompanyId(companyId);
        return ResponseEntity.ok(equipmentList);
    }

}
