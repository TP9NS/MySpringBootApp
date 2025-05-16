package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Department;
import com.basic.myspringboot.entity.Student;
import com.basic.myspringboot.entity.StudentDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations="classpath:/application-test.properties")
public class StudentDepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    private Department department;
    private Student student;
    private StudentDetail studentDetail;

    @BeforeEach
    void setUp() {
        // Create department
        department = Department.builder()
                .name("Computer Science")
                .code("CS")
                .build();
        entityManager.persistAndFlush(department);

        // Create student
        student = Student.builder()
                .name("John Doe")
                .studentNumber("CS001")
                .department(department)
                .build();
        entityManager.persistAndFlush(student);

        // Create student detail
        studentDetail = StudentDetail.builder()
                .address("123 Main St")
                .phoneNumber("010-1234-5678")
                .email("john@example.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .student(student)
                .build();
        entityManager.persistAndFlush(studentDetail);

        //양방항이므로 연관관계 저장
        student.setStudentDetail(studentDetail);
        entityManager.persistAndFlush(student);
    }

    @Test
    void findByStudentNumber_ShouldReturnStudent() {
        // When
        Optional<Student> found = studentRepository.findByStudentNumber("CS001");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("John Doe");
        assertThat(found.get().getStudentNumber()).isEqualTo("CS001");
    }

    @Test
    void findByStudentNumber_ShouldReturnEmpty_WhenNotFound() {
        // When
        Optional<Student> found = studentRepository.findByStudentNumber("NOTFOUND");

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    void findByIdWithAllDetails_ShouldReturnStudentWithDetails() {
        // When
        Optional<Student> found = studentRepository.findByIdWithAllDetails(student.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getStudentDetail()).isNotNull();
        assertThat(found.get().getDepartment()).isNotNull();
        assertThat(found.get().getDepartment().getName()).isEqualTo("Computer Science");
        assertThat(found.get().getStudentDetail().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void findByDepartmentId_ShouldReturnStudents() {
        // When
        List<Student> found = studentRepository.findByDepartmentId(department.getId());

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    void countByDepartmentId_ShouldReturnCorrectCount() {
        // When
        Long count = studentRepository.countByDepartmentId(department.getId());

        // Then
        assertThat(count).isEqualTo(1);
    }

    @Test
    void existsByStudentNumber_ShouldReturnTrue() {
        // When
        boolean exists = studentRepository.existsByStudentNumber("CS001");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByStudentNumber_ShouldReturnFalse() {
        // When
        boolean exists = studentRepository.existsByStudentNumber("NOTFOUND");

        // Then
        assertThat(exists).isFalse();
    }
}