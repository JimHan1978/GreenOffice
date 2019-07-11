package com.hyetec.moa.view.ui.ImageBucket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Camera {
	public static final int TAKE_PICTURE = 0x000000;
	public static final String IMAGE_PATH_DEFAULT = "myimage";

	/**
	 * 
	 * 调用系统相机
	 */
	public static String takePhoto(Fragment fragment) {
		String filePath = ImageUtils.getImagesPath(fragment.getActivity());
		Intent openCameraIntent = createCameraIntent(filePath, fragment.getActivity());
		fragment.startActivityForResult(openCameraIntent, TAKE_PICTURE);
		return filePath;
	}
	public static String takePhoto(Activity context) {
		String filePath = ImageUtils.getImagesPath(context);
		Intent openCameraIntent = createCameraIntent(filePath, context);
		//openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		context.startActivityForResult(openCameraIntent, TAKE_PICTURE);
		return filePath;
	}
	/**
	 * 
	 * 调用系统相机
	 */
	public static void takePhoto(Activity activity, String filePath) {
		//String filePath = ImageUtils.getImagesPath(activity);
		Intent openCameraIntent = createCameraIntent(filePath,activity);
		activity.startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	/*public static String saveBitmap(String filePath) {
		File f = new File(filePath);
		try {
			if (f.exists()) { 
				Bitmap bm = FileUtils.compressImage(filePath, 320, 480);
				FileOutputStream out = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();
				} 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}*/

	/**
	 * @Title:
	 * @Description:
	 *
	 * @return
	 */
	private static Intent createCameraIntent(String filePath, Context context) {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File vFile = new File(filePath);// 检测该文件路径，以当成时间为文件名的jpg文件
		if (!vFile.exists()) {
			File vDirPath = vFile.getParentFile();// 如果文件不存在就创建
			vDirPath.mkdirs();
		} else {
			if (vFile.exists()) {
				vFile.delete();
			}
		}
		Uri cameraUri = null;
		if(android.os.Build.VERSION.SDK_INT>23){
			cameraUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", vFile);
		}else{
			cameraUri = Uri.fromFile(vFile);
		}

		Log.i("TAG", "------>" + cameraUri);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
		return openCameraIntent;
	}

	/**
	 * 方法说明：图片存储的位置
	 * 
	 * @return
	 */
	public static File imageFile() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			String path = sdcardDir.getPath() + "/imageCache";
			File path1 = new File(path);
			if (!path1.exists()) {
				path1.mkdirs();
			}
			return path1;
		}
		return null;
	}

	/**
	 * 读取图片的旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return 图片的旋转角度
	 */
	public  static  int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 将图片按照某个角度进行旋转
	 *
	 * @param bm
	 *            需要旋转的图片
	 * @param degree
	 *            旋转角度
	 * @return 旋转后的图片
	 */
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		Bitmap returnBm = null;

		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}

	public static File saveFile(Bitmap bm, String fileName) throws IOException {//将Bitmap类型的图片转化成file类型，便于上传到服务器
		String path = Environment.getExternalStorageDirectory() + "/exhibits";
		File dirFile = new File(path);
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		File myCaptureFile = new File(path + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
		return myCaptureFile;

	}


	public static Bitmap getSDCardImg(String imagePath)
	{
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return BitmapFactory.decodeFile(imagePath, opt);
	}

    public static String getTime(){

        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳

        String str=String.valueOf(time);

        return str;

    }

	public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		File temFile = new File(filePath);

		return BitmapFactory.decodeFile(filePath, options);
	}
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
}
