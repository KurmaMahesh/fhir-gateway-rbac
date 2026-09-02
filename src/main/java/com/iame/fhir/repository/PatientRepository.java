package com.iame.fhir.repository;

import java.util.Optional;
import com.iame.fhir.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    
        Optional<PatientEntity> findByPatientFhirId(String patientFhirId);
}  