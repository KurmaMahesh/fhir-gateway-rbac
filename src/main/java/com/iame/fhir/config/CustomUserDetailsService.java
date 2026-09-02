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
        // మనం క్రియేట్ చేసుకున్న patients టేబుల్ నుంచి యూజర్ డేటాని ఫెచ్ చేయడం
        String sql = "SELECT patient_fhir_id, password, role FROM patients WHERE patient_fhir_id = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                String dbUsername = rs.getString("patient_fhir_id");
                String dbPassword = rs.getString("password");
                String dbRole = rs.getString("role");

                // స్ప్రింగ్ సెక్యూరిటీకి అవసరమైన యూజర్‌నేమ్, పాస్‌వర్డ్ మరియు రోల్‌ని ఇస్తాం
                return User.withUsername(dbUsername)
                        .password(dbPassword)
                        .roles(dbRole.replace("ROLE_", "")) // స్ప్రింగ్ ఆటోమేటిక్‌గా ROLE_ యాడ్ చేస్తుంది కాబట్టి
                        .build();
            });
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with id: " + username);
        }
    }
}