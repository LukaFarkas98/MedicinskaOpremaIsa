package LukaFarkas.MedOpremaBackend.mapper;

import LukaFarkas.MedOpremaBackend.dto.ComplaintDto;
import LukaFarkas.MedOpremaBackend.entity.Complaint;
import LukaFarkas.MedOpremaBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplaintMapper {

    @Autowired
    private UserRepository userRepository;

    public static ComplaintDto toDTO(Complaint complaint) {
        ComplaintDto dto = new ComplaintDto();
        dto.setId(complaint.getId());
        dto.setUserId(complaint.getUser().getId());
        dto.setCompanyId(complaint.getCompany() != null ? complaint.getCompany().getId() : null);
        dto.setAdminId(complaint.getAdmin()!= null ? complaint.getAdmin().getId() : null);
        dto.setDetails(complaint.getDetails());
        dto.setDate(complaint.getDate());
        dto.setResponse(complaint.getResponse());
        return dto;
    }

    public static Complaint toEntity(ComplaintDto dto) {
        Complaint complaint = new Complaint();
        complaint.setId(dto.getId());
        complaint.setDetails(dto.getDetails());
        complaint.setDate(dto.getDate());
        return complaint;
    }

}
