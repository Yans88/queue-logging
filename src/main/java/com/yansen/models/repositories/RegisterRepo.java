package com.yansen.models.repositories;

import com.yansen.models.entities.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo extends JpaRepository<Register, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}
