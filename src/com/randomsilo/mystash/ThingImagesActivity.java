package com.randomsilo.mystash;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.randomsilo.mystash.db.pojo.ThingImage;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.util.ExifHelper;
import com.randomsilo.mystash.util.ImageData;

public class ThingImagesActivity extends BaseThingNavigationActivity {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	String mCurrentPhotoPath;
	public ImageView itemImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thing_images);
		itemImage = (ImageView)findViewById(R.id.ItemImage);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		itemImage.setImageDrawable(null);
	}
	
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);
	    
	    refreshImage();
	}
	
	public void clearPicture(View view) {

		// Get Model
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		
		// Clear Image
	    MyStashSession.getInstance().getThingService().clearImages(m);
		
		// Set Thing Image
	    m.getThingImage().setItemImage(null);
	    m.getThingImage().setRotation(0);
	    m.getThingImage().setHeight(0);
	    m.getThingImage().setWidth(0);
	    MyStashSession.getInstance().getThingService().save(m);
	    
	    // Refresh Image
	    itemImage.setImageDrawable(null);
	}
	
	public void takePicture(View view) {
		final int REQUEST_IMAGE_CAPTURE = 1;
		
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	    	// Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	            
	            if (photoFile != null) {
		            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		        }
	        } catch (IOException ex) {
	            Log.e("MyStash", "takePicture", ex);
	            Toast.makeText(this, getResources().getString(R.string.error_take_picture), Toast.LENGTH_LONG).show();
	        }
	        
	    }
	}
	
	private File createImageFile() throws IOException {
	    File image = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "last_mystash_photo" + ".jpg");
	    mCurrentPhotoPath = image.getAbsolutePath();
	    
	    return image;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	    	savePicture();
	    }
	}
	
	private void savePicture() {

		try {
			ImageData imageData = ExifHelper.digestFileImage(mCurrentPhotoPath);
		    
			// Get the dimensions of the View
			double targetW = itemImage.getWidth();
		    double targetH = itemImage.getHeight();
	
		    // Decode the image file into a Bitmap sized to fill the View
		    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		    bmOptions.inSampleSize = calculateInSampleSize( imageData, (int)targetW, (int)targetH);
		    bmOptions.inPurgeable = true;
		    
		    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData.getBytes(), 0, imageData.getBytes().length, bmOptions);
			
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bitmap.compress(Bitmap.CompressFormat.PNG, 60, stream);
		    byte[] byteArray = stream.toByteArray();
		    
		    ThingModel m = MyStashSession.getInstance().getActiveThing();
		    m.getThingImage().setItemImage(byteArray);
		    m.getThingImage().setRotation(imageData.getRotation());
		    m.getThingImage().setHeight(bitmap.getHeight());
		    m.getThingImage().setWidth(bitmap.getWidth());
		    MyStashSession.getInstance().getThingService().save(m);
		    
		    // Clean up File System
		    if(!new File(mCurrentPhotoPath).delete()) {
		    	Log.e("MyStash", "Failed to delete temporary image file: " + mCurrentPhotoPath);
		    }
		    
		    // Refresh Image
		    refreshImage();
		    
		} catch( Exception e) {
			Log.e("MyStash", "refreshImage", e);
		}
	}
	
	private void refreshImage() {
		try {
			ThingModel m = MyStashSession.getInstance().getActiveThing();
			if(m != null && m.getThingImage() != null && m.getThingImage().getItemImage() != null) {
				displayImage(m.getThingImage());
			}
			
		} catch (Exception e) {
        	Log.e("MyStash", "refreshImage", e);
        }
	}
	
	private void displayImage(ThingImage thingImage) {
		
		// Get the dimensions of the View
	    double targetW = itemImage.getWidth();
	    double targetH = itemImage.getHeight();
	    
	    // Decode the image file into a Bitmap sized to fill the View
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inSampleSize = calculateInSampleSize(thingImage.getWidth(), thingImage.getHeight(), (int)targetW, (int)targetH);
	    bmOptions.inPurgeable = true;
	    
	    Bitmap bitmap = BitmapFactory.decodeByteArray(thingImage.getItemImage(), 0, thingImage.getItemImage().length, bmOptions);
	    
	    itemImage.setImageBitmap(bitmap);
	    itemImage.setRotation(thingImage.getRotation());
	}
	
	
	public static int calculateInSampleSize( ImageData imageData, int reqWidth, int reqHeight) {
	    int inSampleSize = 1;
	    
	    inSampleSize = calculateInSampleSize( imageData.getWidth(), imageData.getHeight(), reqWidth, reqHeight);
	    if( imageData.getFileSize() > 1500000) {
	    	inSampleSize = inSampleSize * 2;
	    }
	
	    return inSampleSize;
	}
	
	public static int calculateInSampleSize( int imageWidth, int imageHeight, int reqWidth, int reqHeight) {
		
	    // Raw height and width of image
	    final int height = imageHeight;
	    final int width = imageWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}
	
}
