package com.hyetec.moa.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyetec.hmdp.core.base.BaseFragment;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.view.activity.CompanyActivity;
import com.hyetec.moa.view.activity.CompanyListActivity;
import com.hyetec.moa.view.activity.LoginActivity;
import com.hyetec.moa.view.activity.PunchCardActivity;
import com.hyetec.moa.view.activity.WebViewActivity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.pullview.GdPullToRefreshView;
import com.hyetec.moa.viewmodel.MessageViewModel;

import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment<MessageViewModel> implements GdPullToRefreshView.OnHeaderRefreshListener {


    @BindView(R.id.lv_item)
    MyListView lvItem;
    @BindView(R.id.tv_title)
    TextView mTitleView;
    Unbinder unbinder;
    @BindView(R.id.gv_message)
    GdPullToRefreshView gvMessage;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    private LoginUserEntity userInfo;
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
        mTitleView.setText("格林办公");
        gvMessage.setLoadMoreEnable(false);
        gvMessage.setOnHeaderRefreshListener(this);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_message);
        Glide.with(getActivity()).load(Api.IMG_URL + "/upload/banner.jpg"+"?v="+TimeUtil.getTime()).apply(requestOptions).into(ivBanner);
        gvMessage.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
//      gvMessage.getHeaderView().setBackgroundColor(getActivity().getResources().getColor(R.color.rosybrown));


        if (ACache.get(getActivity()).getAsObject(MoaApp.USER_DATA) != null) {
            userInfo = (LoginUserEntity) ACache.get(getActivity().getApplicationContext()).getAsObject(MoaApp.USER_DATA);
        }
  //      getData();
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (messageList.get(i).getMessageType().equals("report")) {
                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("date", messageList.get(i).getYearAndMonth()));
                } else if (messageList.get(i).getMessageType().equals("attendance")) {
                    startActivity(new Intent(getActivity(), PunchCardActivity.class));
                }else {
                    MessageEntity messageEntity = messageList.get(i);
                    startActivity(new Intent(getActivity(), CompanyActivity.class).putExtra("date", messageList.get(i)));
                }
            }
        });

    }

    @Override
    public void setData(Object data) {

    }

    public void getData() {
        mViewModel.getMessageList(userInfo.getUserId()).observe(this, messageLists -> {
            gvMessage.onHeaderRefreshFinish();
            if (messageLists != null && messageLists.isSuccess()) {
                if (messageLists.getResult() != null) {
                    messageList = messageLists.getResult();

                    lvItem.setAdapter(mAdapter = new CommonAdapter<MessageEntity>(
                            getActivity().getApplicationContext(), messageList, R.layout.item_message) {
                        @Override
                        public void convert(ViewHolder helper, MessageEntity item, int pos) {
                            helper.setText(R.id.tv_message_name, item.getTitle());
                            helper.setText(R.id.tv_message_content, item.getSubTitle());

                            if (item.getMessageType().equals("report")) {
                                helper.setImageResource(R.id.iv_message, R.drawable.ic_message_logo);
                                helper.setText(R.id.tv_message_time, item.getDate());
                                if (item.getSeeCount() > 0) {
                                    helper.setViewVisibility(R.id.iv_message_new, View.GONE);
                                } else {
                                    helper.setViewVisibility(R.id.iv_message_new, View.VISIBLE);
                                }
                            } else if (item.getMessageType().equals("activity")) {
                                helper.setImagehttpMessage(R.id.iv_message, item.getIconUrl(), getActivity());
                                helper.setViewVisibility(R.id.iv_message_new, View.GONE);
                            } else if (item.getMessageType().equals("attendance")) {
                                helper.setImageResource(R.id.iv_message, R.drawable.ic_attendance);
                                helper.setViewVisibility(R.id.iv_message_new, View.GONE);
                            }
                        }
                    });
                }
            } else if (messageLists.getMessage() != null && !messageLists.isSuccess()) {
                if (messageLists.getMessage().equals("session过期")) {
                    JPushInterface.stopPush(getActivity());
                    Toast.makeText(getActivity(), "登录失效，请重新登录!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), messageLists.getMessage(), Toast.LENGTH_LONG).show();
                }
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

    @Override
    public void onHeaderRefresh(GdPullToRefreshView view) {
        getData();
    }
}
