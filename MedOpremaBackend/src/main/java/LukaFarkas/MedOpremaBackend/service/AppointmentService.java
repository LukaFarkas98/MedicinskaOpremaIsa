package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.repository.AppointmentRepository;
import com.google.zxing.WriterException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface AppointmentService {
    AppointmentDto createAppointment(AppointmentDto appointmentDto) throws MessagingException, jakarta.mail.MessagingException, IOException, WriterException;

    List<AppointmentDto> getAppointmentsByCompanyId(Long companyId);

    void cancelAppointment(Long appointmentId, Long userId);

    public List<AppointmentDto> getAllAppointmentsByUserId(Long userId);
}
