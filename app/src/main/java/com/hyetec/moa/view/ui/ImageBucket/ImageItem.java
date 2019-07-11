package com.hyetec.moa.view.ui.ImageBucket;

import java.io.Serializable;

/**
 * 图片对象
 *
 */
public class ImageItem implements Serializable {
	private static final long serialVersionUID = -7188270558443739436L;
	public String imageId;
	public String thumbnailPath;

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String sourcePath;
	public boolean isSelected = false;

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public ImageItem(String imageId, String thumbnailPath, String sourcePath) {
		this.imageId = imageId;
		this.thumbnailPath = thumbnailPath;
		this.sourcePath = sourcePath;
	}

	/**
	 * 
	 */
	public ImageItem() {
		// TODO Auto-generated constructor stub
	}

}
