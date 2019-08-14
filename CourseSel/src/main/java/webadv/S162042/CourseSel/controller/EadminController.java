package webadv.S162042.CourseSel.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import webadv.S162042.CourseSel.entity.*;
import webadv.S162042.CourseSel.repository.EadminRepository;
import webadv.S162042.CourseSel.repository.TeacherRepository;

import java.util.List;


@Controller
public class EadminController {
    private String[] week={"星期一","星期二","星期三","星期四","星期五"};
    @Autowired
    private EadminRepository er;
    @Autowired
    private TeacherRepository tr;
    //教师申请处理界面
    @GetMapping("/ead_application_processing")
    public String ead_application_processing(Model model,HttpSession session) {
        List<can_course_modify> ccmList=er.findAllCCM();
        model.addAttribute("ccm_List",ccmList);
        model.addAttribute("eadmin",(Eadmin) session.getAttribute("eadmin"));
        return "ead_application_processing";
    }

    @GetMapping("/ead_arranging_courses")
    public String arranging_courses(Model model,HttpSession session) {
    	List<Course_setting> list=er.findAllCS();
    	System.out.println(list.get(0).getCs_type());
    	System.out.println(list.size());
    	model.addAttribute("type",list);
        model.addAttribute("eadmin",  (Eadmin) session.getAttribute("eadmin"));
        return "ead_arranging_courses";
    }
    @GetMapping("/ead_Audit_cou_sel")
    public String ead_Audit_cou_sel(Model model,HttpSession session) {
        List<Coursesel_setting> courseselSettingList=er.findAllCSS();
        model.addAttribute("courseselSettingList",courseselSettingList);
        model.addAttribute("eadmin",  (Eadmin) session.getAttribute("eadmin"));
        return "ead_Audit_cou_sel";
    }

    @PostMapping("/ead_Audit_cou_sel_true")
    public String ead_Audit_cou_sel_true(Model model, HttpSession session, String cssnum, String id) {
        model.addAttribute("eadmin",  (Eadmin) session.getAttribute("eadmin"));
        er.updCSS(Integer.parseInt(cssnum.trim()),Integer.parseInt(id.trim()));
        return "redirect:/ead_Audit_cou_sel";
    }
    //审核实验课程界面
    @GetMapping("/ead_examine_experiment")
    public String ead_examine_experiment(Model model,HttpSession session) {
        List<Audited> auditedList=er.findCDAllExp_Cou("true");
        model.addAttribute("audited_list",auditedList);
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "ead_examine_experiment";
    }
    //审核普通课程界面
    @GetMapping("/ead_examine_course")
    public String ead_examine_course(Model model,HttpSession session) {
        List<Audited> auditedList=er.findCDAllExp_Cou("false");
        model.addAttribute("audited_list",auditedList);
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "ead_examine_course";
    }
    @GetMapping("/ead_main")
    public String index(Model model,HttpSession session) {
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "ead_main";
    }

    //审核普通课程操作
    @GetMapping("/ead_examine_course_judge_true/{id}")
    public String ead_examine_course_judge_true(Model model, HttpSession session, @PathVariable(value="id")String judge) {
        er.updAud(1,judge);
        Audited audited=er.findAudOne(judge);
        Coursedb coursedb=er.findCDOne(Integer.parseInt(audited.getA_cid()));
        Teacher teacher=tr.findOne(audited.getA_tid());
        String[] w={"星期一","星期三","星期五"};
        /*System.out.println(Integer.parseInt(audited.getA_id()));
        System.out.println(coursedb.getCd_name());
        System.out.println(teacher.getName());
        System.out.println(audited.getA_times());
        System.out.println(coursedb.getCd_type());
        System.out.println(coursedb.getCd_exp());
        System.out.println(coursedb.getCd_max());*/
        List<Class_room> classroomList=er.findCRAll();//获取所有教室
        //System.out.println(classroomList.size());
        Course_setting courseser_setting =er.findCStype(coursedb.getCd_type());
        String[] cs_m= courseser_setting.getCs_m().split(",");
        int n=Integer.parseInt(audited.getA_times());
        System.out.println(n);
        int w_num=0;
        if (n==3){
            w_num=0;
        }
        else {
            for (int f=0;f<(week.length);f++){
                System.out.println(cs_m[0]+"-"+week[f]);
                if (cs_m[0].equals(week[f])){
                    w_num=f;

                }
            }
        }
        String course_p="|";
        for (int o=0;o<classroomList.size();o++){
            if (n==0){
                break;
            }
            Class_room classroom=classroomList.get(o);
            if (classroom.getCr_num()>=coursedb.getCd_max()){
                for (int i=0;i<n;i++){
                    List<Class_room_time> classRoomTimes=er.findCRT(""+classroom.getCr_id(),week[w_num]);
                    System.out.println(week[w_num]);
                    if(classRoomTimes.size()==0){
                        int j=1;
                        int k=n;
                        for (;k>0&&j<5;){
                        	System.out.println("CCCCC2");
                            er.insCRT(""+classroom.getCr_id(),audited.getA_id(),week[w_num],""+j);
                            course_p=course_p+classroom.getCr_place()+week[w_num]+"第"+j+"节课|";
                            k--;
                            j++;
                            System.out.println(k);
                            break;
                        }
                        n=k;
                    }
                    else {
                    	System.out.println("CCCCC3");

                        if (classRoomTimes.size()<4){
                            int[] ints={1,2,3,4};
                            for (int p=0;p<classRoomTimes.size();p++){
                                for (int q=0;q<(ints.length-1);q++){
                           
                                    if (ints[q]==Integer.parseInt(classRoomTimes.get(p).getCrt_d())){
                                    	System.out.println("CCCCC2"+ints[q]);
                                        ints[q]=0;
                                    }
                                }
                            }
                            for (int l=0;l<=(ints.length-1);l++){
                                if (ints[l]!=0){
                                    n--;
                                    System.out.println("CCCCC1");
                                    er.insCRT(""+classroom.getCr_id(),audited.getA_id(),week[w_num],""+ints[l]);
                                    course_p=course_p+classroom.getCr_place()+week[w_num]+"第"+ints[l]+"节课|";
                                    break;
                                }
                            }
                        }
                    }
                    w_num=(w_num+2)%5;
                }
            }
        }
        System.out.println(course_p);
        er.insCC(Integer.parseInt(audited.getA_id()),coursedb.getCd_name(),teacher.getT_name(),
                audited.getA_times(),course_p,0,
                coursedb.getCd_type(),audited.getA_exp(),coursedb.getCd_max());
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "redirect:/ead_examine_course";
    }
    @GetMapping("/ead_examine_course_judge_false/{id}")
    public String ead_examine_course_judge_false(Model model, HttpSession session, @PathVariable(value="id")String judge) {
        er.updAud(-1,judge);
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "redirect:/ead_examine_course";
    }

