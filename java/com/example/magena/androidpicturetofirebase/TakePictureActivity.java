package com.example.magena.androidpicturetofirebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.example.magena.androidpicturetofirebase.R.id.img_picture;

/**
 * Created by Magena on 3/6/2017.
 */

public class TakePictureActivity extends Activity {

    private ImageView image_picture;
    private Button btn_delete;
    private Button btn_identify;
    private String picturePath;
    private static int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_identify = (Button) findViewById(R.id.btn_identify);
        image_picture = (ImageView) findViewById(img_picture);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://resight-d6b9c.appspot.com");

        mProgress = new ProgressDialog(this);
        picturePath = "";
        takePicture();

        btn_identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picturePath != ""){
                    // send current picture

                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete the picture
            }
        });

    }



    private void takePicture(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAMERA_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            mProgress.setMessage("Uploading image...");
            mProgress.show();
            Uri uri = data.getData();
            StorageReference filepath = mStorageRef.child("Photos").child("new photo");
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TakePictureActivity.this, "Uploading Finished", Toast.LENGTH_LONG).show();
                    mProgress.dismiss();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(TakePictureActivity.this)
                            .load(downloadUri)
                            .fit().centerCrop()
                            .rotate(90f)
                            .into(image_picture);
                    //sendNotificationToUser("Test", "Hi there!");


                }
            });


        }


    }
/*
    public static void sendNotificationToUser(String user, final String message) {
        Firebase ref = new Firebase(FIREBASE_URL);
        final Firebase notifications = ref.child("notificationRequests");

        Map notification = new HashMap<>();
        notification.put("username", user);
        notification.put("message", message);

        notifications.push().setValue(notification);
    }
    */
}
