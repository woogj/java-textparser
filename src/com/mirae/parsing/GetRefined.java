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
	
	//commit 시 삭제
	public static void main(String[] args) {
		String s1 = "\"이러한 가치관은, 창업동아리 길거리 세일즈 활동에서 20만 원 매출을 달성하는 데 도움이 되었습니다.\r\n" + 
				"\r\n" + 
				" 2013년, 마케팅 강의에서 다양한 기업의 사례를 공부했고, 고객의 니즈를 빠르게 분석하고 대응하지 못한다면 시장에서 경쟁력을 잃고 도태된다는 것을 배웠습니다. \"\r\n" + 
				"";
		String s2 = "\"만점으로 성적장학금을 받아 연기된 학기의 학비를 마련하여 다니겠다는 약속으로 부모님을 설득시켰고, 충분한 시간동안 전공, 특히 회로분야를 완벽히 공부하겠다는 목표를 세웠습니다.\r\n" + 
				"\r\n" + 
				"2016년 1학기, 아침 생활 스터디를 통해 매일 아침 7시 반까지 도서관에 오며 전자공학과에서 배울 수 있는 영역이 다양하여 모든 영역을 소화하기 위해 계획적으로 나눠 균형 있게 공부하려고 노력하였습니다. \"\r\n" + 
				"";
		String s3 = "저는 팀장으로서 4명의 팀원들과 함께 목표설정과 부품구입을 위해 토의하는 시간을 가졌습니다. 5미터 트랙을 4초 이내에 주파했던 전년도 선배들의 1등 기록을 앞서는 것을 목표로 정하였고 각자 모터, 바퀴, 태양판, 몸체부품을 조사해오고 개인이 조사한 내용을 발표하였습니다. ";
		String s4 = "전공 실험과목에서 팀별로 주어진 금액을 가지고 태양광자동차를 만드는 프로젝트를 M.M.D 수행하였습니다. test문장쓰";
		String s5 = "전공 실험과목에서 팀별로 주어진 금액을 가지고 태양광자동차를 만드는 프로젝트를 M.M.D 수행하였습니다.김test문장쓰";
		String s6 = "출국 직전에 알았고 어쩔 수 없이 우선 중국으로 떠났습니다.처음엔 유학원생들과 같이 움직였기 때문에 사정을 말하여 조금의 생활비를 빌려 첫 고비는 넘겼습니다. "; 
		String s7 ="'왜 COOL만?'\r\n"; 
		String s8 ="이라는 의문을 갖게 되었고 끊임없이 고민하였습니다. 먼저, 드라이기 COOL 상태에서 전압과 전류의 파형을 오실로스코프로 측정하는 것부터 시작하였습니다. 여기서 예상하지 못한 결과가 나왔습니다. 전열기기로 역률이 1이기 때문에 전압과 전류 모두 정현파가 나오리라 생각과 다르게 전류의 파형이 왜곡되어 있었습니다. 전류는 전압과 위상은 같았지만, clipping 되어 첨두 부분이 잘려나간 것입니다. clipping된 파형을 어떻게 측정할 수 있을지 또 고민하였습니다. 정답은 적분으로 계산하는 것이었고, 더 정확하게 측정할 수 있도록 전력계 프로그래밍을 바꾸었습니다. 정현파가 아닌 어떤 파형이 나오더라도 전류 값을 정확하게 측정할 수 있게 하였습니다. 전류 측정이 정확해져 전력을 오차 1%이하로 구할 수 있었고 역률 측정이 가능하여 기능 추가도 할 수 있었습니다. 그 결과, A+를 얻을 수 있었습니다.\r\n";
		String s9 = "'전공과 관련이 없는데 흥미를 붙일 수 있을까?'";
		String s10 = ", '여기서 배울 것이 있기는 할까?'";
		String s11 = "같은";
		String s12 ="회의적인 생각이 들었고 어떻게 해야 할지 답을 내지 못하였습니다.";
		String s13 ="1.";
		String s14 ="회의적인 생각이 들었고 어떻게 해야 할지 답을 내지 못하였습니다?";
		String s15 ="메롱";
		String ws1="[제조업 실무 경험에서 주체가 되어 문제 해결 - 진취적인 자세와 자신감으로 극복]\n\n- 저는 2014년 지이티에스라는 환경 기계 업체에서 6개월간 근무했었습니다. 기계공학 엔지니어가 되기 위해 역량을 쌓기 위한 목표로 제조업에서 기계 설계 직무를 수행해보자는 목표로 업무를 수행했습니다. 설계팀에서 모노펌프와 필터프레스의 설계를 담당했습니다. 제품을 고객의 현장에 올바르게 사용할 수 있도록 제품의 형상을 구상했고 변수들을 고려하여 설계했습니다. 최종적으로 3D/2D 도면으로 구조검토와 부품 생산을 준비했고 원활한 시공을 위한 기술지원도 수행했습니다.\n\n- 필터프레스 설비의 성능확대를 담당했을 때 도전적인 업무에 직면했습니다. 성능확대를 위해 Plate 수를 늘리면서 축의 처짐이 오차범위를 초과하는 문제가 발생했습니다. 저는 이 문제에 대해 주체가 되어 반드시 해결하겠다는 집념으로 연구했습니다. 이를 담당했던 대리님과 생산직 반장님께 조언을 구하여 중요하게 다룰 부분과 해결 사례에 대한 노하우를 들었습니다. 이를 바탕으로 제가 직면한 이 문제를 꼭 해결하겠다는 집념과 공학적인 마인드로 고심하여 보강 Plate 설치방안을 고안해냈습니다.\n\n- 한 달 후 제품이 생산을 마치고 시운전에 들어갔습니다. 반장님께서 시험성적서를 보여주시며 성능과 안정성 모두 합격이라고 말씀하셨습니다. 처음 실무를 접하면서 힘겹게 배우고 우여곡절 끝에 설계를 완료했던 과정이 생각나면서 성취감을 느꼈습니다. 무엇보다도 능력 밖의 일이라고 느껴짐에도 불구하고 도전적인 마음가짐으로 임하면 해낼 수 있다는 자신감이 생겼습니다.\n";
		String ws2="자발적으로 최고 수준의 목표를 세우고 끈질기게 성취한 경험은 소속부대를 화생방 평가성적 1등으로 만든 것입니다.\r\n" + 
				"화학장교로써 제가 설정한 목표는 10개의 직할부대 중 1등을 함과 동시에 소속부대 화생방 평가장병들 중에 전체 1등을 만들어 사령관님 상장을 받게 하는 것이었습니다.\r\n" + 
				"화생방 평가는 상급부대에서 평가를 하는 것이고 1년에 전/후반기 한번씩 있었습니다. \r\n" + 
				"상급부대 지침 상 화학장교는 화생방 평가에 참가를 못하였고, 그대신 소속부대에서 참가하는 장병들을 교육시킬 임무가 있었습니다.\r\n" + 
				"화생방 평가는 저에게 있어 저를 어필할 수 있는 기회라 생각하였기 때문에 상급부대에서 평가하기전 저는 지휘관의 결심을 받아 2박 3일동안 간부, 병사 16명을 대상으로 자체교육을 진행하였습니다.\r\n" + 
				"처음에는 제 머릿속에 있는 지식을 교육시키는 것이 많이 힘들어서 난감했었습니다. 교육받는 인원들도 생소한 이론을 배우다보니 집중을 잘 못하였습니다.  \r\n" + 
				"하지만 하나하나 반복학습을 통하여 이론을 숙달 시켰고, 작도훈련은 실제로 지도를 준비하여 그려보면서 연습시켰습니다. 또한 주말에도 스터디를 만들어서 같이 문제도 풀었습니다.\r\n" + 
				"결국엔 '16년도 후반기, '17년도 전반기 2번 연속으로 수도방위사령부 직할부대 10개 중 저희 헌병단을 1등으로 만들었고, 2번 다 사령관님 상장을 받게 하였습니다.\r\n" + 
				"이번일은 저의 군생활 중 가장 큰 성과라고 생각합니다.\r\n" + 
				"좋은 결과를 얻기위해서는 사전에 치밀하게 준비를 하여야 하고, 준비과정 속에서 겪는 시행착오는 꾸준히 피드백하여 고쳐나가야 한다고 생각합니다.   \r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"";
			
//		String[] sentences = {ws1};
//		String[] excelFile = ReadExcel.getExcel("testset2");
		ArrayList<String> sentenceList= new ArrayList<>();
		sentenceList.add(s10);
		sentenceList.add(s11);
		sentenceList.add(s13);
		sentenceList.add(s14);
		sentenceList.add(s15);
//		for(String str: sentenceList) {
//			System.out.println(str);
//		}
//		 confirmSentence(sentenceList);
//		cropLine(sentences);
		Reattach(sentenceList);
//		removeLine(excelFile);
//		removeLine(sentences);
	}
	
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

	// 구문 입력 시 cropLine 메소드 210123
	public static ArrayList<String> cropLine(String construction){
		ArrayList<String> rsList = new ArrayList<>();
		if(construction != null) {
			construction += "\n";
			String regex = ".*[\r\n]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(construction);
			while(m.find()) {
				String tmpStr = m.group(0);
				tmpStr = tmpStr.trim();
				if(!tmpStr.isEmpty()) {
					rsList.add(tmpStr);
				}
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
