package fr.icamping.car;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
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
import android.widget.TextView;

public class addArea extends Activity {
    /** Called when the activity is first created. */
	
	Spinner DptSpinner,KmDeSpinner,OrientationSpinner;
	ImageView imageiewImageCaptured;
	private static final int ACTION_TAKE_PHOTO_B = 1;
	private String mCurrentPhotoPath;
	 private static final String JPEG_FILE_PREFIX = "IMG_";
     private static final String JPEG_FILE_SUFFIX = ".jpg";
     private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
     private ImageView mImageView;
 	
     // Location
     private LocationManager locationManager;
 	private LocationListener listenerCoarse;
 	private LocationListener listenerFine;
 	
 	// Holds the most up to date location. 
 	private Location currentLocation;
 	
 	// Set to false when location services are 
 	// unavailable. 
 	private boolean locationAvailable = true;
 	// End location 
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
        DptSpinner = (Spinner)findViewById(R.id.spinnerDepartement);
        OrientationSpinner = (Spinner)findViewById(R.id.spinnerOrientation);
        KmDeSpinner = (Spinner)findViewById(R.id.spinnerKmde);
        
        populateSpinners();
        mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        mImageView = (ImageView)findViewById(R.id.photoResultView);  		
		
        Button buttonFindLocation = (Button) findViewById(R.id.findLocationButton);
        buttonFindLocation.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
            	
            	  registerLocationListeners();
            	  
      
            	
            }
        }
        );
        
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
    	builder.setMessage(R.string.SureCancel)
    	       .setCancelable(false)
    	       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   addArea.this.finish();
    	           }
    	       })
    	       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        printMessage(""+targetW);
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
      
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
      
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
      
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
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
    	ArrayAdapter<CharSequence> dAdapter,oAdapter,kAdapter;
	    int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
	   
	    dAdapter = ArrayAdapter.createFromResource(this, R.array.departements,  android.R.layout.simple_spinner_item);
	    dAdapter.setDropDownViewResource(spinner_dd_item);
	    DptSpinner.setAdapter(dAdapter);
	    
	    oAdapter = ArrayAdapter.createFromResource(this, R.array.orientation,  android.R.layout.simple_spinner_item);
	    oAdapter.setDropDownViewResource(spinner_dd_item);
	    OrientationSpinner.setAdapter(oAdapter);
	    
	    kAdapter = ArrayAdapter.createFromResource(this, R.array.kmde,  android.R.layout.simple_spinner_item);
	    oAdapter.setDropDownViewResource(spinner_dd_item);
	    KmDeSpinner.setAdapter(kAdapter);
    
    }

    private void printMessage (String message)
    {
    	Log.d("Yann", "printMessage :"+message);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(message);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
  	      public void onClick(DialogInterface dialog, int which) {  
  	        return;  
  	    } });   
    	AlertDialog alert = builder.create();
    	builder.show();
    }
    
    private void registerLocationListeners() {
		locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		
		// Initialize criteria for location providers
		Criteria fine = new Criteria();
		fine.setAccuracy(Criteria.ACCURACY_FINE);
		Criteria coarse = new Criteria();
		coarse.setAccuracy(Criteria.ACCURACY_COARSE);
		
		// Get at least something from the device,
		// could be very inaccurate though
		currentLocation = locationManager.getLastKnownLocation(
			locationManager.getBestProvider(fine, true));
		
		if (listenerFine == null || listenerCoarse == null)
			createLocationListeners();
			
		// Will keep updating about every 500 ms until 
		// accuracy is about 1000 meters to get quick fix.
		locationManager.requestLocationUpdates(
			locationManager.getBestProvider(coarse, true), 
			500, 1000, listenerCoarse);
		// Will keep updating about every 500 ms until 
		// accuracy is about 50 meters to get accurate fix.
		locationManager.requestLocationUpdates(
			locationManager.getBestProvider(fine, true), 
			500, 50, listenerFine);
		 TextView Textlon = (TextView) findViewById(R.id.gpsLon);
		 TextView Textlat = (TextView) findViewById(R.id.gpsLat);
		  Textlon.setText("null");
		  Textlat.setText("null");
		if(currentLocation != null)
		{
			
			   Textlon.setText(currentLocation.getLatitude()+"");
			   Textlon.setText(currentLocation.getLongitude()+"");
		}
		
		   

	}

	/**
	* 	Creates LocationListeners
	*/
	private void createLocationListeners() {
		 Log.d("Yann", "into createLocationListeners" );
		listenerCoarse = new LocationListener() {
			public void onStatusChanged(String provider, 
				int status, Bundle extras) {
				TextView Textlon = (TextView) findViewById(R.id.gpsLon);
				   Textlon.setText(""+status);
				switch(status) {
				  
				case LocationProvider.OUT_OF_SERVICE:
				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					locationAvailable = false;
					break;
				case LocationProvider.AVAILABLE:
					locationAvailable = true;
				}
			}
			public void onProviderEnabled(String provider) {
			}
			public void onProviderDisabled(String provider) {
			}
			public void onLocationChanged(Location location) {
				currentLocation = location;
				if (location.getAccuracy() > 1000 && 
					location.hasAccuracy())
					locationManager.removeUpdates(this);
			}
		};
		listenerFine = new LocationListener() {
			public void onStatusChanged(String provider, 
				int status, Bundle extras) {
				switch(status) {
				case LocationProvider.OUT_OF_SERVICE:
				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					locationAvailable = false;
					break;
				case LocationProvider.AVAILABLE:
					locationAvailable = true;
				}
			}
			public void onProviderEnabled(String provider) {
			}
			public void onProviderDisabled(String provider) {
			}
			public void onLocationChanged(Location location) {
				currentLocation = location;
				if (location.getAccuracy() > 1000 
					&& location.hasAccuracy())
					locationManager.removeUpdates(this);
			}
		};
	}
	
    
  
}
