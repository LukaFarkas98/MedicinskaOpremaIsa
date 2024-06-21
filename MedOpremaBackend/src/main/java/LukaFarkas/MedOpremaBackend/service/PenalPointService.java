package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.dto.PenalPointDto;
import LukaFarkas.MedOpremaBackend.repository.PenalPointRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PenalPointService {
     List<PenalPointDto> getAllPenalPointsByUserId(Long userId) ;
}
