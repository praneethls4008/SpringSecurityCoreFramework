package org.springframeworkcore.mvc.javaannotationbased.dtomapper.teacher;

import org.springframework.stereotype.Component;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.teacher.TeacherCreateRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.teacher.TeacherGetResponseDTO;
import org.springframeworkcore.mvc.javaannotationbased.model.Teacher;

@Component
public class TeacherDTOMapper {
    public Teacher teacherDTOToTeacherMapper(TeacherCreateRequestDTO teacherDTO){
        Teacher teacher = new Teacher();
        teacher.setUsername(teacherDTO.username());
        teacher.setPassword(teacherDTO.password());
        return teacher;
    }

    public TeacherGetResponseDTO teacherToTeacherDTOMapper(Teacher teacher){
        return new TeacherGetResponseDTO(teacher.getUsername(), teacher.getPassword());
    }
}
