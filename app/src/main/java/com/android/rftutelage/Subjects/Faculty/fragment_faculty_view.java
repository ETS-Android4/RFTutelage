package com.android.rftutelage.Subjects.Faculty;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.FragmentSubjects;
import com.android.rftutelage.Subjects.subjectadapter;
import com.android.rftutelage.Subjects.subjects;
import com.android.rftutelage.Subjects.subjects_menu;
import com.android.rftutelage.utils.PreferenceUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragment_faculty_view extends Fragment implements faculty_data_adapter.OnNoteListener{
    ProgressDialog progressDialog;
    RecyclerView ffvrecyclerview;
    String apiurl = "http://192.168.43.84/parentportal/subjects_faculty.php";
    public static ArrayList<facultydata> datalist = new ArrayList<facultydata>();

    public fragment_faculty_view() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new subjects_menu()).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculty_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ffvrecyclerview = view.findViewById(R.id.ffvrecyclerview);
        ffvrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        ffvrecyclerview.setHasFixedSize(true);

        displayfacultydata();

    }

    private void displayfacultydata() {

        Log.d("method is called", "subjects: ");
        // progressDiag.show(getContext(), "loading", "please wait", false);
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
                datalist.clear();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array", jsonArray.toString());

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        facultydata table = new facultydata();
                        table.setFaculty_image(object.getString("faculty_image"));
                        table.setFaculty_name(object.getString("faculty_name"));
                        table.setPost(object.getString("post"));
                        table.setQualification(object.getString("qualification"));
                        table.setCourses_taught(object.getString("courses_taught"));
                        table.setArea_of_expertise(object.getString( "area_of_expertise"));
                        table.setAcademic_experience(object.getString( "Academic_experience"));
                        table.setEmail(object.getString( "email"));
                        table.setPhone_no(object.getString( "phone_no"));
                        datalist.add(table);
                    }

                }catch (JSONException exception){
                    exception.printStackTrace();
                }

                faculty_data_adapter Faculty_data_adapter = new faculty_data_adapter(getContext(),datalist, fragment_faculty_view.this::onNoteClicked);
                ffvrecyclerview.setAdapter(Faculty_data_adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
//                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("faculty_code",PreferenceUtils.getfaculty1_code(getContext()));
                params.put("faculty_code1",PreferenceUtils.getfaculty2_code(getContext()));
                params.put("faculty_code2",PreferenceUtils.getfaculty3_code(getContext()));
                //params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                return params;

            }


        };
        queue.add(stringRequest);



    }

    @Override
    public void onNoteClicked(int position) {
        facultydata itemclicked = datalist.get(position);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + itemclicked.getPhone_no()));

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
        }
        else
        {
            getActivity().startActivity(callIntent);
        }


    }
}