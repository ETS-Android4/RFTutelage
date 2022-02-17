package com.android.rftutelage.Calendar;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rftutelage.R;
import com.android.rftutelage.Timetable.Fragmenttimetable;
import com.android.rftutelage.Timetable.fragment_timetable_view;
import com.android.rftutelage.ui.CollegeBlog.CollegeBlogDashboard;
import com.bumptech.glide.Glide;


public class calendaritemview extends Fragment {
    public int day;
    public int month;
    public int year;


    public calendaritemview() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putInt("day",day);
                bundle.putInt("month",month);
                bundle.putInt("year",year);
                calendar senditem = new calendar();
                senditem.setArguments(bundle);
                //error found here
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clendaritemview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        String title = bundle.getString("title");
        String location = bundle.getString("location");
        String description = bundle.getString("description");
        String image = bundle.getString("image");
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        day = bundle.getInt("days");
        month = bundle.getInt("months");
        year = bundle.getInt("years");


        Log.d("title", "onViewCreated: "+title+location+description+image+date+time);
        TextView cvtitle = (TextView)view.findViewById(R.id.civtitle);
        TextView cvlocation = (TextView)view.findViewById(R.id.civlocation);
        TextView cvdate = (TextView)view.findViewById(R.id.civdate);
        TextView cvtime = (TextView)view.findViewById(R.id.civtime);
        ImageView cvimage = (ImageView)view.findViewById(R.id.civimage);
        TextView civdescription = (TextView)view.findViewById(R.id.civdescription);

        cvtitle.setText(title);
        cvlocation.setText(location);
        civdescription.setText(description);
        Glide.with(getContext()).load(image).placeholder(R.drawable.diamondshape).into(cvimage);
        cvdate.setText(date);
        cvtime.setText(time);

    }
}