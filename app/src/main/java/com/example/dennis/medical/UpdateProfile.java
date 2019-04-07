package com.example.dennis.medical;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {
    private EditText newEmail;
    private EditText newPassword;
    private EditText newFullName, newStaffID, newAge, newHandphone, newHomeAddress, newPosition, newDepartment, newHospital;
    String  fullname,email,password,staffID,age,handphone, homeaddress, clinicianposition, department, hospital;

    private ImageView userProfilePic;

    private Button Updatedata;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private static int PICK_IMAGE = 123;
    Uri ImageUri;
    private String downloadProfileUrl;


    ProgressBar progressBar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            ImageUri = data.getData();
            userProfilePic.setImageURI(ImageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdateProfile.this.getApplicationContext().getContentResolver(), ImageUri);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");

        userProfilePic = (ImageView)findViewById(R.id.userProfilepic) ;

        newEmail = (EditText)findViewById(R.id.loginEmail);
        newPassword = (EditText)findViewById(R.id.loginPassword);
        newFullName = (EditText) findViewById(R.id.userName);
        newStaffID = (EditText) findViewById(R.id.userStaffID);
        newAge = (EditText) findViewById(R.id.userAge);
        newHandphone = (EditText) findViewById(R.id.userHandphone);
        newHomeAddress = (EditText) findViewById(R.id.userHomeAddress);
        newPosition = (EditText) findViewById(R.id.userPosition);
        newDepartment = (EditText) findViewById(R.id.userDepartment);
        newHospital = (EditText) findViewById(R.id.userHospital);

        Updatedata = (Button)findViewById(R.id.updateBtn);

        //progressBar = (ProgressBar) findViewById(R.id.progressbar3);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();


        final StorageReference storageReference = firebaseStorage.getReference();
//        userProfilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
//            }
//        });
        storageReference.child(mAuth.getUid()).child("Images/Profile_Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(userProfilePic);
            }
        });



        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newFullName.setText(userProfile.getuserFullName());
                newEmail.setText(userProfile.getuserEmail());
                newAge.setText(userProfile.getuserAge());
                newDepartment.setText(userProfile.getuserDepartment());
                newHomeAddress.setText(userProfile.getuserHomeAddress());
                newHospital.setText(userProfile.getuserHospital());
                newHandphone.setText(userProfile.getuserHandphone());
                newPosition.setText(userProfile.getuserPosition());
                newStaffID.setText(userProfile.getuserStaffID());
                newPassword.setText(userProfile.getuserPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        Updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = newFullName.getText().toString().trim();
                staffID = newStaffID.getText().toString().trim();
                email = newEmail.getText().toString().trim();
                password = newPassword.getText().toString().trim();
                age = newAge.getText().toString().trim();
                handphone = newHandphone.getText().toString().trim();
                homeaddress = newHomeAddress.getText().toString().trim();
                clinicianposition = newPosition.getText().toString().trim();
                department = newDepartment.getText().toString().trim();
                hospital = newHospital.getText().toString().trim();

//             //update data
//               Intent intent = getIntent();
//               String image_url = intent.getStringExtra("Url_Key");

                String user_id = mAuth.getUid();
//                UserProfile userProfile = new UserProfile(fullname,email,password, staffID, age, handphone, homeaddress,
//                        clinicianposition, department,hospital,user_id);
//                databaseReference.setValue(userProfile);
                HashMap<String, Object>hashMap = new HashMap<>();
                hashMap.put("userId", user_id);
                hashMap.put("userFullName", fullname);
                hashMap.put("userEmail", email);
                hashMap.put("userPassword", password);
                hashMap.put("userStaffID", staffID);
                hashMap.put("userAge", age);
                hashMap.put("userHandphone", handphone);
                hashMap.put("userHomeAddress", homeaddress);
                hashMap.put("userPosition", clinicianposition);
                hashMap.put("userDepartment", department);
                hashMap.put("userHospital", hospital);
                databaseReference.updateChildren(hashMap);

                finish();
                Toast.makeText(UpdateProfile.this, "Update Profile Successful!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
