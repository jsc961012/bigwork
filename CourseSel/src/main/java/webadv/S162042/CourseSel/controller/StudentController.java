package webadv.S162042.CourseSel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import webadv.S162042.CourseSel.entity.Student;
import webadv.S162042.CourseSel.repository.StudentRepository;

@Controller

public class StudentController {
	@Autowired
	private StudentRepository st;
	private String name;
	/*
	@GetMapping("/")
	public String login(Model model) {
		return "login";
	}
	@GetMapping("/index.html")
	public String index(Model model,String username,String password) {
		if(name!=null) {
			model.addAttribute("name",name);
			return "index";
		}

		Student s=st.validStudent(username, password);
		if(s!= null) {
			name = s.getName();
			model.addAttribute("name",s.getName());
			//session.setAttribute("name1", s.getName());
			return "index";
		}
		else
			return "login";

	}
	 */
	@GetMapping("/s_calendar.html")
	public String calendar(Model model) {
		model.addAttribute("name",name);
		return "s_calendar";
	}

	@GetMapping("/s_selcourse.html")
	public String glyphicons(Model model) {
		return "s_selcourse";
	}

	@GetMapping("/s_couview.html")
	public String media_gallery(Model model) {
		return "s_couview";
	}

	@GetMapping("/s_Results.html")
	public String tables_dynamic(Model model) {
		return "s_Results";
	}

	@GetMapping("/s_Evaluation.html")
	public String tables(Model model) {
		return "s_Evaluation";
	}

}
