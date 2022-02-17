package com.android.rftutelage.ui.CollegeBlog.College;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rftutelage.Calendar.calendar;
import com.android.rftutelage.R;
import com.android.rftutelage.ui.CollegeBlog.CollegeBlogDashboard;
import com.bumptech.glide.Glide;

public class collegeblogdatailedview extends Fragment {

    public collegeblogdatailedview() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putInt("number",0);
                CollegeBlogDashboard senditem = new CollegeBlogDashboard();
                senditem.setArguments(bundle);
                //error found here
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collegeblogdatailedview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Bundle bundle = this.getArguments();
        String title = bundle.getString("blogtitle");
        String date = bundle.getString("blogdate");
        String description = bundle.getString("blogdescription");
        String image = bundle.getString("blogimage");
        String image1 = bundle.getString("blogimage1");
        String image2 = bundle.getString("blogimage2");

        TextView cbdvtitle = (TextView)view.findViewById(R.id.cbdvtitle);
        TextView cbdvdate = (TextView)view.findViewById(R.id.cbdvdate);
        ImageView cbdvimage = (ImageView)view.findViewById(R.id.cbdvimage);
        TextView cbdvdescription = (TextView)view.findViewById(R.id.cbdvdescription);
        ImageView cbdvimage1 = (ImageView)view.findViewById(R.id.cbdvimage1);
        ImageView cbdvimage2 = (ImageView)view.findViewById(R.id.cbdvimage2);


        cbdvtitle.setText(title);
        cbdvdate.setText(date);
        cbdvdescription.setText(description);
        Glide.with(getContext()).load(image).placeholder(R.drawable.diamondshape).into(cbdvimage);
        Glide.with(getContext()).load(image1).placeholder(R.drawable.diamondshape).into(cbdvimage1);
        Glide.with(getContext()).load(image2).placeholder(R.drawable.diamondshape).into(cbdvimage2);


    }
}