package org.springframeworkcore.mvc.javaannotationbased.dtomapper.student;

import org.springframework.stereotype.Component;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.student.StudentCreateRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.student.StudentGetResponseDTO;
import org.springframeworkcore.mvc.javaannotationbased.model.Student;

@Component
public class StudentDTOMapper {
    public Student studentDTOToStudentMapper(StudentCreateRequestDTO studentDTO){
        Student student = new Student();
        student.setUsername(studentDTO.username());
        student.setPassword(studentDTO.password());
        return student;
    }

    public StudentGetResponseDTO studentToStudentDTOMapper(Student student){
        return new StudentGetResponseDTO(student.getUsername(), student.getPassword());
    }
}
