package webadv.S162042.CourseSel.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import webadv.S162042.CourseSel.entity.Tea_addgrade;
import webadv.S162042.CourseSel.entity.Tea_audited;
import webadv.S162042.CourseSel.entity.Tea_can_course;
import webadv.S162042.CourseSel.entity.Tea_coursedb;
import webadv.S162042.CourseSel.entity.Teacher;
import webadv.S162042.CourseSel.repository.TeacherRepository;

@Controller
public class TeacherController {
	@Autowired
	private TeacherRepository tr;
	private Teacher teacher;

	@GetMapping("/tea_main")
	public String tea_main(Model model, HttpSession session) {
		// 教师主页
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		teacher = (Teacher) session.getAttribute("teacher");
		List<Tea_audited> audited = tr.findWaitAudited(teacher.getT_no());
		List<Tea_audited> passed = tr.findPassAudited(teacher.getT_no());
		List<Tea_can_course> cancourse = tr.findMyCourse_week(teacher.getT_name(), getWeek());
		model.addAttribute("teacher", session.getAttribute("teacher"));
		model.addAttribute("audited", audited);
		model.addAttribute("passed", passed);
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		List<String> place = new ArrayList<>();
		for (int i = 0; i < cancourse.size(); i++) {
			String[] strs = cancourse.get(i).getCc_place().split("\\|");
			for (int j = 0; j < strs.length; j++) {
				if (strs[j].contains(getWeek())) {
					String build = strs[j].substring(0, strs[j].indexOf("星"));
					place.add(build);
					cancourse.get(i).setCc_place(build);
				}
			}
		}
		model.addAttribute("cancourse", cancourse);
		model.addAttribute("week", week);
		model.addAttribute("weeke", getWeeke(week));
		return "tea_main";
	}

	@GetMapping("/tea_teach")
	public String tea_teach(Model model, HttpSession session) {
		// 进入教授课程界面
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		teacher = (Teacher) session.getAttribute("teacher");
		List<Tea_can_course> can_course_list = tr.findMyCourse(teacher.getT_name());
		List<String> typelist = tr.findMyCourseType();
		model.addAttribute("can_course_list", can_course_list);
		model.addAttribute("typelist", typelist);
		return "tea_teach";
	}

	@GetMapping("/tea_applycou")
	public String tea_applycou(Model model, HttpSession session) {
		// 申请开课界面
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		teacher = (Teacher) session.getAttribute("teacher");

		List<Tea_coursedb> coursedb = tr.findCoursedb(teacher.getT_name(), teacher.getT_no());
		int flag = 0;
		if (coursedb.isEmpty()) {
			flag = 1;
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		model.addAttribute("coursedb", coursedb);
		model.addAttribute("flag", flag);// 判断是否有课可选
		return "tea_applycou";
	}

	@GetMapping("/tea_applycou_w")
	public String tea_applycou_w(Model model, HttpSession session, String courseid, String num, String time,
			String exp) {
		// 申请开课后跳回主界面
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}

		String[] str = courseid.split(" ");
		String id = str[0];
		String name = str[1];
		teacher = (Teacher) session.getAttribute("teacher");
		tr.insertApply(id + teacher.getT_no(), id, name, teacher.getT_no(), num, time, 0, exp);
		model.addAttribute("teacher", session.getAttribute("teacher"));
		List<Tea_audited> audited = tr.findWaitAudited(teacher.getT_no());
		List<Tea_audited> passed = tr.findPassAudited(teacher.getT_no());
		List<Tea_can_course> cancourse = tr.findMyCourse_week(teacher.getT_name(), getWeek());
		model.addAttribute("teacher", session.getAttribute("teacher"));
		model.addAttribute("audited", audited);
		model.addAttribute("passed", passed);
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		List<String> place = new ArrayList<>();
		for (int i = 0; i < cancourse.size(); i++) {
			String[] strs = cancourse.get(i).getCc_place().split("\\|");
			for (int j = 0; j < strs.length; j++) {
				if (strs[j].contains(getWeek())) {
					String build = strs[j].substring(0, strs[j].indexOf("星"));
					place.add(build);
					cancourse.get(i).setCc_place(build);
				}
			}
		}
		model.addAttribute("cancourse", cancourse);
		model.addAttribute("week", week);
		model.addAttribute("weeke", getWeeke(week));
		return "tea_main";
	}

