package com.svi.san.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.svi.san.entity.Document;
import com.svi.san.service.IDocumentService;

@Controller
@RequestMapping("/document")
public class DocumentController {
	
	@Autowired
	private IDocumentService docuService;

	@PostMapping("/upload")
	public String FileUpload(@RequestParam Long docId,@RequestParam MultipartFile docOb) {
		try {
			Document doc=new Document();
			doc.setId(docId);
			doc.setDocName(docOb.getOriginalFilename());
			doc.setDocData(docOb.getBytes());
			docuService.saveDocument(doc);
		}catch (Exception e) {
			e.getMessage();
		}
		return "redirect:all";
	}
	
	@GetMapping("/all")
	public String showAllDocument(Model model) {
		model.addAttribute("idVal", System.currentTimeMillis());
		List<Object[]> list=docuService.getDocumentByIdAndName();
		model.addAttribute("list", list);
		return "FileUpload";
	}
	
	@GetMapping("/delete")
	public String deleteDocument(@RequestParam Long docId) {
		try {
			docuService.deleteDocumentById(docId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	@GetMapping("/download")
	public void downlodDocument(@RequestParam Long id,HttpServletResponse response) {
		try {
			Document doc=docuService.getDocumentById(id);
			//provide filename using header
			response.setHeader("Content-Disposition","attachment;filename="+doc.getDocName());
			//copy document data to response
			FileCopyUtils.copy(doc.getDocData(), response.getOutputStream());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
