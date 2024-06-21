package LukaFarkas.MedOpremaBackend.repository;

import LukaFarkas.MedOpremaBackend.entity.PenalPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenalPointRepository extends JpaRepository<PenalPoint, Long> {
    List<PenalPoint> findAllByUserId(Long userId);
}
