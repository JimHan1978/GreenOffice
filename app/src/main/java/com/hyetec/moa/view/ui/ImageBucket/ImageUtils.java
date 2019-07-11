/*******************************************************************************
 * -----------------------------------------------------------------
 * Copyright (c) 2015 by Hyetec Corporation all right reserved.
 * -----------------------------------------------------------------
 * 
 * File: ImageUtils.java
 * Author: JimHan
 * Version: 1.0
 * Create(创建日期): 2015年11月26日
 *
 * Changes (from 2015年11月26日)
 * -----------------------------------------------------------------
 * 2015年11月26日 : 创建 ImageUtils.java (JimHan);
 * -----------------------------------------------------------------
 *******************************************************************************/
package com.hyetec.moa.view.ui.ImageBucket;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;


import com.hyetec.moa.utils.SharedPreferencesUtil;
import com.hyetec.moa.utils.TimeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ImageUtils.java
 * @Description: 获取图片工具辅助类
 * @author JimHan
 * @date 2015年11月26日 下午1:44:52
 */

public class ImageUtils {

	public static final String IMAGE_PATH_THUMBNAIL = "thumbnail";
	public static final String IMAGE_PATH = "images";
	private static boolean takePicture = false;
	public static ConcurrentHashMap<String, Collection<ImageItem>> mImageCache;
	// private static ConcurrentHashMap<String, Thread> mThreadMap;

	private static String tag = "ImageUtils";

	static {
		mImageCache = new ConcurrentHashMap<String, Collection<ImageItem>>();
		// mThreadMap = new ConcurrentHashMap();
	}

	/*
	 * private static Thread getThread(String userId, String catalog) { return
	 * (Thread) mThreadMap.get(getKey(userId, catalog)); }
	 * 
	 * private static void setThread(String userId, String catalog, Thread
	 * paramThread) { mThreadMap.put(getKey(userId, catalog), paramThread); }
	 */

	/*
	 * public static void setCurrentSyncThread(String userId, Thread
	 * paramThread) { setThread(userId, "CurrentSyncThread", paramThread); }
	 * 
	 * public static Thread getCurrentSyncThread(String userId) { return
	 * getThread(userId, "CurrentSyncThread"); }
	 * 
	 * private static String getKey(String userId, String catalog) { return
	 * userId + "/" + catalog; }
	 */

	/**
	 * 
	 * @Title: showTakeImageDialog
	 * @Description: 显示获取图片操作对话框
	 *
	 * @param context
	 * @param imagCatalog
	 */
	// public static void showTakeImageDialog(final Activity context, final ImageCatalog
	// imagCatalog) {
	// AlertDialog ad = new AlertDialog.Builder(context).setTitle("操作")
	// .setItems(new String[] { "拍照", "相册" }, new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// if (which == 0) {
	// List<ImageItem> photos = new ArrayList<ImageItem>();
	// String photoPath = Camera.takePhoto(context);
	// ImageItem item = createImageItemInstance(context, photoPath);
	// photos.add(item);
	// mImageCache.put(imagCatalog.name(), photos);
	// } else if (which == 1) {
	// Intent intent = new Intent(context, ImageBucketChooseActivity.class);
	// intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, 1);
	// intent.putExtra(IntentConstants.EXTRA_IMAGE_CATALOG, imagCatalog.name());
	// context.startActivity(intent);
	// }
	// }
	// }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	//
	// }).show();
	// ad.setCanceledOnTouchOutside(true);
	// }

	/**
	 * 
	 * @Title:
	 * @Description: 更新拍照状态标识
	 *
	 * @param isTake
	 */
	public static void updateTakePhotoState(boolean isTake) {
		takePicture = isTake;
	}

	public static boolean isTakePhoto() {
		return takePicture;
	}

