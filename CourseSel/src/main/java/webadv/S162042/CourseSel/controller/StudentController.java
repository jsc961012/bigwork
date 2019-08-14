package webadv.S162042.CourseSel.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import webadv.S162042.CourseSel.entity.Student;
import webadv.S162042.CourseSel.entity.can_course;
import webadv.S162042.CourseSel.entity.s_calendar;
import webadv.S162042.CourseSel.entity.s_exp;
import webadv.S162042.CourseSel.repository.StudentRepository;

@Controller

public class StudentController {
	@Autowired
	private StudentRepository st;
	private String name;

	@GetMapping("/s_calendar.html")
	public String calendar(Model model, HttpSession session, HttpServletRequest request) {
		model.addAttribute("student", session.getAttribute("student"));
		Student s = (Student) request.getSession().getAttribute("student");
		List<can_course> lc = st.findtime(s.getSno());
		s_calendar l1 = new s_calendar();
		s_calendar l2 = new s_calendar();
		s_calendar l3 = new s_calendar();
		s_calendar l4 = new s_calendar();
		s_calendar l5 = new s_calendar();
		for (int i = 0; i < lc.size(); i++) {
			String str[] = (lc.get(i).getCc_place()).split("\\|");
			for (int j = 1; j < str.length; j++) {
				String time[] = str[j].split("星期");
				if (time[1].indexOf("一") != -1) {
					l1 = tclass(l1, time[1], lc.get(i), time[0]);
				}
				if (time[1].indexOf("二") != -1) {
					l2 = tclass(l2, time[1], lc.get(i), time[0]);
				}
				if (time[1].indexOf("三") != -1) {
					l3 = tclass(l3, time[1], lc.get(i), time[0]);
				}
				if (time[1].indexOf("四") != -1) {
					l4 = tclass(l4, time[1], lc.get(i), time[0]);
				}
				if (time[1].indexOf("五") != -1) {
					l5 = tclass(l5, time[1], lc.get(i), time[0]);
				}
			}
		}
		
		ArrayList<s_exp> le = new ArrayList<>();
		for(int i=0;i<lc.size();i++) {
			s_exp se=st.findexp(lc.get(i).getCc_id());
			if(se!=null)
				le.add(se);
		}
		for(int i=0;i<le.size();i++) {
			if(le.get(i).getCrt_m().equals("星期一"))
				exptime(l1,le.get(i));
			if(le.get(i).getCrt_m().equals("星期二"))
				exptime(l2,le.get(i));
			if(le.get(i).getCrt_m().equals("星期三"))
				exptime(l3,le.get(i));
			if(le.get(i).getCrt_m().equals("星期四"))
				exptime(l4,le.get(i));
			if(le.get(i).getCrt_m().equals("星期五"))
				exptime(l5,le.get(i));
		}
		model.addAttribute("l1", l1);
		model.addAttribute("l2", l2);
		model.addAttribute("l3", l3);
		model.addAttribute("l4", l4);
		model.addAttribute("l5", l5);
		return "s_calendar";
	}

	public s_calendar exptime(s_calendar l,s_exp s) {
		if(s.getCrt_sec().equals("1"))
			l.setA1(s.getCrt_cname()+"  实验室"+s.getCr_id());
		if(s.getCrt_sec().equals("2"))
			l.setA2(s.getCrt_cname()+"  实验室"+s.getCr_id());
		if(s.getCrt_sec().equals("3"))
			l.setP1(s.getCrt_cname()+"  实验室"+s.getCr_id());
		if(s.getCrt_sec().equals("4"))
			l.setP1(s.getCrt_cname()+"  实验室"+s.getCr_id());
		return l;
	}
	public s_calendar tclass(s_calendar l, String time, can_course lc, String place) {
		String time2[] = time.split("第");
		if (time2[1].indexOf("1") != -1) {
			l.setA1(lc.getCc_name() + "                   " + lc.getCc_tname() + "                     " + place);
		}
		if (time2[1].indexOf("2") != -1) {
			l.setA2(lc.getCc_name() + "" + lc.getCc_tname() + "" + place);
		}
		if (time2[1].indexOf("3") != -1) {
			l.setP1(lc.getCc_name() + "" + lc.getCc_tname() + "" + place);
		}
		if (time2[1].indexOf("4") != -1) {
			l.setP2(lc.getCc_name() + "" + lc.getCc_tname() + "" + place);
		}
		if (time2[1].indexOf("5") != -1) {
			l.setEv1(lc.getCc_name() + "" + lc.getCc_tname() + "" + place);
		}
		return l;
	}

