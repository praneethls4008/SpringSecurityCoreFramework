package org.springframeworkcore.mvc.javaannotationbased.controller.basics;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.student.StudentCreateRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.student.StudentLoginRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.service.AuthService;
import org.springframeworkcore.mvc.javaannotationbased.service.StudentService;

import java.util.List;

@Controller
@RequestMapping(value = "/student")
@SessionAttributes("userSession")//session attributes will be pushed to request scope
public class StudentController {

	private final PasswordEncoder passwordEncoder;
	private final UserDetailsManager userDetailsManager;
	private final StudentService studentService;
	private final AuthService authService;

	@Autowired
	public StudentController(StudentService studentService, AuthService authService, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
		this.studentService = studentService;
		this.authService = authService;
		this.userDetailsManager = userDetailsManager;
		this.passwordEncoder  = passwordEncoder;

		System.out.println("[Controller] UserDetailsManager instance: " + System.identityHashCode(userDetailsManager));
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
//        System.out.println("bind");
	}

	

	@GetMapping("/login")
	public String studentLoginPage(Model model) {
		model.addAttribute("studentLoginRequestDTO", new StudentLoginRequestDTO("", "", false));
		return "studentLoginPage";
	}

	@GetMapping("/dashboard")
	public ModelAndView studentDashBoardPage(@AuthenticationPrincipal UserDetails userDetails) {
		return new ModelAndView("studentDashboardPage", "username", userDetails.getUsername());
	}

	@GetMapping("/register")
	public String studentRegisterPage(Model model) {
		System.out.println("inside regisetr");
		if (!model.containsAttribute("studentCreateRequestDTO")) {
			model.addAttribute("studentCreateRequestDTO", new StudentCreateRequestDTO("", ""));
		}
		return "studentRegisterPage";
	}

	@PostMapping("/newaccount")
	public String studentNewAccount(@Valid @ModelAttribute StudentCreateRequestDTO studentRequestDTO,
			BindingResult bindingResult, Model model) {
		System.out.println("inside newaccount");
		if (bindingResult.hasErrors()) {
			return "studentRegisterPage";
		}
		try {
			System.out.println(passwordEncoder.encode(studentRequestDTO.password()));
			userDetailsManager.createUser(
					new User(studentRequestDTO.username(),
							passwordEncoder.encode(studentRequestDTO.password()),
                            List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))));

			System.out.println("iIs new user present: " + userDetailsManager.userExists(studentRequestDTO.username()));
			System.out.println("new user : " + userDetailsManager.loadUserByUsername(studentRequestDTO.username()));


		} catch (Exception e) {
			model.addAttribute("loginError", e.getMessage());
			System.out.println("iIs new user present(exception): " + userDetailsManager.userExists(studentRequestDTO.username()));
			e.printStackTrace();
			return "studentRegisterPage";
		}
		return "redirect:/student/login";
	}

}
