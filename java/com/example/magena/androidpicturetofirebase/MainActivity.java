package com.example.magena.androidpicturetofirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_TakePicture;
    Button btn_ReviewGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_TakePicture = (Button) findViewById(R.id.btn_takePicture);
        btn_ReviewGallery = (Button) findViewById(R.id.btn_ReviewGallery);

        btn_TakePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TakePictureActivity.class));
            }
        });

        /*
        btn_ReviewGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReviewGalleryActivity.class));
            }
        });
        */

    }
}
