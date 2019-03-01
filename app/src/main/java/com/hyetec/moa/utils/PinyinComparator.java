package com.hyetec.moa.utils;

import java.util.Comparator;


import com.hyetec.moa.model.entity.ContactEntity;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


public class PinyinComparator implements Comparator<ContactEntity> {


	@Override
	public int compare(ContactEntity contactEntity1, ContactEntity contactEntity2) {
		String s = contactEntity1.getInitialIndex();
		if (s.equals("@") || contactEntity2.getInitialIndex().equals("#")) {
			return -1;
		} else if (contactEntity1.getInitialIndex().equals("#") || contactEntity2.getInitialIndex().equals("@")) {
			return 1;
		} else {
			return contactEntity1.getInitialIndex().compareTo(contactEntity2.getInitialIndex());
		}
	}
	
	/**
	 * 将汉字转换为全拼
	 */
	
	public static String getComplex(String str) {
		StringBuilder sb = new StringBuilder();
		String tempSimple = null;
		for (int i = 0; i < str.length(); i++) {
			tempSimple = getCharacterComplex(str.charAt(i));
			if (tempSimple == null) {
				sb.append(str.charAt(i));
			} else {
				sb.append(tempSimple);
				// System.out.println(sb.toString());
			}
		}
		String s = sb.toString();
		return s;
	}
	public static String getCharacterComplex(char c) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		String[] str = null;
		try {
			str = PinyinHelper.toHanyuPinyinStringArray(c, format);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str == null)
			return null;
		return str[0];
	}
	public static String getPingYin(String src) {

		char[] t1 = null;

		t1 = src.toCharArray();

		String[] t2 = new String[t1.length];

		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);

		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		t3.setVCharType(HanyuPinyinVCharType.WITH_V);

		String t4 = "";

		try {

			for (int i = 0; i < t1.length; i++) {

				// 判断是否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[i];
				} else {
					t4 += Character.toString(t1[i]);
				}
			}
			// System.out.println(t4);
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}
	
	
	
	/**
	 * 返回中文的首字母
	 * 
	 * @Title:
	 * @Description:
	 *
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int i = 0; i < str.length(); i++) {
			char word = str.charAt(i);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
	static String chReg = "[\\u4E00-\\u9FA5]+";//中文字符串匹配
	/**
	 * 解析sort_key,封装简拼,全拼
	 * @param sortKey
	 * @return
	 */
	public static String getSimpleSpell(String sortKey) {
		String simpleSpell = "";
		if (sortKey != null && sortKey.length() > 0) {
			//其中包含的中文字符
			String[] enStrs = sortKey.replace(" ", "").split(chReg);
			for (int i = 0, length = enStrs.length; i < length; i++) {
				if (enStrs[i].length() > 0) {
					//拼接简拼
					simpleSpell += enStrs[i].charAt(0);
				}
			}
		}
		return simpleSpell;
	}

}
