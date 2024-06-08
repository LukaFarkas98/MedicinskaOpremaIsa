package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.TimeSlotDto;
import LukaFarkas.MedOpremaBackend.dto.UserDto;
import LukaFarkas.MedOpremaBackend.entity.*;
import LukaFarkas.MedOpremaBackend.mapper.TimeSlotMapper;
import LukaFarkas.MedOpremaBackend.mapper.UserMapper;
import LukaFarkas.MedOpremaBackend.service.CompanyService;
import LukaFarkas.MedOpremaBackend.service.EmailService;
import LukaFarkas.MedOpremaBackend.service.EquipmentService;
import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.service.UserService;
import LukaFarkas.MedOpremaBackend.service.impl.TimeSlotServiceImpl;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private UserService userService;


    @Autowired
    private TimeSlotServiceImpl timeSlotService;

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        try {
            emailService.sendSimpleMessage(to, subject, text);
            return "Email sent successfully";
        } catch (MessagingException | jakarta.mail.MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }

    @PostMapping("/sendAppointmentConfirmation")
    public String sendAppointmentConfirmation(@RequestBody AppointmentDto appointmentDTO) {
        try {
            // Retrieve related entities (Company, Equipment, User) from the database based on IDs
            Company company = companyService.findById(appointmentDTO.getCompanyId());
            Equipment equipment = equipmentService.findById(appointmentDTO.getEquipmentId());
            UserDto userDto = userService.getUserById(appointmentDTO.getUserId());
            TimeSlotDto timeslot = timeSlotService.findById(appointmentDTO.getTimeSlotId());
            // Create Appointment entity
            Appointment appointment = new Appointment(
                    appointmentDTO.getAppointmentId(),
                    equipment,
                    company,
                    UserMapper.mapToUser(userDto),
                    TimeSlotMapper.toEntity(timeslot, equipment)
            );

            // Send appointment confirmation email
            emailService.sendAppointmentConfirmation(UserMapper.mapToUser(userDto), appointment);
            return "Appointment confirmation email sent successfully";
        } catch (MessagingException | IOException e) {
            return "Error sending appointment confirmation email: " + e.getMessage();
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
