package com.example.reportmanagementapp.domain.repository;

import com.example.reportmanagementapp.domain.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenceRepository  extends JpaRepository<Evidence, Long>
{
   List<Evidence> findByUserId(Long userId);
//    @Query("SELECT e FROM Evidence e WHERE e.user.id = :userId")
//    List<Evidence> findByUserId(@Param("userId") Long userId);
}
