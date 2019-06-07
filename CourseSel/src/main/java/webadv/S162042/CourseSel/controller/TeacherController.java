package webadv.S162042.CourseSel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import webadv.S162042.CourseSel.entity.Teacher;
import webadv.S162042.CourseSel.repository.TeacherRepository;

@Controller
public class TeacherController {
	@Autowired
	private TeacherRepository tr;
	private Teacher teacher=new Teacher("teacher","女","软件学院","123");

	@GetMapping("/tea_teach")
	public String tea_teach(Model model) {
		model.addAttribute("teacher", teacher);
		return "tea_teach";
	}

	@GetMapping("/tea_applycou")
	public String tea_applycou(Model model) {
		model.addAttribute("teacher", teacher);
		return "tea_applycou";
	}

	@GetMapping("/tea_applyexp")
	public String tea_applyexp(Model model) {
		model.addAttribute("teacher", teacher);
		return "tea_applyexp";
	}

	@GetMapping("/tea_applymod")
	public String tea_applymod(Model model) {
		model.addAttribute("teacher", teacher);
		return "tea_applymod";
	}

	@GetMapping("/tea_addgrades")
	public String tea_addgrades(Model model) {
		model.addAttribute("teacher", teacher);
		return "tea_addgrades";
	}

	@GetMapping("/tea_stulist")
	public String tea_stulist(Model model) {
		model.addAttribute("teacher", teacher);
		return "tea_stulist";
	}

}
