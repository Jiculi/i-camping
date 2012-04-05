package fr.icamping.car;



import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;


public class campingCarActivity extends Activity {
    /** Called when the activity is first created. */

	


	private void exitIcamping()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getString(R.string.sureExit))
    	       .setCancelable(false)
    	       .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   campingCarActivity.this.finish();
    	           }
    	       })
    	       .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	builder.show();
	}
	
	private void aboutIcamping()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getString(R.string.about_text));
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
  	      public void onClick(DialogInterface dialog, int which) {  
  	        return;  
  	    } });   
    	AlertDialog alert = builder.create();
    	
    	builder.show();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button buttonExit = (Button) findViewById(R.id.ButtonExit);
        buttonExit.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
        
            	exitIcamping();
            	
            	
            }
        }
        );
        
        Button buttonAbout = (Button) findViewById(R.id.ButtonAbout);
        buttonAbout.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
        
            	aboutIcamping();
            	
            	
            }
        }
        );


      

    // Intent intent = new Intent(this, addArea.class);
     //startActivity(intent);
           
      Button buttonAddArea=(Button)findViewById(R.id.ButtonAddArea);
 /*     Button buttonTest=(Button)findViewById(R.id.ButtonTest);
     
      buttonTest.setOnClickListener(new android.view.View.OnClickListener() {

      public void onClick(View view) {
    	 
    	
   	Intent intent = new Intent(campingCarActivity.this, makeTest.class);
   	startActivity(intent);
    	
      }
      }
      );
      */
      
      buttonAddArea.setOnClickListener(new android.view.View.OnClickListener() {

      public void onClick(View view) {
    	// sendArea.postData();
    	 // Log.d("YourTag", "YourOutput");
    	//  Log.d("Yann", "Lancement addArea ");
   	Intent intent = new Intent(campingCarActivity.this, addArea.class);
   	startActivity(intent);
    	
      }
      }
     );
    }
  
}




