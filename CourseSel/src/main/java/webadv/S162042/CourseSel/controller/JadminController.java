package webadv.S162042.CourseSel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import webadv.S162042.CourseSel.entity.Jadmin;
import webadv.S162042.CourseSel.repository.JadminRepository;

@Controller
public class JadminController {
	@Autowired
	private JadminRepository jr;
	private Jadmin jadmin=new Jadmin("302001","¼ÖÎ¬Ë¹","ÄÐ","123");

	@GetMapping("/jad_addjf")
	public String jad_addjf(Model model) {
		model.addAttribute("jadmin", jadmin);
		return "jad_addjf";
	}
	
	@GetMapping("/jad_deletejf")
	public String jad_deletejf(Model model) {
		model.addAttribute("jadmin", jadmin);
		return "jad_deletejf";
	}
	
	@GetMapping("/jad_modjf")
	public String jad_modjf(Model model) {
		model.addAttribute("jadmin", jadmin);
		return "jad_modjf";
	}
	
	@GetMapping("/jad_searjf")
	public String jad_searjf(Model model) {
		model.addAttribute("jadmin", jadmin);
		return "jad_searjf";
	}
	
	@GetMapping("/jad_expmanage")
	public String jad_expmanage(Model model) {
		model.addAttribute("jadmin", jadmin);
		return "jad_expmanage";
	}

}
