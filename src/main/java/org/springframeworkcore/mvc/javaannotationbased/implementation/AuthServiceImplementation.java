package org.springframeworkcore.mvc.javaannotationbased.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.student.StudentLoginRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.student.StudentGetResponseDTO;
import org.springframeworkcore.mvc.javaannotationbased.service.AuthService;
import org.springframeworkcore.mvc.javaannotationbased.service.StudentService;

@Service
public class AuthServiceImplementation implements AuthService{

    private final StudentService studentService;

    @Autowired
    public AuthServiceImplementation(StudentService studentService){
        this.studentService = studentService;
    }

    public void studentLogin(StudentLoginRequestDTO studentLoginRequestDTO) throws Exception{
        if(studentLoginRequestDTO==null){
            throw new Exception("student login credentials null!");
        }
        if(studentLoginRequestDTO.username().isBlank()){
            throw new Exception("student login credentials username is empty!");
        }
        if(studentLoginRequestDTO.password().isBlank()){
            throw new Exception("student login credentials password is empty!");
        }
        if(studentLoginRequestDTO.username().length()<4){
            throw new Exception("student login credentials username is length should be greater than 3!");
        }
        if(studentLoginRequestDTO.password().length()<4){
            throw new Exception("student login credentials password is length should be greater than 3!");
        }
        try{
            StudentGetResponseDTO studentDTOResponse = studentService.findByUsername(studentLoginRequestDTO.username());
            if(!studentDTOResponse.password().equals(studentLoginRequestDTO.password())){
                throw new Exception("student login credentials password is incorrect!");
            }
        }
        catch(Exception e){
            throw e;
        }
    }
}