	@GetMapping("/tea_applyexp")
	public String tea_applyexp(Model model, HttpSession session) {
		// 申请实验
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		return "tea_applyexp";
	}

	@GetMapping("/tea_applymod")
	public String tea_applymod(Model model, HttpSession session) {
		// 申请调课
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		teacher = (Teacher) session.getAttribute("teacher");
		List<Tea_can_course> can_course_list = tr.findMyCourse(teacher.getT_name());
		List<String> typelist = tr.findMyCourseType();
		model.addAttribute("can_course_list", can_course_list);
		model.addAttribute("typelist", typelist);
		return "tea_applymod";
	}

	@GetMapping("/tea_addgrades")
	public String tea_addgrades(Model model, HttpSession session) {
		// 学生成绩界面
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		teacher = (Teacher) session.getAttribute("teacher");
		List<Tea_addgrade> gradelist = tr.FindStu(teacher.getT_name());
		List<String> couType = tr.FindCouType(teacher.getT_name());
		model.addAttribute("teacher", session.getAttribute("teacher"));
		model.addAttribute("gradelist", gradelist);
		model.addAttribute("couType", couType);
		return "tea_addgrades";
	}

	@GetMapping("/tea_addgrades_edit{couname}")
	public String tea_addgrades_edit(@PathVariable(value = "couname") String couname, Model model,
			HttpSession session) {
		// 编辑学生成绩
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		String[] str = couname.split(" ");
		teacher = (Teacher) session.getAttribute("teacher");
		List<Tea_addgrade> gradelist = tr.FindStu(teacher.getT_name());
		List<String> couType = tr.FindCouType(teacher.getT_name());
		model.addAttribute("teacher", session.getAttribute("teacher"));
		model.addAttribute("gradelist", gradelist);
		model.addAttribute("types", str[1]);
		model.addAttribute("sno", str[0]);
		model.addAttribute("couType", couType);
		return "tea_addgrades_edit";
	}

	@GetMapping("/tea_finish_add")
	public String tea_finish_add(Model model, HttpSession session, String sno, String cid, String grades) {
		// 填写学生成绩
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		tr.InsertGrade(sno, cid, grades);
		teacher = (Teacher) session.getAttribute("teacher");
		List<Tea_addgrade> gradelist = tr.FindStu(teacher.getT_name());
		List<String> couType = tr.FindCouType(teacher.getT_name());
		model.addAttribute("teacher", session.getAttribute("teacher"));
		model.addAttribute("gradelist", gradelist);
		model.addAttribute("couType", couType);
		return "tea_addgrades";
	}

	@GetMapping("/tea_stulist")
	public String tea_stulist(Model model, HttpSession session) {
		// 学生列表
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		return "tea_stulist";
	}

	@GetMapping("/tea_applymod_write{id}")
	public String tea_applymod_write(@PathVariable(value = "id") String id, Model model, HttpSession session) {
		// 课程时间信息
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		Tea_can_course course = tr.findCourseDetial(id);
		String[] str = course.getCc_place().split("\\|");
		List<String> place = new ArrayList<>();
		for (int i = 1; i < str.length; i++) {
			place.add(str[i]);
		}
		model.addAttribute("course", course);
		model.addAttribute("place", place);
		model.addAttribute("flag", 0);
		return "tea_applymod_write";
	}

