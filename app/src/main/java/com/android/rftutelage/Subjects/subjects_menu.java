package com.android.rftutelage.Subjects;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rftutelage.Calendar.calendar;
import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Assignment.fragment_assignment_view;
import com.android.rftutelage.Subjects.Attendance.fragment_attendance_view;
import com.android.rftutelage.Subjects.Faculty.fragment_faculty_view;
import com.android.rftutelage.Subjects.Marks.fragment_marks_view;
import com.android.rftutelage.Subjects.Remarks.fragment_remarks_view;
import com.android.rftutelage.Subjects.test.fragment_test_view;

public class subjects_menu extends Fragment {


    public subjects_menu() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                Bundle bundle = new Bundle();
                bundle.putInt("getnotify",1);
                FragmentSubjects senditem = new FragmentSubjects();
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
        return inflater.inflate(R.layout.fragment_subjects_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView fsmcardview = view.findViewById(R.id.fsmcardview);
        CardView fsmtest = view.findViewById(R.id.fsmtest);
        CardView fsmassignment = view.findViewById(R.id.fsmassignment);
        CardView fsmmarks = view.findViewById(R.id.fsmmarks);
        CardView fsmattendance = view.findViewById(R.id.fsmattendance);
        CardView fsmremarks = view.findViewById(R.id.fsmremarks);
        fsmcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_faculty_view senditem = new fragment_faculty_view();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem ).commit();

            }
        });

        fsmtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment_test_view senditem = new fragment_test_view();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem ).commit();
            }
        });

        fsmassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment_assignment_view senditem = new fragment_assignment_view();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem ).commit();

            }
        });

        fsmmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragment_marks_view senditem = new fragment_marks_view();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem ).commit();

            }
        });

        fsmattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment_attendance_view senditem = new fragment_attendance_view();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem ).commit();

            }
        });

        fsmremarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment_remarks_view senditem = new fragment_remarks_view();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem ).commit();

            }
        });
    }



}