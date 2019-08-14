package webadv.S162042.CourseSel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import webadv.S162042.CourseSel.entity.Audited;
import webadv.S162042.CourseSel.entity.Computer_cou_schedual;
import webadv.S162042.CourseSel.entity.Computer_room;
import webadv.S162042.CourseSel.entity.Jadmin;
import webadv.S162042.CourseSel.entity.SectionDetail;
import webadv.S162042.CourseSel.entity.Teacher;
import webadv.S162042.CourseSel.repository.JadminRepository;

@Controller
public class JadminController {
	@Autowired
	private JadminRepository jr;
	private Jadmin jadmin;

	@GetMapping("/jad_main")
	public String jad_main(Model model, HttpSession session) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		List<Computer_room> room = jr.findRoom();
		List<String> type = jr.findRoomType();
		model.addAttribute("Jadmin_computer_room", room);
		model.addAttribute("type", type);
		model.addAttribute("num", jr.AuditedNum());
		List<Audited> au = jr.findAudited();
		model.addAttribute("Audited", au);
		return "jad_main";
	}

	@GetMapping("/jad_addjf")
	public String jad_addjf(Model model, HttpSession session) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		model.addAttribute("num", jr.AuditedNum());
		List<Audited> au = jr.findAudited();
		model.addAttribute("Audited", au);
		return "jad_addjf";
	}

	@GetMapping("/add")
	public String add(@Validated Computer_room Cr, Model model, HttpSession session, String com_place, Integer com_num,
			String com_charge) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		model.addAttribute(Cr);
		model.addAttribute("num", jr.AuditedNum());
		jr.insert(com_place, com_num, com_charge);
		return "redirect:/jad_main";
	}

	@GetMapping("/jad_deletejf")
	public String jad_deletejf(Model model, HttpSession session, String com_place) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		List<Computer_room> room = jr.findRoom();
		List<String> type = jr.findRoomType();
		model.addAttribute("Jadmin_computer_room", room);
		model.addAttribute("type", type);
		List<Audited> au = jr.findAudited();
		model.addAttribute("Audited", au);
		model.addAttribute("com_place", com_place);
		model.addAttribute("num", jr.AuditedNum());
		return "jad_deletejf";
	}

	@GetMapping("/delete")
	public String delete(Model model, HttpSession session, String com_place) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		jr.delete(com_place);
		List<Computer_room> room = jr.findRoom();
		List<String> type = jr.findRoomType();
		model.addAttribute("Jadmin_computer_room", room);
		model.addAttribute("type", type);
		model.addAttribute("num", jr.AuditedNum());
		return "jad_deletejf";
	}

	@GetMapping("/jad_modjf")
	public String jad_modjf(Model model, HttpSession session) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		List<Computer_room> room = jr.findRoom();
		List<String> type = jr.findRoomType();
		model.addAttribute("Jadmin_computer_room", room);
		model.addAttribute("type", type);
		return "jad_modjf";
	}

	@GetMapping("/jad_modjf_edit{com_place}")
	public String jad_modjf_edit(@PathVariable(value = "com_place") String com_place, @Validated Computer_room Cr,
			Model model, HttpSession session) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		model.addAttribute(Cr);
		List<Computer_room> room = jr.findRoom();
		List<String> type = jr.findRoomType();
		model.addAttribute("Jadmin_computer_room", room);
		model.addAttribute("type", type);
		model.addAttribute("com_place", com_place);
		return "jad_modjf_edit";
	}

	@GetMapping("/update")
	public String update(@Validated Computer_room Cr, Model model, HttpSession session, String com_place,
			Integer com_num, String com_charge) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		model.addAttribute(Cr);
		jr.update(com_place, com_num, com_charge);
		List<Computer_room> room = jr.findRoom();
		List<String> type = jr.findRoomType();
		model.addAttribute("Jadmin_computer_room", room);
		model.addAttribute("type", type);

		return "jad_modjf";
	}

	@GetMapping("/jad_searjf")
	public String jad_searjf(Model model, HttpSession session) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		List<Computer_room> room = jr.findRoom();
		model.addAttribute("Jadmin_computer_room", room);
		List<String> type = jr.findRoomType();
		model.addAttribute("type", type);
		List<Audited> au = jr.findAudited();
		model.addAttribute("Audited", au);
		model.addAttribute("num", jr.AuditedNum());
		return "jad_searjf";
	}

	@GetMapping("/jad_expmanage")
	public String jad_expmanage(Model model, HttpSession session) {
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		List<Computer_cou_schedual> Crt = jr.getSchedual();
		model.addAttribute("Jadmin_computer_room_time", Crt);		
		List<Computer_room> room = jr.findRoom();
		model.addAttribute("Jadmin_computer_room", room);
		model.addAttribute("num", jr.AuditedNum());
		List<Audited> au = jr.findAudited();
		model.addAttribute("Audited", au);
		return "jad_expmanage";
	}

	@GetMapping("/addcourse{content}")
	public String addcourse(@PathVariable(value = "content") String content,Model model, HttpSession session) {
		String[] str=content.split(" ");
		String cou=str[0];
		String tid=str[1];
		model.addAttribute("jadmin", session.getAttribute("jadmin"));
		Audited au = jr.findOneAudited(cou);
		int flag = 0;
		Random ra = new Random();

		String[] weeks = { "星期一", "星期二", "星期三", "星期四", "星期五" };
		String[] courses = { "1", "2", "3", "4" };
		List<String> week = new ArrayList<>();
		List<String> course = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			if (jr.findSectionNum(weeks[i]) < 4) {
				week.add(weeks[i]);
			}
		}
		if (week == null) {
			flag = 1;
			System.out.println("排课失败");
		}
		int w = ra.nextInt(week.size() - 1) + 0;// 随机出星期几
		int fl = 0;
		List<SectionDetail> courset = jr.findDetail(week.get(w));
		for (int k = 0; k < courses.length; k++) {
			fl = 0;
			for (int ki = 0; ki < courset.size(); ki++) {
				if (courset.get(ki).getCrt_sec().equals(courses[k])) {
					fl = 1;
				}
			}
			if (fl == 0) {
				course.add(courses[k]);
			}
		}
		int s;
		if (course.size() == 1) {
			s = 0;
		} else {
			s = ra.nextInt(course.size() - 1) + 0;// 随机出第几节课
		}
		List<Computer_room> cr=jr.findRoom();
		List<Computer_cou_schedual> Crt = jr.getSchedual();
		int cid=ra.nextInt(jr.findcountroom() - 1) + 0;
		Teacher t=jr.findTeaName(tid);
		jr.Addcourse(cr.get(cid).getCom_id(),au.getA_cid(), au.getA_cname(), week.get(w), course.get(s),au.getA_tid(),t.getT_name(),cr.get(cid).getCom_place());
		jr.CorrectAudited(au.getA_id());
		String c="|"+cr.get(cid).getCom_place()+week.get(w)+"第"+course.get(s)+"节课|";
		System.out.println("******"+c);
		jr.insCC(au.getA_id(), au.getA_cname(),t.getT_name(), au.getA_times(), c, 0, jr.findType(au.getA_cid()), "true", 100);
		List<Audited> au1 = jr.findAudited();
		List<Computer_cou_schedual> Crt2 = jr.getSchedual();
		
		model.addAttribute("Jadmin_computer_room_time", Crt2);
		model.addAttribute("Audited", au1);
		model.addAttribute("flag", flag);
		return "jad_expmanage";
	}

