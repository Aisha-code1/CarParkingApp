package com.example.carparkingapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carparkingapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedlocationclient;

    public LocationFragment() {
        // Required empty public constructor
    }
    private final LatLng[] malllocations = {
            new LatLng(32.1618, 74.1883), // King's Mall
            new LatLng(32.1829, 74.2042), // Mall of Gujranwala
            new LatLng(32.1656, 74.1875)
    };
    private final String[] mallNames = {
            "King's Mall",
            "Mall of Gujranwala",
            "G-Town Mall"
    };

    private final String[] mallMapLinks = {
            "https://maps.google.com/?q=32.1618,74.1883",
            "https://maps.google.com/?q=32.1829,74.2042",
            "https://maps.google.com/?q=32.1656,74.1875",

    };
//    public static LocationFragment newInstance(String param1, String param2) {
//        LocationFragment fragment = new LocationFragment();
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
        return inflater.inflate(R.layout.fragment_location, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


        // Marker and Camera move
        LatLng Gujranwala = new LatLng(32.1877, 74.1945);
        mMap.addMarker(new MarkerOptions().position(Gujranwala).title("Parking spot in Gujranwala"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Gujranwala, 15));
    }
}