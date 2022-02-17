package com.android.rftutelage.Profile;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.rftutelage.Profile.Fees.student_fees;
import com.android.rftutelage.Profile.Student_profile.student_profile;
import com.android.rftutelage.Profile.Transport.student_transport;
import com.android.rftutelage.R;

import java.util.ArrayList;

public class profile_main_Activity extends Fragment {
    GridView photosGV;


    public profile_main_Activity() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile_main__activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photosGV = view.findViewById(R.id.idGVphotos);

        ArrayList<photo_model> photoModelArrayList = new ArrayList<photo_model>();
        photoModelArrayList.add(new photo_model("Student", R.drawable.student));
        //photoModelArrayList.add(new photo_model("Parent", R.drawable.couple));
        photoModelArrayList.add(new photo_model("Fees", R.drawable.fees));
        photoModelArrayList.add(new photo_model("Transport", R.drawable.bus));

        photoGVAdapter adapter = new photoGVAdapter(getContext(), photoModelArrayList);
        photosGV.setAdapter(adapter);
        photosGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new student_profile()).commit();
                        break;

                    //case 1:
                    //    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new parent_profile()).commit();
                   //     break;
                    case 1:
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new student_fees()).commit();
                        break;

                    case 2:
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new student_transport()).commit();
                        break;
                }
            }
        });
    }


}