package fr.icamping.car;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class addArea extends Activity {
    /** Called when the activity is first created. */
	
	Spinner updateFreqSpinner;
	ImageView imageiewImageCaptured;
	private static final int ACTION_TAKE_PHOTO_B = 1;
	private String mCurrentPhotoPath;
	 private static final String JPEG_FILE_PREFIX = "IMG_";
     private static final String JPEG_FILE_SUFFIX = ".jpg";
     private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
     private ImageView mImageView;
 	
     
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	   
	  if (resultCode == RESULT_OK) 
	  {
		  /*
		 
		  if(data != null)
		  {
	  imageiewImageCaptured = (ImageView)findViewById(R.id.photoResultView);     
	  Bundle extras = data.getExtras();
	  Bitmap bmp = (Bitmap) extras.get("data");
	  imageiewImageCaptured.setImageBitmap(bmp);
		  }
		  else
		  {
			  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    	builder.setMessage("Error data is null !");
		    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
		  	      public void onClick(DialogInterface dialog, int which) {  
		  	        return;  
		  	    } });   
		    	AlertDialog alert = builder.create();
		    	
		    	builder.show();
		  }
		  
	*/
	   if (mCurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			mCurrentPhotoPath = null;
		}
	  }
	}

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        updateFreqSpinner = (Spinner)findViewById(R.id.spinnerDepartement);
        populateSpinners();
        mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        mImageView = (ImageView)findViewById(R.id.photoResultView);  		
		
		 Button buttonTakePicture = (Button) findViewById(R.id.takePictureButton);
        buttonTakePicture.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
            	dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
            	int targetW = mImageView.getMeasuredHeight();
        		int targetH = mImageView.getHeight();

        		Log.d("Yann", "targetW 1 : "+targetW);
        		Log.d("Yann", "targetH 1 : "+targetH);
           	
         
            	
            	
            }
        }
        );
      
        Button buttonSearchArea = (Button) findViewById(R.id.cancelButton);
        buttonSearchArea.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
            	exitAddArea();
            	
            	
            }
        }
        );
    }
    /* Photo album for this application */
    private String getAlbumName() {
            return getString(R.string.album_name);
    }
    
    private void exitAddArea()
	{
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to cancel ?")
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   addArea.this.finish();
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	builder.show();
	}

    private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
}

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		Log.d("Yann", "targetW 2 : "+targetW);
		Log.d("Yann", "targetH 2 : "+targetH);
		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		 bmOptions.inJustDecodeBounds = false;
		 bmOptions.inSampleSize = scaleFactor;
		 bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, null);
		
		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmap);

		mImageView.setVisibility(View.VISIBLE);

	}
    private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
    private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		
		return f;
	}
    private File createImageFile() throws IOException {
    	File Repertoire = getAlbumDir();
        // Create an image file name
        String timeStamp = 
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File image = File.createTempFile(
            imageFileName, 
            JPEG_FILE_SUFFIX, 
            Repertoire
        );
        mCurrentPhotoPath = image.getAbsolutePath();
       // Log.d("Yann", imageFileName);
        return image;
    }
    private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch(actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;

		default:
			break;			
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
	}
   
    
    private void populateSpinners() { 
    	ArrayAdapter<CharSequence> fAdapter;
	    fAdapter = ArrayAdapter.createFromResource(this, R.array.departements,  android.R.layout.simple_spinner_item);
	    int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
	    fAdapter.setDropDownViewResource(spinner_dd_item);
	    updateFreqSpinner.setAdapter(fAdapter);
    
    }

  
}
