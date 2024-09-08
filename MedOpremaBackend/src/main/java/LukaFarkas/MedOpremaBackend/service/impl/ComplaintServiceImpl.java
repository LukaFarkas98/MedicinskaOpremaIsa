package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.ComplaintDto;
import LukaFarkas.MedOpremaBackend.entity.Appointment;
import LukaFarkas.MedOpremaBackend.entity.Complaint;
import LukaFarkas.MedOpremaBackend.entity.User;
import LukaFarkas.MedOpremaBackend.mapper.ComplaintMapper;
import LukaFarkas.MedOpremaBackend.repository.AppointmentRepository;
import LukaFarkas.MedOpremaBackend.repository.ComplaintRepository;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import LukaFarkas.MedOpremaBackend.service.ComplaintService;
import LukaFarkas.MedOpremaBackend.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    EmailService emailService;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    @Transactional
    @Override
    public ComplaintDto respondToComplaint(Long complaintId, String response) throws Exception {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new Exception("Complaint not found"));

        complaint.setResponse(response);
        Complaint updatedComplaint = complaintRepository.save(complaint);

        // Assuming there's a method to get the user’s email
        String userEmail = complaint.getUser().getEmail();
        emailService.sendEmailResponse(userEmail, response);

        return ComplaintMapper.toDTO(updatedComplaint);
    }


    @Transactional
    @Override
    public ComplaintDto createComplaint(Long userId, Long companyId, Long adminId, String details, ComplaintDto.ComplaintType complaintType) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new Exception("User not found");
        }

        User user = userOptional.get();
        List<Appointment> appointments = appointmentRepository.findByUserIdAndCompanyId(userId, companyId);

        if (complaintType == ComplaintDto.ComplaintType.COMPANY && appointments.isEmpty()) {
            throw new Exception("User doesn't have any appointment currently with this company to complain on");
        } else if (complaintType == ComplaintDto.ComplaintType.ADMIN) {
            boolean isValidAdmin = appointments.stream()
                    .anyMatch(appointment -> appointment.getCompany().getAdmin().getId().equals(adminId));

            if (appointments.isEmpty() || !isValidAdmin) {
                throw new Exception("User doesn't have any appointment with the company that this admin is associated with");
            }
        }

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        if (complaintType == ComplaintDto.ComplaintType.COMPANY) {
            // Use the first appointment's company
            complaint.setCompany(appointments.get(0).getCompany());
        } else {
            complaint.setAdmin(userRepository.findById(adminId).orElseThrow(() -> new Exception("Admin not found")));
        }
        complaint.setDetails(details);
        complaint.setDate(LocalDateTime.now());

        Complaint savedComplaint = complaintRepository.save(complaint);

        return ComplaintMapper.toDTO(savedComplaint);
    }



    @Override
    public List<ComplaintDto> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream()
                .map(ComplaintMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ComplaintDto> getComplaintsByUser(Long userId) throws Exception {
        List<Complaint> complaints = complaintRepository.findByUserId(userId);
        if (complaints.isEmpty()) {
            throw new Exception("No complaints found for the user");
        }
        return complaints.stream()
                .map(ComplaintMapper::toDTO)
                .collect(Collectors.toList());
    }
}
