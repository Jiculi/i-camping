package fr.icamping.car;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class campingCarActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    // Intent intent = new Intent(this, addArea.class);
     //startActivity(intent);
           
      Button buttonAddArea=(Button)findViewById(R.id.ButtonAddArea);
      Button buttonTest=(Button)findViewById(R.id.ButtonTest);
     
      buttonTest.setOnClickListener(new android.view.View.OnClickListener() {

      public void onClick(View view) {
    	 
    	
   	Intent intent = new Intent(campingCarActivity.this, makeTest.class);
   	startActivity(intent);
    	
      }
      }
      );
      
      buttonAddArea.setOnClickListener(new android.view.View.OnClickListener() {

      public void onClick(View view) {
    	  int actionCode = 0;
    	 // Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	  //  startActivityForResult(takePictureIntent, actionCode);

    	// sendArea.postData();
    	 // Log.d("YourTag", "YourOutput");
   	Intent intent = new Intent(campingCarActivity.this, addArea.class);
   	startActivity(intent);
    	
      }
      }
     );
    }
}


