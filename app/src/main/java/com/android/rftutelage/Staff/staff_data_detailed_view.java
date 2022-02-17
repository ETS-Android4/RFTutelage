package com.android.rftutelage.Staff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Faculty.faculty_data_adapter;
import com.android.rftutelage.Subjects.subjects_menu;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

public class staff_data_detailed_view extends Fragment {

    TextView fsddvname,fsddvpost,fsddvqualification,fsddvcoursestaught,fsddvareaofexpertise,fsddvemail,fsddvphonenumber,fsddvacademicexperience;
    CircularImageView fsddvphoto;
    ImageView fsddvphonecall;

    public staff_data_detailed_view() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new fragment_staff_view()).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff_data_detailed_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fsddvname = (TextView) view.findViewById(R.id.fsddvname);
        fsddvpost = (TextView) view.findViewById(R.id.fsddvpost);
        fsddvqualification = (TextView) view.findViewById(R.id.fsddvqualification);
        fsddvcoursestaught = (TextView) view.findViewById(R.id.fsddvcoursestaught);
        fsddvareaofexpertise = (TextView) view.findViewById(R.id.fsddvareaofexpertise);
        fsddvacademicexperience = (TextView) view.findViewById(R.id.fsddvacademicexperience);
        fsddvemail = (TextView) view.findViewById(R.id.fsddvemail);
        fsddvphonenumber = (TextView) view.findViewById(R.id.fsddvphone_number);
        fsddvphoto = (CircularImageView) view.findViewById(R.id.fsddvphoto);
        fsddvphonecall = (ImageView) view.findViewById(R.id.fsddvphonecall);

        Bundle bundle = this.getArguments();
        String faculty_name = bundle.getString("faculty_name");
        String faculty_image = bundle.getString("faculty_image");
        String post = bundle.getString("post");
        String qualification = bundle.getString("qualification");
        String courses_taught = bundle.getString("courses_taught");
        String area_of_expertise = bundle.getString("area_of_expertise");
        String Academic_experience = bundle.getString("Academic_experience");
        String email = bundle.getString("email");
        String phone_no = bundle.getString("phone_no");

        fsddvname.setText(faculty_name);
        fsddvpost.setText(post);
        fsddvqualification.setText(qualification);
        fsddvcoursestaught.setText(courses_taught);
        fsddvareaofexpertise.setText(area_of_expertise);
        fsddvemail.setText(email);
        fsddvphonenumber.setText(phone_no);
        fsddvacademicexperience.setText(Academic_experience);
        Glide.with(this).load(faculty_image).placeholder(R.drawable.diamondshape).into(fsddvphoto);
        fsddvphonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + phone_no));

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    getActivity().startActivity(callIntent);
                }
            }
        });

    }
}