	@GetMapping("/s_selcourse.html")
	public String glyphicons(Model model, HttpSession session, String alret) {
		model.addAttribute("student", session.getAttribute("student"));
		model.addAttribute("lc", st.findallcou());
		model.addAttribute("alret", alret);
		return "s_selcourse";
	}

	@GetMapping("/s_couview.html")
	public String media_gallery(Model model, HttpSession session, HttpServletRequest request) {
		model.addAttribute("student", session.getAttribute("student"));
		Student s = (Student) request.getSession().getAttribute("student");
		List<String> l = st.findcouview(s.getSno());
		ArrayList<can_course> lc = new ArrayList<>();
		for (int i = 0; i < l.size(); i++) {
			can_course c = st.findcan_course(l.get(i));
			lc.add(c);
		}
		model.addAttribute("lc", lc);
		return "s_couview";
	}

	@GetMapping("/s_Results.html")
	public String tables_dynamic(Model model, HttpSession session, HttpServletRequest request) {
		model.addAttribute("student", session.getAttribute("student"));
		Student s = (Student) request.getSession().getAttribute("student");
		List<String> l = st.findcouview(s.getSno());
		ArrayList<can_course> lc = new ArrayList<>();

		for (int i = 0; i < l.size(); i++) {
			String grade;
			// alter table alr_course alter column alr_grad set default -1;
			if (st.findgrade(l.get(i), s.getSno()) == -1)
				grade = "成绩未出";
			else
				grade = "" + st.findgrade(l.get(i), s.getSno());
			can_course c = st.findcan_course(l.get(i));
			lc.add(c);
			c.setGrade(grade);
		}
		model.addAttribute("lc", lc);
		return "s_Results";
	}

	@GetMapping("/s_Evaluation.html")
	public String tables(Model model, HttpSession session, HttpServletRequest request) {
		model.addAttribute("student", session.getAttribute("student"));
		Student s = (Student) request.getSession().getAttribute("student");
		List<String> l = st.findcouview(s.getSno());
		ArrayList<can_course> lc = new ArrayList<>();
		for (int i = 0; i < l.size(); i++) {
			can_course c = st.findcan_course(l.get(i));
			c.setEva(st.findeva(s.getSno(), c.getCc_id()));
			lc.add(c);
		}
		model.addAttribute("lc", lc);
		return "s_Evaluation";
	}

	@GetMapping("/s_main")
	public String s_main(Model model, HttpSession session) {
		model.addAttribute("student", session.getAttribute("student"));
		return "s_main";
	}

	@GetMapping("/select/{id}")
	public String select(Model model, HttpSession session, @PathVariable(value = "id") int id,
			HttpServletRequest request, RedirectAttributes attributes) {
		model.addAttribute("student", session.getAttribute("student"));
		model.addAttribute("lc", st.findallcou());
		Student s = (Student) request.getSession().getAttribute("student");
		String type = st.findtype(id);
		int num = st.findbytype(type, s.getSno());
		boolean istime = true;
		if (num == st.findlimit(type)) {
			attributes.addAttribute("alret", "对应类型课程选择已达上限");
		} else {
			if (st.findalr(s.getSno(), id) == 0) {
				List<can_course> lc = st.findtime(s.getSno());
				for (int i = 0; i < lc.size(); i++) {
					can_course c = st.findcan_course("" + id);
					String str[] = (c.getCc_place()).split("\\|");
					for (int j = 1; j < str.length; j++) {
						String time[] = str[j].split("星期");
						if (lc.get(i).getCc_place().contains(time[1]))
							istime = false;
					}
				}
				if (istime) {
					st.addcou(s.getSno(), id);
					st.addnum(id);
					attributes.addAttribute("alret", "选课成功");
				} else
					attributes.addAttribute("alret", "所选课程与已有课程时间冲突");

			} else {
				attributes.addAttribute("alret", "您已选过此课");
			}

		}
		return "redirect:/s_selcourse.html";
	}

	@GetMapping("/evaluation")
	public String evaluation(Model model, HttpSession session, HttpServletRequest request, String cid, String ctname,
			String cname) {
		model.addAttribute("student", session.getAttribute("student"));
		Student s = (Student) request.getSession().getAttribute("student");
		model.addAttribute("cname", cname);
		model.addAttribute("ctname", ctname);
		model.addAttribute("cid", cid);
		return "s_form";
	}

	@GetMapping("/commiteva")
	public String commiteva(Model model, HttpSession session, HttpServletRequest request, int cid, String message,
			String level) {
		model.addAttribute("student", session.getAttribute("student"));
		Student s = (Student) request.getSession().getAttribute("student");
		System.out.print(message);
		st.addeva(s.getSno(), cid, level, message);
		return "redirect:/s_Evaluation.html";
	}

}
