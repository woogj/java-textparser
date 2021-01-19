package com.mirae.parsing.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirae.parsing.GetRefined;

public class ReadExcel {
	private static Logger log = LoggerFactory.getLogger(ReadExcel.class);
	
	public static String[] getExcel(String filename) {
		String[] excelData = {};
		try {
			Charset.forName("MS949");
			FileInputStream fis = new FileInputStream(".\\input\\"+filename+".xlsx");
			Workbook workbook = WorkbookFactory.create(fis);
			int rowIndex = 0;
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			excelData = new String[rows];
			for(rowIndex = 1; rowIndex < rows;rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if(row !=null) {
					Cell cell = sheet.getRow(rowIndex).getCell(1);
					if(cell == null) {
						continue;
					}else {
						excelData[rowIndex-1] = cell.getStringCellValue();
					}
				}
			}
			fis.close();
			workbook.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
		log.debug("Excel loading successed, total text is "+excelData.length);
		return excelData;
	}
	
	public static ArrayList<String[]> getExcelList(String filename) {
		String[] no = {};
		String[] excelData = {};
		ArrayList<String[]> rs = new ArrayList<>();
		try {
			Charset.forName("MS949");
			FileInputStream fis = new FileInputStream(".\\input\\"+filename+".xlsx");
			Workbook workbook = WorkbookFactory.create(fis);
			int rowIndex = 0;
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			no = new String[rows];
			excelData = new String[rows];
			for(rowIndex = 1; rowIndex < rows;rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if(row !=null) {
					Cell cell = sheet.getRow(rowIndex).getCell(0);
					Cell cellText = sheet.getRow(rowIndex).getCell(1);
					if(cell == null) {
						continue;
					}else {
						if(cell.getCellType() == CellType.STRING) {
							no[rowIndex-1] = cell.getStringCellValue();
						}else if(cell.getCellType() == CellType.NUMERIC) {
							no[rowIndex-1] = Integer.toString((int)cell.getNumericCellValue());
						}
						excelData[rowIndex-1] = cellText.getStringCellValue();
					}
				}
			}
			fis.close();
			workbook.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
		rs.add(no);
		rs.add(excelData);
		log.debug("Excel loading successed, total text is "+excelData.length);
		return rs;
	}
	
	public static void toStringExcel(String filename) {
		String[] excelData = {};
		excelData = getExcel(filename);
		for(String index: excelData) {
			System.out.println(index);
		}
	}
}
