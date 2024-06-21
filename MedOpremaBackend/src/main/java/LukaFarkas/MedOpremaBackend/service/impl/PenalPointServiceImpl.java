package LukaFarkas.MedOpremaBackend.service.impl;

import LukaFarkas.MedOpremaBackend.dto.PenalPointDto;
import LukaFarkas.MedOpremaBackend.entity.PenalPoint;
import LukaFarkas.MedOpremaBackend.mapper.PenalPointMapper;
import LukaFarkas.MedOpremaBackend.repository.PenalPointRepository;
import LukaFarkas.MedOpremaBackend.service.PenalPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenalPointServiceImpl implements PenalPointService {

    @Autowired
    private PenalPointRepository penalPointRepository;

    @Override
    public List<PenalPointDto> getAllPenalPointsByUserId(Long userId) {
        List<PenalPoint> penalPoints = penalPointRepository.findAllByUserId(userId);
        return PenalPointMapper.toDtoList(penalPoints);
    }
}
