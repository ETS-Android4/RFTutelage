package com.android.rftutelage.Profile.Fees;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class student_fees extends Fragment {

    TextView total_fees, fees_paid, fees_pending, annual_total_fees, annual_fees_paid, annual_fees_pending;
    LinearLayout llstudentfees;
    ProgressDialog progressDialog;
    private static final String apiurl = "http://192.168.43.84/parentportal/student_fees.php";

    public student_fees() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_fees, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        total_fees = view.findViewById(R.id.total_fees);
        fees_paid = view.findViewById(R.id.fees_paid);
        fees_pending = view.findViewById(R.id.fees_pending);
        annual_total_fees = view.findViewById(R.id.annual_total_fees);
        annual_fees_paid = view.findViewById(R.id.annual_fees_paid);
        annual_fees_pending = view.findViewById(R.id.annual_fees_pending);
        llstudentfees = view.findViewById(R.id.llstudentfees);
        llstudentfees.setVisibility(View.GONE);

        student_fees_module();
    }

    private void student_fees_module() {

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
                        llstudentfees.setVisibility(View.GONE);
                    }else if(success_code==1){
                        llstudentfees.setVisibility(View.VISIBLE);
                    }
                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array is", jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        total_fees.setText(object.getString("Toatalfees"));
                        fees_paid.setText(object.getString("total_fees_paid"));
                        fees_pending.setText(object.getString("Total_pending_fees"));
                        annual_total_fees.setText(object.getString("annual_fees"));
                        annual_fees_paid.setText(object.getString("annual_fees_paid"));
                        annual_fees_pending.setText(object.getString("annual_pending_fees"));

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
                params.put("roll_no", PreferenceUtils.getRollNumber(getContext()));
                return params;

            }

        };
        queue.add(stringRequest);

    }
}