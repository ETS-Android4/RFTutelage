package com.android.rftutelage.ui.CollegeBlog.Faculty;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rftutelage.R;
import com.android.rftutelage.ui.CollegeBlog.CollegeBlogDashboard;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

public class facultyblogdetailedview extends Fragment {


    public facultyblogdetailedview() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putInt("number",1);
                CollegeBlogDashboard senditem = new CollegeBlogDashboard();
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
        return inflater.inflate(R.layout.fragment_facultyblogdetailedview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        String name = bundle.getString("name");
        String facultyphoto = bundle.getString("facultyphoto");
        String department = bundle.getString("department");
        String image = bundle.getString("image");
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String date = bundle.getString("date");

        CircularImageView ffbdvphoto = (CircularImageView) view.findViewById(R.id.ffbdvphoto);
        TextView ffbdvname = (TextView)view.findViewById(R.id.ffbdvname);
        TextView ffbdvdepartment = (TextView) view.findViewById(R.id.ffbdvdepartment);
        TextView ffbdvdate = (TextView)view.findViewById(R.id.ffbdvdate);
        ImageView ffbdvimage = (ImageView)view.findViewById(R.id.ffbdvimage);
        TextView ffbdvtitle = (TextView) view.findViewById(R.id.ffbdvtitle);
        TextView ffbdvdescription = (TextView)view.findViewById(R.id.ffbdvdescrption);

        ffbdvname.setText(name);
        ffbdvdepartment.setText(department);
        ffbdvdescription.setText(description);
        ffbdvdate.setText(date);
        ffbdvtitle.setText(title);
        Glide.with(getContext()).load(image).placeholder(R.drawable.diamondshape).into(ffbdvimage);
        Glide.with(getContext()).load(facultyphoto).placeholder(R.drawable.diamondshape).into(ffbdvphoto);

    }
}