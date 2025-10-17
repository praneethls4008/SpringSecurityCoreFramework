package org.springframeworkcore.mvc.javaannotationbased.dto.request.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframeworkcore.mvc.javaannotationbased.validators.annotations.NoSpaces;

public record TeacherCreateRequestDTO(
        @NotBlank(message = "username should not be blank")
        @Size(min = 4, max = 8, message = "username length should be in between 4-8")
        @NoSpaces(message = "username should not contain spaces")
        String username,

        @NotBlank(message = "password should not be blank")
        @Size(min = 4, max = 8, message = "password length should be in between 4-8")
        @NoSpaces(message = "password should not contain spaces")
        String password
) { }
