package org.springframeworkcore.mvc.javaannotationbased.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframeworkcore.mvc.javaannotationbased.model.Student;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findByUsername(String username);
}
