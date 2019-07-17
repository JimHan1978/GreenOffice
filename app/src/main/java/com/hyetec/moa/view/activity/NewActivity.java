package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.BonusEntity;
import com.hyetec.moa.model.entity.DictionaryEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.utils.DateTimePickDialogUtil;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.utils.spinner.MaterialSpinner;
import com.hyetec.moa.utils.spinner.MaterialSpinnerAdapter;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.viewmodel.CompanyViewModel;
import com.hyetec.moa.viewmodel.SettingViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewActivity extends BaseActivity<CompanyViewModel> implements View.OnClickListener {

    @BindView (R.id.spinner_type)
    MaterialSpinner spinner_type;
    @BindView (R.id.spinner_place)
    MaterialSpinner spinner_place;
    @BindView (R.id.activity_assembling_place)
    EditText assemblingPlace;
    @BindView (R.id.activity_start_time)
    Button startTime;
    @BindView(R.id.activity_create)
    Button createActivity;
    @BindView (R.id.activity_bonus)
    EditText bonus;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;

    List<String> mActivityType;
    List<String> mActivityPlace;

    private DateTimePickDialogUtil dateTimePicKDialog;
    private List<DictionaryEntity> mDictionaryList;
//    private CommonAdapter mAdapter;

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        ivLeft.setVisibility(View.VISIBLE);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CompanyViewModel.class);

        return R.layout.activity_new;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("创建活动");
        String time = TimeUtil.getNowTimeString();
        startTime.setText(time);
        dateTimePicKDialog = new DateTimePickDialogUtil(this, TimeUtil.getNowTimeString());
        startTime.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        createActivity.setOnClickListener(this);

        getTypeListData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getTypeListData() {
        mViewModel.setDictionary().observe(this, activityTypeList -> {
            if (activityTypeList != null && activityTypeList.isSuccess()) {
                if( activityTypeList.getResult()!=null) {
                    mDictionaryList =  activityTypeList.getResult();
                    mActivityType = new ArrayList<>();
                    mActivityPlace = new ArrayList<>();
                    mActivityPlace.add("-请选择-");
                    mActivityType.add("-请选择-");
                    Iterator it1 = mDictionaryList.iterator();
                    Iterator it = mDictionaryList.iterator();
                    while(it.hasNext()){
                        DictionaryEntity de = (DictionaryEntity) it.next();
                        if(de.getType().equals( "activity_type")){
                            mActivityType.add(de.getLabel());
                        }
                    }

                    while(it1.hasNext()){
                        DictionaryEntity de = (DictionaryEntity) it1.next();
                        if(de.getType().equals( "activity_address")){
                            mActivityPlace.add(de.getLabel());
                        }
                    }

                    //设置下拉列表
                    spinner_place.setItems(mActivityPlace);
                    spinner_place.setSelectedIndex(0);
                    spinner_place.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                        }
                    });

                    spinner_place.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                        @Override
                        public void onNothingSelected(MaterialSpinner spinner) {
                            spinner.getSelectedIndex();
                        }
                    });

                    //设置下拉列表
                    spinner_type.setItems(mActivityType);
                    spinner_type.setSelectedIndex(0);
                    spinner_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                        }
                    });

                    spinner_type.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                        @Override
                        public void onNothingSelected(MaterialSpinner spinner) {
                            spinner.getSelectedIndex();
                        }
                    });

                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_start_time:
                dateTimePicKDialog.dateTimePicKDialog(startTime);
                break;

            case R.id.iv_left:
                finish();
                break;

            case R.id.activity_create:

                int ac_place = 0;
                int ac_type = 0;
                Iterator it = mDictionaryList.iterator();
                while(it.hasNext()){
                    DictionaryEntity de = (DictionaryEntity) it.next();
                    if(de.getLabel().equals(spinner_type.getItems().get(spinner_type.getSelectedIndex()))){
                        ac_type = de.getId();
                    }
                }

                Iterator it1 = mDictionaryList.iterator();
                while(it1.hasNext()){
                    DictionaryEntity de = (DictionaryEntity) it1.next();
                    if(de.getLabel().equals(spinner_place.getItems().get(spinner_place.getSelectedIndex()))){
                        ac_place = de.getId();
                    }
                }


                String as_place = assemblingPlace.getText().toString();
                int ac_bonus =  Integer.valueOf(bonus.getText().toString());
                String st_time = startTime.getText().toString();

                LoginUserEntity userInfo = (LoginUserEntity) ACache.get(this.getApplicationContext()).getAsObject(MoaApp.USER_DATA);

                ActivityEventEntity newActivity = new ActivityEventEntity();
                newActivity.setType(ac_type);
                newActivity.setTarget(ac_place);
                newActivity.setOrganiser(userInfo.getUserId());
                newActivity.setJoinDate(st_time);
                newActivity.setVenue(as_place);
                newActivity.setAmount(ac_bonus);

                mViewModel.SaveAndUpdateActivity(newActivity).observe(this, activityUpdateList -> {

                });

                Intent intent = new Intent(NewActivity.this,CompanyListActivity.class);
//                intent.putExtra("ac_type",newActivity.getType());
//                intent.putExtra("ac_place",newActivity.getTarget());
//                intent.putExtra("st_time",newActivity.getJoinDate());
//                intent.putExtra("as_place",newActivity.getVenue());
//                intent.putExtra("ac_type",newActivity.getType());

                startActivity(intent);

                break;


            default:
                break;
        }

    }
}
