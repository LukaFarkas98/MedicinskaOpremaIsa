package LukaFarkas.MedOpremaBackend.repository;

import LukaFarkas.MedOpremaBackend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CompanyRepository extends JpaRepository<Company, Long> {
}
