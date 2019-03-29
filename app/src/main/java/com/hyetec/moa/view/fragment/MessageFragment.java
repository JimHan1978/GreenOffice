package com.hyetec.moa.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hyetec.hmdp.core.base.BaseFragment;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.view.activity.WebViewActivity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment<MessageViewModel> {


    @BindView(R.id.lv_item)
    MyListView lvItem;

    Unbinder unbinder;
    private CommonAdapter mAdapter;
    private List<MessageEntity> messageList;
    private String[] messageStr={"待办事项","月度报告","报表","待办事项","徒步活动","月度会议"};
    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance() {
        MessageFragment messageFragment = new MessageFragment();
        return messageFragment;
    }

    /**
     * UI 初始化
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        messageList=new ArrayList<>();
        for(int i=0;i<messageStr.length;i++){
            MessageEntity messageEntity=new MessageEntity();
            messageEntity.setMseeageName(messageStr[i]);
            messageList.add(messageEntity);
        }
        lvItem.setAdapter(mAdapter = new CommonAdapter<MessageEntity>(
                getContext(), messageList, R.layout.item_message) {
            @Override
            public void convert(ViewHolder helper, MessageEntity item, int pos) {
                helper.setText(R.id.tv_message_name, item.getMseeageName());
            }
        });
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    startActivity(new Intent(getActivity(),WebViewActivity.class));
                }
            }
        });

    }

    @Override
    public void setData(Object data) {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
