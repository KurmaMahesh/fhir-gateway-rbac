package com.iame.fhir.entity; // నీ పాకేజ్ నేమ్ చూసుకో

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "patientFhirId is mandatory and cannot be empty")
    @Column(name = "patient_fhir_id", nullable = false)
    private String patientFhirId;

    @NotBlank(message = "Patient name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(male|female|other|unknown)$", message = "Gender must be male, female, other, or unknown")
    @Column(name = "gender")
    private String gender;

    @NotNull(message = "Birth date is required")
    @Column(name = "birth_date")
    private String birthDate;

    
    public PatientEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientFhirId() { return patientFhirId; }
    public void setPatientFhirId(String patientFhirId) { this.patientFhirId = patientFhirId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
}