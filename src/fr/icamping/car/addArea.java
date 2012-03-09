package fr.icamping.car;


import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class addArea extends Activity {
    /** Called when the activity is first created. */
	
	Spinner updateFreqSpinner;
	static  int TAKE_PICTURE = 1;
	private Uri outputFileUri;
		
	  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		  Log.d("Yann", "Dans onActivityResult ");
		  /*
	        if (requestCode == TAKE_PICTURE) {  
	        	Uri imageUri = null;
	        	if(data != null)
	        	{
	        		if(data.hasExtra("data"))
	        		{
	        		    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");  
	        		    ImageView image = (ImageView) findViewById(R.id.photoResultView);  
	        		    image.setImageBitmap(thumbnail);  
	        		}
	        		}
	        	}
	        	*/
	        }  
	  
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
    	   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	  	  File file = new File(Environment.getExternalStorageDirectory(),"area.jpg");
    	  	  outputFileUri = Uri.fromFile(file);
    	  	  takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
    	  	  startActivityForResult(takePictureIntent, TAKE_PICTURE);
    }
    
    private void populateSpinners() { 
    	ArrayAdapter<CharSequence> fAdapter;
	    fAdapter = ArrayAdapter.createFromResource(this, R.array.departements,  android.R.layout.simple_spinner_item);
	    int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
	    fAdapter.setDropDownViewResource(spinner_dd_item);
	    updateFreqSpinner.setAdapter(fAdapter);
    
    }
}
