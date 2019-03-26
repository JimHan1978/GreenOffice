/** 
 * @Title: MyListView.java 
 * @Package com.hyetec.growupdiary.ui 
 * @Description: 
 * @author zzn 
 * @date 2016年2月29日 下午4:48:40 
 * @version V1.0 
 */    
package com.hyetec.moa.view.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/** 
 * @Title: MyListView.java 
 * @Package com.hyetec.growupdiary.ui 
 * @Description: TODO(添加描述) 
 * @author zzn 
 * @date 2016年2月29日 下午4:48:40 
 * @version V1.0 
 */

public class MyListView extends ListView {

	/**
	 * @param context
	 * @param attrs
	 */
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see android.widget.ListView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//TODO Auto-generated method stub
		 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
		           MeasureSpec.AT_MOST);
		 super.onMeasure(widthMeasureSpec, expandSpec); 
	}

}
