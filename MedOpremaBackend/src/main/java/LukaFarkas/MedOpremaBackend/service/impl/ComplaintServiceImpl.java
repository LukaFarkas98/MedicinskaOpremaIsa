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
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    @Override
    public ComplaintDto createComplaint(Long userId, Long companyId, Long adminId,String details) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new Exception("User not found");
        }

        User user = userOptional.get();

        Appointment appointmentOptional = appointmentRepository.findByUserIdAndCompanyId(userId, companyId);

        if(appointmentOptional == null)
        {
            throw new Exception("user doesnt have any appointment currently with this company to complain on !!");
        }

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setCompany(appointmentOptional.getCompany());
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
}
