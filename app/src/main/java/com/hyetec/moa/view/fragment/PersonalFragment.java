package com.hyetec.moa.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyetec.hmdp.core.base.BaseFragment;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.view.activity.SettingActivity;
import com.hyetec.moa.viewmodel.PersonalViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonalFragment extends BaseFragment<PersonalViewModel> {
    @BindView(R.id.tv_title)
    TextView mTitleView;
    @BindView(R.id.iv_avatar)
    RoundedImageView mAvatarView;
    @BindView(R.id.tv_userName)
    TextView mUserNameView;
    @BindView(R.id.tv_dept)
    TextView mDeptView;
    @BindView(R.id.tv_empCode)
    TextView mEmpCodeView;


    @BindView(R.id.layout_setting)
    View mSettingView;


    Unbinder unbinder;

    public static PersonalFragment newInstance() {
        PersonalFragment personalFragment = new PersonalFragment();
        return personalFragment;
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
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        mTitleView.setText(getResources().getString(R.string.personal));
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PersonalViewModel.class);


        /*ConstraintLayout setting = view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalFragment.this.getContext(), "xxxx", Toast.LENGTH_LONG).show();
            }
        });*/
        return view;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        if( ACache.get(getActivity().getApplicationContext()).getAsObject(MoaApp.USER_DATA)!=null) {
            UserEntity user = (UserEntity) ACache.get(getActivity().getApplicationContext()).getAsObject(MoaApp.USER_DATA);
            mViewModel.getUserInfo(user.getUserId() + "").observe(PersonalFragment.this, userEntity -> {
                if (userEntity != null) {
                    mUserNameView.setText(userEntity.getUserName());
                    mDeptView.setText(userEntity.getOrgName());

                    Glide.with(this).load(Api.IMG_URL+ userEntity.getPhoto()).into(mAvatarView);
                }
            });
        }
    }

    /**
     * Activity 与 Fragment 通信接口
     * 此方法是让外部调用使 Fragment 做一些操作的,比如说外部的 Fragment 想让 Fragment 对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传 {@link Message},通过what字段,来区分不同的方法,
     * 在此方法中就可以 switch 做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 新姿势：可以通过 Activity 的 ViewModel 共享数据给包含的 Fragment，配合 LiveData 好用到爆。
     *
     * @param data Object
     * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing_data_between_fragments">Sharing Data Between Fragments</a>
     */
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

    @OnClick(R.id.layout_setting)
    public void onSettingViewClicked() {
        startActivity(new Intent(PersonalFragment.this.getActivity(), SettingActivity.class));
    }
}
