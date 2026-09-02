package com.iame.fhir.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
    	String sql = "SELECT patient_fhir_id, password, role FROM patients WHERE patient_fhir_id = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                String dbUsername = rs.getString("patient_fhir_id");
                String dbPassword = rs.getString("password");
                String dbRole = rs.getString("role");

                
                return User.withUsername(dbUsername)
                        .password(dbPassword)
                        .roles(dbRole.replace("ROLE_", ""))
                        .build();
            });
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with id: " + username);
        }
    }
}