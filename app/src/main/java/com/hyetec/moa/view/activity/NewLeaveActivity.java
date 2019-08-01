package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.DaysCalculationEntity;
import com.hyetec.moa.model.entity.LeaveTypeEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MyLeaveEntity;
import com.hyetec.moa.utils.DateTimePickDialogUtil;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.utils.spinner.MaterialSpinner;
import com.hyetec.moa.view.ui.pullview.GdPullToRefreshView;
import com.hyetec.moa.viewmodel.CompanyViewModel;
import com.hyetec.moa.viewmodel.LeaveViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewLeaveActivity extends BaseActivity<LeaveViewModel> implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.leaveName)
    TextView ap_name;
//    @BindView(R.id.offId)
//    TextView ap_id;
    @BindView(R.id.leave_days)
    TextView leave_days;
    @BindView(R.id.start_date)
    Button start_date;
    @BindView(R.id.end_date)
    Button end_date;
    @BindView(R.id.off_Type)
    MaterialSpinner leave_type;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.end_activity)
    TextView endActivity;
    @BindView(R.id.replace_name)
    EditText replaceName;
    @BindView(R.id.reason)
    EditText leaveReason;
    @BindView(R.id.commit_ui)
    LinearLayout commit_ui;
    @BindView(R.id.button_pass)
    Button button_pass;
    @BindView(R.id.button_rebut)
    Button button_rebut;
    @BindView(R.id.commit)
    EditText et_commit;

    List<LeaveTypeEntity> leaveTypeEntityList;
    List<String> mLeaveType;
    List<DaysCalculationEntity> mLeaveDays;
    private DateTimePickDialogUtil dateTimePicKDialog;
    LoginUserEntity userInfo;
    Intent intent;

    private boolean startDate = false;
    private boolean endDate = false;



    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.new_leave_application);
        ButterKnife.bind(this);
        ivLeft.setVisibility(View.VISIBLE);
        intent = getIntent();
        if(intent.getStringExtra("intent").equals("create")){
            endActivity.setVisibility(View.VISIBLE);
            endActivity.setText("提交  ");
        }
        userInfo = (LoginUserEntity) ACache.get(this.getApplicationContext()).getAsObject(MoaApp.USER_DATA);
        if(intent.getBooleanExtra("commit",false) == true){
            commit_ui.setVisibility(View.VISIBLE);
        }

        tv_title.setText("请假申请");
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LeaveViewModel.class);
        return R.layout.new_leave_application;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        String time = TimeUtil.getNowTimeString();
        dateTimePicKDialog = new DateTimePickDialogUtil(this, TimeUtil.getToadyString());
        getLeaveType();
        userInfo = (LoginUserEntity) ACache.get(this.getApplicationContext()).getAsObject(MoaApp.USER_DATA);
        ap_name.setText(userInfo.getUserName());
