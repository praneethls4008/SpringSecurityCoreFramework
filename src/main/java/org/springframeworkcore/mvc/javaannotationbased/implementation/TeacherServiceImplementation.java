package org.springframeworkcore.mvc.javaannotationbased.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframeworkcore.mvc.javaannotationbased.dao.TeacherRepository;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.teacher.TeacherCreateRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.teacher.TeacherGetResponseDTO;
import org.springframeworkcore.mvc.javaannotationbased.dtomapper.teacher.TeacherDTOMapper;
import org.springframeworkcore.mvc.javaannotationbased.model.Teacher;
import org.springframeworkcore.mvc.javaannotationbased.service.TeacherService;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TeacherServiceImplementation implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherDTOMapper teacherDTOMapper;

    @Autowired
    public TeacherServiceImplementation(TeacherRepository teacherRepository, TeacherDTOMapper teacherDTOMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherDTOMapper = teacherDTOMapper;
    }

    @Override
    @Transactional(readOnly = false)
    public TeacherGetResponseDTO save(TeacherCreateRequestDTO teacherDTO) throws Exception {

        if(teacherRepository.findByUsername(teacherDTO.username()).isPresent()){
            throw new Exception("username already exists!");
        }

        Teacher teacher = teacherRepository.save(teacherDTOMapper.teacherDTOToTeacherMapper(teacherDTO));
        if(!teacher.getUsername().equals(teacherDTO.username())){
            throw new Exception("user not created!");
        }
        return teacherDTOMapper.teacherToTeacherDTOMapper(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherGetResponseDTO findByUsername(String username) throws Exception {
        Optional<Teacher> teacherOptional = teacherRepository.findByUsername(username);
        if(!teacherOptional.isPresent()){
            throw new Exception("Username not found");
        }
        Teacher Teacher = teacherOptional.get();
        return teacherDTOMapper.teacherToTeacherDTOMapper(Teacher);
    }

}
