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



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.GroupContactEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.view.activity.DetailsActivity;
import com.hyetec.moa.view.ui.PinnedHeaderExpandableListView;

import java.util.List;

@SuppressLint("ResourceAsColor")
public class ExpandableContactAdapter extends BaseExpandableListAdapter{

	public List<GroupContactEntity> list;
	private Context context;


	public ExpandableContactAdapter(List<GroupContactEntity> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public View getGroupView(int groupPosition, final boolean isExpanded, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		final GroupHeple g;
		if (v == null) {
			g = new GroupHeple();
			v = LayoutInflater.from(context).inflate(R.layout.item_group, null);
			g.group_name = (TextView) v.findViewById(R.id.tv_group_name);
			g.iv_group_head = (ImageView) v.findViewById(R.id.iv_group_head);
			g.user_num = (TextView) v.findViewById(R.id.tv_user_num);
			v.setTag(g);
		} else {
			g = (GroupHeple) v.getTag();
		}
		if(!TextUtils.isEmpty(list.get(groupPosition).getGroupName())) {
			String name = list.get(groupPosition).getGroupName();
			g.group_name.setText(name);
		}
		if(list.get(groupPosition).getUserEntities()!=null){
			g.user_num.setText(list.get(groupPosition).getUserNum()+" 人");
		}else {
			g.user_num.setText("0 人");
		}

		 if (isExpanded) {
			 g.iv_group_head.setBackgroundResource(R.drawable.buddy_header_arrow_1);
		}else {
			g.iv_group_head.setBackgroundResource(R.drawable.buddy_header_arrow);
		}
		 
		return v;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildHeple c = null;
		if (v == null) {
			c = new ChildHeple();
			v = LayoutInflater.from(context).inflate(R.layout.item_group_child, null);
			c.catalog = (TextView) v.findViewById(R.id.catalog);
			c.tv_name = (TextView) v.findViewById(R.id.tv_name);
			c.tv_phone_num = (TextView) v.findViewById(R.id.tv_phone_num);
			c.tv_phone_type = (TextView) v.findViewById(R.id.tv_phone_type);
			c.iv_head = (CircleImageView) v.findViewById(R.id.iv_head);
			c.tv = (ImageView) v.findViewById(R.id.iv_go);
			c.name = (TextView) v.findViewById(R.id.name);
			v.setTag(c);
		} else {
			c = (ChildHeple) v.getTag();
		}

		final UserEntity userEntity = list.get(groupPosition).getUserEntities().get(childPosition);
		String SortNo=(userEntity.getSortNo()+"").substring(0, 1);
		if( !TextUtils.isEmpty(userEntity.getDeptName()) && SortNo.equals("4") ){
			c.catalog.setVisibility(View.VISIBLE);
			c.catalog.setText(userEntity.getDeptName());
		}else {
			c.catalog.setVisibility(View.GONE);
		}
		
		c.tv_name.setText(userEntity.getUserName());
		c.tv_phone_num.setText(userEntity.getMobile());
		c.tv_phone_type.setText(userEntity.getPositionName());
		String photo = userEntity.getPhoto();
		if (photo == null || photo.equals("")) {
			if (userEntity.getUserName().length() > 2) {
				String name = userEntity.getUserName().substring(userEntity.getUserName().length() - 2, userEntity.getUserName().length());
				c.name.setText(name);
			}else {
				c.name.setText(userEntity.getUserName());
			}
			c.iv_head.setImageResource(R.drawable.bg_portrait);
		} else {
			c.name.setText("");
			RequestOptions requestOptions = new RequestOptions();
			requestOptions.placeholder(R.drawable.ic_avatar_default);
			Glide.with(context).load(Api.IMG_URL+photo).apply(requestOptions).into( c.iv_head);
		}
		c.tv_phone_num.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// 系统默认的action，用来打开默认的电话界面
				//intent.setAction(Intent.ACTION_CALL);
				intent.setAction(Intent.ACTION_DIAL);
				// 需要拨打的号码
				intent.setData(Uri.parse("tel:" + userEntity.getMobile()));
				context.startActivity(intent);
			}
		});
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, DetailsActivity.class);
				intent.putExtra("info", userEntity);
				context.startActivity(intent);
			}
		});
		return v;
	}

	class GroupHeple {
		TextView group_name;
		ImageView iv_group_head;
		TextView user_num;
	}

	class ChildHeple {
		TextView catalog;
		TextView tv_name;
		TextView tv_phone_num;
		TextView tv_phone_type;
		ImageView iv_head;
		ImageView tv;
		TextView name;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getUserEntities().get(childPosition);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getUserEntities().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
