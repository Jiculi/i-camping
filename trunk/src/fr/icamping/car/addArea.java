package fr.icamping.car;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class addArea extends Activity {
    /** Called when the activity is first created. */
	private Uri mImageCaptureUri;
	Spinner updateFreqSpinner;
	private static final int PICK_FROM_CAMERA = 1;
	 ImageView imageiewImageCaptured;
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // TODO Auto-generated method stub
	  super.onActivityResult(requestCode, resultCode, data);
	   
	  if (resultCode == RESULT_OK)
	  {
		    imageiewImageCaptured = (ImageView)findViewById(R.id.photoResultView);
		      
	   Bundle extras = data.getExtras();
	   Bitmap bmp = (Bitmap) extras.get("data");
	   imageiewImageCaptured.setImageBitmap(bmp);
	  }
	   
	 }
	/*
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		    	mImageCaptureUri = data.getData();
		    	Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
     		    ImageView image = (ImageView) findViewById(R.id.photoResultView);
     		    image.setImageBitmap(thumbnail);
	    }
	   */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        updateFreqSpinner = (Spinner)findViewById(R.id.spinnerDepartement);
        populateSpinners();
        Log.d("Yann", "Lancement make_picture");
        make_picture();
        
     
    }
       
    private void make_picture() {
    	/*
    	//define the file-name to save photo taken by Camera activity
    	String fileName = "new-photo-name.jpg";
    	//create parameters for Intent with filename
    	ContentValues values = new ContentValues();
    	values.put(MediaStore.Images.Media.TITLE, fileName);
    	values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
    	//imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
    	
    	mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				   "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
    	intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
    	try {
			intent.putExtra("return-data", true);
			startActivityForResult(intent, PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
       */
    	  Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	   startActivityForResult(intent, 0);
    	   
    	}
    
    private void populateSpinners() { 
    	ArrayAdapter<CharSequence> fAdapter;
	    fAdapter = ArrayAdapter.createFromResource(this, R.array.departements,  android.R.layout.simple_spinner_item);
	    int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
	    fAdapter.setDropDownViewResource(spinner_dd_item);
	    updateFreqSpinner.setAdapter(fAdapter);
    
    }

}
