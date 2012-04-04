package fr.icamping.car;

import android.app.Activity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Interface {

/*
* Instantiates the spinner with it's content
*/
    public static ArrayAdapter getSpinnerAdapter(Activity context, Boolean has_guess) {
     ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item);
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     fillSpinner(adapter, has_guess);
        return adapter;
    }
    
    /*
* Fills the spinner with the languages list
*/
public static void fillSpinner(ArrayAdapter adapter, Boolean has_guess) {
adapter.clear();

}}