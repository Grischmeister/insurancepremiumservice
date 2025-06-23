package grischa.insuranceapplicationservice.repository;

import grischa.insuranceapplicationservice.model.InsuranceApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceApplicationRepository extends JpaRepository<InsuranceApplication, Long> {
}
