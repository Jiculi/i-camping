package fr.icamping.car;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class addArea extends Activity {
    /** Called when the activity is first created. */
	
	 Spinner updateFreqSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        updateFreqSpinner = (Spinner)findViewById(R.id.spinnerDepartement);
        populateSpinners();
      
    }
    
    private void populateSpinners() { 
    	ArrayAdapter<CharSequence> fAdapter;
	    fAdapter = ArrayAdapter.createFromResource(this, R.array.departements,  android.R.layout.simple_spinner_item);
	    int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
	    fAdapter.setDropDownViewResource(spinner_dd_item);
	    updateFreqSpinner.setAdapter(fAdapter);
/*
	    // Renseigne le bouton fléché de magnitude minimum
	    ArrayAdapter<CharSequence> mAdapter;
	    mAdapter = ArrayAdapter.createFromResource(this, R.array.magnitude_options, android.R.layout.simple_spinner_item);
	    mAdapter.setDropDownViewResource(spinner_dd_item);
	    magnitudeSpinner.setAdapter(mAdapter);
   */ 	
    
    }
}