	// public static void showTakeImageDialog(final Fragment context, final ImageCatalog
	// imagCatalog) {
	// AlertDialog ad = new AlertDialog.Builder(context.getActivity()).setTitle("操作")
	// .setItems(new String[] { "拍照", "相册" }, new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// if (which == 0) {
	// ImageUtils.updateTakePhotoState(true);
	// List<ImageItem> photos = new ArrayList<ImageItem>();
	// String photoPath = Camera.takePhoto(context);
	// ImageItem item = createImageItemInstance(context.getActivity(), photoPath);
	// photos.add(item);
	// mImageCache.put(imagCatalog.name(), photos);
	// } else if (which == 1) {
	// ImageUtils.updateTakePhotoState(true);
	// Intent intent = new Intent(context.getActivity(), ImageBucketChooseActivity.class);
	// intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, 1);
	// intent.putExtra(IntentConstants.EXTRA_IMAGE_CATALOG, imagCatalog.name());
	// context.startActivity(intent);
	// }
	//
	// }
	// }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	//
	// }).show();
	// ad.setCanceledOnTouchOutside(true);
	// }

	public static void putImageCache(String imageCatalog, Collection<ImageItem> images) {
		mImageCache.put(imageCatalog, images);
	}

	public static void clearImageCache(String imageCatalog) {
		mImageCache.get(imageCatalog).clear();
	}

	public static Collection<ImageItem> getImageCache(String imageCatalog) {
		return mImageCache.get(imageCatalog);
	}

	public static ImageItem createImageItemInstance(String imageId, String thumbnailPath, String sourcePath) {
		return new ImageItem(imageId, thumbnailPath, sourcePath);
	}

	public static ImageItem createImageItemInstance(Context context, String photoPath) {

		String imageId = photoPath.substring(photoPath.lastIndexOf("/"), photoPath.lastIndexOf("."));
		String thumbnailPath = photoPath;// getThumbnailPath(context);
		// new CompressImageTask().execute(photoPath,thumbnailPath,30,30,50);
		return createImageItemInstance(imageId, thumbnailPath, photoPath);
	}

	/**
	 * @Title:
	 * @Description:
	 *
	 * @return
	 */
	private static String getThumbnailPath(Context context) {
		return getImagesPath(context) + String.valueOf(System.currentTimeMillis()) + ".jpg";
	}
	/*
	 * private static String getImagesPath(Context context) {
	 * //Environment.getExternalStorageDirectory() + "/"+IMAGE_PATH_DEFAULT+"/",
	 * String.valueOf(System.currentTimeMillis()) + ".jpg" return
	 * getImagesPath(context)+String.valueOf(System.currentTimeMillis())+
	 * ".jpg"; }
	 */

	/**
	 * @Title:
	 * @Description:
	 *
	 * @return
	 */
	public static String getImagesPath(Context context) {

		String directory = SystemSettings.getAppName() + File.separator + IMAGE_PATH + "/" + SharedPreferencesUtil.getSettingMessage(context, "userId", "temp") + "/"
				+ TimeUtil.getToadyString() + "/";
		// return Environment.getExternalStorageDirectory().getAbsolutePath() +
		// File.separator + directory +
		// String.valueOf(System.currentTimeMillis()) + ".jpg";   /mnt/sdcard

		return Environment.getExternalStorageDirectory().toString() + "/myimage/" + String.valueOf(System.currentTimeMillis()) + ".jpg";

	}

