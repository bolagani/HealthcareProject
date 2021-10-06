package com.rk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rk.entity.Doctor;
import com.rk.exception.DoctorNotFoundException;
import com.rk.service.IDoctorService;
import com.rk.service.ISpecializationService;


@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private IDoctorService service;
	
	@Autowired
	private ISpecializationService specializationService;
	
	private void creteDynamicUi(Model model) {
		
		model.addAttribute("specializations", specializationService.getSpecIdAndName());
	}
	
	// 1. show register page
	@GetMapping("/register")
	public String showReg(@RequestParam(value = "message", required = false) String message, Model model)
	{
		
		model.addAttribute("message", message);
		creteDynamicUi(model);
		return "DoctorRegister";
	}
	
	// 2. save on submit
	@PostMapping("/save")
	public String save( @ModelAttribute Doctor doctor, RedirectAttributes attributes)
	{
		Long id = service.saveDoctor(doctor);
		String message = "Doctor ("+id+") is created";
		
		attributes.addAttribute("message", message);
	
		return "redirect:register";
	}

	/*
	 *  3. display data
	 */
	@GetMapping("/all")
	public String display( @RequestParam(value = "message", required = false) String message, Model model)
	{
		List<Doctor> list = service.getAllDoctors();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		
		return "DoctorData";
	}
	
	/*
	 *  4. delete by id
	 */
	
	public String delete(@RequestParam("id") Long id, RedirectAttributes attributes)
	{
		String message;
		try {
				service.removeDoctor(id);
				message = "Doctor Removed";
		}catch(DoctorNotFoundException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		
		attributes.addAttribute("message", message);
		return "redirect:all";
		
	}
	
	/*
	 *  5. show edit
	 */
	@GetMapping("/edit")
	public String edit( @RequestParam("id") Long id, RedirectAttributes attributes, Model model)
	{
		String page;
		
		try {
			Doctor doctor = service.getOneDoctor(id);
			model.addAttribute("doctor", doctor);
			page = "DoctorEdit";
		}catch(DoctorNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		
		return page;
	}
	
	// 6. do update
	public String doUpdate( @ModelAttribute Doctor doctor, RedirectAttributes attributes)
	{
		service.updateDoctor(doctor);
		
		attributes.addAttribute("message", doctor.getId()+", updated!");
		return "redirect:all";
	}
	
	// 7. email and mobile duplicate validations (Ajax)
	
	
	// 8. excel export

}
