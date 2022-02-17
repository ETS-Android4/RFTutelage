package com.android.rftutelage.Noticeboard;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.rftutelage.Attendance.attendance_adapter;
import com.android.rftutelage.Attendance.attendance_datas;
import com.android.rftutelage.R;
import com.android.rftutelage.ui.CollegeBlog.CollegeBlogDashboard;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentNoticeboard extends Fragment {

    DatePicker noticeboard_datepicker;
    ConstraintLayout constraintLayout;
    ProgressDialog progressDialog;
    RecyclerView fnrecyclerview;
    noticeboard_adapter Noticeboard_adapter;

    String apiurl = "http://192.168.43.84/parentportal/noticeboard.php";
    public static ArrayList<noticeboard_data> datalist = new ArrayList<noticeboard_data>();

    public FragmentNoticeboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                HomeFragment senditem = new HomeFragment();
                //error found here
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noticeboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fnrecyclerview = (RecyclerView) view.findViewById(R.id.fnrecyclerview);
        fnrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fnrecyclerview.setHasFixedSize(true);

        displaydata();
        noticeboard_datepicker = view.findViewById(R.id.noticeboard_datepicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        noticeboard_datepicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                //Toast.makeText(getContext(),selectedDate,Toast.LENGTH_SHORT).show();
                filter(selectedDate);

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
                        noticeboard_data table = new noticeboard_data();
                        table.setDate(object.getString("date"));
                        table.setImage(object.getString("image"));
                        datalist.add(table);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                Noticeboard_adapter = new noticeboard_adapter(getContext(), datalist);
                fnrecyclerview.setAdapter(Noticeboard_adapter);


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

    private void filter(String text) {

        ArrayList<noticeboard_data> filteredList = new ArrayList<>();

        for (noticeboard_data item : datalist)
        {
            if (item.getDate().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        Noticeboard_adapter.filterList(filteredList);
    }


}
