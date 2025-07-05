package com.example.carparkingapp.Fragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carparkingapp.LoginActivity;
import com.example.carparkingapp.Manage;
import com.example.carparkingapp.MyUtil;
import com.example.carparkingapp.R;
import com.example.carparkingapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;


public class ProfileFragment extends Fragment {

    ImageView imgProfile, imgEditIcon, imgCameraIcon;
    TextView tvName, tvEmail;
    EditText edtName, edtEmail;
    Button btnSave;
    private static final int REQUEST_IMAGE_PICK = 101;
    private Uri imageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();
    String uid = user.getUid();
    private final ActivityResultLauncher<Uri> captureImageLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        //handle capture image
                        if (imageUri != null) {
                            //do something with the capture image
                            imgProfile.setImageURI(imageUri);
                            uploadImageToFirebase(imageUri);
                        }
                    }
                }
            });

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        imgProfile.setImageURI(result);
                         uploadImageToFirebase(result);
                          }
                }
            });

    private void captureImage() {
        imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        captureImageLauncher.launch(imageUri);
    }

    private void pickImage() {
        pickImageLauncher.launch("image/*");
    }
    private void uploadImageToFirebase(Uri uri) {
        String imageString = MyUtil.imageUriToBase64(uri, requireActivity().getContentResolver());
        if (imageString != null) {
            String authId = FirebaseAuth.getInstance().getUid();
            FirebaseDatabase.getInstance().getReference("Images")
                    .child(authId)
                    .setValue(imageString);
        } else {
            Toast.makeText(getContext(), "Image conversion failed", Toast.LENGTH_SHORT).show();
        }
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


//    public static ProfileFragment newInstance(String param1, String param2) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        imgProfile = view.findViewById(R.id.iv_profile);
        imgEditIcon = view.findViewById(R.id.iv_edit);
        imgCameraIcon = view.findViewById(R.id.iv_camera);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        edtName = view.findViewById(R.id.edt_name);
        edtEmail = view.findViewById(R.id.edt_email);
        btnSave = view.findViewById(R.id.btn_save);

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getUid())
//                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        tvName.setText(name);
                        tvEmail.setText(email);
                        edtName.setText(name);
                        FirebaseDatabase.getInstance().getReference("Images")
                                .child(FirebaseAuth.getInstance().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String base64Image = snapshot.getValue(String.class);
                                        if (base64Image != null) {
                                            Bitmap bitmap = MyUtil.base64ToBitmap(base64Image);
                                            if (bitmap != null) {
                                                imgProfile.setImageBitmap(bitmap);
                                            } else {
                                                Toast.makeText(getContext(), "Invalid image data", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getContext(), "Failed to load profile image", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error loading profile", Toast.LENGTH_SHORT).show();
                    }
                });


        imgEditIcon.setOnClickListener(v -> {
            imgEditIcon.setVisibility(View.GONE);
            imgCameraIcon.setVisibility(View.VISIBLE);
            edtName.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.GONE);
            tvEmail.setVisibility(View.GONE);
        });

        imgCameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            //    pickImage();
            }


           });


        btnSave.setOnClickListener(v -> {
            String updatedName = edtName.getText().toString().trim();

            if (updatedName.isEmpty()) {
                edtName.setError("Name cannot be empty");
                return;

            }

            FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("name").setValue(updatedName)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Name updated successfully", Toast.LENGTH_SHORT).show();
                        tvName.setText(updatedName);
                        edtName.setVisibility(View.GONE);
                        btnSave.setVisibility(View.GONE);
                        imgCameraIcon.setVisibility(View.GONE);
                        imgEditIcon.setVisibility(View.VISIBLE);
                        tvName.setVisibility(View.VISIBLE);
                        tvEmail.setVisibility(View.VISIBLE);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to update name", Toast.LENGTH_SHORT).show();
                    });
        });


        Button logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}

