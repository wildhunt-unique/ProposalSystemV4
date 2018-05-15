package cn.edu.qut.common;

import java.util.HashMap;
import java.util.HashSet;

public class WordMap {
	private static HashMap<String, String> translate = new HashMap<String, String>();
	static{
		translate.put("root", "管理员");
		translate.put("user", "成员");
		translate.put("admin", "团长");
		translate.put("master", "联络员");
		translate.put("manager", "分管校长");
		
		translate.put("all_proposal", null);
		translate.put("passed_proposal", "通过");
		translate.put("unPassed_proposal", "驳回");
		translate.put("checking_proposal", "待审核");
		translate.put("dealing_proposal", "部署中");
		translate.put("working_proposal", "实施中");
		translate.put("over_proposal", "实施完成");
		
		translate.put("normal_proposal", "提案");
		translate.put("meeting_proposal", "会议");
		
		translate.put("suggest", "建议");
		translate.put("proposal", "立案");
		
		translate.put("allType_proposal", null);
	}
	public static String translate(String word) {
		return translate.get(word);
	}
}
