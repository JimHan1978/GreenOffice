package com.hyetec.moa.view.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.didichuxing.doraemonkit.util.DeviceUtils;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.view.activity.BonusListActivity;

public class ViewHolder
{
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position)
    {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position)
    {

//        if (convertView == null)
//        {
            return new ViewHolder(context, parent, layoutId, position);
//        }
//        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView()
    {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }
    public ViewHolder setViewVisibility(int viewId,int i)
    {
       View view = getView(viewId);
        view.setVisibility(i);
        return this;
    }
    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @return
     */
    public ViewHolder setClick(int viewId, View.OnClickListener itemsOnClick)
    {
        TextView view = getView(viewId);
        view.setOnClickListener(itemsOnClick);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }


    public ViewHolder setTextColor(int viewId, int drawableId)
    {
        TextView view = getView(viewId);
        view.setTextColor(drawableId);

        return this;
    }

    public ViewHolder setImagehttps(int viewId, String phont,Context context)
    {
        ImageView view = getView(viewId);
        Glide.with(context).load(Api.IMG_URL+phont).into(view);
        return this;
    }

    public ViewHolder setImageAttachments(int viewId, String phont,Context context)
    {
        ImageView view = getView(viewId);
        /*ViewGroup.LayoutParams vgl = view.getLayoutParams();
        final float scale = context.getResources().getDisplayMetrics().density;
        int height_px = (int)(height*scale + 0.5f);

        vgl.height = height_px;

        view.setLayoutParams(vgl);*/
        view.setVisibility(View.VISIBLE);
        Glide.with(context).load(Api.IMG_URL_ATTACHMENT+phont).into(view);

        return this;
    }

    public ViewHolder setImagehttp(int viewId, String phont,Context context)
    {
        ImageView view = getView(viewId);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_avatar_default);
        Glide.with(context).load(Api.IMG_URL+phont).apply(requestOptions).into(view);
        return this;
    }

    public ViewHolder setImagehttpMessage(int viewId, String phont,Context context)
    {
        ImageView view = getView(viewId);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_walker_icon);
        Glide.with(context).load(Api.IMG_URL+phont).apply(requestOptions).into(view);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }


    public int getPosition()
    {
        return mPosition;
    }

    public void setCompoundDrawables(int viewId, Drawable drawable) {
        CheckedTextView view = getView(viewId);
        view.setCompoundDrawables(drawable,null,null,null);
    }

    public void changeView(int viewId){
        ImageView imageView = getView(viewId);
        ViewGroup.LayoutParams vgl = imageView.getLayoutParams();
        vgl.height = 2000;
        imageView.setLayoutParams(vgl);
    }

}
