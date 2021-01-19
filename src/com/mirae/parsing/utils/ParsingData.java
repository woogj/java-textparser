package com.mirae.parsing.utils;

import java.util.ArrayList;

public class ParsingData {
	public static void main(String[] args) {
		String[] excelFile = ReadExcel.getExcel("testset2");
		ArrayList<String[]> setences = new ArrayList<>();
		for(String s: excelFile) {
			setences.add(getSentence(s));
		}
		WriteExcel.exportExcel_test(setences, "testResult8");
	}

	public static String[] getSentence(String s) {
		String[] sentences = {};
		String pattern = "\\.[^0-9]";
		
		if(s != null) {
			sentences = s.split(pattern);
			for(String index: sentences) {
				index = index.trim();
			}
		}
		
		return sentences;
	}
	
	public void toStringSentence(String[] sentences) {
		for(String key: sentences) {
			System.out.println(key.concat("."));
		}
	}

}
