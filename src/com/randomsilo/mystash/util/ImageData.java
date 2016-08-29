package com.randomsilo.mystash.util;

public class ImageData {
	private int rotation;
	private int width;
	private int height;
	private byte[] bytes;
	private long fileSize;
	private String fileName;
	
	public ImageData() {
		
	}
	
	public ImageData(int rotation, int height, int width, byte[] bytes) {
		this.rotation = rotation;
		this.height = height;
		this.width = width;
		this.bytes = bytes;
	}
	
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
