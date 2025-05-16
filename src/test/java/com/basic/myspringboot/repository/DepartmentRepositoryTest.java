package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .name("Computer Science")
                .code("CS")
                .build();
        entityManager.persistAndFlush(department);
    }

    @Test
    void findByCode_ShouldReturnDepartment() {
        // When
        Optional<Department> found = departmentRepository.findByCode("CS");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Computer Science");
        assertThat(found.get().getCode()).isEqualTo("CS");
    }

    @Test
    void findByCode_ShouldReturnEmpty_WhenNotFound() {
        // When
        Optional<Department> found = departmentRepository.findByCode("NOTFOUND");

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    void existsByCode_ShouldReturnTrue() {
        // When
        boolean exists = departmentRepository.existsByCode("CS");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByCode_ShouldReturnFalse() {
        // When
        boolean exists = departmentRepository.existsByCode("NOTFOUND");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void existsByName_ShouldReturnTrue() {
        // When
        boolean exists = departmentRepository.existsByName("Computer Science");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByName_ShouldReturnFalse() {
        // When
        boolean exists = departmentRepository.existsByName("Not Found");

        // Then
        assertThat(exists).isFalse();
    }
}