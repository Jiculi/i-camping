package fr.icamping.car;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.translate.Language;



public class makeTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traduire);
        
        /*
        * We get the two spinners : the language to translate from
        * And the language to translate to. And we add them the languages list
        */
                ((Spinner) findViewById(R.id.languages_from)).setAdapter(Interface.getSpinnerAdapter(this, true));
                ((Spinner) findViewById(R.id.languages_to)).setAdapter(Interface.getSpinnerAdapter(this, false));

                
                /*
        * Whenever we click on the button, we translate the string.
        */
        findViewById(R.id.translate_button).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        String text = ((EditText) findViewById(R.id.text_to_translate)).getText().toString();

        try {
        Language lang_from = Translator.getLanguageString((Spinner) findViewById(R.id.languages_from));
        Language lang_to = Translator.getLanguageString((Spinner) findViewById(R.id.languages_to));

        String translatedText = Translator.translate(text, lang_from, lang_to);
        ((TextView) findViewById(R.id.translated_text)).setText(translatedText);
        } catch(Exception e) {
        /*
        * It failed. We display the error message
        */
        ((TextView) findViewById(R.id.translated_text)).setText(e.toString());
        }
        }
        });
    }
}