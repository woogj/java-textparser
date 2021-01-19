package com.mirae.parsing;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirae.parsing.utils.DOMParser;
import com.mirae.parsing.utils.ReadExcel;
import com.mirae.parsing.utils.WriteExcel;
import com.mirae.parsing.utils.getObjectSize;

public class TextParsingMain {
	private static Logger log = LoggerFactory.getLogger(TextParsingMain.class);
	private static long startTm = 0L;
	private static long endTm = 0L;
	
	public static void main(String[] args) {
		Date date = new Date();
		
		SimpleDateFormat forExcel = new SimpleDateFormat("yyMMddHHmmss");
		SimpleDateFormat forLog = new SimpleDateFormat("mm:ss:S");
		startTm = System.currentTimeMillis();
		log.debug("@@ TextParsing Started");
		Scanner sc =new Scanner(System.in);
		System.out.println("Input Excel 파일 명을 입력해주세요. (파일명을 입력하지 않으신 경우 conf/information.xml에 설정되어 있는 filename이 입력됩니다.)");
		String str = sc.nextLine();
		String filename ="";
		if(str.isEmpty()) {
//			conf/information.xml에서 설정된 파일명 가져오기
			filename = DOMParser.getFilename();
		}else {
			filename = str;
		}
		ArrayList<String[]> excel = ReadExcel.getExcelList(filename);
		String[] no = excel.get(0);
		String[] excelFile = excel.get(1);
		ArrayList<ArrayList<String>> setences = new ArrayList<>();
		ArrayList<ArrayList<String>> rsSetences = new ArrayList<>();
		BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.KOREA);
		setences = GetRefined.cropLine(excelFile);
		
		for(ArrayList<String> Index: setences) {
			rsSetences.add(GetRefined.Reattach(GetRefined.confirmSentence(GetBreakIterator.printEachForward(boundary, Index))));
		}
		log.debug("TextParsing terminated- total text: "+rsSetences.size()+", total sentences: "+getObjectSize.getSizeObj(rsSetences));
		
		WriteExcel.exportExcel(no, excelFile, rsSetences, filename+"_parsed"+forExcel.format(date));
		endTm = System.currentTimeMillis();
		log.debug(String.format("@@ TextParsing Terminated- %s (%s)", getObjectSize.getSize(rsSetences), forLog.format(endTm-startTm)));
	}
}
