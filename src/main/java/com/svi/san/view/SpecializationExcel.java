package com.svi.san.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.svi.san.entity.Specialization;

public class SpecializationExcel extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, 
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception {
		//define your won excel file name
		response.addHeader("content-Dispostion","attachment:filename=SPEC.xlsx");
		//read data given by controller
		@SuppressWarnings("unchecked")
		List<Specialization> specList = (List<Specialization>)model.get("specList");
		//create one sheet
		Sheet sheet=workbook.createSheet("SPECIALIZATION");
		//create row#0  as header
		setHeader(sheet);
		//create row#1 onWord from specList
		setBody(sheet,specList);
	}
	private void setHeader(Sheet sheet) {
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("CODE");
		row.createCell(2).setCellValue("NAME");
		row.createCell(3).setCellValue("NOTE");
	}
	private void setBody(Sheet sheet,List<Specialization> list) {
		int rowNum=1;
		for(Specialization spec : list) {
			Row row=sheet.createRow(rowNum);
			row.createCell(0).setCellValue(spec.getId());
			row.createCell(1).setCellValue(spec.getSpecCode());
			row.createCell(2).setCellValue(spec.getSpecName());
			row.createCell(3).setCellValue(spec.getSpecNote());
		}
	}

}
