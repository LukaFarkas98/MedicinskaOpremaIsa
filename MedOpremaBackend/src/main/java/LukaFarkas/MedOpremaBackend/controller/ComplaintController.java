package LukaFarkas.MedOpremaBackend.controller;


import LukaFarkas.MedOpremaBackend.dto.ComplaintDto;
import LukaFarkas.MedOpremaBackend.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;



    @PutMapping("/respond/{complaintId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ComplaintDto> respondToComplaint(
            @PathVariable Long complaintId,
            @RequestParam String response) {
        try {
            ComplaintDto updatedComplaint = complaintService.respondToComplaint(complaintId, response);
            return ResponseEntity.ok(updatedComplaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Handle exceptions as needed
        }
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ComplaintDto>> getAllComplaints() {
        List<ComplaintDto> complaints = complaintService.getAllComplaints();
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ComplaintDto>> getComplaintsByUser(@PathVariable Long userId) {
        try {
            List<ComplaintDto> complaints = complaintService.getComplaintsByUser(userId);
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList()); // Or handle with a custom error message
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ComplaintDto> createComplaint(
            @RequestBody ComplaintDto complaintDto) {
        try {
            ComplaintDto.ComplaintType complaintType = "company".equalsIgnoreCase(complaintDto.getComplaintType().toString()) ? ComplaintDto.ComplaintType.COMPANY : ComplaintDto.ComplaintType.ADMIN;
            ComplaintDto complaintDTO1 = complaintService.createComplaint(
                    complaintDto.getUserId(),
                    complaintDto.getCompanyId(),
                    complaintDto.getAdminId(),
                    complaintDto.getDetails(),
                    complaintType // Add this parameter
            );
            return ResponseEntity.ok(complaintDTO1);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // Return a generic error response or handle specific exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ComplaintDto()); // Or handle with a custom error message
        }
    }

}
