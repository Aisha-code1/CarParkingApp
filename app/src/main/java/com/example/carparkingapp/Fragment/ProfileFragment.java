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
    TextView tvName, tvEmail, tvVehicleType, tvVehicleNumber, tvContact;
    EditText edtName, edtEmail, edtVehicleType, edtVehicleNumber, edtContact;
    Button btnSave, logoutButton;

    private static final int REQUEST_IMAGE_PICK = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private Uri imageUri;
    private String role; // "admin" or "user"

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private final ActivityResultLauncher<Uri> captureImageLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
                if (result && imageUri != null) {
                    imgProfile.setImageURI(imageUri);
                    uploadImageToFirebase(imageUri);
                }
            });

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
                if (result != null) {
                    imgProfile.setImageURI(result);
                    uploadImageToFirebase(result);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = view.findViewById(R.id.iv_profile);
        imgEditIcon = view.findViewById(R.id.iv_edit);
        imgCameraIcon = view.findViewById(R.id.iv_camera);

        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvVehicleType = view.findViewById(R.id.tvVehicleType);
        tvVehicleNumber = view.findViewById(R.id.tvVehicleNumber);
        tvContact = view.findViewById(R.id.tvContact);

        edtName = view.findViewById(R.id.edt_name);
        edtEmail = view.findViewById(R.id.edt_email);
        edtVehicleType = view.findViewById(R.id.edttype);
        edtVehicleNumber = view.findViewById(R.id.edtno);
        edtContact = view.findViewById(R.id.edtcontact);

        btnSave = view.findViewById(R.id.btn_save);
        logoutButton = view.findViewById(R.id.btn_logout);

        // ----- Load Role & Profile -----
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        role = snapshot.child("role").getValue(String.class);

                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String vehicleType = snapshot.child("vehicleType").getValue(String.class);
                        String vehicleNumber = snapshot.child("vehicleNumber").getValue(String.class);
                        String contact = snapshot.child("contact").getValue(String.class);

                        tvName.setText(name);
                        tvEmail.setText(email);
                        tvVehicleType.setText(vehicleType);
                        tvVehicleNumber.setText(vehicleNumber);
                        tvContact.setText(contact);

                        edtName.setText(name);
                        edtEmail.setText(email);
                        edtVehicleType.setText(vehicleType);
                        edtVehicleNumber.setText(vehicleNumber);
                        edtContact.setText(contact);

                        // Load profile image
                        FirebaseDatabase.getInstance().getReference("Images")
                                .child(user.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String base64Image = snapshot.getValue(String.class);
                                        if (base64Image != null) {
                                            Bitmap bitmap = MyUtil.base64ToBitmap(base64Image);
                                            if (bitmap != null) imgProfile.setImageBitmap(bitmap);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });

                        setupUIByRole();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        // ----- Logout -----
        logoutButton.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(requireActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
        });

        return view;
    }

    private void setupUIByRole() {
        if ("admin".equals(role)) {
            // Admin sees only name, no edit
            edtName.setVisibility(View.GONE);
            edtEmail.setVisibility(View.GONE);
            edtVehicleType.setVisibility(View.GONE);
            edtVehicleNumber.setVisibility(View.GONE);
            edtContact.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            imgCameraIcon.setVisibility(View.GONE);
            imgEditIcon.setVisibility(View.GONE);

            tvName.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.GONE);
            tvVehicleType.setVisibility(View.GONE);
            tvVehicleNumber.setVisibility(View.GONE);
            tvContact.setVisibility(View.GONE);

        } else {
            // User sees full editable profile
            tvEmail.setVisibility(View.VISIBLE);
            tvVehicleType.setVisibility(View.VISIBLE);
            tvVehicleNumber.setVisibility(View.VISIBLE);
            tvContact.setVisibility(View.VISIBLE);

            imgEditIcon.setVisibility(View.VISIBLE);
            imgEditIcon.setOnClickListener(v -> enableEditing());
            imgCameraIcon.setOnClickListener(v -> showImageSourceDialog());
            btnSave.setOnClickListener(v -> saveProfile());
        }
    }

    private void enableEditing() {
        imgEditIcon.setVisibility(View.GONE);
        imgCameraIcon.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);

        edtName.setVisibility(View.VISIBLE);
        edtEmail.setVisibility(View.VISIBLE);
        edtVehicleType.setVisibility(View.VISIBLE);
        edtVehicleNumber.setVisibility(View.VISIBLE);
        edtContact.setVisibility(View.VISIBLE);

        tvName.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);
        tvVehicleType.setVisibility(View.GONE);
        tvVehicleNumber.setVisibility(View.GONE);
        tvContact.setVisibility(View.GONE);
    }

    private void saveProfile() {
        String updatedName = edtName.getText().toString().trim();
        String updatedEmail = edtEmail.getText().toString().trim();
        String updatedVehicleType = edtVehicleType.getText().toString().trim();
        String updatedVehicleNumber = edtVehicleNumber.getText().toString().trim();
        String updatedContact = edtContact.getText().toString().trim();

        if (updatedName.isEmpty()) {
            edtName.setError("Name cannot be empty");
            return;
        }

        FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("name").setValue(updatedName);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("email").setValue(updatedEmail);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("vehicleType").setValue(updatedVehicleType);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("vehicleNumber").setValue(updatedVehicleNumber);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("contact").setValue(updatedContact)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    setupUIByRole(); // refresh UI
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                });
    }

    private void showImageSourceDialog() {
        String[] options = {"Capture from Camera", "Choose from Gallery"};
        new AlertDialog.Builder(getContext())
                .setTitle("Select Image Source")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) checkCameraPermissionAndCapture();
                    else pickImage();
                }).show();
    }

    private void pickImage() {
        pickImageLauncher.launch("image/*");
    }

    private void checkCameraPermissionAndCapture() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else captureImage();
    }

    private void captureImage() {
        File photoFile;
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
            requireActivity().grantUriPermission(
                    requireContext().getPackageName(),
                    imageUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
            captureImageLauncher.launch(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri uri) {
        String imageString = MyUtil.imageUriToBase64(uri, requireActivity().getContentResolver());
        if (imageString != null) {
            FirebaseDatabase.getInstance().getReference("Images")
                    .child(user.getUid())
                    .setValue(imageString);
        } else {
            Toast.makeText(getContext(), "Image conversion failed", Toast.LENGTH_SHORT).show();
        }
    }
}
