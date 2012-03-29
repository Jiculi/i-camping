package fr.icamping.car;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.graphics.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
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
	ImageView imageiewImageCaptured;
	private String mCurrentPhotoPath;
	 private static final String JPEG_FILE_PREFIX = "IMG_";
     private static final String JPEG_FILE_SUFFIX = ".jpg";
     private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	   
	  if (resultCode == RESULT_OK)
	  {
	   imageiewImageCaptured = (ImageView)findViewById(R.id.photoResultView);     
	   Bundle extras = data.getExtras();
	   Bitmap bmp = (Bitmap) extras.get("data");
	   imageiewImageCaptured.setImageBitmap(bmp);
	   
	  }
	 }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        updateFreqSpinner = (Spinner)findViewById(R.id.spinnerDepartement);
        populateSpinners();
        make_picture();  
    }
    /* Photo album for this application */
    private String getAlbumName() {
            return getString(R.string.album_name);
    }


  
    
    private File createImageFile() throws IOException {
    	File Repertoire = new File("/sdcard/");
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
        Log.d("Yann", imageFileName);
        return image;
    }
  
    private void make_picture() {
    	  Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	  startActivityForResult(intent, 0);
    	  File f = null;
		try {
			f = createImageFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
    	}
    
    private void populateSpinners() { 
    	ArrayAdapter<CharSequence> fAdapter;
	    fAdapter = ArrayAdapter.createFromResource(this, R.array.departements,  android.R.layout.simple_spinner_item);
	    int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
	    fAdapter.setDropDownViewResource(spinner_dd_item);
	    updateFreqSpinner.setAdapter(fAdapter);
    
    }

  
}
