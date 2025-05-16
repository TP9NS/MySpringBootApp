package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//StudentRepository 인터페이스
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentNumber(String studentNumber);

    @Query("SELECT s FROM Student s JOIN FETCH s.studentDetail WHERE s.id = :id")
    Optional<Student> findByIdWithStudentDetail(@Param("id") Long id);

    boolean existsByStudentNumber(String studentNumber);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.studentDetail LEFT JOIN FETCH s.department WHERE s.id = :id")
    Optional<Student> findByIdWithAllDetails(@Param("id") Long id);

    // 새로 추가된 메서드
    List<Student> findByDepartmentId(Long departmentId);

    // 새로 추가된 메서드
    @Query("SELECT COUNT(s) FROM Student s WHERE s.department.id = :departmentId")
    Long countByDepartmentId(@Param("departmentId") Long departmentId);


}