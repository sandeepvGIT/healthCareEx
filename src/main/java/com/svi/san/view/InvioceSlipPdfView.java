package com.svi.san.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.svi.san.entity.SlotRequest;

@Component
public class InvioceSlipPdfView extends AbstractPdfView {
	
	@Override
	protected void buildPdfMetadata(Map<String, Object> model,
			                                                            Document document, 
			                                                            HttpServletRequest request) {
		//Create Header is a Rectangle with text that can be put above every page. 
		HeaderFooter header = new HeaderFooter(new Phrase("INVOICE PDF VIEW"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		//header.setBorder(HeaderFooter.UNDEFINED);
		document.setHeader(header);
		
		//Create Footer is a Rectangle with text that can be put below every page. 
		HeaderFooter footer = new HeaderFooter(new Phrase(new Date()+" (C) Nareshit, Page # "), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		//footer.setBorder(HeaderFooter.UNDEFINED);
		document.setFooter(footer);
		
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, 
			                                                              Document document, 
			                                                              PdfWriter writer,
			                                                              HttpServletRequest request, 
			                                                              HttpServletResponse response) throws Exception {
		
		//Get Data from Controller
		SlotRequest slotReq = (SlotRequest)model.get("slotRequest");
		//Download PDF
		//download PDF with a given filename
		response.addHeader("Content-Disposition", "attachment:filename=invoice.pdf");
		// font-family, size, style and color. 
		Font titaleFont=new Font(Font.TIMES_ROMAN,40,Font.BOLD,Color.BLACK);
		Paragraph title=new Paragraph("SV HOSPITAL", titaleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(20.0f);
		title.setSpacingAfter(25.0f);
		//add to document
		document.add(title);
		
		Font addrFont=new Font(Font.TIMES_ROMAN, 15, Font.BOLD, Color.BLACK);
		Paragraph addrsTitle=new Paragraph("NH-12 Lucknow Hyderabad", addrFont);
		addrsTitle.setSpacingAfter(25.0f);
		//add to document
		document.add(addrsTitle);
		
		//add image
		Image img=Image.getInstance("https://w7.pngwing.com/pngs/565/349/png-transparent-hospital-health-facility-health-care-management-hospitals-angle-building-business.png");
		//set the height and width
		img.scaleAbsolute(100.0f, 100.0f);
		//align center
		img.setAlignment(Element.ALIGN_CENTER);
		//add to document
		document.add(img);
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MMM/yyyy");
		String date=sdf.format(slotReq.getAppointment().getDate());
		double fee =slotReq.getAppointment().getAmt();
		double gst=fee*6/100.0f;
		double finalAmt=fee+(2*gst);
		
		PdfPTable table = new PdfPTable(4);
		table.setSpacingBefore(25.0f);
		table.setSpacingAfter(20.0f);
		table.addCell(getDesignCell("Appointment Date"));
		table.addCell(date);
		table.addCell(getDesignCell("Patient Name"));
		table.addCell(slotReq.getPatient().getFirstName()+""+slotReq.getPatient().getLastName());
		table.addCell(getDesignCell("Doctor Name"));
		table.addCell(slotReq.getAppointment().getDoctor().getFirstName()+""
		                          +slotReq.getAppointment().getDoctor().getLastName());
		table.addCell(getDesignCell("Final Amount"));
		table.addCell(String.valueOf(finalAmt));
		document.add(table);
		
		PdfPTable billData = new PdfPTable(2);
		//set border width none
		billData.getDefaultCell().setBorderWidth(0f);
		billData.setSpacingBefore(35.0f);
		billData.setHorizontalAlignment(Element.ALIGN_RIGHT);
		billData.addCell(getTextCell("Booking Amount"));
		billData.addCell(slotReq.getAppointment().getAmt().toString());
		billData.addCell(getTextCell("GST"));
		billData.addCell(String.valueOf(gst));
		billData.addCell(getTextCell("SGST"));
		billData.addCell(String.valueOf(gst));
		billData.addCell(getTextCell("Total Amount"));
		billData.addCell(getTextCell(String.valueOf(finalAmt)));
		//add to document
		document.add(billData);
		
		Font noteFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.RED);
		Paragraph note = new Paragraph("NOTE: THIS IS AUTO-GENERATED PAYMENT SLIP, FOR MORE DETALS CONTACT FRONTDESK @ 1234567890",noteFont);
		note.setAlignment(Element.ALIGN_CENTER);
		note.setSpacingBefore(15.0f);
		document.add(note);
		
		Font signFont = new Font(Font.TIMES_ROMAN, 20, Font.BOLD, Color.BLACK);
		Paragraph sign = new Paragraph("SIGNATURE",signFont);
		sign.setAlignment(Element.ALIGN_RIGHT);
		sign.setSpacingBefore(35.0f);
		sign.setSpacingAfter(10.0f);
		//add to document
		document.add(sign);
		Paragraph datePar = new Paragraph("Date :" + new Date());
		datePar.setAlignment(Element.ALIGN_RIGHT);
		document.add(datePar);
		
	}
	
	private Phrase getDesignCell(String data) {
		Font font = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLUE);
		return new Phrase(data, font);
		
	}
	
	private Phrase getTextCell(String data) {
		Font font = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK);
		return new Phrase(data, font);
	}

}
