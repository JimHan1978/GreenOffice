package com.hyetec.moa.view.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.UserEntity;



public class TestBaseAdapter extends BaseAdapter implements
		StickyListHeadersAdapter, SectionIndexer {

	private List<UserEntity> list;
	private Context context;
	/** 选中目录 */
	private int[] sectionIndices;
	/** 选中字母 */
	private Character[] sectionsLetters;

	public TestBaseAdapter(Context context, List<UserEntity> list) {
		this.list = list;
		this.context = context;
		sectionIndices = getSectionIndices();
		sectionsLetters = getStartingLetters();
	}

	public void setList(List<UserEntity> list) {
		this.list = list;
	}


	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ChildHeple c = null;
		if (v == null) {
			c = new ChildHeple();
			v = LayoutInflater.from(context).inflate(R.layout.item_lv, null);
			c.catalog = (TextView) v.findViewById(R.id.catalog);
			c.tv_name = (TextView) v.findViewById(R.id.tv_name);
			c.tv_phone_num = (TextView) v.findViewById(R.id.tv_phone_num);
			c.tv_phone_type = (TextView) v.findViewById(R.id.tv_phone_type);
			c.iv_head = (ImageView) v.findViewById(R.id.iv_head);
			c.iv_arrow = (ImageView) v.findViewById(R.id.iv_arrow);
			c.cb_sel = (CheckBox) v.findViewById(R.id.cb_sel);
			c.tv_head_name = (TextView) v.findViewById(R.id.tv_head_name);
			v.setTag(c);
		} else {
			c = (ChildHeple) v.getTag();
		}
		final UserEntity ce = list.get(position);
		c.catalog.setText(ce.getInitialIndex());
		String userName = ce.getUserName();
		c.tv_name.setText(userName);
		String num = ce.getMobile();
		c.tv_phone_num.setText(num);
		c.tv_phone_type.setText(ce.getPositionName());

		if (ce.getPhoto() == null || ce.getPhoto().equals("")) {
			if (userName.length() > 2) {
				String name = userName.substring(userName.length() - 2, userName.length());
				c.tv_head_name.setText(name);
			}else {
				c.tv_head_name.setText(userName);
			}

			c.iv_head.setImageResource(R.drawable.bg_portrait);
		} else {
			c.tv_head_name.setText("");
			RequestOptions requestOptions = new RequestOptions();
			requestOptions.placeholder(R.drawable.ic_avatar_default);
			Glide.with(context).load(Api.IMG_URL+ce.getPhoto()).apply(requestOptions).into(c.iv_head);
		}

			c.catalog.setVisibility(View.GONE);
			c.iv_arrow.setVisibility(View.VISIBLE);
			c.cb_sel.setVisibility(View.GONE);
			c.tv_phone_num.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// 系统默认的action，用来打开默认的电话界面
				//intent.setAction(Intent.ACTION_CALL);
				intent.setAction(Intent.ACTION_DIAL);
				// 需要拨打的号码
				intent.setData(Uri.parse("tel:" + ce.getMobile()));
				context.startActivity(intent);
			}
		});
		return v;
	}
	public int getPositionForSection(String buMen) {
		if (!buMen.equals("")) {
			for (int i = 0; i < getCount(); i++) {
				if (buMen.equals(list.get(i).getInitialIndex())) {
					return i;
				}
			}
		}
		return -1;
	}
	
	class ChildHeple {
		TextView catalog;
		TextView tv_name;
		TextView tv_phone_num;
		TextView tv_phone_type;
		ImageView iv_head;
		ImageView iv_arrow;
		CheckBox cb_sel;
		TextView tv_head_name;
	}

	private int[] getSectionIndices() {
		int[] sections = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			sections[i] = i;
		}
		return sections;
	}

	private Character[] getStartingLetters() {
		Character[] letters = new Character[sectionIndices.length];
		for (int i = 0; i < sectionIndices.length; i++) {
			letters[i] = list.get(sectionIndices[i]).getInitialIndex().charAt(0);
		}
		return letters;
	}

	@Override
	public Object[] getSections() {
		return sectionsLetters;
	}

	public int getSectionStart(int itemPosition) {
		return getPositionForSection(getSectionForPosition(itemPosition));
	} // remember that these have to be static, postion=1 should walys return
		// the

	// same Id that is.
	@Override
	public long getHeaderId(int position) {
		// return the first character of the country as ID because this is what
		// headers are based upon
		return list.get(position).getInitialIndex().charAt(0);
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < sectionIndices.length; i++) {
			if (position < sectionIndices[i]) {
				return i - 1;
			}
		}
		return sectionIndices.length - 1;
	}

	@Override
	public int getPositionForSection(int section) {
		if (section >= sectionIndices.length) {
			section = sectionIndices.length - 1;
		} else if (section < 0) {
			section = 0;
		}
		return sectionIndices[section];
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_contact_head, parent, false);
		}
		TextView txt = (TextView) convertView.findViewById(R.id.header_text);
		// set header text as first char in name
		txt.setText(list.get(position).getInitialIndex().charAt(0) + "");
		return convertView;
	}


}
