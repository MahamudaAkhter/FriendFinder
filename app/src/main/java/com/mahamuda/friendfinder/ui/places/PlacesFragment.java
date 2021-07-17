package com.mahamuda.friendfinder.ui.places;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mahamuda.friendfinder.R;

public class PlacesFragment extends Fragment {

    private PlacesViewModel placesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placesViewModel =
                new ViewModelProvider(this).get(PlacesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_places, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
        placesViewModel.getText().observe(getViewLifecycleOwner(), s -> {
//            textView.setText(s);
        });

        return root;
    }
}