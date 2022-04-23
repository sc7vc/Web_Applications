package com.example.icp11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText text;
    Button butn;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for text
        text = findViewById(R.id.editTextTTS);
        //for button
        butn = findViewById(R.id.btnTTS);

        butn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TextToSpeech listener
                textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        // If Input is success
                        if(status == TextToSpeech.SUCCESS) {
                            int result = textToSpeech.setLanguage(Locale.US);

                            //Input valiadtion
                            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                                Log.e("message", "Input Language is not supported");
                            } else {
                                //output
                                speakToUser();
                            }
                        } else{
                            Log.e("message", "TTS is not supported for given input");
                        }

                    }
                });
            }
        });
    }
    void speakToUser(){
        String input = text.getText().toString();
        textToSpeech.setSpeechRate(0.5f);
        textToSpeech.speak(input, TextToSpeech.QUEUE_ADD, null);
    }
    @Override
    protected void onPause(){
        super.onPause();
        textToSpeech.stop();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }
}