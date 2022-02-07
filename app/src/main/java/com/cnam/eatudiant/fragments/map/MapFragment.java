package com.cnam.eatudiant.fragments.map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import com.cnam.eatudiant.databinding.FragmentMapBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GalleryViewModel galleryViewModel;
    private FragmentMapBinding binding;
    private FragmentActivity contextActivity;
    // private SupportMapFragment mapFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Construct a PlacesClient
        // Places.initialize(contextActivity.getApplicationContext()

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentActivity){
            contextActivity = (FragmentActivity) context;
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(0, 0))
                        .title("Marker")
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
