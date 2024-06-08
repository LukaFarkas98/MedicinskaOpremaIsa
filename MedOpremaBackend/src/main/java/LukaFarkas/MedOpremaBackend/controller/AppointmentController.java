package LukaFarkas.MedOpremaBackend.controller;

import LukaFarkas.MedOpremaBackend.dto.AppointmentDto;
import LukaFarkas.MedOpremaBackend.service.AppointmentService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

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
        AppointmentDto createdAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

}
