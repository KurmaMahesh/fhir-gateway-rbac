package com.iame.fhir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @GetMapping("/{id}")
    public Object getPatient(@PathVariable("id") String id, Authentication authentication) {
        String loggedInUser = authentication.getName();
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR") || a.getAuthority().equals("ROLE_ROLE_DOCTOR"));

        if (!isDoctor && !loggedInUser.equals(id)) {
            return "Access Denied: You can only view your own records!";
        }

        String sql = "SELECT patient_fhir_id, name, gender, birth_date, role FROM patients WHERE patient_fhir_id = ?";
        
        try {
            return jdbcTemplate.queryForMap(sql, id);
        } catch (Exception e) {
            return "Patient not found with id: " + id;
        }
    }

       @PostMapping
    public String createPatient(@RequestBody Map<String, Object> patientData, Authentication authentication) {
       
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR") || a.getAuthority().equals("ROLE_ROLE_DOCTOR"));

        if (!isDoctor) {
            return "Access Denied: Only doctors are allowed to add new patient records!";
        }

        try {
            String sql = "INSERT INTO patients (patient_fhir_id, name, gender, birth_date, password, role) VALUES (?, ?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql,
                patientData.get("patient_fhir_id"),
                patientData.get("name"),
                patientData.get("gender"),
                patientData.get("birth_date"),
                "{noop}" + patientData.get("password"), 
                 patientData.get("role")
            );

            return "Patient record created successfully!";
        } catch (Exception e) {
            return "Error creating patient: " + e.getMessage();
        }
    }
}