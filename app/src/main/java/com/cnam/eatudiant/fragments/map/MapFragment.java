package com.cnam.eatudiant.fragments.map;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.cnam.eatudiant.Config;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.databinding.FragmentMapBinding;
import com.cnam.eatudiant.view.HomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final int DEFAULT_ZOOM = 15;
    private FragmentMapBinding binding;
    // private GoogleMap map;
    private final LatLng defaultLocation = new LatLng(49.258329, 4.031696);
    private SupportMapFragment mapFragment;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        // assert mapFragment != null;
        if (mapFragment != null) {
            Log.i(Config.LOG_TAG, "mapFragment not null");
            mapFragment.getMapAsync(this);
        }

        // Construct a FusedLocationProviderClient to find the device's last-known location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        // Initialize the SDK
        // Places.initialize(this.requireContext(), BuildConfig.MAPS_API_KEY);

        // Create a new PlacesClient instance
        // PlacesClient placesClient = Places.createClient(this.requireContext());

        return root;
    }

    private HomeActivity getHomeActivity() {
        return (HomeActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap map) {
        map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(0, 0))
                        .title("Marker")
        );
        getHomeActivity().getLocationPermission();
        // Turn on the MyLocation layer and the related control on the map.
        updateLocationUI(map);
        // Get the current location of the device and set the position of the map.
        getDeviceLocation(map);
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI(GoogleMap map) {
        Log.i(Config.LOG_TAG, "updateLocationUI");
        if (map == null) {
            return;
        }
        try {
            if (getHomeActivity().isLocationPermissionGranted()) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getHomeActivity().getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation(GoogleMap map) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        Log.i(Config.LOG_TAG, "getDeviceLocation");
        try {
            if (getHomeActivity().isLocationPermissionGranted()) {
                Log.i(Config.LOG_TAG, "getDeviceLocation_location_granted");
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                                new LatLng(
                                                        lastKnownLocation.getLatitude(),
                                                        lastKnownLocation.getLongitude()
                                                ),
                                                DEFAULT_ZOOM
                                        )
                                );
                            }
                        } else {
                            Log.d(Config.LOG_TAG, "Current location is null. Using defaults.");
                            Log.e(Config.LOG_TAG, "Exception: %s", task.getException());
                            map.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM)
                            );
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e(Config.LOG_TAG, "Exception: %s ", e);
        }
    }

}
