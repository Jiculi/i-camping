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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    	// sendArea.postData();
    	 // Log.d("YourTag", "YourOutput");
    	  Log.d("Yann", "Lancement addArea ");
   	Intent intent = new Intent(campingCarActivity.this, addArea.class);
   	startActivity(intent);
    	
      }
      }
     );
    }
  
}




