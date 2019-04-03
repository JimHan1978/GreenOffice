package com.hyetec.moa.view.ui.pullview;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.View;



/**
 * 名称：GbDialogUtil.java
 * 描述：Dialog工具类.
 */

public class GdDialogUtil {
	
	/** dialog tag*/
	private static String mDialogTag = "dialog";
	/**
	 * 全屏显示一个Fragment
	 * @param view
	 * @return
	 */
	public static GdSampleDialogFragment showFragment(View view) {

		removeDialog(view);

		FragmentActivity activity = (FragmentActivity)view.getContext();
		// Create and show the dialog.
		GdSampleDialogFragment newFragment = GdSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light);
		newFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

		// 作为全屏显示,使用“content”作为fragment容器的基本视图,这始终是Activity的基本视图
		ft.add(android.R.id.content, newFragment, mDialogTag).addToBackStack(null).commit();

		return newFragment;
	}

	/**
	 * 显示一个自定义的对话框(有背景层)
	 * @param view
	 */
	public static GdSampleDialogFragment showDialog(View view) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
		removeDialog(activity);

		// Create and show the dialog.
		GdSampleDialogFragment newFragment = GdSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light_Dialog);
		newFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		newFragment.show(ft, mDialogTag);
		return newFragment;
	}

	/**
	 * 显示一个自定义的对话框(有背景层)
	 * @param view
	 * @param animEnter
	 * @param animExit
	 * @param animPopEnter
	 * @param animPopExit
	 * @return
	 */
	public static GdSampleDialogFragment showDialog(View view, int animEnter, int animExit, int animPopEnter, int animPopExit) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
		removeDialog(activity);

		// Create and show the dialog.
		GdSampleDialogFragment newFragment = GdSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light_Dialog);
		newFragment.setContentView(view);

		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		ft.setCustomAnimations(animEnter,animExit,animPopEnter,animPopExit);
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		newFragment.show(ft, mDialogTag);
		return newFragment;
	}

	/**
	 * 显示一个自定义的对话框(有背景层)
	 * @param view
	 * @param onCancelListener
	 * @return
	 */
	public static GdSampleDialogFragment showDialog(View view, DialogInterface.OnCancelListener onCancelListener) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
		removeDialog(activity);

		// Create and show the dialog.
		GdSampleDialogFragment newFragment = GdSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light_Dialog);
		newFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		newFragment.setOnCancelListener(onCancelListener);
		newFragment.show(ft, mDialogTag);
		return newFragment;
	}

	/**
	 * 显示一个自定义的对话框(有背景层)
	 * @param view
	 * @param animEnter
	 * @param animExit
	 * @param animPopEnter
	 * @param animPopExit
	 * @param onCancelListener
	 * @return
	 */
	public static GdSampleDialogFragment showDialog(View view, int animEnter, int animExit, int animPopEnter, int animPopExit, DialogInterface.OnCancelListener onCancelListener) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
		removeDialog(activity);

		// Create and show the dialog.
		GdSampleDialogFragment newFragment = GdSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light_Dialog);
		newFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		ft.setCustomAnimations(animEnter,animExit,animPopEnter,animPopExit);
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		newFragment.setOnCancelListener(onCancelListener);
		newFragment.show(ft, mDialogTag);
		return newFragment;
	}

	/**
	 * 显示一个自定义的对话框(无背景层)
	 * @param view
	 */
	public static GdSampleDialogFragment showPanel(View view) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
		removeDialog(activity);

		// Create and show the dialog.
		GdSampleDialogFragment newFragment = GdSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Light_Panel);
		newFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		newFragment.show(ft, mDialogTag);
		return newFragment;
	}

	/**
	 * 显示一个自定义的对话框(无背景层)
	 * @param view
	 * @param onCancelListener
	 * @return
	 */
	public static GdSampleDialogFragment showPanel(View view, DialogInterface.OnCancelListener onCancelListener) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
		removeDialog(activity);

		// Create and show the dialog.
		GdSampleDialogFragment newFragment = GdSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Light_Panel);
		newFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		newFragment.setOnCancelListener(onCancelListener);
		newFragment.show(ft, mDialogTag);
		return newFragment;
	}

	/**
	 * 描述：显示加载框.
	 * @param context the context
	 * @param indeterminateDrawable
	 * @param message the message
	 */
	public static GdLoadDialogFragment showLoadDialog(Context context, int indeterminateDrawable, String message) {
		Activity activity = (Activity)context;
		removeDialog(activity);
		GdLoadDialogFragment newFragment = GdLoadDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light_Dialog);
		newFragment.setIndeterminateDrawable(indeterminateDrawable);
		newFragment.setMessage(message);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		// 指定一个过渡动画  
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		newFragment.show(ft, mDialogTag);
		return newFragment;
	}
	/**
	 * 描述：移除Fragment.
	 * @param context the context
	 */
	public static void removeDialog(Context context){
		try {
			FragmentActivity activity = (FragmentActivity)context; 
			FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
	        // 指定一个过渡动画  
	        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			Fragment prev = activity.getFragmentManager().findFragmentByTag(mDialogTag);
			if (prev != null) {
			    ft.remove(prev);
			}
			ft.addToBackStack(null);
		    ft.commit();
		} catch (Exception e) {
			//可能有Activity已经被销毁的异常
			e.printStackTrace();
		}
	}
	
	/**
	 * 描述：移除Fragment和View
	 * @param view
	 */
	public static void removeDialog(View view){
		removeDialog(view.getContext());
		GdViewUtil.removeSelfFromParent(view);
	}
	

}
