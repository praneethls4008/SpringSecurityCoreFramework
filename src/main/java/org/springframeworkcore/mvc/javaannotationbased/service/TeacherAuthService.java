package org.springframeworkcore.mvc.javaannotationbased.service;

import org.springframeworkcore.mvc.javaannotationbased.dto.request.teacher.TeacherLoginRequestDTO;



public interface TeacherAuthService {
    public void teacherLogin(TeacherLoginRequestDTO teacherLoginRequestDTO) throws Exception;
}