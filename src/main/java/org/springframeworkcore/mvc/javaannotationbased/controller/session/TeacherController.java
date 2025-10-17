package org.springframeworkcore.mvc.javaannotationbased.controller.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
	TeacherService teacherService;
	TeacherAuthService teacherAuthService;
	SessionDataService sessionDataService;

	@Autowired
	public TeacherController(TeacherService teacherService, TeacherAuthService teacherAuthService,
			SessionDataService sessionDataService) {
		this.teacherService = teacherService;
		this.teacherAuthService = teacherAuthService;
		this.sessionDataService = sessionDataService;
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinderxdss) {
		
	}
	
	@GetMapping("/login")
	public String teacherLoginPage(Model model, HttpServletRequest httpRequest) {
		System.out.println("inside login");
	
		 if (!model.containsAttribute("teacherLoginRequestDTO")) {
		        model.addAttribute("teacherLoginRequestDTO", new TeacherLoginRequestDTO("", "", true));
		    }
		String loginSessionId = (String) httpRequest.getSession().getAttribute("teacherLoginSessionId");
		
		if (loginSessionId!=null && !loginSessionId.isBlank()) {
			try {
				SessionData sessionData = sessionDataService.getSession(loginSessionId, SessionData.class);
				if (sessionData != null) {
					model.addAttribute("teacherLoginRequestDTO",new TeacherLoginRequestDTO(sessionData.username(), "", false));
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		return "teacherLoginPage";

	}

	@PostMapping("/auth")
	public String teacherAuthPage(@Valid @ModelAttribute("teacherLoginRequestDTO") TeacherLoginRequestDTO teacherLoginReqDTO,
			BindingResult bindingResult, Model model, HttpServletRequest httpRequest) {

		System.out.println("inside auth");
		if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "teacherLoginPage";
		}

		try {
			teacherAuthService.teacherLogin(teacherLoginReqDTO);
		} catch (Exception authException) {
			model.addAttribute("loginError", authException.getMessage());
			authException.printStackTrace();
			return "teacherLoginPage";
		}
		
		 
		String loginSessionId = GenerateUUID.getRandom();
		SessionData sessionData = new SessionData(loginSessionId, teacherLoginReqDTO.username(), "teacher", System.currentTimeMillis());

		try {
			sessionDataService.saveSession(loginSessionId, sessionData, 120);
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpRequest.getSession().setAttribute("teacherLoginSessionId", loginSessionId);
		return "redirect:/teacher/dashboard/?username=" + teacherLoginReqDTO.username();

	}

	@GetMapping("/dashboard/")
	public ModelAndView teacherDashBoardPage(@RequestParam("username") String username) {
		return new ModelAndView("teacherDashboardPage", "username", username);
	}

	@GetMapping("/register")
	public String teacherRegisterPage(Model model) {
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
			teacherService.save(teacherRequestDTO);
		} catch (Exception e) {
			model.addAttribute("loginError", e.getMessage());
			e.printStackTrace();
			return "teacherRegisterPage";
		}
		return "redirect:/teacher/login";
	}

	@GetMapping("/logout")
	public String teacherLogout(@CookieValue(value = "userSession", required = false) String userSession,
			@RequestParam("username") String username, Model model, HttpServletResponse httpResponse) {
		if (userSession != null) {
			CookieServiceUtil.delete(httpResponse, "userSession");
		}
		return "redirect:/teacher/login";
	}

}
