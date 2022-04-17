package com.example.sulli.appdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.text.TextWatcher;
import android.text.Editable;
import android.media.MediaPlayer;

public class FormDemo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_demo);

        final TextView copied_text = (TextView) findViewById(R.id.copiedtext);
        final EditText text_to_copy = (EditText) findViewById(R.id.editText);
        copied_text.setText(text_to_copy.getText().toString());
        final MediaPlayer blip_sound = MediaPlayer.create(this, R.raw.blip);
        text_to_copy.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                copied_text.setText(text_to_copy.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        final Button blip_button = (Button)this.findViewById(R.id.make_sound);
        blip_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                blip_sound.start();
            }
        });

    }


}
