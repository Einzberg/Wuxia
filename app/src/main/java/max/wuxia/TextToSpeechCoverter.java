package max.wuxia;

/**
 * Created by MaxBlue on 12/22/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import org.w3c.dom.Text;

import java.util.Locale;


public class TextToSpeechCoverter {

    TextToSpeech textToSpeech;
    Context context;

    public TextToSpeechCoverter(Context context){
        this.context = context;
        setupTextToSpeech();
    }

    public void setupTextToSpeech(){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
//                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }


    protected void onPause() {
        if(textToSpeech != null){

            textToSpeech.stop();

//            textToSpeech.shutdown();
        }
    }

    public void convertTextToSpeech(String text){
//        String test = text.substring(0, 500);
        Log.d("content", "trying to speech: " + text);

        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
    }

}
