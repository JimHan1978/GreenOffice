package com.hyetec.moa.view.ui.ImageBucket;

import java.util.List;

/**
 * 相册对象
 *
 */
public class ImageBucket {
	public int count = 0;
	public String bucketName;// 相册名字
	public List<ImageItem> imageList;// 相册中的照片
	public boolean selected = false;
}
