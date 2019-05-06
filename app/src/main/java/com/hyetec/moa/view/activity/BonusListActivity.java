package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BonusEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.viewmodel.PunchCardViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class BonusListActivity extends BaseActivity<PunchCardViewModel> {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_item)
    MyListView lvItem;

    private CommonAdapter mAdapter;
    private List<BonusEntity> mBonusList;
    /**
     * UI 初始化
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bonus_list);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PunchCardViewModel.class);
        return R.layout.activity_bonus_list;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("月度红包排行榜");
        ivLeft.setVisibility(View.VISIBLE);
        mViewModel.getBonusList().observe(this, bonuslists -> {
            if(bonuslists!=null && bonuslists.isSuccess()){
                if( bonuslists.getResult()!=null) {
                    mBonusList = bonuslists.getResult();
                    lvItem.setAdapter(mAdapter = new CommonAdapter<BonusEntity>(
                            this, mBonusList, R.layout.item_bonus) {
                        @Override
                        public void convert(ViewHolder helper, BonusEntity item, int pos) {
                            helper.setText(R.id.tv_ranking, pos+1+"");
                            helper.setText(R.id.tv_user_name, item.getUserName());
                            helper.setText(R.id.tv_amount_money, item.getSumAmount()+"元");
                            helper.setImagehttp(R.id.iv_head, item.getPhoto(),BonusListActivity.this);

                            if(pos==0){
                                helper.setImageResource(R.id.iv_crown,R.drawable.shape_ring_gold);
                                helper.setImageResource(R.id.iv_crown_type,R.drawable.ic_crown_gold);
                                helper.setViewVisibility(R.id.iv_crown,View.VISIBLE);
                                helper.setViewVisibility(R.id.iv_crown_type,View.VISIBLE);
                                helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.crown_gold));
                            }else if(pos==1){
                                helper.setImageResource(R.id.iv_crown,R.drawable.shape_ring_silver);
                                helper.setImageResource(R.id.iv_crown_type,R.drawable.ic_crown_silver);
                                helper.setViewVisibility(R.id.iv_crown,View.VISIBLE);
                                helper.setViewVisibility(R.id.iv_crown_type,View.VISIBLE);
                                helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.crown_silver));
                            }else if(pos==2){
                                helper.setImageResource(R.id.iv_crown,R.drawable.shape_ring_copper);
                                helper.setImageResource(R.id.iv_crown_type,R.drawable.ic_crown_copper);
                                helper.setViewVisibility(R.id.iv_crown,View.VISIBLE);
                                helper.setViewVisibility(R.id.iv_crown_type,View.VISIBLE);
                                helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.crown_copper));
                            }else {
                                helper.setViewVisibility(R.id.iv_crown,View.GONE);
                                helper.setViewVisibility(R.id.iv_crown_type,View.GONE);
                                helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.text_gray));
                            }
                        }
                    });
                }

            }else {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
