package com.cnam.eatudiant.fragments.map;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.BuildConfig;
import com.cnam.eatudiant.Config;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.databinding.FragmentMapBinding;
import com.cnam.eatudiant.utils.PlacesService;
import com.cnam.eatudiant.view.HomeActivity;
import com.cnam.eatudiant.view.RegisterActivity;
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
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding4.view.RxView;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;
    private GoogleMap map;

    @BindView(R.id.places_button)
    FloatingActionButton placesButton;

    private FragmentMapBinding binding;
    private final LatLng defaultLocation = new LatLng(49.258329, 4.031696);
    private PlacesClient placesClient;
    private PlacesService placesService;
    private SupportMapFragment mapFragment;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ButterKnife.bind(this, root);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            Log.i(Config.LOG_TAG, "mapFragment not null");
            mapFragment.getMapAsync(this);
        }

        // Construct a FusedLocationProviderClient to find the device's last-known location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        // Initialize the SDK
        Places.initialize(this.requireContext(), BuildConfig.MAPS_API_KEY);

        // Create a new PlacesClient instance
        placesClient = Places.createClient(this.requireContext());

        // handle click on places button
        placesButton.setOnClickListener(view -> {
            Log.i(Config.LOG_TAG, "places_clicked");
            this.showCurrentPlace();
        });

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
    public void onMapReady(@NonNull GoogleMap map) {
        this.map = map;
        this.placesService = new PlacesService(getHomeActivity(), map);
        placesService.refreshLastKnownLocation();
        map.addMarker(
                new MarkerOptions()
                        .position(placesService.getDefaultLocation())
                        .title("Position")
        );
        getHomeActivity().getLocationPermission();
        // Turn on the MyLocation layer and the related control on the map.
        updateLocationUI(map);
        // Get the current location of the device and set the position of the map.
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

    private void openPlacesDialog(Place[] places) {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener = (dialog, which) -> {
            // The "which" argument contains the position of the selected item.
            LatLng markerLatLng = places[which].getLatLng();
            String markerSnippet = places[which].getAddress();
            if (likelyPlaceAttributions[which] != null) {
                markerSnippet = markerSnippet + "\n" + places[which].getAttributions();
            }

            // Add a marker for the selected place, with an info window
            // showing information about that place.
            map.addMarker(new MarkerOptions()
                    .title(places[which].getName())
                    .position(markerLatLng)
                    .snippet(markerSnippet));

            // Position the map's camera at the location of the marker.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                    PlacesService.DEFAULT_ZOOM));
        };
        String[] names = (String[]) Arrays
                .stream(places)
                .map(Place::getName)
                .toArray();
        // Display the dialog.
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.pick_place)
                .setItems(names, listener)
                .show();
    }


    private void showCurrentPlace() {
        if (map == null) {
            return;
        }

        if (getHomeActivity().isLocationPermissionGranted()) {
            // Use fields to define the data types to return.
            List<Place.Field> placeFields = Arrays.asList(
                    Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.LAT_LNG, Place.Field.TYPES
            );
            List<TypeFilter> placeFilters = Collections.singletonList(TypeFilter.ESTABLISHMENT);

            Place[] places = placesService.getPlacesWithTypeFilter(placeFilters, placeFields);
            // Place[] places = placesService.getCurrentPlaces(placeFields);
            Log.d(Config.LOG_TAG, "places_array : " + Arrays.toString(places));
            openPlacesDialog(
                    places
            );
        } else {
            // The user has not granted permission.
            Log.i(Config.LOG_TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            map.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_marker_title))
                    .position(defaultLocation)
                    .snippet("default_info_snippet"));

            // Prompt the user for permission.
            getHomeActivity().getLocationPermission();
        }
    }

}
