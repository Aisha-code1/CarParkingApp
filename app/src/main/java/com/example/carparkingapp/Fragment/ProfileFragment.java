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
    Button btnSave;
    private static final int REQUEST_IMAGE_PICK = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private Uri imageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userRole = "user";   // default

    public ProfileFragment() {}

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

        // ----- Load Profile -----
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) return;

                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String vehicleType = snapshot.child("vehicleType").getValue(String.class);
                        String vehicleNumber = snapshot.child("vehicleNumber").getValue(String.class);
                        String contact = snapshot.child("contact").getValue(String.class);

                        if (snapshot.hasChild("role")) {
                            userRole = snapshot.child("role").getValue(String.class);
                        }

                        // ---- Show according to role ----
                        tvName.setText(name);
                        tvEmail.setText(email);
                        edtName.setText(name);
                        edtEmail.setText(email);

                        if ("admin".equalsIgnoreCase(userRole)) {
                            // hide vehicle fields for admin
                            tvVehicleType.setVisibility(View.GONE);
                            tvVehicleNumber.setVisibility(View.GONE);
                            tvContact.setVisibility(View.GONE);

                            edtVehicleType.setVisibility(View.GONE);
                            edtVehicleNumber.setVisibility(View.GONE);
                            edtContact.setVisibility(View.GONE);
                        } else {
                            // show vehicle fields for user
                            tvVehicleType.setText(vehicleType);
                            tvVehicleNumber.setText(vehicleNumber);
                            tvContact.setText(contact);

                            edtVehicleType.setText(vehicleType);
                            edtVehicleNumber.setText(vehicleNumber);
                            edtContact.setText(contact);
                        }

                        // load profile image
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

        // 🔹 Edit aur Save button ka code tumhara hi same chalega
        // bs jab role = admin ho to vehicle wale edit/save skip kar do

        btnSave.setOnClickListener(v -> {
            String updatedName = edtName.getText().toString().trim();
            String updatedEmail = edtEmail.getText().toString().trim();

            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("name").setValue(updatedName);

            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("email").setValue(updatedEmail);

            if (!"admin".equalsIgnoreCase(userRole)) {
                String updatedVehicleType = edtVehicleType.getText().toString().trim();
                String updatedVehicleNumber = edtVehicleNumber.getText().toString().trim();
                String updatedContact = edtContact.getText().toString().trim();

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("vehicleType").setValue(updatedVehicleType);

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("vehicleNumber").setValue(updatedVehicleNumber);

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("contact").setValue(updatedContact);
            }

            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