//	@GetMapping("/addallcourse")
//	public String addallcourse(Model model, HttpSession session) {
//		model.addAttribute("jadmin", session.getAttribute("jadmin"));
//		List<Audited> au = jr.findAudited();
//		int flag = 0;
//		Random ra = new Random();
//
//		String[] weeks = { "星期一", "星期二", "星期三", "星期四", "星期五" };
//		String[] courses = { "1", "2", "3", "4" };
//		List<String> week = new ArrayList<>();
//		List<String> course = new ArrayList<>();
//		for (int i = 0; i < au.size(); i++) {
//			for (int l = 0; l < 5; i++) {
//				if (jr.findSectionNum(weeks[l]) < 4) {
//					week.add(weeks[l]);
//				}
//			}
//			if (week == null) {
//				flag = 1;
//				System.out.println("排课失败");
//			}
//			int w = ra.nextInt(week.size() - 1) + 0;// 随机出星期几
//			int fl = 0;
//			List<SectionDetail> courset = jr.findDetail(week.get(w));
//			for (int k = 0; k < courses.length; k++) {
//				fl = 0;
//				for (int ki = 0; ki < courset.size(); ki++) {
//					if (courset.get(ki).getCrt_sec().equals(courses[k])) {
//						fl = 1;
//					}
//				}
//				if (fl == 0) {
//					course.add(courses[k]);
//				}
//			}
//			int s;
//			if(course.size()==1) {
//				s=0;
//			}else {
//				s = ra.nextInt(course.size() - 1) + 0;// 随机出第几节课
//			}
//			jr.Addcourse(au.get(i).getA_cid(), au.get(i).getA_cname(), week.get(w), course.get(s));
//			jr.CorrectAudited(au.get(i).getA_id());
//			week.clear();
//			course.clear();
//			courset.clear();
//		}
//
//		List<Audited> au1 = jr.findAudited();
//		List<Computer_cou_schedual> Crt = jr.getSchedual();
//		model.addAttribute("Jadmin_computer_room_time", Crt);
//		model.addAttribute("Audited", au1);
//		model.addAttribute("flag", flag);
//		return "jad_main";
//	}

}
