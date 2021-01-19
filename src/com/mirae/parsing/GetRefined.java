package com.mirae.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirae.parsing.utils.ReadExcel;
import com.mirae.parsing.utils.getObjectSize;

public class GetRefined {
	private static Logger log = LoggerFactory.getLogger(GetRefined.class);
	/*
	 * Parsing rules of the Textparser_v3 (2019.09.04) 
	 *  1. 줄바꿈 parsing (GetRefined.cropLine)
		2. 공백 제거 (GetRefined.cropLine)
		3. BreakIterator
		4. [.숫자/알파벳] parsing (GetRefined.confirmSentence)
		5. [.] 뒤에 띄어쓰기 없어도 Parsing (GetRefined.confirmSentence)
		6. 인용문 내의 ?와 !으로 끝나는 문장 Reattach (GetRefined.Reattach)
		7. [숫자목록. 문장] 형식 parsing 룰 삭제 (GetRefined.Reattach)
	 */
	
	
	//전처리
	// \n일때만 작동, \r\n일 때 작동 안되는거 수정 211019
	public static ArrayList<ArrayList<String>> cropLine(String[] excelFile){
		ArrayList<ArrayList<String>> rsList = new ArrayList<>();
		for(String s: excelFile) {
			if(s != null) {
				s += "\n";
				String regex = ".*[\r\n]";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(s);
				ArrayList<String> tmp = new ArrayList<>();
				while(m.find()) { 
					String tmpStr = m.group(0);
					tmpStr = tmpStr.trim();
					if(!tmpStr.isEmpty()) {
						tmp.add(tmpStr);
					}
				}
				rsList.add(tmp);
			}
		}
		return rsList;
	}
	
	public static ArrayList<String> removeLine(String[] excelFile) {
		ArrayList<String> sentences = new ArrayList<>();
		String regex= "(\r\n|\r|\n|\n\r)";
		for(String s: excelFile) {
			if(s !=null) {
				s = s.replaceAll(regex, " ");
				sentences.add(s);
			}
		}
		return sentences;
	}

	public static ArrayList<String> confirmSentence(ArrayList<String> sentences){
			ArrayList<String> rs = new ArrayList<>();
			String regex = "([^0-9,^a-z,^A-Z])(\\.)\\s?([0-9,a-z,A-Z,ㄱ-힣])";
			Pattern p = Pattern.compile(regex);
			for(String s: sentences) {
				Matcher m = p.matcher(s);
				String[] tmp = {};
				while(m.find()) {
					s = s.replaceAll(regex, "$1$2\n$3");
					tmp = s.split("\n");
				}
				if(tmp.length>0) {
					for(String index: tmp) {
						rs.add(index);
					}
				}else {
					rs.add(s);
				}
			}
			return rs;
	}
	
	public static ArrayList<String> Reattach(ArrayList<String> sentences){
		ArrayList<String> rs = new ArrayList<>();
		String regex = "([\\??,!?][^\\]]{1})$";
		String regex2 = "^[0-9]*\\.$";
		String tmp = "";
		Pattern p = Pattern.compile(regex);
		Pattern p2 = Pattern.compile(regex2);

		for(int i=0; i<sentences.size(); i++) {
			tmp = sentences.get(i).trim();
			Matcher m = p.matcher(tmp);
			while(m.find()) {
				tmp += sentences.get(i+1);
				m = p.matcher(tmp.trim());
				i++;
			}
			Matcher m2 = p2.matcher(tmp);
			while(m2.find()) {
				tmp += sentences.get(i+1);
				i++;
			}
			rs.add(tmp);
		}
		return rs;
	}
}
