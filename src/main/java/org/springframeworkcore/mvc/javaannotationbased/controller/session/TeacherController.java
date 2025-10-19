package org.springframeworkcore.mvc.javaannotationbased.controller.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.teacher.TeacherCreateRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.teacher.TeacherLoginRequestDTO;
import org.springframeworkcore.mvc.javaannotationbased.service.TeacherAuthService;
import org.springframeworkcore.mvc.javaannotationbased.service.TeacherService;
import org.springframeworkcore.mvc.javaannotationbased.session.model.SessionData;
import org.springframeworkcore.mvc.javaannotationbased.session.service.SessionDataService;
import org.springframeworkcore.mvc.javaannotationbased.utils.CookieServiceUtil;
import org.springframeworkcore.mvc.javaannotationbased.utils.GenerateUUID;

import java.util.List;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

	private final PasswordEncoder passwordEncoder;
	private final UserDetailsManager userDetailsManager;
	private final TeacherService teacherService;
	private final TeacherAuthService teacherAuthService;
	private final SessionDataService sessionDataService;

	@Autowired
	public TeacherController(TeacherService teacherService, TeacherAuthService teacherAuthService, SessionDataService sessionDataService, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
		this.teacherService = teacherService;
		this.teacherAuthService = teacherAuthService;
		this.sessionDataService = sessionDataService;
		this.userDetailsManager = userDetailsManager;
		this.passwordEncoder  = passwordEncoder;
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinderxdss) {
		
	}
	
	@GetMapping("/login")
	public String teacherLoginPage(Model model) {
		System.out.println("inside login");
	
		 model.addAttribute("teacherLoginRequestDTO", new TeacherLoginRequestDTO("", "", false));
		 return "teacherLoginPage";

	}

	@GetMapping("/dashboard/")
	public ModelAndView teacherDashBoardPage(@RequestParam("username") String username) {
		return new ModelAndView("teacherDashboardPage", "username", username);
	}

	@GetMapping("/register")
	public String teacherRegisterPage(Model model) {
		System.out.println("inside regisetr");
		if (!model.containsAttribute("teacherCreateRequestDTO")) {
			model.addAttribute("teacherCreateRequestDTO", new TeacherCreateRequestDTO("", ""));
		}
		return "teacherRegisterPage";
	}

	@PostMapping("/newaccount")
	public String teacherNewAccount(@Valid @ModelAttribute TeacherCreateRequestDTO teacherRequestDTO,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "teacherRegisterPage";
		}
		try {
			System.out.println(passwordEncoder.encode(teacherRequestDTO.password()));
			userDetailsManager.createUser(
					new User(teacherRequestDTO.username(),
							passwordEncoder.encode(teacherRequestDTO.password()),
							List.of(new SimpleGrantedAuthority("TEACHER"))));
		} catch (Exception e) {
			model.addAttribute("loginError", e.getMessage());
			System.out.println("Is new user present(exception): " + userDetailsManager.userExists(teacherRequestDTO.username()));
			e.printStackTrace();
			return "teacherRegisterPage";
		}
		return "redirect:/teacher/login";
	}

}