    //设置课程安排
    @PostMapping("/ead_arranging_courses_setting")
    public String ead_arranging_courses_setting(Model model,String interest,String type){
        System.out.println(type+interest);
        er.updCS(interest,type);
        return "redirect:/ead_arranging_courses";
    }

    //审核实验课程操作
    @GetMapping("/ead_examine_experiment_judge_true/{id}")
    public String ead_examine_experiment_judge_true(Model model, HttpSession session, @PathVariable(value="id")String judge) {
        er.updAud(2,judge);
        Audited audited=er.findAudOne(judge);
        Coursedb coursedb=er.findCDOne(Integer.parseInt(audited.getA_cid()));
        Teacher teacher=tr.findOne(audited.getA_tid());
        /*er.insCC(Integer.parseInt(audited.getA_id()),coursedb.getCd_name(),teacher.getName(),
                audited.getA_times(),"D栋",0,
                coursedb.getCd_type(),coursedb.getCd_exp(),coursedb.getCd_max());*/
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "redirect:/ead_examine_experiment";
    }

    @GetMapping("/ead_examine_experiment_judge_false/{id}")
    public String ead_examine_experiment_judge_false(Model model, HttpSession session, @PathVariable(value="id")String judge) {
        er.updAud(-1,judge);
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "redirect:/ead_examine_experiment";
    }


    //教师申请处理操作
    @GetMapping("/ead_application_processing_judge_true/{id}")
    public String ead_application_processing_judge_true(Model model, HttpSession session, @PathVariable(value="id")String judge){
        can_course_modify ccm=er.findCcmOne(judge);
        String[] string=ccm.getCcm_operation().split("-");
        String s=string[1];
        String place=s.substring(0,s.indexOf("星"));
        String m=s.substring(s.indexOf("星"),s.indexOf("第"));
        String d=s.substring(s.indexOf("第")+1,s.indexOf("节"));
        //System.out.println(place+m+d);
        Class_room class_room=er.findCRplace(place);
        //System.out.println(class_room.getCr_id());
        Class_room classRoom=er.findCCR_cr(class_room.getCr_id(),m,d);
        if (classRoom!=null){//判断是否该时间点已经有课c
            er.updCmm(-1,judge);
            return "redirect:/ead_application_processing";
        }
        else {
            String[] strings=ccm.getCc_place().split("\\|");
            String str="|";
            for (int i=1;i<strings.length;i++){
                if (string[0].equals(strings[i])){
                    strings[i]=string[1];
                }
                str=str+strings[i]+"|";
            }

            er.updCmm(1,judge);
            er.updCcm_cc(str,ccm.getCc_id());
            model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
            return "redirect:/ead_application_processing";
        }
    }


    @GetMapping("/ead_application_processing_judge_false/{id}")
    public String ead_application_processing_judge_false(Model model, HttpSession session, @PathVariable(value="id")String judge){
        er.updCmm(-1,judge);
        model.addAttribute("eadmin", (Eadmin) session.getAttribute("eadmin"));
        return "redirect:/ead_application_processing";
    }
}
