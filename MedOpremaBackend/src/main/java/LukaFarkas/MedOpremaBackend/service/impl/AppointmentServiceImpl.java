package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.*;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.mapper.AppointmentMapper;
import LukaFarkas.MedOpremaBackend.mapper.TimeSlotMapper;
import LukaFarkas.MedOpremaBackend.mapper.UserMapper;
import LukaFarkas.MedOpremaBackend.repository.AppointmentRepository;
import LukaFarkas.MedOpremaBackend.repository.CompanyRepository;
import LukaFarkas.MedOpremaBackend.repository.EquipmentRepository;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import LukaFarkas.MedOpremaBackend.service.*;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {


    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TimeSlotService timeSlotService;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) throws MessagingException, jakarta.mail.MessagingException, IOException, WriterException {

        Optional<Appointment> existingAppointment = appointmentRepository.findByEquipmentAndTimeSlotAndCompany(
                appointmentDto.getEquipmentId(), appointmentDto.getTimeSlotId(), appointmentDto.getCompanyId());

        if (existingAppointment.isPresent()) {
            throw new IllegalStateException("An appointment already exists for this equipment at the selected time slot.");
        }else{
            Company company = companyService.findById(appointmentDto.getCompanyId());
            Equipment equipment = equipmentService.findById(appointmentDto.getEquipmentId());
            User user = UserMapper.mapToUser(userService.getUserById(appointmentDto.getUserId()));
            TimeSlotDto timeSlot = timeSlotService.findById(appointmentDto.getTimeSlotId());
            Appointment appointment = new Appointment(appointmentDto.getAppointmentId(), equipment, company, user, TimeSlotMapper.toEntity(timeSlot, equipment));
            return AppointmentMapper.toDto(appointmentRepository.save(appointment));
        }

    }

    @Override
    public List<AppointmentDto> getAppointmentsByCompanyId(Long companyId) {
        List<Appointment> appointments = appointmentRepository.findByCompanyId(companyId);
        return appointments.stream()
                .map(AppointmentMapper::toDto)
                .collect(Collectors.toList());
    }

}
