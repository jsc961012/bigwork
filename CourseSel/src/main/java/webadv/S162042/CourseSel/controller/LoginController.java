package webadv.S162042.CourseSel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import webadv.S162042.CourseSel.entity.Eadmin;
import webadv.S162042.CourseSel.entity.Jadmin;
import webadv.S162042.CourseSel.entity.Student;
import webadv.S162042.CourseSel.entity.Teacher;
import webadv.S162042.CourseSel.repository.EadminRepository;
import webadv.S162042.CourseSel.repository.JadminRepository;
import webadv.S162042.CourseSel.repository.StudentRepository;
import webadv.S162042.CourseSel.repository.TeacherRepository;

@Controller
public class LoginController {
	@Autowired
	private TeacherRepository tr;
	private Teacher teacher;
	@Autowired
	private StudentRepository sr;
	private Student student;
	@Autowired
	private EadminRepository er;
	private Eadmin eadmin;
	@Autowired
	private JadminRepository jr;
	private Jadmin jadmin;

	@GetMapping("/")
	public String login(Model model) {
		return "login";
	}

	@SuppressWarnings("unused")
	@GetMapping("/index.html")
	public String index(Model model, String no, String password, String optionsRadiosinline) {
		if (student != null) {
			model.addAttribute("student", student);
			return "s_main";
		}
		if (teacher != null) {
			model.addAttribute("teacher", teacher);
			return "tea_main";
		}

		if (eadmin != null) {
			model.addAttribute("eadmin", eadmin);
			return "ead_main";
		}
		if (jadmin != null) {
			model.addAttribute("jadmin", jadmin);
			return "jad_main";
		}

		if (optionsRadiosinline.equals("option1")) {
			// Ñ§ÉúµÇÂ¼
			Student s = sr.validStudent(no, password);
			System.out.println("****" + s.getName());
			if (s == null) {
				System.out.println("ÕËºÅÃÜÂë´íÎó");
				return "login";
			} else {
				model.addAttribute("student", s);
				student = s;
				return "s_main";
			}
		} else if (optionsRadiosinline.equals("option2")) {
			// ½ÌÊ¦µÇÂ¼
			Teacher t = tr.validTeacher(no, password);
			if (t == null) {
				System.out.println("ÕËºÅÃÜÂë´íÎó");
				return "login";
			} else {
				model.addAttribute("teacher", t);
				teacher = t;
				return "tea_main";
			}
		} else if (optionsRadiosinline.equals("option3")) {
			// ½ÌÎñµÇÂ¼
			Eadmin e = er.validEadmin(no, password);
			if (e == null) {
				System.out.println("ÕËºÅÃÜÂë´íÎó");
				return "login";
			} else {
				model.addAttribute("eadmin", e);
				eadmin = e;
				return "ead_main";
			}
		} else { // »ú·¿¹ÜÀíÔ±µÇÂ½
			Jadmin j = jr.validJadmin(no, password);
			if (j == null) {
				System.out.println("ÕËºÅÃÜÂë´íÎó");
				return "login";
			} else {
				model.addAttribute("jadmin", j);
				jadmin = j;
				return "jad_main";
			}
		}

	}

}
