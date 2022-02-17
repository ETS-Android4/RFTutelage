package com.android.rftutelage.Reportleave.History;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.rftutelage.Attendance.attendance_adapter;
import com.android.rftutelage.Attendance.attendance_datas;
import com.android.rftutelage.MainActivity;
import com.android.rftutelage.R;
import com.android.rftutelage.Reportleave.report_leave_main_page;
import com.android.rftutelage.Staff.staff_data;
import com.android.rftutelage.Staff.staff_data_adapter;
import com.android.rftutelage.Subjects.Faculty.faculty_data_adapter;
import com.android.rftutelage.Subjects.Faculty.fragment_faculty_view;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdata;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdetailedview;
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

public class Fragment_history extends Fragment implements History_data_adapter.OnNoteListener{

    DatePicker datePicker;
    ConstraintLayout constraintLayout;
    RecyclerView history_recycler_view;
    History_data_adapter history_data_adapter;
    TextView total_attendance;
    ProgressDialog progressDialog;
    Boolean status=false;
    ArrayList<history_data> filteredList = new ArrayList<>();
    String apiurl = "http://192.168.43.84/parentportal/history.php";
    public static ArrayList<history_data> datalist = new ArrayList<history_data>();

    public Fragment_history() {
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        history_recycler_view = (RecyclerView) view.findViewById(R.id.history_recycler_view);
        history_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        history_recycler_view.setHasFixedSize(true);

       // handler.postDelayed(r, 10000);


        displaydata();



        datePicker = view.findViewById(R.id.history_datepicker);
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

    public void displaydata() {

        Log.d("method is called", "subjects: ");
        // progressDiag.show(getContext(), "loading", "please wait", false);
        //if(status=false) {
        //    progressDialog = new ProgressDialog(getContext());
        //    progressDialog.setTitle("Loading");
        //    progressDialog.setMessage("Wait while loading...");
        //    progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
        //    progressDialog.show();
        //}


        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //if(status==false){
                //    progressDialog.dismiss();
                //}
                datalist.clear();
                filteredList.clear();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try {
                    jsonresponse = new JSONObject(response);

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        history_data table = new history_data();
                        table.setDate(object.getString("date"));
                        table.setSubject(object.getString("subject"));
                        table.setDuration(object.getString("duration"));
                        table.setFrom_date(object.getString("from_date"));
                        table.setTo_date(object.getString("to_date"));
                        table.setNote(object.getString("note"));
                        table.setImage(object.getString("image"));
                        table.setCurrent_date_time(object.getString("current_date_time"));
                        table.setStatus(object.getString("status"));
                        datalist.add(table);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                history_data_adapter = new History_data_adapter(getContext(),datalist, Fragment_history.this::onNoteClicked);
                history_recycler_view.setAdapter(history_data_adapter);


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
                params.put("student_roll_no", PreferenceUtils.getRollNumber(getContext()));
                //params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                return params;

            }


        };
        queue.add(stringRequest);

    }




    @Override
    public void onNoteClicked(int position) {

        Log.d("clicked", "onNoteClicked: ");

        if(filteredList.size()==0) {
            history_data itemclicked = datalist.get(position);

            //  Toast.makeText(getContext(), "youclicked"+itemclicked.getBlogtitle(),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("date", itemclicked.getDate());
            bundle.putString("subject", itemclicked.getSubject());
            bundle.putString("duration", itemclicked.getDuration());
            bundle.putString("from_date", itemclicked.getDate());
            bundle.putString("to_date", itemclicked.getTo_date());
            bundle.putString("note", itemclicked.getNote());
            bundle.putString("image", itemclicked.getImage());
            bundle.putString("current_date_time", itemclicked.getCurrent_date_time());
            bundle.putString("status", itemclicked.getStatus());
            history_data_detailed_view senditem = new history_data_detailed_view();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();
        }else{
            history_data itemclicked = filteredList.get(position);

            //  Toast.makeText(getContext(), "youclicked"+itemclicked.getBlogtitle(),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("date", itemclicked.getDate());
            bundle.putString("subject", itemclicked.getSubject());
            bundle.putString("duration", itemclicked.getDuration());
            bundle.putString("from_date", itemclicked.getDate());
            bundle.putString("to_date", itemclicked.getTo_date());
            bundle.putString("note", itemclicked.getNote());
            bundle.putString("image", itemclicked.getImage());
            bundle.putString("current_date_time", itemclicked.getCurrent_date_time());
            bundle.putString("status", itemclicked.getStatus());
            history_data_detailed_view senditem = new history_data_detailed_view();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();
        }

    }

    private void filter(String text) {
         filteredList.clear();
        for (history_data item : datalist)
        {
            if (item.getDate().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        history_data_adapter.filterList(filteredList);
    }

    @Override
    public void setMenuVisibility(boolean visible) {
        super.setMenuVisibility(visible);

        if (isVisible()) {
            displaydata();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

}