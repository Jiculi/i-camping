package fr.icamping.car;

import android.widget.Spinner;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;


public class Translator {
/*
* Takes the string to translate and does the work
*/
public static String translate(String text, Language lang_from, Language lang_to) throws Exception {
 Translate.setHttpReferrer("http//www.dmathieu.com");
 String translatedText = Translate.execute(text, lang_from, lang_to);
return translatedText;
}

/*
* Gets a language string from it's name (ENGLISH -> en)
*/
public static Language getLanguageString(Spinner spinner) throws Exception {
String lang = spinner.getSelectedItem().toString();

for (Language l : Language.values()) {
if (l.name() == lang) {
return l;
}
}
throw new Exception("Unknown language provided : " + lang);
}
}