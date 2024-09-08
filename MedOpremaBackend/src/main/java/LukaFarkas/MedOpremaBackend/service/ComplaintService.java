package LukaFarkas.MedOpremaBackend.service;


import LukaFarkas.MedOpremaBackend.dto.ComplaintDto;
import LukaFarkas.MedOpremaBackend.repository.ComplaintRepository;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ComplaintService {

    @Transactional
    ComplaintDto respondToComplaint(Long complaintId, String response) throws Exception;

    ComplaintDto createComplaint(Long userId, Long companyId, Long adminId, String details, ComplaintDto.ComplaintType complaintType) throws Exception;

    List<ComplaintDto> getAllComplaints();

    List<ComplaintDto> getComplaintsByUser(Long userId) throws Exception;
}
