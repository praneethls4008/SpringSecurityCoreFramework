package org.springframeworkcore.mvc.javaannotationbased.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframeworkcore.mvc.javaannotationbased.model.Teacher;

import java.util.Optional;


public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
    Optional<Teacher> findByUsername(String username);
}
