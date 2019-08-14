package webadv.S162042.CourseSel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import webadv.S162042.CourseSel.entity.*;
import webadv.S162042.CourseSel.repository.EadminRepository;
import webadv.S162042.CourseSel.repository.JadminRepository;
import webadv.S162042.CourseSel.repository.StudentRepository;
import webadv.S162042.CourseSel.repository.TeacherRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private TeacherRepository tr;
    @Autowired
    private StudentRepository sr;
    @Autowired
    private EadminRepository er;
    @Autowired
    private JadminRepository jr;

    @GetMapping("/")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/index.html")
    public String index(Model model, HttpSession session, String no, String password, String optionsRadiosinline) {
        if (optionsRadiosinline.equals("student")) {
            Student s = sr.validStudent(no, password);
            if (s != null) {
                session.setAttribute("student", s);

                List<String> l=sr.findcouview(s.getSno());
                ArrayList<can_course> lc = new ArrayList<>();

                for(int i=0;i<l.size();i++) {
                    String grade = "";

                    //alter table alr_course alter column alr_grad set default -1;
                    if(sr.findgrade(l.get(i), s.getSno())!=-1) {
                        grade = ""+sr.findgrade(l.get(i), s.getSno());
                        can_course c = sr.findcan_course(l.get(i));
                        c.setGrade(grade);
                        lc.add(c);
                    }
                }
                model.addAttribute("lc",lc);

                return "s_main";
            } else {
                System.out.println("wrong password!!!");
                return "login";
            }
        }
        if (optionsRadiosinline.equals("teacher")) {
            Teacher t = tr.validTeacher(no, password);
            if (t != null) {
                session.setAttribute("teacher", t);
                List<Tea_audited> audited = tr.findWaitAudited(t.getT_no());
                List<Tea_audited> passed = tr.findPassAudited(t.getT_no());
                List<Tea_can_course> cancourse = tr.findMyCourse_week(t.getT_name(), getWeek());
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
            } else {
                System.out.println("wrong password!!!");
                return "login";
            }
        }
        if (optionsRadiosinline.equals("eadmin")) {
            Eadmin e = er.validEadmin(no, password);
            if (e != null) {
                session.setAttribute("eadmin", e);
                return "ead_main";
            } else {
                System.out.println("wrong password!!!");
                return "login";
            }
        }
        if (optionsRadiosinline.equals("jadmin")) {
        	Jadmin j = jr.validJadmin(no, password);
			if (j != null) {
				session.setAttribute("jadmin", j);
				List<Computer_room> room = jr.findRoom();
				List<String> type = jr.findRoomType();
				List<Audited> au = jr.findAudited();
				model.addAttribute("Audited", au);
				model.addAttribute("num", jr.AuditedNum());
				model.addAttribute("Jadmin_computer_room", room);
				model.addAttribute("type", type);	
				return "jad_main";
			} else {
                System.out.println("wrong password!!!");
                return "login";
            }
        }
        return "login";

    }

    //获取星期几
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

    //获取星期
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
                return "";
        }

    }

}
