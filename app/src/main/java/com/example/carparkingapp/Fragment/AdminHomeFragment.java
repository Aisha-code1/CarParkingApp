package com.example.carparkingapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carparkingapp.ManageSlotActivity;
import com.example.carparkingapp.R;
import com.example.carparkingapp.ViewBookingActivity;
import com.example.carparkingapp.ViewFeedbackActivity;


public class AdminHomeFragment extends Fragment {


    public AdminHomeFragment() {
        // Required empty public constructor
    }

//    public static AdminHomeFragment newInstance(String param1, String param2) {
//        AdminHomeFragment fragment = new AdminHomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//

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
        View view = inflater.inflate(R.layout.fragment_adminhome, container, false);
        CardView manageSlot = view.findViewById(R.id.manageSlot);
        CardView viewBooking = view.findViewById(R.id.viewBooking);
        CardView viewFeedback = view.findViewById(R.id.viewFeedback);


        manageSlot.setOnClickListener(v -> {
            startActivity(new Intent(getContext(),ManageSlotActivity.class));
        });
        viewBooking.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ViewBookingActivity.class));
        });
       viewFeedback.setOnClickListener(v -> {
           startActivity(new Intent(getContext(), ViewFeedbackActivity.class ));
       });

        return view;
    }
}