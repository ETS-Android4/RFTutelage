package com.android.rftutelage.Staff;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.rftutelage.R;
import com.android.rftutelage.ui.CollegeBlog.Faculty.FragmentFacultyBlogView;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdata;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdetailedview;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogviewadapter;
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

public class fragment_staff_view extends Fragment implements staff_data_adapter.OnNoteListener {

    private staff_data_adapter Staff_data_adapter;
    ProgressDialog progressDialog;
    RecyclerView csvrecyclerview;
    EditText etsearch;

    String apiurl = "http://192.168.43.84/parentportal/faculty.php";

    public static ArrayList<staff_data> datalist = new ArrayList<staff_data>();
    public static ArrayList<staff_data> filteredList;

    public fragment_staff_view() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        csvrecyclerview = (RecyclerView) view.findViewById(R.id.fsvrecyclerview);
        etsearch = view.findViewById(R.id.fsvetsearch);
        csvrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        csvrecyclerview.setHasFixedSize(true);
        // hideKeyboardFrom(getContext(), view);

        display_data();
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
    }

    private void display_data() {

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
                Log.d("faculty blog data", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array events", jsonArray.toString());

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        staff_data table = new staff_data();
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

                Staff_data_adapter = new staff_data_adapter(getContext(), datalist, fragment_staff_view.this::onNoteClicked);
                csvrecyclerview.setAdapter(Staff_data_adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
//                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        queue.add(stringRequest);

    }

    @Override
    public void onNoteClicked(int position) {

        Log.d("clicked", "onNoteClicked: ");
        String data = etsearch.getText().toString();
        if(data.equals("")){

            staff_data itemclicked = datalist.get(position);
            //  Toast.makeText(getContext(), "youclicked"+itemclicked.getBlogtitle(),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("faculty_name", itemclicked.getFaculty_name());
            bundle.putString("faculty_image", itemclicked.getFaculty_image());
            bundle.putString("post", itemclicked.getPost());
            bundle.putString("qualification", itemclicked.getQualification());
            bundle.putString("courses_taught", itemclicked.getCourses_taught());
            bundle.putString("area_of_expertise", itemclicked.getArea_of_expertise());
            bundle.putString("Academic_experience", itemclicked.getAcademic_experience());
            bundle.putString("email", itemclicked.getEmail());
            bundle.putString("phone_no", itemclicked.getPhone_no());
            staff_data_detailed_view senditem = new staff_data_detailed_view();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();

        }else {

            staff_data itemclicked = filteredList.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("faculty_name",itemclicked.getFaculty_name());
            bundle.putString("faculty_image",itemclicked.getFaculty_image());
            bundle.putString("post",itemclicked.getPost());
            bundle.putString("qualification",itemclicked.getQualification());
            bundle.putString("courses_taught",itemclicked.getCourses_taught());
            bundle.putString("area_of_expertise",itemclicked.getArea_of_expertise());
            bundle.putString("Academic_experience",itemclicked.getAcademic_experience());
            bundle.putString("email",itemclicked.getEmail());
            bundle.putString("phone_no",itemclicked.getPhone_no());
            staff_data_detailed_view senditem = new staff_data_detailed_view();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();

        }
    }

    private void filter(String text) {

        filteredList = new ArrayList<>();

        for (staff_data item : datalist)
        {
            if (item.getFaculty_name().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        Staff_data_adapter.filterList(filteredList);
    }
}