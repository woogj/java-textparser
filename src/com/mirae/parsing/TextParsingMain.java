package com.mirae.parsing;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

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
		System.out.println("파싱하실 구문 혹은 Excel 파일 명(파일이름.확장자)을 입력해주세요. (파일명을 입력하지 않으신 경우 conf/information.xml에 설정되어 있는 filename이 입력됩니다.)");
		String str = sc.nextLine();
		String filename ="";
		String pattern = ".*\\.xls[x]?";


		if(str.isEmpty()) {
//			conf/information.xml에서 설정된 파일명 가져오기
			filename = DOMParser.getFilename();
			ParsingExcel.parsingExcel(filename);
		}else if(str.matches(pattern)){
			filename = str;
			ParsingExcel.parsingExcel(filename);
		}else{
			ParsingText.parsingText(str);
		}
	}
}
