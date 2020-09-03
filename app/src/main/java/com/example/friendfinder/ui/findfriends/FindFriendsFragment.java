package com.example.friendfinder.ui.findfriends;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.friendfinder.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class FindFriendsFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 101;
    private FindFriendsViewModel findFriendsViewModel;
    private View root;
    private FusedLocationProviderClient fusedLocationClient;

    //Places mGeoDataClient, mPlaceDetectionClient;
    private GoogleMap map;
    private MapView mapView;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private Task<Location> locationResult;
    private MarkerOptions markerOptions;
    private LatLng latLng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        fetchLastLocation();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        findFriendsViewModel =
                ViewModelProviders.of(this).get(FindFriendsViewModel.class);
        root = inflater.inflate(R.layout.fragment_findfriends, container, false);

        mapView = root.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(FindFriendsFragment.this);
        }

        return root;

    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Providing more details to explain the users why location is needed

                new AlertDialog.Builder(this.requireContext())
                        .setTitle("Required Location Permission")
                        .setMessage("Just do it man!!!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(requireActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed; Request the permission
                // REQUEST_CODE is an app-defined int constant
                // The callback method gets the result of the request

                ActivityCompat.requestPermissions(this.requireActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE);
            }
        } else {
            // Accessing the last known location
            // Changing the camera position
            // Adding Marker

            locationResult = fusedLocationClient.getLastLocation();
            locationResult.addOnCompleteListener(this.requireActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> locationTask) {
                    if (locationTask.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = locationTask.getResult();
                        if (mLastKnownLocation != null && map != null) {
                            latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            map.addMarker(markerOptions);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(requireContext());

        map = googleMap;

        map.getUiSettings().setMyLocationButtonEnabled(true);

        fetchLastLocation();

        updateLocationUI();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
                break;
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                fetchLastLocation();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", Objects.requireNonNull(e.getMessage()));
        }
    }
}