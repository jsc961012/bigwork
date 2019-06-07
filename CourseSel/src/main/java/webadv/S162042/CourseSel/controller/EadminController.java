package webadv.S162042.CourseSel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import webadv.S162042.CourseSel.entity.Eadmin;


@Controller
public class EadminController {
	private Eadmin eadmin=new Eadmin("888888","蔡徐坤","男");

    @GetMapping("/ead_application_processing")
    public String application_processing(Model model) {
    	model.addAttribute("eadmin", eadmin);
        return "ead_application_processing";
    }
    @GetMapping("/ead_arranging_courses")
    public String arranging_courses(Model model) {
    	model.addAttribute("eadmin", eadmin);

        return "ead_arranging_courses";
    }
    @GetMapping("/ead_Audit_cou_sel")
    public String Audit_cou_sel(Model model) {
    	model.addAttribute("eadmin", eadmin);

        return "ead_Audit_cou_sel";
    }
    @GetMapping("/ead_examine_experiment")
    public String examine_experiment(Model model) {
    	model.addAttribute("eadmin", eadmin);

        return "ead_examine_experiment";
    }
    @GetMapping("/ead_examine_course")
    public String examine_course(Model model) {
    	model.addAttribute("eadmin", eadmin);

        return "ead_examine_course";
    }
    @GetMapping("/ead_main")
    public String index(Model model) {
    	model.addAttribute("eadmin", eadmin);

        return "ead_main";
    }
}
