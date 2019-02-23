package com.hyetec.moa.view.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyetec.moa.R;
import com.hyetec.moa.viewmodel.PersonalViewModel;
import com.hyetec.hmdp.core.base.BaseFragment;

import butterknife.ButterKnife;

public class PersonalFragment extends BaseFragment<PersonalViewModel> {
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
        ButterKnife.bind(this,view);
        ConstraintLayout setting = view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalFragment.this.getContext(),"xxxx",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {

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
}
