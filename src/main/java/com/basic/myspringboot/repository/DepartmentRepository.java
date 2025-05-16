package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    Optional<Department> findByCode(String code);
    
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.students WHERE d.id = :id")
    Optional<Department> findByIdWithStudents(@Param("id") Long id);
    
    boolean existsByCode(String code);
    
    boolean existsByName(String name);
}