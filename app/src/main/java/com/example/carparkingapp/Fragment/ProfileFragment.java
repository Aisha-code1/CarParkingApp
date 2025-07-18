package com.example.carparkingapp.Fragment;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carparkingapp.LoginActivity;
import com.example.carparkingapp.MyUtil;
import com.example.carparkingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ProfileFragment extends Fragment {

    ImageView imgProfile, imgEditIcon, imgCameraIcon;
    TextView tvName, tvEmail;
    EditText edtName, edtEmail;
    Button btnSave;
    private static final int REQUEST_IMAGE_PICK = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

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
    private void checkCameraPermissionAndCapture() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
           captureImage();
        }
    }

    private void captureImage() {
        File photoFile = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException ex) {
            Toast.makeText(getContext(), "Error while creating image file", Toast.LENGTH_SHORT).show();
            return;
        }

        if (photoFile != null) {
            imageUri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getPackageName() + ".provider",
                    photoFile
            );
            // Grant URI permission
            requireActivity().grantUriPermission(
                    requireContext().getPackageName(),
                    imageUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
            captureImageLauncher.launch(imageUri);
        }
    }

    private void pickImage() {

        pickImageLauncher.launch("image/*");
    }
    private void showImageSourceDialog() {
        String[] options = {"Capture from Camera", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Image Source");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
              checkCameraPermissionAndCapture();
            } else {
                pickImage();
            }
        });
        builder.show();
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

        imgCameraIcon.setOnClickListener(v -> {
            showImageSourceDialog();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(requireActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
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

