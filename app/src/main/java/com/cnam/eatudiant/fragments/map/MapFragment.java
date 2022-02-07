package com.cnam.eatudiant.fragments.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.databinding.FragmentMapBinding;
import com.cnam.eatudiant.view.HomeActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import org.jetbrains.annotations.NotNull;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentMapBinding binding;
    private GoogleMap map;
    private SupportMapFragment mapFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapFragment = (SupportMapFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
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
    }
/*
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (getHomeActivity().isLocationPermissionGranted()) {
                // map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                // map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                getHomeActivity().getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    */
}
