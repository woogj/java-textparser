package com.mirae.parsing.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirae.parsing.GetRefined;

public class WriteExcel {
	private static Logger log = LoggerFactory.getLogger(GetRefined.class);
	
	public static void exportExcel(String[] inputFile, ArrayList<ArrayList<String>> sentences, String filename) {
		Charset.forName("MS949");
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("sheet1");
		Row row = null;
		Cell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("TextNo.");
		cell = row.createCell(1);
		cell.setCellValue("SentenceNo.");
		cell = row.createCell(2);
		cell.setCellValue("Text");
		cell = row.createCell(3);
		cell.setCellValue("Sentence");
		if(sentences != null && sentences.size() > 0) {
			int i=1;
			int j=0;
			for(ArrayList<String> index: sentences) {
				int t=1;
				for(String s: index) {
					row = sheet.createRow((short)i);
					cell = row.createCell(0);
					cell.setCellValue(j+1);
					cell = row.createCell(1);
					cell.setCellValue(t);
					cell = row.createCell(2);
					cell.setCellValue(inputFile[j]);
					i++;
					t++;
					cell = row.createCell(3);
					cell.setCellValue(s);
				}
				if(j > inputFile.length) break;
				j++;
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(".\\output\\"+filename+".xlsx");
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
		
		log.debug("Exported Excel File \""+filename+".xlsx\"");
	}
	
	public static void exportExcel(String[] No,String[] Text, ArrayList<ArrayList<String>> sentences, String filename) {
		Charset.forName("MS949");
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("sheet1");
		Row row = null;
		Cell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("TextNo.");
		cell = row.createCell(1);
		cell.setCellValue("SentenceNo.");
		cell = row.createCell(2);
		cell.setCellValue("Text");
		cell = row.createCell(3);
		cell.setCellValue("Sentence");
		if(sentences != null && sentences.size() > 0) {
			int i=1;
			int j=0;
			for(ArrayList<String> index: sentences) {
				int t=1;
				for(String s: index) {
					row = sheet.createRow((short)i);
					cell = row.createCell(0);
					cell.setCellValue(No[j]);
					cell = row.createCell(1);
					cell.setCellValue(t);
					cell = row.createCell(2);
					cell.setCellValue(Text[j]);
					i++;
					t++;
					cell = row.createCell(3);
					cell.setCellValue(s);
				}
				if(j > Text.length) break;
				j++;
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(".\\output\\"+filename+".xlsx");
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
		log.debug("Exported Excel File \""+filename+".xlsx\"");
	}
	
	public static void exportExcel(ArrayList<ArrayList<String>> sentences, String filename) {
//		public static void exportExcel(String[] excelFile, ArrayList<ArrayList<String>> senteces, String filename) {
		Charset.forName("MS949");
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("sheet1");
		Row row = null;
		Cell cell = null;
		if(sentences != null && sentences.size() > 0) {
			int i=0;
			for(ArrayList<String> index: sentences) {
				for(String s: index) {
					row = sheet.createRow((short)i);
					cell = row.createCell(0);
					i++;
					cell.setCellValue(s);
				}
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(".\\output\\"+filename+".xlsx");
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
		System.out.println("@@ Exported Excel File: "+filename+".xlsx");
	}
	public static void exportExcel_test(ArrayList<String[]> sentences, String filename) {
//		public static void exportExcel(String[] excelFile, ArrayList<ArrayList<String>> senteces, String filename) {
		Charset.forName("MS949");
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("sheet1");
		Row row = null;
		Cell cell = null;
		if(sentences != null && sentences.size() > 0) {
			int i=0;
			for(String[] index: sentences) {
				for(String s: index) {
					row = sheet.createRow((short)i);
					cell = row.createCell(0);
					i++;
					cell.setCellValue(s);
				}
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(".\\output\\"+filename+".xlsx");
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
		System.out.println("@@ Exported Excel File: "+filename+".xlsx");
	}

}
