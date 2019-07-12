package com.hyetec.moa.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.utils.DateTimePickDialogUtil;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.viewmodel.CompanyViewModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateActivity extends BaseActivity<CompanyViewModel> {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.location_text)
    EditText locationEdit;
    @BindView(R.id.start_time_text)
    TextView startTimeEdit;
    @BindView(R.id.lottery_text)
    EditText lotteryEdit;
    @BindView(R.id.layout_time)
    RelativeLayout layoutTime;
    @BindView(R.id.type_spinner)
    Spinner typeSpinner;

    private DateTimePickDialogUtil dateTimePicKDialog;
    private Handler handler = null;
    private String[] activityType = {"1","2","3"};
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CompanyViewModel.class);
        return R.layout.activity_create;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        dateTimePicKDialog = new DateTimePickDialogUtil(this, TimeUtil.getNowTimeString());
        String time = TimeUtil.getNowTimeString();
        startTimeEdit.setText(time);
        ivLeft.setVisibility(View.VISIBLE);
        handler = new Handler();


        ArrayAdapter<String> adapter = new ArrayAdapter<String >(this, android.R.layout.simple_list_item_1,activityType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    @OnClick({R.id.iv_left, R.id.start_time_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.start_time_text:

                /*Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();*/

                /*AlertDialog.Builder localBuilder = new AlertDialog.Builder(NewEvent.this);
                localBuilder.setTitle("选择日期");*/


                        dateTimePicKDialog.dateTimePickDialog2(startTimeEdit, true);
                        System.out.println();


                //dateTimePicKDialog.dateTimePickDialog2(startTimeEdit, true);


        }
    }
    /*Handler mTimeHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                dateTimePicKDialog.dateTimePickDialog2(startTimeEdit, true);
                sendEmptyMessageDelayed(0, 1000);
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dateTimePicKDialog.dateTimePickDialog2(startTimeEdit, true);
        }
    };*/
}
