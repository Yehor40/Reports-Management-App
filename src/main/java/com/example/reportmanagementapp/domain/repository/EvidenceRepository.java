package com.example.reportmanagementapp.domain.repository;

import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvidenceRepository  extends JpaRepository<Evidence, Long>
{
   List<Evidence> findByUserId(Long userId);
//    @Query("SELECT e FROM Evidence e WHERE e.user.id = :userId")
//    List<Evidence> findByUserId(@Param("userId") Long userId);
}
