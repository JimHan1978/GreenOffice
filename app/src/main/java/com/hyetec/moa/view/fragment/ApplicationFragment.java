package com.hyetec.moa.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseFragment;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.view.activity.BonusListActivity;
import com.hyetec.moa.view.activity.CompanyListActivity;
import com.hyetec.moa.view.activity.LeaveListActivity;
import com.hyetec.moa.view.activity.PunchCardActivity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyGridView;
import com.hyetec.moa.viewmodel.ApplicationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ApplicationFragment extends BaseFragment<ApplicationViewModel> {

    @BindView(R.id.tv_title)
    TextView mTitleView;
    @BindView(R.id.iv_left)
    ImageView ivLeft;


    @BindView(R.id.gv_office)
    MyGridView gvOffice;
    @BindView(R.id.gv_attendance)
    MyGridView gvAttendance;


    Unbinder unbinder;
    private CommonAdapter oaAdapter;
    private CommonAdapter kqAdapter;
    private LoginUserEntity userInfo;
    public static ApplicationFragment newInstance() {
        ApplicationFragment applicationFragment = new ApplicationFragment();
        return applicationFragment;
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
        View view = inflater.inflate(R.layout.fragment_application, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleView.setText("应用");
        if( ACache.get(getActivity()).getAsObject(MoaApp.USER_DATA)!=null) {
            userInfo = (LoginUserEntity) ACache.get(getActivity().getApplicationContext()).getAsObject(MoaApp.USER_DATA);

            if(userInfo.getKqMenus()!=null ) {
                gvOffice.setAdapter(oaAdapter = new CommonAdapter<LoginUserEntity.OaMenusBean>(
                        getActivity().getApplicationContext(), userInfo.getOaMenus(), R.layout.item_menu) {
                    @Override
                    public void convert(ViewHolder helper, LoginUserEntity.OaMenusBean item, int pos) {

                        helper.setText(R.id.tv_name, item.getMenuName());
                        helper.setImagehttpMessage(R.id.iv_application, item.getMenuIcon()+"?v="+TimeUtil.getTime(), getActivity());


                    }
                });


                gvOffice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (userInfo.getOaMenus().get(i).getMenuCode()) {
                            case "event":
                                startActivity(new Intent(getActivity(), CompanyListActivity.class));
                                break;
                            case "lottery":
                                startActivity(new Intent(getActivity(), BonusListActivity.class).putExtra("type","All"));
                                break;
                            case "bill":
                                Toast.makeText(getActivity(), "功能未上线,敬请期待!", Toast.LENGTH_SHORT).show();
                                break;
                            case "notice":
                                Toast.makeText(getActivity(), "功能未上线,敬请期待!", Toast.LENGTH_SHORT).show();
                                break;
                            case "leave":
                                Toast.makeText(getActivity(), "功能未上线,敬请期待!", Toast.LENGTH_SHORT).show();
                               // startActivity(new Intent(getActivity(), LeaveListActivity.class));
                                break;
                            default:
                                Toast.makeText(getActivity(), "功能未上线,敬请期待!", Toast.LENGTH_SHORT).show();
                                break;


                        }
                    }
                });

                gvAttendance.setAdapter(kqAdapter = new CommonAdapter<LoginUserEntity.KqMenusBean>(
                        getActivity().getApplicationContext(), userInfo.getKqMenus(), R.layout.item_menu) {
                    @Override
                    public void convert(ViewHolder helper, LoginUserEntity.KqMenusBean item, int pos) {
                        helper.setText(R.id.tv_name, item.getMenuName());
                        helper.setImagehttp(R.id.iv_application, item.getMenuIcon(), getActivity());

                    }
                });

                gvAttendance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (userInfo.getKqMenus().get(i).getMenuCode()) {
                            case "attendence":
                                startActivity(new Intent(getActivity(), PunchCardActivity.class));
                                break;


                        }
                    }
                });
            }
        }

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
