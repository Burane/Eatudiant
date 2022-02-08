package com.cnam.eatudiant.fragments.map;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.cnam.eatudiant.BuildConfig;
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
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final int DEFAULT_ZOOM = 15;
    private static final int M_MAX_ENTRIES = 5;
    private FragmentMapBinding binding;
    private GoogleMap map;
    private final LatLng defaultLocation = new LatLng(49.258329, 4.031696);
    PlacesClient placesClient;
    private SupportMapFragment mapFragment;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;

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
        Places.initialize(this.requireContext(), BuildConfig.MAPS_API_KEY);

        // Create a new PlacesClient instance
        placesClient = Places.createClient(this.requireContext());

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
        this.map = map;
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

    private void openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener = (dialog, which) -> {
            // The "which" argument contains the position of the selected item.
            LatLng markerLatLng = likelyPlaceLatLngs[which];
            String markerSnippet = likelyPlaceAddresses[which];
            if (likelyPlaceAttributions[which] != null) {
                markerSnippet = markerSnippet + "\n" + likelyPlaceAttributions[which];
            }

            // Add a marker for the selected place, with an info window
            // showing information about that place.
            map.addMarker(new MarkerOptions()
                    .title(likelyPlaceNames[which])
                    .position(markerLatLng)
                    .snippet(markerSnippet));

            // Position the map's camera at the location of the marker.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                    DEFAULT_ZOOM));
        };

        // Display the dialog.
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.pick_place)
                .setItems(likelyPlaceNames, listener)
                .show();
    }


    private void showCurrentPlace() {
        if (map == null) {
            return;
        }

        if (getHomeActivity().isLocationPermissionGranted()) {
            // Use fields to define the data types to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.LAT_LNG);
            // Use the builder to create a FindCurrentPlaceRequest.
            FindCurrentPlaceRequest request =
                    FindCurrentPlaceRequest.newInstance(placeFields);

            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final
            Task<FindCurrentPlaceResponse> placeResult =
                    placesClient.findCurrentPlace(request);
            placeResult.addOnCompleteListener (new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        FindCurrentPlaceResponse likelyPlaces = task.getResult();

                        // Set the count, handling cases where less than 5 entries are returned.
                        int count;
                        if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                            count = likelyPlaces.getPlaceLikelihoods().size();
                        } else {
                            count = M_MAX_ENTRIES;
                        }

                        int i = 0;
                        likelyPlaceNames = new String[count];
                        likelyPlaceAddresses = new String[count];
                        likelyPlaceAttributions = new List[count];
                        likelyPlaceLatLngs = new LatLng[count];

                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
                            // Build a list of likely places to show the user.
                            likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
                            likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
                            likelyPlaceAttributions[i] = placeLikelihood.getPlace().getAttributions();
                            likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                            i++;
                            if (i > (count - 1)) {
                                break;
                            }
                        }

                        // Show a dialog offering the user the list of likely places, and add a
                        // marker at the selected place.
                        openPlacesDialog();
                    }
                    else {
                        Log.e(Config.LOG_TAG, "Exception: %s", task.getException());
                    }
                }
            });
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
