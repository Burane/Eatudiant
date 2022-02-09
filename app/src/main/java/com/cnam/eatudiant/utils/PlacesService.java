package com.cnam.eatudiant.utils;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;
import com.cnam.eatudiant.BuildConfig;
import com.cnam.eatudiant.Config;
import com.cnam.eatudiant.view.HomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlacesService {
    public static final int M_MAX_ENTRIES = 5;
    public static final int DEFAULT_ZOOM = 15;

    private HomeActivity activity;

    private Location lastKnownLocation;
    private final LatLng defaultLocation = new LatLng(49.258329, 4.031696);
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private GoogleMap map;

    public PlacesService(HomeActivity activity, GoogleMap map) {
        this.map = map;
        this.activity = activity;
        // Construct a FusedLocationProviderClient to find the device's last-known location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        // Initialize the SDK
        Places.initialize(activity.getApplicationContext(), BuildConfig.MAPS_API_KEY);
        // Create a new PlacesClient instance
        placesClient = Places.createClient(activity.getApplicationContext());
    }

    /**
     * Get a google place with his id
     * @param placeId google place id
     * @param placeFields place fields to populate in result
     * @return a place with fields
     */
    public Place getPlaceWithId(String placeId, List<Place.Field> placeFields) {
        Log.i(Config.LOG_TAG, "getPlaceWithId");
        final Place[] place = {null};
        FetchPlaceRequest request =
                FetchPlaceRequest.newInstance(placeId, placeFields);

        Task<FetchPlaceResponse> placeResponse = placesClient.fetchPlace(request);

        placeResponse.addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                // cannot update var normally in lambda -> need to trick by setting a
                // one element array
                place[0] = task.getResult().getPlace();
            }
        });
        return place[0];
    }

    public LatLng getDefaultLocation() {
        return defaultLocation;
    }

    public Location getDeviceLocation() {
        refreshLastKnownLocation();
        return lastKnownLocation;
    }

    @SuppressLint("MissingPermission")
    public void refreshLastKnownLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        Log.i(Config.LOG_TAG, "refreshLastKnownLocation");
        try {
            if (activity.isLocationPermissionGranted()) {
                Log.i(Config.LOG_TAG, "refreshLastKnownLocation_location_granted");
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(activity, task -> {
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
                                            PlacesService.DEFAULT_ZOOM
                                    )
                            );
                        }
                    } else {
                        Log.d(Config.LOG_TAG, "Current location is null. Using defaults.");
                        Log.e(Config.LOG_TAG, "exception", task.getException());
                        map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(defaultLocation, PlacesService.DEFAULT_ZOOM)
                        );
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e(Config.LOG_TAG, "Exception: " + e.getMessage(), e);
        }
    }

    /**
     * Provide a list of google places with given place fields base on filter
     * @param typeFilters type to filter result
     * @param placeFields fields to populate places result
     * @return multiple places with fields
     */
    public Place[] getPlacesWithTypeFilter(List<TypeFilter> typeFilters, List<Place.Field> placeFields) {
        Log.i(Config.LOG_TAG, "getPlacesWithTypeFilter");
        final Place[][] place = {{null}};

        FindAutocompletePredictionsRequest.Builder requestBuilder =
                FindAutocompletePredictionsRequest
                        .builder()
                        .setOrigin(getLastLocLatLng());
        typeFilters.forEach(requestBuilder::setTypeFilter);

        FindAutocompletePredictionsRequest predictionsRequest = requestBuilder.build();

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        Task<FindAutocompletePredictionsResponse> placeResult =
                placesClient.findAutocompletePredictions(predictionsRequest);
        placeResult.addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Log.i(Config.LOG_TAG, "getPlacesWithTypeFilter : " + "places fetched");
                List<AutocompletePrediction> likelyPlaces = task.getResult().getAutocompletePredictions();
                place[0] = new Place[likelyPlaces.size()];
                int i = 0;
                for (AutocompletePrediction placeLikelihood : likelyPlaces) {
                    // cannot update var normally in lambda -> need to trick by setting a
                    // one element array
                    place[0][i] = getPlaceWithId(placeLikelihood.getPlaceId(), placeFields);
                }
            } else {
                Log.e(Config.LOG_TAG, "Exception: %s", task.getException());
            }
        });
        return place[0];
    }

    @NotNull
    private LatLng getLastLocLatLng() {
        return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
    }
}
