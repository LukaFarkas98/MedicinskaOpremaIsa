package LukaFarkas.MedOpremaBackend.repository;

import LukaFarkas.MedOpremaBackend.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
