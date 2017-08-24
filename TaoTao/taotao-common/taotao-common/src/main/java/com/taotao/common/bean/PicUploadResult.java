package com.taotao.common.bean;

import java.io.Serializable;

public class PicUploadResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer error; // 0无错误 1.上传失败有错
	private String url; // 图片绝对路径
	private String width;
	private String height;

	@Override
	public String toString() {
		return "PicUploadResult [error=" + error + ", url=" + url + ", width=" + width + ", height=" + height + "]";
	}
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
}
