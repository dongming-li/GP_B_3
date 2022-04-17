package com.example.jffalat.rateyourdriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private EditText comments;
    private Button submit;
    private String commentText;
    private double rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comments = (EditText) findViewById(R.id.commentText);
        addListenerOnRatingBar();
        addListenerOnSubmitButton();
    }


    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.ratingText);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));

            }
        });
    }

    public void addListenerOnSubmitButton() {

        submit = (Button) findViewById(R.id.submit_button);
        comments = (EditText) findViewById(R.id.commentText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                commentText = comments.getText().toString();
                rating = ratingBar.getRating();
                //TODO Send string to database

                Toast toast = Toast.makeText(MainActivity.this, "Thank you for submitting your rating!", Toast.LENGTH_LONG);
                toast.show();
            }

        });
    }

}
