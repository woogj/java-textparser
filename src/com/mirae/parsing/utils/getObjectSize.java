package com.mirae.parsing.utils;

import java.util.ArrayList;

public class getObjectSize {
	
	public static int getSizeList(ArrayList<String> obj) {
		int size = 0;
		for(String str: obj) {
			size++;
		}
		return size;
	}

	public static int getSizeObj(ArrayList<ArrayList<String>> obj) {
		int size = 0;
		for(ArrayList<String> index: obj) {
			for(String str: index) {
				size++;
			}
		}
		return size;
	}
	
	public static String getSize(ArrayList<ArrayList<String>> obj) {
		
		int i=0;
		int j=0;
		for(ArrayList<String> index: obj) {
			i++;
			for(String str: index) {
				j++;
			}
		}
		String rsSize ="total text :"+i+", total sentences: "+j;
		return rsSize;
	}
}