	@GetMapping("/tea_applymod_edit{content}")
	public String tea_applymod_edit(@PathVariable(value = "content") String content, Model model, HttpSession session) {
		// 编辑申请调课的信息
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		String[] strs = content.split(" ");
		model.addAttribute("teacher", session.getAttribute("teacher"));
		Tea_can_course course = tr.findCourseDetial(strs[0]);
		String[] str = course.getCc_place().split("\\|");
		List<String> place = new ArrayList<>();
		for (int i = 1; i < str.length; i++) {
			place.add(str[i]);
		}
		String build = place.get(Integer.parseInt(strs[1]) - 1).substring(0,
				place.get(Integer.parseInt(strs[1]) - 1).indexOf("星"));
		String week = place.get(Integer.parseInt(strs[1]) - 1).substring(
				place.get(Integer.parseInt(strs[1]) - 1).indexOf("星"),
				place.get(Integer.parseInt(strs[1]) - 1).indexOf("第"));
		String num = place.get(Integer.parseInt(strs[1]) - 1).substring(
				place.get(Integer.parseInt(strs[1]) - 1).indexOf("第") + 1,
				place.get(Integer.parseInt(strs[1]) - 1).indexOf("节"));
		model.addAttribute("course", course);
		model.addAttribute("place", place);
		model.addAttribute("flag", strs[1]);
		model.addAttribute("build", build);
		model.addAttribute("week", week);
		model.addAttribute("num", num);
		List<String> buildlist = tr.findClass();
		List<String> weeklist = new ArrayList<>();
		weeklist.add("星期一");
		weeklist.add("星期二");
		weeklist.add("星期三");
		weeklist.add("星期四");
		weeklist.add("星期五");
		List<String> numlist = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			numlist.add("第" + i + "节课");
		}
		model.addAttribute("buildlist", buildlist);
		model.addAttribute("weeklist", weeklist);
		model.addAttribute("numlist", numlist);
		return "tea_applymod_edit";
	}

	@GetMapping("/tea_applymod_succ")
	public String tea_applymod_succ(Model model, HttpSession session, String cid, String flag, String build,
			String week, String num) {
		// 填写申请调课的信息
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		Tea_can_course course = tr.findCourseDetial(cid);
		String[] str = course.getCc_place().split("\\|");
		List<String> place = new ArrayList<>();
		for (int i = 1; i < str.length; i++) {
			place.add(str[i]);
		}
		String cplace = place.get(Integer.parseInt(flag) - 1) + "-" + build + week + num;
		System.out.println("&&&" + cplace);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		tr.InsertMod(course.getCc_id(), course.getCc_name(), course.getCc_tname(), course.getCc_time(),
				course.getCc_place(), course.getCc_type(), course.getCc_exp(), course.getCc_max(), "0", cplace,
				dateFormat.format(new Date()), "特殊情况");
		model.addAttribute("teacher", session.getAttribute("teacher"));
		model.addAttribute("course", course);
		model.addAttribute("place", place);
		model.addAttribute("flag", 0);
		return "tea_applymod_write";
	}

	@GetMapping("/course_detail{id}")
	public String course_detail(@PathVariable(value = "id") String id, Model model, HttpSession session) {
		// 课程详细信息
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		Tea_can_course course = tr.findCourseDetial(id);
		model.addAttribute("course", course);
		model.addAttribute("flag", 0);
		return "tea_teach_detial";
	}

	@GetMapping("/find_teach_cou")
	public String find_teach_cou(Model model, HttpSession session, String content) {
		// 搜索课程详细信息
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		teacher = (Teacher) session.getAttribute("teacher");
		Tea_can_course course = tr.findCourseDetial_cn(content, teacher.getT_name());
		int flag = 0;
		if (course == null) {
			flag = 1;
		}
		model.addAttribute("course", course);
		model.addAttribute("flag", flag);
		return "tea_teach_detial";
	}

	@GetMapping("/find_mod_cou")
	public String find_mod_cou(Model model, HttpSession session, String content) {
		// 搜索课程时间信息以修改
		if (session.getAttribute("teacher") == null) {
			// 判断session是否存在
			System.out.println("lost session!!!");
			return "login";
		}
		model.addAttribute("teacher", session.getAttribute("teacher"));
		teacher = (Teacher) session.getAttribute("teacher");
		Tea_can_course course = tr.findCourseDetial_cn(content, teacher.getT_name());
		if (course == null) {
			int flag = 1;
			Tea_can_course course1 = new Tea_can_course();
			course1.setCc_name(" ");
			model.addAttribute("course", course1);
			model.addAttribute("flag", flag);
			return "tea_applymod_write";
		}
		String[] str = course.getCc_place().split("\\|");
		List<String> place = new ArrayList<>();
		for (int i = 1; i < str.length; i++) {
			place.add(str[i]);
		}
		model.addAttribute("course", course);
		model.addAttribute("place", place);
		model.addAttribute("flag", 0);
		return "tea_applymod_write";
	}

	// 获取星期几
	public static String getWeek() {
		Calendar cal = Calendar.getInstance();
		int i = cal.get(Calendar.DAY_OF_WEEK);
		switch (i) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return "";
		}
	}

	// 获取星期
	public static String getWeeke(int i) {
		switch (i) {
		case 1:
			return "Mon";
		case 2:
			return "Tues";
		case 3:
			return "Wed";
		case 4:
			return "Thur";
		case 5:
			return "Fri";
		default:
			return " ";
		}

	}

}
