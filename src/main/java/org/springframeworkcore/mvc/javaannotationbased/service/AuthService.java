package org.springframeworkcore.mvc.javaannotationbased.service;

import org.springframeworkcore.mvc.javaannotationbased.dto.request.student.StudentLoginRequestDTO;


public interface AuthService {
    public void studentLogin(StudentLoginRequestDTO studentLoginRequestDTO) throws Exception;
}
