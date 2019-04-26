package com.ampcus.qcoeportal.web.controller;

import com.ampcus.qcoeportal.core.entity.Requirement;
import com.ampcus.qcoeportal.usecase.ProcessRequirement;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import sun.awt.image.ToolkitImage;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Component("defaultController")
@Scope("request")
public class DefaultController extends AbstractController {
	
	private ProcessRequirement requirementService;
	
	@Autowired
	public void setRequirementService(ProcessRequirement requirementService)
	{
	    this.requirementService = requirementService;
	}
	
	@Override	
	@RequestMapping(value="/hello.htm", method=RequestMethod.GET)
	public ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception 
	{
		ModelAndView modelAndView = new ModelAndView("hello");
		List requirements = requirementService.getAllRequirements(new Integer(1));
		modelAndView.addObject("requirements", requirements);		
		
		return modelAndView;
	}
	
	@RequestMapping("/getClients.htm")
	public void getClients(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			Map object = (Map)ois.readObject();
			
			List<Map> clients = requirementService.getAllClients();
			
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(clients);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getProjectsForClient.htm")
	public void getProjectsForClient(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			String clientId = (String)ois.readObject();
			
			List<Map> projects = requirementService.getProjectsForClient(clientId);
			
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(projects);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping({"/receiveNewRequirement.htm"})
	public ModelAndView receiveNewRequirement(HttpServletRequest request)
	{
	    ModelAndView modelAndView = new ModelAndView("Test");
	    try
	    {
	      modelAndView.addObject("name", "Rohit Lal");
	      ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
	      Map object = (Map)ois.readObject();
	      
	      String requirementTitle = (String)object.get("RESULTS_KEY_TEXT_FROM_IMAGE");
	      System.out.println("RESULTS_KEY_TEXT_FROM_IMAGE: " + requirementTitle);
	      
	      ImageIcon imageIcon = (ImageIcon)object.get("RESULTS_KEY_IMAGE");
	      BufferedImage image = ((ToolkitImage)imageIcon.getImage()).getBufferedImage();
	      requirementService.saveNewRequirement(requirementTitle, image);

	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    
	    return modelAndView;
	}
	  
	@RequestMapping({"/getRequirement/{id}.htm"})
	public void getRequirement(HttpServletResponse response, @PathVariable("id") int requirementId) throws IOException
	{
	    response.setContentType("image/jpeg");
	    Requirement requirement = requirementService.getRequirement(Integer.valueOf(requirementId));
	    
	    byte[] buffer = requirement.getReqImageContent();
	    
	    InputStream in1 = new ByteArrayInputStream(buffer);
	    IOUtils.copy(in1, response.getOutputStream());
	}	
}
