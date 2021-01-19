package com.mirae.parsing;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirae.parsing.utils.getObjectSize;

public class GetBreakIterator {
	private static Logger log = LoggerFactory.getLogger(GetBreakIterator.class);
	
	public static void getWordInstanse(String s) {
		String stringToExamine = s;
        //print each word in order
		BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(stringToExamine);
        printEachForward(boundary, stringToExamine);
		
	} 
	
	 public static ArrayList<String> printEachForward(BreakIterator boundary, String source) {
		 ArrayList<String> sentences = new ArrayList<>();
	     int start = boundary.first();
	     for (int end = boundary.next();
	          end != BreakIterator.DONE;
	          start = end, end = boundary.next()) {
	    	  sentences.add(source.substring(start,end));
	     }
	     return sentences;
	 }
	
	 public static ArrayList<String> printEachForward(BreakIterator boundary, ArrayList<String>  source) {
		 ArrayList<String> sentences = new ArrayList<>();
		 for(String s: source) {
			 boundary.setText(s);
			 int start = boundary.first();
		     for (int end = boundary.next();
		          end != BreakIterator.DONE;
		          start = end, end = boundary.next()) {
		    	  sentences.add(s.substring(start,end));
		     }
		 }
	     return sentences;
	 }
	
	 
	 public static void printEachBackward(BreakIterator boundary, String source) {
	     int end = boundary.last();
	     for (int start = boundary.previous();
	          start != BreakIterator.DONE;
	          end = start, start = boundary.previous()) {
	     }
	 }
	 
	 public static void printFirst(BreakIterator boundary, String source) {
	     int start = boundary.first();
	     int end = boundary.next();
	 }
	 
	 public static void printLast(BreakIterator boundary, String source) {
	     int end = boundary.last();
	     int start = boundary.previous();
	 }
	 
	 public static void printAt(BreakIterator boundary, int pos, String source) {
	     int end = boundary.following(pos);
	     int start = boundary.previous();
	 }
	 
	 public static int nextWordStartAfter(int pos, String text) {
		 BreakIterator wb = BreakIterator.getWordInstance();
	     wb.setText(text);
	     int last = wb.following(pos);
	     int current = wb.next();
	     while (current != BreakIterator.DONE) {
	         for (int p = last; p < current; p++) {
	             if (Character.isLetter(text.codePointAt(p)))
	                 return last;
	         }
	         last = current;
	         current = wb.next();
	     }
	     return BreakIterator.DONE;
	 }
	 public static void getLineInstance(String s) {
			String stringToExamine = s;
	        //print each word in order
			BreakIterator boundary = BreakIterator.getLineInstance(Locale.KOREA);
	        boundary.setText(stringToExamine);
	        int start = boundary.first();
	        int end = boundary.next();
	        int lineLength=0;
	        
	        while(end != BreakIterator.DONE) {
	        	String word = stringToExamine.substring(start, end);
	        	lineLength = lineLength + word.length();
	        	if(lineLength >= s.length()) {
	        		lineLength = word.length();
	        	}
	        	start = end;
	        	end = boundary.next();
	        }
		} 

	 

}