//        ap_id.setText(userInfo.getUserno());

        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        endActivity.setOnClickListener(this);
        button_pass.setOnClickListener(this);
        button_rebut.setOnClickListener(this);

        intent = getIntent();
        if(intent.getStringExtra("intent").equals("checkDetail")){
            getLeaveDetail();

        }
    }

    public void getLeaveDays(){
        mViewModel.getLeaveDaysList(start_date.getText().toString(),end_date.getText().toString()).observe(this, leaveDays -> {
            if (leaveDays != null && leaveDays.isSuccess()) {
                if (leaveDays.getResult() != null) {
                    mLeaveDays = leaveDays.getResult();
                    if(mLeaveDays!=null){
                        leave_days.setText(mLeaveDays.size()+"");
                    }else {
                        Toast.makeText(this,"请输入正确时间", Toast.LENGTH_SHORT).show();
                    }
                }

            }else {
                Toast.makeText(NewLeaveActivity.this,leaveDays.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void getLeaveDetail(){
        mViewModel.getMyLeaveDetail(intent.getStringExtra("id")).observe(this, leaveDetail -> {

            if(leaveDetail.getResult().getStatus() == 1){
                leaveReason.setEnabled(false);
                replaceName.setEnabled(false);
                start_date.setClickable(false);
                end_date.setClickable(false);
                leave_type.setClickable(false);
            }

            if (leaveDetail != null && leaveDetail.isSuccess()) {
                ap_name.setText(leaveDetail.getResult().getApplyUserName());
                replaceName.setText(leaveDetail.getResult().getWorkReplace());
                leaveReason.setText(leaveDetail.getResult().getApplyReason());
                start_date.setText(leaveDetail.getResult().getStartDate());
                end_date.setText(leaveDetail.getResult().getEndDate());
                leave_days.setText(leaveDetail.getResult().getValidHoliday()+"");
                leave_type.setText(leaveDetail.getResult().getTypeName());
            }
        });
    }


    public void getLeaveType() {
        mViewModel.getLeaveTypeList("QJLX").observe(this, leaveType -> {
            if (leaveType != null && leaveType.isSuccess()) {
                if (leaveType.getResult() != null) {
                    leaveTypeEntityList = leaveType.getResult();
                    mLeaveType = new ArrayList<>();
                    mLeaveType.add("-请选择-");
                    Iterator it = leaveTypeEntityList.iterator();
                    while (it.hasNext()) {
                        LeaveTypeEntity lt = (LeaveTypeEntity) it.next();
                        mLeaveType.add(lt.getLabel());
                    }

                    //设置下拉列表
                    leave_type.setItems(mLeaveType);
                    leave_type.setSelectedIndex(0);
                    leave_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                        }
                    });

                    leave_type.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

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
        switch (v.getId()) {
            case R.id.start_date:

                start_date.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 10){
                            startDate = true;
                        }
                        if(startDate == true && endDate == true){
                            getLeaveDays();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                dateTimePicKDialog.dateTimePickDialog4(start_date);

                break;
            case R.id.end_date:

                end_date.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 10){
                            endDate = true;
                        }
                        if(startDate == true && endDate == true){
                            getLeaveDays();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                dateTimePicKDialog.dateTimePickDialog4(end_date);

                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.end_activity:
                if(leave_type.getText().toString().equals("-请选择-") || replaceName.getText().toString() == "" ||
                        leaveReason.getText().toString() == ""){
                    Toast.makeText(NewLeaveActivity.this, "内容不能为空",Toast.LENGTH_SHORT).show();
                }
                if(leave_days.getText().toString().equals("0") || leave_days.getText().toString() == ""){
                    Toast.makeText(NewLeaveActivity.this, "请检查请假日期",Toast.LENGTH_SHORT).show();
                    leave_days.setText("");
                    start_date.setText("");
                    end_date.setText("");
                }
                else{
                    MyLeaveEntity myLeaveEntity = new MyLeaveEntity();
                    myLeaveEntity.setApplyUserName(userInfo.getUserName());
                    myLeaveEntity.setApplyUserId(userInfo.getUserId());
                    myLeaveEntity.setEndDate(end_date.getText().toString());
                    myLeaveEntity.setStartDate(start_date.getText().toString());
                    myLeaveEntity.setValidHoliday(Integer.valueOf(leave_days.getText().toString()));
                    Iterator it = leaveTypeEntityList.iterator();
                    LeaveTypeEntity lt;
                    while(it.hasNext()){
                        lt = (LeaveTypeEntity)it.next();
                        if(lt.getLabel() == leave_type.getText().toString()){
                            myLeaveEntity.setType(Integer.valueOf(lt.getCode()));
                        }
                    }
                    myLeaveEntity.setWorkReplace(replaceName.getText().toString());
                    myLeaveEntity.setApplyReason(leaveReason.getText().toString());
                    myLeaveEntity.setStatus(1);

                    mViewModel.getLeaveSaveData(myLeaveEntity).observe(this, leaveMessageList -> {

                    });
                    finish();
                }
                break;
            case R.id.button_pass:
                intent = getIntent();
                mViewModel.getCommitLeaveList(intent.getStringExtra("commitId"),"pass",et_commit.getText().toString(),"").observe(this, leaveMessageList ->{

            });
                finish();
                break;

            case R.id.button_rebut:
                intent = getIntent();
                mViewModel.getCommitLeaveList(intent.getStringExtra("commitId"),"rebut",et_commit.getText().toString(),"").observe(this, leaveMessageList ->{

                });
                finish();
                break;

            default:
                break;
        }
    }
}
