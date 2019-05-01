package com.example.dennis.medical;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextView profileName, profilePosition, profileDepartment, profileHospital;
    private TextView profileEmail,profilestaffID, profileAge, profileHP, profileHomeAddress;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private ImageView profilePic;
    private String downloadProfileUrl;
    ProgressBar progressBar;

    private static int PICK_IMAGE = 123;
    Uri ImageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            ImageUri = data.getData();
            profilePic.setImageURI(ImageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), ImageUri);
                profilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        profilestaffID = view.findViewById(R.id.userStaffID);
        profileEmail = view.findViewById(R.id.userEmail);
        profileName = view.findViewById(R.id.userName);
        profileAge = view.findViewById(R.id.userAge);
        profileDepartment = view.findViewById(R.id.userDepartment);
        profileHomeAddress = view.findViewById(R.id.userHomeAddress);
        profilePosition = view.findViewById(R.id.userPosition);
        profileHospital = view.findViewById(R.id.userHospital);
        profileHP = view.findViewById(R.id.userHP);
        profilePic = view.findViewById(R.id.userProfilePic);
        progressBar = view.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);

        firebaseDatabase = firebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = firebaseStorage.getInstance();

        final StorageReference sendpic = firebaseStorage.getReference();
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        StorageReference displaypic = firebaseStorage.getReference();
        displaypic.child(mAuth.getUid()).child("Images/Profile_Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);

            }
        });
//        firebaseDatabase.getReference().child("Users").child(mAuth.getUid()).child("Profile Picture URL").setValue(firebaseStorage.getReference()
//                .child(mAuth.getUid()).child("/Images/Profile_Pic").getDownloadUrl().toString());



        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText(userProfile.getuserFullName());
                profileEmail.setText(userProfile.getuserEmail());
                profileAge.setText(userProfile.getuserAge());
                profileDepartment.setText(userProfile.getuserDepartment());
                profileHomeAddress.setText(userProfile.getuserHomeAddress());
                profileHospital.setText(userProfile.getuserHospital());
                profileHP.setText(userProfile.getuserHandphone());
                profilePosition.setText(userProfile.getuserPosition());
                profilestaffID.setText(userProfile.getuserStaffID());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        Button buttoneditprofile = (Button)view.findViewById(R.id.btn_edit_profile_button);
        buttoneditprofile.setOnClickListener(this);

        Button uploadprofilepic = (Button)view.findViewById(R.id.btn_uploadpic);
        uploadprofilepic.setOnClickListener(this);

        return view;
    }

    private void imageurl(){
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference sendpic = firebaseStorage.getReference();
        final StorageReference imageReference = sendpic.child(mAuth.getUid()).child("Images").child("Profile_Pic");
//                final StorageReference imageReference = sendpic.child(mAuth.getUid()).child("Images").child(ImageUri.getLastPathSegment() + "jpg");
        final UploadTask uploadTask = imageReference.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getActivity(),"Error: "+ message, Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Image upload Successfully!", Toast.LENGTH_LONG).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        progressBar.setVisibility(View.GONE);
                        downloadProfileUrl = imageReference.getDownloadUrl().toString();
                        return imageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            downloadProfileUrl = task.getResult().toString();
                            firebaseDatabase.getReference().child("Users").child(mAuth.getUid()).child("userImageurl").setValue(downloadProfileUrl);

                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_edit_profile_button:
               startActivity(new Intent(getActivity(), UpdateProfile.class));
            break;

            case R.id.btn_uploadpic:
                imageurl();
                break;
        }

    }


}