	public static String compressImage(String filePath, String fileName, int quality, File imageDir) {

		File outputFile = null;
		try {
			Bitmap bm = getSmallBitmap(filePath, 720, 1280);
			int degree = readPictureDegree(filePath);
			if (degree != 0) {// 旋转照片角度
				bm = rotateBitmap(bm, degree);
			}
			outputFile = new File(imageDir, fileName);
			FileOutputStream out = new FileOutputStream(outputFile);
			bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputFile.getPath();
	}

	/**
	 * 
	 * @Title: compressImage
	 * @Description: 按参数压缩图片到指定路径
	 *
	 * @param srcFilePath
	 * @param reqWidth
	 * @param reqHeight
	 * @param quality
	 * @return
	 */
	public static String compressImage(String srcFilePath, String destFilePath, int reqWidth, int reqHeight, int quality) {

		File outputFile = null;
		try {
			Bitmap bm = getSmallBitmap(srcFilePath, reqWidth, reqHeight);
			int degree = readPictureDegree(srcFilePath);
			if (degree != 0) {// 旋转照片角度
				bm = rotateBitmap(bm, degree);
			}
			outputFile = new File(destFilePath);
			FileOutputStream out = new FileOutputStream(outputFile);
			bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputFile.getPath();
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
		if (bitmap != null) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}

	public static Bitmap bitmapCompress(String path) {
		// 其实手机屏幕没图片这么大. 只需要根据手机屏幕对图片进行缩放
		// 1.计算图片的宽高
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 不去真正的解析这个bitmap 而是解析他的头信息.
		BitmapFactory.decodeFile(path, opts);
		int picHeight = opts.outHeight;
		// int picWidth = opts.outWidth;
		// 3.计算图片缩放比例.
		int scale = picHeight / 100;
		// 4.把缩放后的图片显示到界面上.
		opts.inJustDecodeBounds = false;// 真正的解析这个bitmap
		opts.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		return bitmap;
	}

	public static Bitmap compressImage(String filePath, int width, int height) {
		Bitmap bm = null;
		try {
			bm = getSmallBitmap(filePath, width, height);
			int degree = readPictureDegree(filePath);
			if (degree != 0) {// 旋转照片角度
				bm = rotateBitmap(bm, degree);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bm;
	}

	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 压缩指定路径的图片 并且返回bitmap对象 在原图片上压缩
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		File temFile = new File(filePath);
		Log.i(tag, "temFile=====" + temFile.length());

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = reqHeight;
		final int width = reqWidth;
		int inSampleSize = 1;
		float hh = 1280f;// 这里设置高度为800f
		float ww = 720f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可

		if (width > height && width > ww) {// 如果宽度大的话根据宽度固定大小缩放
			inSampleSize = (int) (width / ww);
		} else if (width < height && height > hh) {// 如果高度高的话根据宽度固定大小缩放
			inSampleSize = (int) (height / hh);
		}
		if (inSampleSize <= 0)
			inSampleSize = 1;

		// options.inSampleSize =inSampleSize;//设置缩放比例
		// if (height > reqHeight || width > reqWidth) {
		//
		//
		// final int heightRatio = Math.round((float) height / (float) reqHeight);
		// final int widthRatio = Math.round((float) width / (float) reqWidth);
		//
		// inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		// }

		return inSampleSize;
	}

	public enum ImageCatalog {
		SELF_COVER, SELF_AVATAR, BABY_PHOTOS, BABY_COVER, BABY_AVATAR;
	}

	public static Bitmap compressPixel(String filePath){
		Bitmap bmp = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		//setting inSampleSize value allows to load a scaled down version of the original image
		options.inSampleSize = 2;

		//inJustDecodeBounds set to false to load the actual bitmap
		options.inJustDecodeBounds = false;
		options.inTempStorage = new byte[16 * 1024];
		try {
			//load the bitmap from its path
			bmp = BitmapFactory.decodeFile(filePath, options);
			if (bmp == null) {

				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(filePath);
					BitmapFactory.decodeStream(inputStream, null, options);
					inputStream.close();
				} catch (FileNotFoundException exception) {
					exception.printStackTrace();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}finally {
			return bmp;
		}
	}

	public static String compressImage(String mCurrentPhotoPath, Context context) {

		if (mCurrentPhotoPath != null) {

			try {

				File f = new File(mCurrentPhotoPath);
				Bitmap bm = compressPixel(mCurrentPhotoPath);
				int degree = readPictureDegree(mCurrentPhotoPath);
				if (degree != 0) {// 旋转照片角度
					bm = rotateBitmap(bm, degree);
				}
				//获取文件路径 即：/data/data/***/files目录下的文件
				String path = context.getFilesDir().getPath();
//                Log.e(TAG, "compressImage:path== "+path );
				//获取缓存路径
				File cacheDir = context.getCacheDir();
//                Log.e(TAG, "compressImage:cacheDir== "+cacheDir );
//                File newfile = new File(
//                getAlbumDir(), "small_" + f.getName());
				File newfile = new File(
						cacheDir, "small_" + f.getName());
				FileOutputStream fos = new FileOutputStream(newfile);
				bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);

				return newfile.getPath();

			} catch (Exception e) {
			}

		} else {
		}
		return mCurrentPhotoPath;
	}
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 *
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		options.inSampleSize = calculateInSampleSize(options, 720, 1280);

		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}


}
