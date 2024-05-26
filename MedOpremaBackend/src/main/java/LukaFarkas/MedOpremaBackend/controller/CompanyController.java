package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.CompanyDto;
import LukaFarkas.MedOpremaBackend.dto.EquipmentDto;
import LukaFarkas.MedOpremaBackend.repository.CompanyRepository;
import LukaFarkas.MedOpremaBackend.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyRepository companyRepository;
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<List<CompanyDto>> createCompanies(@RequestBody List<CompanyDto> companyDtos){
        List<CompanyDto> savedCompanies = companyService.createCompanies(companyDtos);
        return new ResponseEntity<>(savedCompanies, HttpStatus.CREATED);
    }

    @GetMapping("/{companyId}/equipment")
    public ResponseEntity<List<EquipmentDto>> getAllEquipmentByCompanyId(@PathVariable Long companyId) {
        List<EquipmentDto> equipmentList = companyService.getAllEquipmentByCompanyId(companyId);
        return ResponseEntity.ok(equipmentList);
    }

}
