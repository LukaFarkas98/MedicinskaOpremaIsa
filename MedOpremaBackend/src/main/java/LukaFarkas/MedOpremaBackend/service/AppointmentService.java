package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.repository.AppointmentRepository;

import java.util.List;

public interface AppointmentService {
    AppointmentDto createAppointment(AppointmentDto appointmentDto);

    List<AppointmentDto> getAppointmentsByCompanyId(Long companyId);
}
