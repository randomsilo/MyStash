package com.randomsilo.mystash.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.util.Log;

public class ExifHelper {
	private static final String TAG = "ExifHelper";

	public static ImageData digestFileImage(String fileName) {
		File imageFile = new File(fileName);
		ImageData imageData = new ImageData();
		
		try
        {
			
			if(!imageFile.exists()) {
				throw new Exception("File Not Found");
			}
			
			imageData.setFileName(fileName);
			imageData.setFileSize(imageFile.length());
			
			imageData.setBytes(getFileBytes(imageFile));
			imageData.setRotation(0);
			
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		    Bitmap bitmap = BitmapFactory.decodeFile(fileName, bmOptions);
		    imageData.setHeight(bitmap.getHeight());
		    imageData.setWidth(bitmap.getWidth());
		
	    
            ExifInterface exif = new ExifInterface(fileName); 
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                	imageData.setRotation(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                	imageData.setRotation(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                	imageData.setRotation(270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                	imageData.setRotation(0);
                    break;
            }

        }
        catch (Exception e) {
        	Log.e(TAG, "Rotation ExifInterface", e);
        	imageData = null;
        }
		
		return imageData;
	}
	
	public static byte[] getFileBytes(File file) throws IOException {
	    ByteArrayOutputStream ous = null;
	    InputStream ios = null;
	    try {
	        byte[] buffer = new byte[4096];
	        ous = new ByteArrayOutputStream();
	        ios = new FileInputStream(file);
	        int read = 0;
	        while ((read = ios.read(buffer)) != -1)
	            ous.write(buffer, 0, read);
	    } finally {
	        try {
	            if (ous != null)
	                ous.close();
	        } catch (IOException e) {
	            // swallow, since not that important
	        }
	        try {
	            if (ios != null)
	                ios.close();
	        } catch (IOException e) {
	            // swallow, since not that important
	        }
	    }
	    return ous.toByteArray();
	}
}
