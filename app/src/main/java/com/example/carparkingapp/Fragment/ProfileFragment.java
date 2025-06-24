package com.example.carparkingapp.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carparkingapp.LoginActivity;
import com.example.carparkingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    ImageView imgProfile, imgEditIcon, imgCameraIcon;
    TextView tvName, tvEmail;
    EditText edtName, edtEmail;
    Button btnSave;
    private static final int REQUEST_IMAGE_PICK = 101;
    Uri imageUri;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String loggedInName = currentUser.getDisplayName();
    String loggedInEmail = currentUser.getEmail();


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
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String loggedInName = currentUser.getDisplayName();
            String loggedInEmail = currentUser.getEmail();

            
            tvEmail.setText(loggedInEmail != null ? loggedInEmail : "No Email");

            edtName.setText(loggedInName);
            edtEmail.setText(loggedInEmail);
        }
        imgEditIcon.setOnClickListener(v -> {
            imgEditIcon.setVisibility(View.GONE);
            imgCameraIcon.setVisibility(View.VISIBLE);
            edtName.setVisibility(View.VISIBLE);
            edtEmail.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);

            tvName.setVisibility(View.GONE);
            tvEmail.setVisibility(View.GONE);




        });
        btnSave.setOnClickListener(v -> {

            edtName.setVisibility(View.GONE);
            edtEmail.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            imgCameraIcon.setVisibility(View.GONE);
            imgEditIcon.setVisibility(View.VISIBLE);

            tvName.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.VISIBLE);
        });
        imgCameraIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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