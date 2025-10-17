package org.springframeworkcore.mvc.javaannotationbased.controller.basics;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframeworkcore.mvc.javaannotationbased.session.model.UserSession;
import org.springframeworkcore.mvc.javaannotationbased.utils.CookieServiceUtil;

@Controller
@RequestMapping(value = "/student")
@SessionAttributes("userSession")//session attributes will be pushed to request scope
public class StudentController {
	StudentService studentService;
	AuthService authService;

	@Autowired
	public StudentController(StudentService studentService, AuthService authService) {
		this.studentService = studentService;
		this.authService = authService;
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
//        System.out.println("bind");
	}

	@GetMapping("/login")
	public String studentLoginPage(@CookieValue(value = "userSession", required = false) String userSession,
			Model model) {
		if (userSession == null) {
			
			if (!model.containsAttribute("studentLoginRequestDTO")) {
				model.addAttribute("studentLoginRequestDTO", new StudentLoginRequestDTO("", "", false));
			}
			return "studentLoginPage";
		}

		try {
			UserSession userSessionObj = CookieServiceUtil.cookieToObject(userSession, UserSession.class);
			model.addAttribute("studentLoginRequestDTO", new StudentLoginRequestDTO(userSessionObj.username(), "", false));

			if (userSessionObj.rememberMe()) {
				return "redirect:/student/dashboard/?username=" + userSessionObj.username();
			} else {
				return "studentLoginPage";
			}
		} catch (Exception e) {
			return "studentLoginPage";
		}

	}

	@PostMapping("/auth")
	public String studentAuthPage(@Valid @ModelAttribute StudentLoginRequestDTO studentLoginReqDTO,
			BindingResult bindingResult, Model model,
			HttpServletResponse httpResponse) {

		if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "studentLoginPage";
		}

		try {
			authService.studentLogin(studentLoginReqDTO);
		} catch (Exception authException) {
			model.addAttribute("loginError", authException.getMessage());
			authException.printStackTrace();
			return "studentLoginPage";
		}
		
		try {
			CookieServiceUtil.add(httpResponse, "userSession", new UserSession(studentLoginReqDTO.username(), studentLoginReqDTO.rememberMe()), 2 * 60);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/student/dashboard/?username=" + studentLoginReqDTO.username();

	}

	@GetMapping("/dashboard/")
	public ModelAndView studentDashBoardPage(@RequestParam("username") String username) {
		return new ModelAndView("studentDashboardPage", "username", username);
	}

	@GetMapping("/register")
	public String studentRegisterPage(Model model) {
		if (!model.containsAttribute("studentCreateRequestDTO")) {
			model.addAttribute("studentCreateRequestDTO", new StudentCreateRequestDTO("", ""));
		}
		return "studentRegisterPage";
	}

	@PostMapping("/newaccount")
	public String studentNewAccount(@Valid @ModelAttribute StudentCreateRequestDTO studentRequestDTO,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "studentRegisterPage";
		}
		try {
			studentService.save(studentRequestDTO);
		} catch (Exception e) {
			model.addAttribute("loginError", e.getMessage());
			e.printStackTrace();
			return "studentRegisterPage";
		}
		return "redirect:/student/login";
	}
	
	@GetMapping("/logout")
	public String studentLogout(@CookieValue(value = "userSession", required = false) String userSession, @RequestParam("username") String username,Model model, HttpServletResponse httpResponse) {
		if(userSession!=null) {
			CookieServiceUtil.delete(httpResponse, "userSession");
		}
		return "redirect:/student/login";
	}

}
