package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.*;
import LukaFarkas.MedOpremaBackend.exception.ResourceNotFoundException;
import LukaFarkas.MedOpremaBackend.mapper.AppointmentMapper;
import LukaFarkas.MedOpremaBackend.mapper.TimeSlotMapper;
import LukaFarkas.MedOpremaBackend.mapper.UserMapper;
import LukaFarkas.MedOpremaBackend.repository.*;
import LukaFarkas.MedOpremaBackend.service.*;
import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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

    @Autowired
    private PenalPointRepository penalPointRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;


    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) throws MessagingException, jakarta.mail.MessagingException, IOException, WriterException {

        Optional<Appointment> existingAppointment = appointmentRepository.findByEquipmentAndTimeSlotAndCompany(
                appointmentDto.getEquipmentId(), appointmentDto.getTimeSlotId(), appointmentDto.getCompanyId());

        TimeSlot timeSlot = timeSlotRepository.findById(appointmentDto.getTimeSlotId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid time slot ID"));

        if(timeSlot.isBooked())
            return null;

        if (existingAppointment.isPresent()) {
            return null;
        }else{
            Appointment appointment = new Appointment();
            appointment.setCompany(companyService.findById(appointmentDto.getCompanyId()));
            appointment.setEquipment(equipmentService.findById(appointmentDto.getEquipmentId()));
            appointment.setUser(UserMapper.mapToUser(userService.getUserById(appointmentDto.getUserId())));
            TimeSlotDto timeSlot1 = timeSlotService.findById(appointmentDto.getTimeSlotId());
            appointment.setTimeSlot(TimeSlotMapper.toEntity(timeSlot1, appointment.getEquipment()));

            //sacuvamo da je bukiran timeslot taj
            timeSlot.setBooked(true);
            timeSlotRepository.save(timeSlot);
           // Appointment appointment = new Appointment(id, equipment, company, user, TimeSlotMapper.toEntity(timeSlot, equipment));
            //sacuvaj appointment u bazu i vrati ga natrag
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


    public TimeSlot getTimeSlotForAppointment(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalStateException("Appointment not found"));

        return appointment.getTimeSlot();
    }

    @Transactional
    public void cancelAppointment(Long appointmentId, Long userId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalStateException("Appointment not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appointmentTime = appointment.getTimeSlot().getStartTime();
        long hoursDifference = ChronoUnit.HOURS.between(now, appointmentTime);

        TimeSlot timeSlot = appointment.getTimeSlot();
        timeSlot.setBooked(false);
        timeSlotRepository.save(timeSlot);

        User user = appointment.getUser();
        int points = hoursDifference >= 24 ? 2 : 1; // poen ako manje od 24h a 2 ako vise

        PenalPoint penalPoint = new PenalPoint(user, points, now);
        penalPointRepository.save(penalPoint);

        //sta znam mozda zatreba nekad obrisacu ako ne bude trebalo
        if (!appointment.getUser().getId().equals(userId)) {
            throw new IllegalStateException("User not authorized to cancel this appointment");
        }

        appointmentRepository.delete(appointment);
    }


    public List<AppointmentDto> getAllAppointmentsByUserId(Long userId) {
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);
        return appointments.stream()
                .map(appointment -> new AppointmentDto(
                        appointment.getAppointment_id(),
                        appointment.getEquipment().getEquipment_id(),
                        appointment.getCompany().getCompany_id(),
                        appointment.getUser().getId(),
                        appointment.getTimeSlot().getId()
                      ))
                .collect(Collectors.toList());
    }

}
