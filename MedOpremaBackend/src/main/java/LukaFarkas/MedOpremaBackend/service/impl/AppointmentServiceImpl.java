package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.entity.Appointment;
import LukaFarkas.MedOpremaBackend.entity.Equipment;
import LukaFarkas.MedOpremaBackend.entity.Company;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.mapper.AppointmentMapper;
import LukaFarkas.MedOpremaBackend.repository.AppointmentRepository;
import LukaFarkas.MedOpremaBackend.repository.CompanyRepository;
import LukaFarkas.MedOpremaBackend.repository.EquipmentRepository;
import LukaFarkas.MedOpremaBackend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        Equipment equipment = equipmentRepository.findById(appointmentDto.getEquipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found."));
        Company company = companyRepository.findById(appointmentDto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found."));

        Appointment appointment = AppointmentMapper.toEntity(appointmentDto, equipment, company);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentMapper.toDto(savedAppointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentsByCompanyId(Long companyId) {
        List<Appointment> appointments =  appointmentRepository.findAll()
                .stream()
                .filter(a -> a.getCompany().getCompany_id().equals(companyId))
                .collect(Collectors.toList());

        return appointments.stream()
                .map(AppointmentMapper::toDto)
                .collect(Collectors.toList());

    }
}
