package org.springframeworkcore.mvc.javaannotationbased.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframeworkcore.mvc.javaannotationbased.dao.StudentRepository;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.student.StudentCreateRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.student.StudentGetResponseDTO;
import org.springframeworkcore.mvc.javaannotationbased.dtomapper.student.StudentDTOMapper;
import org.springframeworkcore.mvc.javaannotationbased.model.Student;
import org.springframeworkcore.mvc.javaannotationbased.service.StudentService;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentDTOMapper studentDTOMapper;

    @Autowired
    public StudentServiceImplementation(StudentRepository studentRepository, StudentDTOMapper studentDTOMapper) {
        this.studentRepository = studentRepository;
        this.studentDTOMapper = studentDTOMapper;
    }

    @Override
    @Transactional(readOnly = false)
    public StudentGetResponseDTO save(StudentCreateRequestDTO studentDTO) throws Exception {

        if(studentRepository.findByUsername(studentDTO.username()).isPresent()){
            throw new Exception("username already exists!");
        }

        Student student = studentRepository.save(studentDTOMapper.studentDTOToStudentMapper(studentDTO));
        if(!student.getUsername().equals(studentDTO.username())){
            throw new Exception("user not created!");
        }
        return studentDTOMapper.studentToStudentDTOMapper(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentGetResponseDTO findByUsername(String username) throws Exception {
        Optional<Student> studentOptional = studentRepository.findByUsername(username);
        if(!studentOptional.isPresent()){
            throw new Exception("Username not found");
        }
        Student student = studentOptional.get();
        return studentDTOMapper.studentToStudentDTOMapper(student);
    }

}
