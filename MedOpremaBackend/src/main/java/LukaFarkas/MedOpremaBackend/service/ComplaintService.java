package LukaFarkas.MedOpremaBackend.service;


import LukaFarkas.MedOpremaBackend.dto.ComplaintDto;
import LukaFarkas.MedOpremaBackend.repository.ComplaintRepository;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ComplaintService {

    ComplaintDto createComplaint(Long userId, Long companyId, Long adminId, String details) throws Exception;

    List<ComplaintDto> getAllComplaints();
}
