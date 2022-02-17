package com.android.rftutelage.Attendance;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.rftutelage.Activities.activity_data;
import com.android.rftutelage.Activities.activity_data_adapter;
import com.android.rftutelage.R;
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

public class FragmentAttendance extends Fragment {

    DatePicker datePicker;
    ConstraintLayout constraintLayout;
    ProgressDialog progressDialog;
    RecyclerView fattendancerecyclerview;
    attendance_adapter Attendance_adapter;
    TextView total_attendance;

    String apiurl = "http://192.168.43.84/parentportal/attendance_view.php";
    public static ArrayList<attendance_datas> datalist = new ArrayList<attendance_datas>();


    public FragmentAttendance() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        total_attendance = view.findViewById(R.id.total_attendance);
        TextPaint paint = total_attendance.getPaint();
        float width = paint.measureText("Tianjin, China");

        Shader textShader = new LinearGradient(0, 0, width, total_attendance.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        total_attendance.getPaint().setShader(textShader);

        fattendancerecyclerview = (RecyclerView) view.findViewById(R.id.fattendancerecyclerview);
        fattendancerecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fattendancerecyclerview.setHasFixedSize(true);

        displaydata();
        datePicker = view.findViewById(R.id.attendance_datepicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

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
                    total_attendance.setText(jsonresponse.getString("total_attendance")+" %");

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        attendance_datas table = new attendance_datas();
                        table.setPaper_name(object.getString("paper_name"));
                        table.setDate(object.getString("date"));
                        table.setAttendance(object.getString("attendance"));
                        table.setTime(object.getString("time"));
                        datalist.add(table);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                Attendance_adapter = new attendance_adapter(getContext(), datalist);
                fattendancerecyclerview.setAdapter(Attendance_adapter);


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
                params.put("semester", PreferenceUtils.getcurrentSemester(getContext()));
                params.put("student_roll_no", PreferenceUtils.getRollNumber(getContext()));
                //params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                return params;

            }


        };
        queue.add(stringRequest);

    }

    private void filter(String text) {

        ArrayList<attendance_datas> filteredList = new ArrayList<>();

        for (attendance_datas item : datalist)
        {
            if (item.getDate().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        Attendance_adapter.filterList(filteredList);
    }


}