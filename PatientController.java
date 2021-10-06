package com.rk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rk.entity.Patient;
import com.rk.service.IPatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private IPatientService service;
	
	@GetMapping("/register")
	public String registerPatient(Model model) 
	{
		model.addAttribute("patient", new Patient());
		
		return "PatientRegister";
	}
	
	@PostMapping("/save")
	public String savePatient( @ModelAttribute Patient patient, Model model)
	{
		Long id = service.savePatient(patient);
		
		model.addAttribute("message", "Patient created with Id:"+id);
		model.addAttribute("patient", new Patient());
		
		return "PatientRegister";
	}
	
	
	
	

}
