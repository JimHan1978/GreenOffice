package com.hyetec.moa.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseFragment;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.view.activity.LoginActivity;
import com.hyetec.moa.view.activity.MainActivity;
import com.hyetec.moa.view.activity.WebViewActivity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.viewmodel.ContactsViewModel;
import com.hyetec.moa.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment<MessageViewModel> {


    @BindView(R.id.lv_item)
    MyListView lvItem;
    @BindView(R.id.tv_title)
    TextView mTitleView;
    Unbinder unbinder;
    private CommonAdapter mAdapter;
    private List<MessageEntity> messageList;
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
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MessageViewModel.class);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleView.setText("消息");
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(messageList.get(i).getMessageType().equals("report")){
                    startActivity(new Intent(getActivity(),WebViewActivity.class).putExtra("date",messageList.get(i).getYearAndMonth()));
                }
            }
        });

    }

    @Override
    public void setData(Object data) {

    }

    public void getData() {
        mViewModel.getMessageList().observe(this, messageLists -> {
            if(messageLists!=null && messageLists.isSuccess()){
                if( messageLists.getResult()!=null) {
                    messageList = messageLists.getResult();
                    lvItem.setAdapter(mAdapter = new CommonAdapter<MessageEntity>(
                            getActivity().getApplicationContext(), messageList, R.layout.item_message) {
                        @Override
                        public void convert(ViewHolder helper, MessageEntity item, int pos) {
                            helper.setText(R.id.tv_message_name, item.getTitle());
                            helper.setText(R.id.tv_message_content, item.getSubTitle());
                            helper.setText(R.id.tv_message_time, item.getDate());
                            helper.setImageResource(R.id.iv_message, R.drawable.ic_message_logo);

                            if (messageList.get(pos).getSeeCount() > 0) {
                                helper.setViewVisibility(R.id.iv_message_new, View.GONE);
                            } else {
                                helper.setViewVisibility(R.id.iv_message_new, View.VISIBLE);
                            }
                        }
                    });
                }
                //Glide.with(this).load(Api.APP_DOMAIN+"urm/"+userEntity.getPhoto()).into(mAvatarView);
            }else {

//                if(messageLists.getMessage().equals("session过期")){
//                    Toast.makeText(getActivity(),"登录失效，请重新登录!",Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getActivity(),LoginActivity.class));
//                    getActivity().finish();
//                }else {
//                    Toast.makeText(getActivity(),messageLists.getMessage(),Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
