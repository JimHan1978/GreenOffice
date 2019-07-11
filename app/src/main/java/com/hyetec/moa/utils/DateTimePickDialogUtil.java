package com.hyetec.moa.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;


import com.hyetec.moa.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

		public class DateTimePickDialogUtil implements OnDateChangedListener, OnTimeChangedListener {
			private DatePicker datePicker;
			private TimePicker timePicker;
			private AlertDialog ad;
			private String dateTime;
			private String initDateTime;
			private Activity activity;

			private DateTimeOnDateChangedListener dtListener;

			public DateTimePickDialogUtil(Activity activity, String initDateTime) {
				this.activity = activity;
				this.initDateTime = initDateTime;
			}

			public void setDateTimeOnDateChangedListener(DateTimeOnDateChangedListener dtListener) {
				this.dtListener = dtListener;
			}

			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				// 获得日历实例
				Calendar calendar = Calendar.getInstance();
				if(timePicker!=null){
					calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					dateTime = sdf.format(calendar.getTime());
					ad.setTitle(dateTime);
				}else {
					calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dateTime = sdf.format(calendar.getTime());
					ad.setTitle(dateTime);
				}
			}

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				onDateChanged(null, 0, 0, 0);
			}
			public void init(DatePicker datePicker) {
				Calendar calendar = Calendar.getInstance();
				if (!(null == initDateTime || "".equals(initDateTime))) {
					calendar = this.getCalendarByInintData(initDateTime);
				} else {
					initDateTime = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
				}

				datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);

			}
			public void init(DatePicker datePicker, TimePicker timePicker) {
				Calendar calendar = Calendar.getInstance();
				if (!(null == initDateTime || "".equals(initDateTime))) {
					calendar = this.getCalendarByInintData(initDateTime);
				} else {
					initDateTime = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY)
							+ ":" + calendar.get(Calendar.MINUTE);
				}

				datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
				timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
				timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
			}
			public AlertDialog dateTimePicKDialog(final TextView inputDate) {
				LinearLayout dateTimeLayout = (LinearLayout) activity
						.getLayoutInflater().inflate(R.layout.common_datetime, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
		//timePicker.setIs24HourView(true);
		timePicker.setIs24HourView(true);
		init(datePicker, timePicker);

		timePicker.setOnTimeChangedListener(this);


		ad = new AlertDialog.Builder(activity)
				.setTitle(initDateTime)
				.setView(dateTimeLayout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						inputDate.setText(dateTime);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {


					public void onClick(DialogInterface dialog, int whichButton) {
						//inputDate.setText("请选择");
					}
				}).show();

		onDateChanged(null, 0, 0, 0);
		return ad;
	}
	public AlertDialog dateTimePickDialog2(final TextView inputDate, final boolean b) {
		LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.common_datetime, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		if (b) {
			datePicker.setMaxDate(TimeUtil.getDateTimeString2Long(TimeUtil.clearTime(TimeUtil.getSQLDateTimeString(new Date(), "yyyy-MM-dd")), "yyyy-MM-dd"));
		} /*else {
			datePicker.setMinDate(TimeUtil.getDateTimeString2Long(TimeUtil.clearTime(TimeUtil.getSQLDateTimeString(new Date(), "yyyy-MM-dd")), "yyyy-MM-dd"));
			}*/
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
		timePicker.setIs24HourView(true);
		init(datePicker, timePicker);
		timePicker.setOnTimeChangedListener(this);

		ad = new AlertDialog.Builder(activity).setTitle(initDateTime).setView(dateTimeLayout).setPositiveButton("设置", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String nowTime = TimeUtil.clearSecond(TimeUtil.getSQLDateTimeString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				try {
					Field field;
					field = ad.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					field.set(ad, true);
					Date dt1 = df.parse(dateTime);
					Date dt2 = df.parse(nowTime);
					if (b) {
						if (dt1.getTime() <= dt2.getTime()) {
							inputDate.setText(dateTime);
							if(DateTimePickDialogUtil.this.dtListener!=null){
								DateTimePickDialogUtil.this.dtListener.onDateTimeChanged(dateTime);
							}
							field.set(ad, true);
						} else {
							Toast.makeText(activity, "不能大于当前时间点", Toast.LENGTH_SHORT).show();
							field.set(ad, false);
						}
					} else {
						inputDate.setText(dateTime);
						if(DateTimePickDialogUtil.this.dtListener!=null){
							DateTimePickDialogUtil.this.dtListener.onDateTimeChanged(dateTime);
						}
					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Field field;
				try {
					field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					field.set(dialog, true);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).show();

		onDateChanged(null, 0, 0, 0);
		return ad;
	}

	public AlertDialog dateTimePickDialog3(final TextView inputDate, final boolean b) {
		LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.common_datetime, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
//		if (b) {
//			datePicker.setMaxDate(TimeUtil.getDateTimeString2Long(TimeUtil.clearTime(TimeUtil.getSQLDateTimeString(new Date(), "yyyy-MM-dd")), "yyyy-MM-dd"));
//		}
		/*else {
			datePicker.setMinDate(TimeUtil.getDateTimeString2Long(TimeUtil.clearTime(TimeUtil.getSQLDateTimeString(new Date(), "yyyy-MM-dd")), "yyyy-MM-dd"));
			}*/
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
		timePicker.setIs24HourView(true);
		init(datePicker, timePicker);
		timePicker.setOnTimeChangedListener(this);

		ad = new AlertDialog.Builder(activity).setTitle(initDateTime).setView(dateTimeLayout).setPositiveButton("设置", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String nowTime = TimeUtil.clearSecond(TimeUtil.getSQLDateTimeString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				try {
					Field field;
					field = ad.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					field.set(ad, true);
					Date dt1 = df.parse(dateTime);
					Date dt2 = df.parse(nowTime);
					if (b) {
						if (dt1.getTime() >= dt2.getTime()) {
							inputDate.setText(dateTime);
							if(DateTimePickDialogUtil.this.dtListener!=null){
								DateTimePickDialogUtil.this.dtListener.onDateTimeChanged(dateTime);
							}
							field.set(ad, true);
						} else {
							Toast.makeText(activity, "不能小于当前时间点", Toast.LENGTH_SHORT).show();
							field.set(ad, false);
						}
					} else {
						inputDate.setText(dateTime);
						if(DateTimePickDialogUtil.this.dtListener!=null){
							DateTimePickDialogUtil.this.dtListener.onDateTimeChanged(dateTime);
						}
					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Field field;
				try {
					field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					field.set(dialog, true);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).show();

		onDateChanged(null, 0, 0, 0);
		return ad;
	}
	/**
	 * 实现将初始日期时间2012-07-02- 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
	 * 
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	private Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();

		// 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
		String date = spliteString(initDateTime, " ", "index", "front"); // 日期
		String time = spliteString(initDateTime, " ", "index", "back"); // 时间

		String yearStr = spliteString(date, "-", "index", "front"); // 年份
		String monthAndDay = spliteString(date, "-", "index", "back"); // 月日

		String monthStr = spliteString(monthAndDay, "-", "index", "front"); // 月
		String dayStr = spliteString(monthAndDay, "-", "index", "back"); // 日

		String hourStr = spliteString(time, ":", "index", "front"); // 时
		String minuteStr = spliteString(time, ":", "index", "back"); // 分

		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
		int currentDay = Integer.valueOf(dayStr.trim()).intValue();
		int currentHour = Integer.valueOf(hourStr.trim()).intValue();
		int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

		calendar.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);
		return calendar;
	}

	/**
	 * 实现将初始日期时间2012年07月02日拆分成年 月 日,并赋值给calendar
	 * 
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	public Calendar getCalendarByInintData2(String initDateTime) {
		Calendar calendar = Calendar.getInstance();

		// 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
		String date = spliteString(initDateTime, " ", "index", "front"); // 日期
		String time = spliteString(initDateTime, " ", "index", "back"); // 时间

		String yearStr = spliteString(date, "年", "index", "front"); // 年份
		String monthAndDay = spliteString(date, "年", "index", "back"); // 月日

		String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
		String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 34日
		String dayS = spliteString(dayStr, "日", "index", "front"); // 日

		String hourStr = spliteString(time, ":", "index", "front"); // 时
		String minuteStr = spliteString(time, ":", "index", "back"); // 分

		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
		int currentDay = Integer.valueOf(dayS.trim()).intValue();
		int currentHour = Integer.valueOf(hourStr.trim()).intValue();
		int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

		calendar.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);
		return calendar;
	}

	/**
	 * 截取子串
	 * 
	 * @param srcStr
	 *            源串
	 * @param pattern
	 *            匹配模式
	 * @param indexOrLast
	 * @param frontOrBack
	 * @return
	 */
	public static String spliteString(String srcStr, String pattern, String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if (indexOrLast.equalsIgnoreCase("index")) {
			loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
		} else {
			loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
		}
		if (frontOrBack.equalsIgnoreCase("front")) {
			if (loc != -1)
				result = srcStr.substring(0, loc); // 截取子串
		} else {
			if (loc != -1)
				result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
		}
		return result;
	}

	public interface DateTimeOnDateChangedListener{
		void onDateTimeChanged(String dateTime);
	}
}
