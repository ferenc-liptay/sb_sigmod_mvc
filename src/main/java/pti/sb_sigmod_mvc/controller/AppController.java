package pti.sb_sigmod_mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_sigmod_mvc.dto.AuthorDTO;
import pti.sb_sigmod_mvc.dto.AuthorResponseDTO;
import pti.sb_sigmod_mvc.dto.SimpleResponseDTO;
import pti.sb_sigmod_mvc.service.AppService;


@Controller
public class AppController {
	
	private AppService service;

	
	@Autowired
	public AppController(AppService service) {
		super();
		this.service = service;
	}
	
	
	
	@GetMapping("/authors")
	public String getAuthors(
				Model model
			) {
		
		List<AuthorDTO> authors = service.getAuthors("abc");
		
		model.addAttribute("authors", authors);
		return "authors.html";
	}
	
	@GetMapping("/authors/search")
	public String getSearchAuthors(
			Model model,
			@RequestParam("searchText") String sText
			) {
		
		List<AuthorDTO> authors = service.getSearchAuthors(sText);
		
		model.addAttribute("authors", authors);
		
		return "authors.html";
	}
	
	@GetMapping("/authors/export")
	public String getExportAuthors(
			Model model,
			@RequestParam("exportType") String exportType
			) {
		SimpleResponseDTO  sRDTO = service.getExportAuthors(exportType);
		
		model.addAttribute("sRDTO", sRDTO);
		
		
		return "authors.html";
	}
	
	@GetMapping("/authors/sort")
	public String getSort(
			Model model,
			@RequestParam("sort") String sortType
			) {
		
		List<AuthorDTO> authors = service.getAuthors(sortType);
		
		model.addAttribute("authors", authors);
		
		return "authors.html";
	}
	
//	@GetMapping("authors/select")
//	public String getSelectedType(
//			
//			@RequestParam("selectedType") String selectedType
//			) {
//		
//		if(selectedType.equals("xml")) {
//			
//			return "xml.html";
//			
//		} else if(selectedType.equals("database")) {
//			
//			return "databse.html";
//		}
//		
//		return "home.html";
//	}
	
//	@GetMapping("/authors/xml")
//	public String getXml(
//			Model model,
//			@RequestParam("xmlPath") String xmlPath
//			) {
//		
//		AuthorResponseDTO aRDTO = service.getAuthorsFromXml(xmlPath);
//		
//		model.addAttribute("aRDTO", aRDTO);
//		return "xml.html";
//	}

}
