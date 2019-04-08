/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */     
package com.hyetec.moa.view.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.view.activity.DetailsActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter {
	
	private List<UserEntity> list = new ArrayList<UserEntity>();

	private Context context;
	
	
	public SearchListAdapter(List<UserEntity> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int i = list.size();
		return i;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		Heple h = null;
		if(v == null){
			h = new Heple();
			v = LayoutInflater.from(context).inflate(R.layout.item_lv, null);
			h.catalog = (TextView) v.findViewById(R.id.catalog);
			h.tv_name = (TextView) v.findViewById(R.id.tv_name);
			h.tv_phone_num = (TextView) v.findViewById(R.id.tv_phone_num);
			h.tv_phone_type = (TextView) v.findViewById(R.id.tv_phone_type);
			h.iv_head = (ImageView) v.findViewById(R.id.iv_head);
			h.iv_arrow = (ImageView) v.findViewById(R.id.iv_arrow);
			h.cb_sel = (CheckBox) v.findViewById(R.id.cb_sel);
			h.tv_head_name = (TextView) v.findViewById(R.id.tv_head_name);
			v.setTag(h);
		}else {
			h = (Heple) v.getTag();
		}
		final UserEntity c = list.get(position);
		h.catalog.setVisibility(View.GONE);
		h.tv_name.setText(c.getUserName());
		h.tv_phone_num.setText(c.getMobile());
		h.iv_arrow.setVisibility(View.VISIBLE);
		h.cb_sel.setVisibility(View.GONE);
		h.tv_phone_type.setText(c.getPositionName());
		String userName = c.getUserName();
		
		if (c.getPhoto() == null || c.getPhoto().equals("")) {
			if(userName.length() >2){
			String name = userName.substring(userName.length() - 2, userName.length());
			h.tv_head_name.setText(name);
			}else {
				h.tv_head_name.setText(userName);
			}
			
			//h.iv_head.setImageResource(R.drawable.shape_bg_green);
			h.iv_head.setImageResource(R.drawable.bg_portrait);
		} else {
			h.tv_head_name.setText("");
			RequestOptions requestOptions = new RequestOptions();
			requestOptions.placeholder(R.drawable.ic_avatar_default);
			Glide.with(context).load(Api.IMG_URL+c.getPhoto()).apply(requestOptions).into( h.iv_head);
			//Utility.loadImage(context, h.iv_head, c.getPhoto(), -1);
		}
		h.tv_phone_num.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// 系统默认的action，用来打开默认的电话界面
				//intent.setAction(Intent.ACTION_CALL);
				intent.setAction(Intent.ACTION_DIAL);
				// 需要拨打的号码
				intent.setData(Uri.parse("tel:" + c.getMobile()));
				context.startActivity(intent);
			}
		});
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, DetailsActivity.class);
				intent.putExtra("info", list.get(position));
				context.startActivity(intent);
			}
		});
		return v;
	}
	
	class Heple {
		TextView catalog;
		TextView tv_name;
		TextView tv_phone_num;
		TextView tv_phone_type;
		ImageView iv_head;
		ImageView iv_arrow;
		CheckBox cb_sel;
		TextView tv_head_name;
	}

}
  