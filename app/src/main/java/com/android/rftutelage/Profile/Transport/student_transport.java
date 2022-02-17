package com.android.rftutelage.Profile.Transport;

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

public class student_transport extends Fragment {

    TextView transport_source, distance, from_destination, to_destination;
    LinearLayout llstudenttransport;
    ProgressDialog progressDialog;
    private static final String apiurl = "http://192.168.43.84/parentportal/student_trasport.php";


    public student_transport() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new profile_main_Activity()).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_transport, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transport_source = view.findViewById(R.id.Transport_Source);
        distance = view.findViewById(R.id.distance);
        from_destination = view.findViewById(R.id.fees_pending);
        to_destination = view.findViewById(R.id.to_destination);
        llstudenttransport = view.findViewById(R.id.llstudenttransport);
        llstudenttransport.setVisibility(View.GONE);

        student_transport_module();
    }

    private void student_transport_module() {

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
                        llstudenttransport.setVisibility(View.GONE);
                    }else if(success_code==1){
                        llstudenttransport.setVisibility(View.VISIBLE);
                    }
                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array is", jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        from_destination.setText(object.getString("fromdestination"));
                        to_destination.setText(object.getString("todestination"));
                        transport_source.setText(object.getString("trasportsource"));
                        distance.setText(object.getString("traveldistance"));

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