/*   
F * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.hyetec.moa.view.activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import java.util.Map;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipboardManager;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.db.DatabaseDAO;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.utils.AssetsDatabaseManager;
import com.hyetec.moa.viewmodel.DetailsViewModel;
import com.hyetec.moa.viewmodel.GroupViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends BaseActivity<DetailsViewModel> implements OnClickListener {
    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.iv_head)
    ImageView iv_head;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.phone_num)
    TextView phone_num;

    @BindView(R.id.position)
    TextView position;

    @BindView(R.id.e_mail)
    TextView e_mail;

    @BindView(R.id.QQ)
    TextView qq;

    @BindView(R.id.tv_head_name)
    TextView tv_head_name;

    @BindView(R.id.iv_tell)
    ImageView iv_tell;

    @BindView(R.id.iv_msg)
    ImageView iv_msg;

    @BindView(R.id.iv_cope)
    ImageView iv_cope;

    @BindView(R.id.tv_txl)
    TextView tv_txl;
    @BindView(R.id.tv_belong_place)
    TextView tv_belong_place;

    private UserEntity userEntity;
    private String userName;
    public DatabaseDAO dao;
    public SQLiteDatabase sqliteDB;

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DetailsViewModel.class);
        return R.layout.activity_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        initDB();
        userEntity = (UserEntity) getIntent().getExtras().get("info");
        userName = userEntity.getUserName();
        if (userEntity.getPhoto() == null || userEntity.getPhoto().equals("")) {
            if (userName.length() > 2) {
                String name = userName.substring(userName.length() - 2, userName.length());
                tv_head_name.setText(name);
            } else {
                tv_head_name.setText(userName);
            }
            iv_head.setImageResource(R.drawable.bg_portrait);
        } else {
            tv_head_name.setText("");
            Glide.with(this).load(Api.APP_DOMAIN+userEntity.getPhoto()).into(iv_head);
        }
        name.setText(userName);
        phone_num.setText(userEntity.getMobile());
        position.setText(userEntity.getOrgName() + " / " + userEntity.getPositionName());
        e_mail.setText(userEntity.getEmail());
        if (userEntity.getQq() == null || userEntity.getQq().equals("")) {
            qq.setText("暂无");
        } else {
            qq.setText(userEntity.getQq());
        }
        getAttribution();
        back.setOnClickListener(this);
        iv_cope.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
        iv_tell.setOnClickListener(this);
        tv_txl.setOnClickListener(this);


    }

    private void initDB() {
        AssetsDatabaseManager.initManager(this);
        AssetsDatabaseManager mg = AssetsDatabaseManager.getAssetsDatabaseManager();
        sqliteDB = mg.getDatabase("number_location.zip");
        dao = new DatabaseDAO(sqliteDB);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_tell:
                Intent intent = new Intent();
                // 系统默认的action，用来打开默认的电话界面
                // intent.setAction(Intent.ACTION_CALL);
                intent.setAction(Intent.ACTION_DIAL);
                // 需要拨打的号码
                intent.setData(Uri.parse("tel:" + phone_num.getText()));
                startActivity(intent);

                break;
            case R.id.iv_msg:
                Uri smsToUri = Uri.parse("smsto:" + phone_num.getText());
                Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                startActivity(mIntent);
                break;
            case R.id.iv_cope:
                ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cmb.setText(phone_num.getText().toString().trim());
                Toast.makeText(this, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_txl:
                addContact(userName, userEntity.getMobile(), userEntity.getEmail());
                // addContact();
                break;
            default:
                break;
        }
    }

    public void addContact() {
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        intent.setType("vnd.android.cursor.item/person");
        intent.setType("vnd.android.cursor.item/contact");
        intent.setType("vnd.android.cursor.item/raw_contact");
        intent.putExtra(ContactsContract.Intents.Insert.NAME, userName);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, userEntity.getEmail());
        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, "华源格林");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, userEntity.getMobile());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, 3);
        startActivity(intent);
    }

    public void addContact(String name, String phoneNumber, String EmailStr) {
        delContact(name, phoneNumber);
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();
        // values.put(RawContacts.AGGREGATION_MODE,RawContacts.AGGREGATION_MODE_DISABLED);
        // 向RawContacts.CONTENT_URI空值插入，
        // 先获取Android系统返回的rawContactId
        // 后面要基于此id插入值
        Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
        values.put(Organization.COMPANY, "华源格林");
        values.put(Organization.TYPE, Organization.TYPE_WORK);
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
        // 联系人的Email地址
        values.put(Email.DATA, EmailStr);
        // 电子邮件的类型
        values.put(Email.TYPE, Email.TYPE_WORK);
        // 向联系人Email URI添加Email数据
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        if (userEntity.getPhoto() != null && !TextUtils.isEmpty(userEntity.getPhoto())) {
            iv_head.setDrawingCacheEnabled(true);
            Bitmap sourceBitmap = iv_head.getDrawingCache();
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            // 将Bitmap压缩成PNG编码，质量为100%存储
            sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            byte[] avatar = os.toByteArray();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE);
            values.put(Photo.PHOTO, avatar);
            iv_head.setDrawingCacheEnabled(false);
            getContentResolver().insert(Data.CONTENT_URI, values);
        }

        Toast.makeText(this, "联系人数据添加成功", Toast.LENGTH_SHORT).show();
    }

    private void delContact(String name, String phoneNumber) {
        Cursor cursor = getContentResolver().query(Phone.CONTENT_URI,
                new String[]{Data.RAW_CONTACT_ID, Phone.NUMBER, Phone.DISPLAY_NAME},
                ContactsContract.Contacts.DISPLAY_NAME + "=?", new String[]{name}, null);
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        if (cursor.moveToFirst()) {
            do {
                long Id = cursor.getLong(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
                String displayName = cursor
                        .getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
                // 获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                if (displayName.equals(name) && number.equals(phoneNumber)) {
                    ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, Id))
                            .build());
                    try {
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void readContacts(String name, String phoneNumber) {
        Cursor cursor = null;
        cursor = getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        while (cursor.moveToNext()) {
            // 获取联系人姓名
            String displayName = cursor
                    .getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
            // 获取联系人手机号
            String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
            if (displayName.equals(name) && number.equals(phoneNumber)) {
                long Id = cursor.getLong(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
                ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, Id))
                        .build());
                try {
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        cursor.close();

    }

    public void getAttribution() {
        String phoneNumber = phone_num.getText().toString().substring(0, 7);
        String prefix, center, province = null, city = null;
        Map<String, String> map = null;

        if (isZeroStarted(phoneNumber) && getNumLength(phoneNumber) > 2) {
            prefix = getAreaCodePrefix(phoneNumber);
            map = dao.queryAeraCode(prefix);
            if (map != null) {
                province = map.get("province");
                city = map.get("city");
            }
            if (province == null || city == null || province.isEmpty() || city.isEmpty()) {
            } else if (province.equals(city))
                tv_belong_place.setText(province);
            else
                tv_belong_place.setText(province + "  " + city);

        } else if (!isZeroStarted(phoneNumber) && getNumLength(phoneNumber) > 6) {
            prefix = getMobilePrefix(phoneNumber);
            center = getCenterNumber(phoneNumber);
            map = dao.queryNumber(prefix, center);
            if (map != null) {
                province = map.get("province");
                city = map.get("city");
            }
            if (province == null || city == null || province.isEmpty() || city.isEmpty()) {
                tv_belong_place.setText("");
            } else if (province.equals(city))
                tv_belong_place.setText(province);
            else
                tv_belong_place.setText(province + "  " + city);

        }
        if (!isZeroStarted(phoneNumber) && getNumLength(phoneNumber) == 3) {

            if (phoneNumber.equals("852")) {
                tv_belong_place.setText("香港");
            } else if (phoneNumber.equals("853")) {
                tv_belong_place.setText("澳门");
            }

        }

    }

    /**
     * 得到输入区号中的前三位数字或前四位数字去掉首位为零后的数字。
     */
    public String getAreaCodePrefix(String number) {
        if (number.length() < 4) {
            if (number.charAt(1) == '1' || number.charAt(1) == '2') {
                return number.substring(1, 3);
            } else {
                return number.substring(1, 3);
            }
        } else {
            return number.substring(1, 4);
        }

    }

    /**
     * 得到输入手机号码的前三位数字。
     */
    public String getMobilePrefix(String number) {
        return number.substring(0, 3);
    }

    /**
     * 得到输入号码的中间四位号码，用来判断手机号码归属地。
     */
    public String getCenterNumber(String number) {
        return number.substring(3, 7);
    }

    /**
     * 判断号码是否以零开头
     */
    public boolean isZeroStarted(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }
        return number.charAt(0) == '0';
    }

    /**
     * 得到号码的长度
     */
    public int getNumLength(String number) {
        if (number == null || number.isEmpty())
            return 0;
        return number.length();
    }


}
