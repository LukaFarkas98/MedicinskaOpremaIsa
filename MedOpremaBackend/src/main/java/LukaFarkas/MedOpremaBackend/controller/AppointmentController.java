package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.entity.Appointment;
import LukaFarkas.MedOpremaBackend.service.AppointmentService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;



    @GetMapping("/{companyId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByCompanyId(@PathVariable Long companyId){
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByCompanyId(companyId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDTO) throws MessagingException, jakarta.mail.MessagingException, IOException, WriterException {
        // Check if any appointment exists for this equipment at the given time slot in the company
        AppointmentDto newAppointment = appointmentService.createAppointment(appointmentDTO);

        if (newAppointment == null) {
            throw new IllegalStateException("Yo, this time slot is already booked for this equipment in the company.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long appointmentId, @RequestParam Long userId) {
        appointmentService.cancelAppointment(appointmentId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentsByUserId(@PathVariable Long userId) {
        List<AppointmentDto> appointments = appointmentService.getAllAppointmentsByUserId(userId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

}
