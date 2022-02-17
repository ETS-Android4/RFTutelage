package com.android.rftutelage.Subjects.Remarks;

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

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Assignment.assignment_data;
import com.android.rftutelage.Subjects.Assignment.assignment_data_adapter;
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

public class fragment_remarks_view extends Fragment {

    ProgressDialog progressDialog;
    RecyclerView frvrecyclerview;
    EditText etsearch;
    remarks_data_adapter Remarks_data_adapter;

    String apiurl = "http://192.168.43.84/parentportal/remarks.php";

    public static ArrayList<remarks_data> datalist = new ArrayList<remarks_data>();

    public fragment_remarks_view() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remarks_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frvrecyclerview = (RecyclerView) view.findViewById(R.id.frvrecyclerview);
        etsearch = view.findViewById(R.id.frvetsearch);
        frvrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        frvrecyclerview.setHasFixedSize(true);

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

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        remarks_data table = new remarks_data();
                        table.setPerformance(object.getString("performance"));
                        table.setDate(object.getString("date"));
                        table.setFeedback(object.getString("feedback"));
                        datalist.add(table);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                Remarks_data_adapter = new remarks_data_adapter(getContext(), datalist);
                frvrecyclerview.setAdapter(Remarks_data_adapter);


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

        ArrayList<remarks_data> filteredList = new ArrayList<>();

        for (remarks_data item : datalist)
        {
            if (item.getDate().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(item);
            }
        }

        Remarks_data_adapter.filterList(filteredList);
    }

}