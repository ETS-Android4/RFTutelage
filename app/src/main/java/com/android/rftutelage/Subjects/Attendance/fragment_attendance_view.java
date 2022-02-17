package com.android.rftutelage.Subjects.Attendance;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Assignment.assignment_data;
import com.android.rftutelage.Subjects.Assignment.assignment_data_adapter;
import com.android.rftutelage.Subjects.Marks.marks_data;
import com.android.rftutelage.Subjects.Marks.marks_data_adapter;
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

public class fragment_attendance_view extends Fragment {

    ProgressDialog progressDialog;
    RecyclerView fatvrecyclerview;
    EditText etsearch;
    TextView fatvattendance,fatvtotalattendance;
    attendance_data_adapter Attendance_data_adapter;

    String apiurl = "http://192.168.43.84/parentportal/attendance.php";

    public static ArrayList<attendance_data> datalist = new ArrayList<attendance_data>();

    public fragment_attendance_view() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new subjects_menu()).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fatvrecyclerview = (RecyclerView) view.findViewById(R.id.fatvrecyclerview);
        fatvattendance = (TextView) view.findViewById(R.id.fatvattendance);
        fatvtotalattendance =(TextView) view.findViewById(R.id.fatvtotalattendance);
        etsearch = view.findViewById(R.id.fatvetsearch);
        fatvrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fatvrecyclerview.setHasFixedSize(true);

        Button fatvpresent = (Button) view.findViewById(R.id.fatvpresent);
        Button fatvabsent = (Button) view.findViewById(R.id.fatvabsent);
        fatvpresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter1("PRESENT");
            }
        });

        fatvabsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter2("ABSENT");
            }
        });


        displaydata();

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

    private void displaydata() {

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
                try {
                    jsonresponse = new JSONObject(response);
                    fatvattendance.setText(jsonresponse.getString("row_count1"));
                    fatvtotalattendance.setText(jsonresponse.getString("row_count"));

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        attendance_data table = new attendance_data();
                        table.setDate(object.getString("date"));
                        table.setAttendance(object.getString("attendance"));
                        table.setTime(object.getString("time"));
                        datalist.add(table);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                Attendance_data_adapter = new attendance_data_adapter(getContext(), datalist);
                fatvrecyclerview.setAdapter(Attendance_data_adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
//                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                params.put("semester", PreferenceUtils.getselectedsemester(getContext()));
                params.put("student_roll_no", PreferenceUtils.getRollNumber(getContext()));
                params.put("paper_code", PreferenceUtils.getpapercode(getContext()));
                //params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                return params;

            }


        };
        queue.add(stringRequest);
    }

    private void filter(String text) {

        ArrayList<attendance_data> filteredList = new ArrayList<>();

        for (attendance_data item : datalist)
        {
            if (item.getDate().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(item);
            }
        }

        Attendance_data_adapter.filterList(filteredList);
    }

    private void filter1(String text) {

        ArrayList<attendance_data> filteredList = new ArrayList<>();

        for (attendance_data item : datalist)
        {
            if (item.getAttendance().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        Attendance_data_adapter.filterList(filteredList);
    }
    private void filter2(String text) {

        ArrayList<attendance_data> filteredList = new ArrayList<>();

        for (attendance_data item : datalist)
        {
            if (item.getAttendance().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        Attendance_data_adapter.filterList(filteredList);
    }


}