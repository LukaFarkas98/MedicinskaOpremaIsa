package LukaFarkas.MedOpremaBackend.controller;


import LukaFarkas.MedOpremaBackend.dto.ComplaintDto;
import LukaFarkas.MedOpremaBackend.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public ResponseEntity<List<ComplaintDto>> getAllComplaints() {
        List<ComplaintDto> complaints = complaintService.getAllComplaints();
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComplaintDto> createComplaint(
            @RequestBody ComplaintDto complaintDto) {
        try {
            ComplaintDto complaintDTO1 = complaintService.createComplaint(complaintDto.getUserId(), complaintDto.getCompanyId(),complaintDto.getAdminId(), complaintDto.getDetails());
            return ResponseEntity.ok(complaintDTO1);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // Return a generic error response or handle specific exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ComplaintDto()); // Or handle with a custom error message
        }
    }

}
