package com.android.rftutelage.ui.CollegeBlog.Student;

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

public class studentblogdetailedview extends Fragment {

    public studentblogdetailedview() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putInt("number",2);
                CollegeBlogDashboard senditem = new CollegeBlogDashboard();
                senditem.setArguments(bundle);
                //error found here
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_studentblogdetailedview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        String name = bundle.getString("name");
        String studentphoto = bundle.getString("Studentphoto");
        String department = bundle.getString("department");
        String image = bundle.getString("image");
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String date = bundle.getString("date");

        CircularImageView fsbdvphoto = (CircularImageView) view.findViewById(R.id.fsbdvphoto);
        TextView fsbdvname = (TextView)view.findViewById(R.id.fsbdvname);
        TextView fsbdvdepartment = (TextView) view.findViewById(R.id.fsbdvdepartment);
        TextView fsbdvdate = (TextView)view.findViewById(R.id.fsbdvdate);
        ImageView fsbdvimage = (ImageView)view.findViewById(R.id.fsbdvimage);
        TextView fsbdvtitle = (TextView) view.findViewById(R.id.fsbdvtitle);
        TextView fsbdvdescription = (TextView)view.findViewById(R.id.fsbdvdescrption);

        fsbdvname.setText(name);
        fsbdvdepartment.setText(department);
        fsbdvdescription.setText(description);
        fsbdvdate.setText(date);
        fsbdvtitle.setText(title);
        Glide.with(getContext()).load(image).placeholder(R.drawable.diamondshape).into(fsbdvimage);
        Glide.with(getContext()).load(studentphoto).placeholder(R.drawable.diamondshape).into(fsbdvphoto);
    }
}