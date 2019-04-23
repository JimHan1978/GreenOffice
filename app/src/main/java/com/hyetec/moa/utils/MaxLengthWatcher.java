/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.hyetec.moa.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class MaxLengthWatcher implements TextWatcher {
	private int maxLen = 0;
	private EditText editText = null;
	private Context context;
	// 是否重置了EditText的内容
	private boolean resetText;
	// 输入表情前的光标位置
	private int cursorPos;
	// 输入表情前EditText中的文本
	private String inputAfterText;

	public MaxLengthWatcher(Context context, int maxLen, EditText editText) {
		this.maxLen = maxLen;
		this.editText = editText;
		this.context = context;
	}

	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		try {
			if (!resetText) {
				cursorPos = editText.getSelectionEnd();
				// 这里用s.toString()而不直接用s是因为如果用s，
				// 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
				// inputAfterText也就改变了，那么表情过滤就失败了
				inputAfterText = s.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		try {
			if (!resetText) {
				if (count >= 2) {// 表情符号的字符长度最小为2
					CharSequence input = s.subSequence(cursorPos, cursorPos + count);
					if (containsEmoji(input.toString())) {
						resetText = true;
						Toast.makeText(context, "不支持输入Emoji表情符号", Toast.LENGTH_SHORT).show();
						// 是表情符号就将文本还原为输入表情符号之前的内容
						editText.setText(inputAfterText);
						CharSequence text = editText.getText();
						if (text instanceof Spannable) {
							Spannable spanText = (Spannable) text;
							Selection.setSelection(spanText, text.length());
						}
					}
				}
			} else {
				resetText = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		Editable editable = editText.getText();
		int len = editable.length();
		if (len > maxLen) {
			Toast.makeText(context, "已到达输入上限", 0).show();
			int selEndIndex = Selection.getSelectionEnd(editable);
			String str = editable.toString();
			// 截取新字符串
			String newStr = str.substring(0, maxLen);
			editText.setText(newStr);
			editable = editText.getText();

			// 新字符串的长度
			int newLen = editable.length();
			// 旧光标位置超过字符串长度
			if (selEndIndex > newLen) {
				selEndIndex = editable.length();
			}
			// 设置新光标所在的位置
			Selection.setSelection(editable, selEndIndex);

		}
	}
	
	/**
	 * 检测是否有emoji表情
	 * 
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source)
	{
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是Emoji
	 * 
	 * @param codePoint
	 *            比较的单个字符
	 * @return
	 */
	private static boolean isEmojiCharacter(char codePoint)
	{
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

}
