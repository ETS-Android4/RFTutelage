
package com.android.rftutelage.Profile.Student_profile;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rftutelage.Profile.profile_main_Activity;
import com.android.rftutelage.R;
import com.android.rftutelage.Timetable.fragment_timetable_view;
import com.android.rftutelage.utils.PreferenceUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class student_profile extends Fragment {

    TextView student_name, date_of_birth,course,subject,batch,admn_no,roll_no,day_scholar_residential,
            admission_date,blood_group,gender,nationality,address,phone_number,email;
    LinearLayout llstudentprofile;
    CircularImageView student_image;
    private static final String apiurl = "http://192.168.43.84/parentportal/studentprofile.php";
    ProgressDialog progressDialog;


    public student_profile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new profile_main_Activity()).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        student_profile_view();
        student_name = view.findViewById(R.id.student_name);
        student_image = view.findViewById(R.id.student_image);
        date_of_birth = view.findViewById(R.id.date_of_birth);
        course = view.findViewById(R.id.course);
        subject = view.findViewById(R.id.subject);
        batch = view.findViewById(R.id.father_phone_number);
        admn_no = view.findViewById(R.id.admn_no);
        roll_no = view.findViewById(R.id.roll_no);
        day_scholar_residential = view.findViewById(R.id.day_scholar_residential);
        admission_date = view.findViewById(R.id.admission_date);
        blood_group = view.findViewById(R.id.blood_group);
        gender = view.findViewById(R.id.gender);
        nationality = view.findViewById(R.id.nationality);
        address = view.findViewById(R.id.address);
        phone_number = view.findViewById(R.id.phone_number);
        email = view.findViewById(R.id.email);
        llstudentprofile = view.findViewById(R.id.llstudentprofile);
        llstudentprofile.setVisibility(View.GONE);




    }

    private void student_profile_view() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               progressDialog.dismiss();
                //detaillist.clear();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);
                    Integer success_code = jsonresponse.getInt("success");
                    if(success_code==0) {
                        llstudentprofile.setVisibility(View.GONE);
                    }else {
                        llstudentprofile.setVisibility(View.VISIBLE);
                    }
                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array is", jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Glide.with(getActivity()).load(object.getString("photo")).placeholder(R.drawable.diamondshape).error(R.drawable.diamondshape).into(student_image);
                        student_name.setText(object.getString("student_name"));
                        //Toast.makeText(getContext(),object.getString("student_name") , Toast.LENGTH_SHORT).show();
                        date_of_birth.setText(object.getString("date_of_birth"));
                        course.setText(object.getString("course"));
                        subject.setText(object.getString("subject"));
                        batch.setText(object.getString("batch"));
                        admn_no.setText(object.getString("admn_no"));
                        roll_no.setText(object.getString("roll_no"));
                        day_scholar_residential.setText(object.getString("day_scholar_residentioal"));
                        admission_date.setText(object.getString("admission_date"));
                        blood_group.setText(object.getString("blood_group"));
                        gender.setText(object.getString("gender"));
                        nationality.setText(object.getString("nationality"));
                        address.setText(object.getString("address"));
                        phone_number.setText(object.getString("phone_number"));
                        email.setText(object.getString("Email"));

                    }

                }catch (JSONException exception){
                    exception.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("roll_no",PreferenceUtils.getRollNumber(getContext()));
                return params;

            }

        };
        queue.add(stringRequest);

    }


    @Override
    public void onStart() {
        super.onStart();


    }
}