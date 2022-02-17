package com.android.rftutelage.Timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.rftutelage.R;

public class Fragmenttimetable extends Fragment {

    public Fragmenttimetable(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_timetable,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView monday = view.findViewById(R.id.Monday);
        CardView tuesday = view.findViewById(R.id.Tuesday);
        CardView wednesday = view.findViewById(R.id.Wednesday);
        CardView thursday = view.findViewById(R.id.Thursday);
        CardView friday = view.findViewById(R.id.Friday);
        CardView saturday = view.findViewById(R.id.Saturday);

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","MONDAY");
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                fragment_timetable_view.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment_timetable_view).commit();

            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","TUESDAY");
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                fragment_timetable_view.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment_timetable_view).commit();

            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","WEDNESDAY");
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                fragment_timetable_view.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment_timetable_view).commit();

            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","THURSDAY");
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                fragment_timetable_view.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment_timetable_view).commit();

            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","FRIDAY");
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                fragment_timetable_view.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment_timetable_view).commit();

            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","SATURDAY");
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                fragment_timetable_view.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment_timetable_view).commit();

            }
        });

    }
}
