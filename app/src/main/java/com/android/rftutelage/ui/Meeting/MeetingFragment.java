package com.android.rftutelage.ui.Meeting;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.Activities.ActivitesFragment;
import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Assignment.assignment_data;
import com.android.rftutelage.Subjects.Assignment.assignment_data_adapter;
import com.android.rftutelage.Subjects.subjects_menu;
import com.android.rftutelage.ui.home.HomeFragment;
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
import java.util.Timer;
import java.util.TimerTask;

public class MeetingFragment extends Fragment {



    ProgressDialog progressDialog;
    RecyclerView fmrecyclerview;
    EditText etsearch;
    meeting_data_adapter Meeting_data_adapter;
    Boolean staus = false;

    String apiurl = "http://192.168.43.84/parentportal/meetings.php";

    public static ArrayList<meeting_data> datalist = new ArrayList<meeting_data>();

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_meeting,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        fmrecyclerview = (RecyclerView) view.findViewById(R.id.fmrecyclerview);
        fmrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fmrecyclerview.setHasFixedSize(true);

         displaydata();

         Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                //transactFragment(MeetingFragment.this,true);
                staus = true;
                displaydata();
                handler.postDelayed(this, 10000);
            }
        };

        handler.postDelayed(r, 10000);

      //
    }



    private void displaydata() {

        Log.d("method is called", "subjects: ");
        // progressDiag.show(getContext(), "loading", "please wait", false);
        if(staus==false) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Wait while loading...");
            progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progressDialog.show();

        }
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(staus==false) {
                    progressDialog.dismiss();
                }
                datalist.clear();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try {
                    jsonresponse = new JSONObject(response);

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        meeting_data table = new meeting_data();
                        table.setTitle(object.getString("title"));
                        table.setDate(object.getString("date"));
                        table.setPlace(object.getString("place"));
                        table.setTime(object.getString("time"));
                        datalist.add(table);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                Meeting_data_adapter = new meeting_data_adapter(getContext(), datalist);
                fmrecyclerview.setAdapter(Meeting_data_adapter);


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
                return params;

            }
        };
        queue.add(stringRequest);
    }
}