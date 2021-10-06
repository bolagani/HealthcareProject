package com.rk.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rk.entity.Specialization;
import com.rk.exception.SpecializationNotFoundException;
import com.rk.service.ISpecializationService;
import com.rk.view.SpecializationExcelView;

@Controller
@RequestMapping("/spec")
public class SpecializationController {
	
	private ISpecializationService service;
	
	/*
	 *  1. show register page.
	 */
	@GetMapping("/register")
	public String displayRegister() {
		
		return "SpecializationRegister";
	}
	
	/*
	 *  2 on submit form data
	 */

	@PostMapping("/save")
	public String saveForm( @ModelAttribute Specialization specialization, Model model ) {
	
		Long id =service.saveSpecialization(specialization);
	
		String message = "Record ("+id+") is crated";
	
		model.addAttribute("message", message);
	
		return "SpecializationRegister";
	}
	
	/*
	 *  3. display all specializations
	 */
	@GetMapping("/all")
	public String viewAll(Model model, @RequestParam(value="message", required = false) String message) 
	{
		List<Specialization> list = service.getAllSpecializations();
		
		model.addAttribute("list", list);
		model.addAttribute("message", message);
				
		return "SpecializationData";
	}
	
	/*
	 *  4. delete by id
	 */
	@GetMapping("/delete")
	public String deleteData( @RequestParam Long id, RedirectAttributes attributes)
	{
		
		try {
				service.removeSpecialization(id);
				attributes.addAttribute("message", "Record ("+id+") is removed");
			}catch( SpecializationNotFoundException e){
				e.printStackTrace();
				attributes.addAttribute("message", e.getMessage());
			}
	
		return "redirect:all";
	}
	
	/*
	 *  5. Fetch data into Edit Page
	 *    end user clicks on link, may enter id manually
	 *    If entered id is present in DB
	 *    	 > loas row as object
	 *       > send to edit page
	 *       > fill in form
	 *    Else
	 *       > redirect to all(Data Page)
	 *       > show error message ( not found)
	 */
	@GetMapping("/edit")
	public String showEditPage( @RequestParam Long id, Model model, RedirectAttributes attributes)
	{
	
		String page = null;
		
		try {
				Specialization spec = service.getOneSpecialization(id);
				model.addAttribute("specialization", spec);
				
				page = "SpecializationEdit";
				
		}catch(SpecializationNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		
			page = "redirect:all";
		}
		
		return page;
		
	}
	
	public String updateData( @ModelAttribute Specialization specialization, RedirectAttributes attributes)
	{
		
		service.updateSpecialization(specialization);
		attributes.addAttribute("message", "Record ("+specialization.getId()+" is updated");
		
		return "redire:all";
	}
	
	/*
	 *  7. read code and check with service
	 *  return message back To UI
	 */
	@GetMapping("/checkCode")
	public String validateSpecCode( @RequestParam String code, @RequestParam Long id)
	{
		
		String message = "";
		
		if(id==0 && service.isSpecCodeExist(code)) { //register check
			message = code + ", already exist";
		} else if(id != 0 && service.isSpecCodeExistForEdit(code, id)) { // edit check
			message = code + ", already exist";	
		}
		
		return message; // this is not view name it is message
	}
	
	/*
	 * 8. export data to excel file
	 */
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		
		ModelAndView m = new ModelAndView();
		
		m.setView(new SpecializationExcelView());
		
		//read data from database
		List<Specialization> list = service.getAllSpecializations();
		
		//send to excel implementation class
		m.addObject("list", list);
		
		return  m;
	}
	
}
