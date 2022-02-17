package com.android.rftutelage.Calendar;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rftutelage.MainActivity;
import com.android.rftutelage.R;
import com.android.rftutelage.Timetable.fragment_timetable_view;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class calendar extends Fragment implements calendarviewadapter.OnNoteListener {
    DatePicker datePicker;
    TextView textView;
    ProgressDialog progressDialog;
    RecyclerView cvrecyclerview;
    public static int days;
    public static int months;
    public static int years;
    String apiurl = "http://192.168.43.84/parentportal/calendarevents.php";
    public static ArrayList<calendarevents> Calendareventsdata = new ArrayList<calendarevents>();

    public calendar() {
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
        return inflater.inflate(R.layout.fragment_calendar, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker= (DatePicker) view.findViewById(R.id.datepicker);

        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            Integer day = bundle.getInt("day");
            Integer month = bundle.getInt("month");
            Integer year = bundle.getInt("year");
            datePicker.updateDate(year,month-1, day);
            String selectedData = day + "-" + (month) + "-" + year;
            eventsview(selectedData);

            datePicker.init(year,month-1,day, new DatePicker.OnDateChangedListener() {

                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                    days = dayOfMonth;
                    months = month + 1;
                    years = year;

                    //  textView.setText(selectedDate);
                    eventsview(selectedDate);

                }
            });

        }


       //textView = (TextView) view.findViewById(R.id.cvtext);
        cvrecyclerview = (RecyclerView) view.findViewById(R.id.cvrecyclerview);
        cvrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        cvrecyclerview.setHasFixedSize(true);

        if(bundle==null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                    days = dayOfMonth;
                    months = month + 1;
                    years = year;

                    //  textView.setText(selectedDate);
                    eventsview(selectedDate);

                }
            });
        }


    }

    private void eventsview(String selecteddate) {

        Log.d("method is called", "timetable: ");
        //progress dialog is removed to avoid chunkiness
// progressDiag.show(getContext(), "loading", "please wait", false);
      //  progressDialog = new ProgressDialog(getContext());
      //  progressDialog.setTitle("Loading");
     //   progressDialog.setMessage("Wait while loading...");
     //   progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
     //   progressDialog.show();


        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           //     progressDialog.dismiss();
                Calendareventsdata.clear();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);

                    Integer result_code = jsonresponse.getInt("success");
                    if(result_code==0){
                        Toast.makeText(getContext(),"No events to show",Toast.LENGTH_SHORT).show();

                    }

                    JSONArray jsonArray = jsonresponse.getJSONArray("productss");
                    Log.i("Array calendar events", jsonArray.toString());

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        calendarevents Calendarevents = new calendarevents();
                        Calendarevents.setTitle(object.getString("title"));
                        Calendarevents.setLocation(object.getString("location"));
                        Calendarevents.setDescription(object.getString("description"));
                        Calendarevents.setImage(object.getString("image"));
                        Calendarevents.setDate(object.getString("date"));
                        Calendarevents.setTime(object.getString("time"));
                        Calendareventsdata.add(Calendarevents);


                    }

                }catch (JSONException exception){
                    exception.printStackTrace();
                }

                    calendarviewadapter Calendarviewadapter = new calendarviewadapter(getContext(), Calendareventsdata,calendar.this::onNoteClicked);
                    cvrecyclerview.setAdapter(Calendarviewadapter);


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
                params.put("date", selecteddate);
                return params;

            }


        };
        queue.add(stringRequest);


    }


    @Override
    public void onNoteClicked(int position) {

        Log.d("clicked", "onNoteClicked: ");
        calendarevents itemclicked = Calendareventsdata.get(position);

        //Toast.makeText(getContext(), "youclicked"+itemclicked.getTitle(),Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("title",itemclicked.getTitle());
        bundle.putString("location",itemclicked.getLocation());
        bundle.putString("description",itemclicked.getDescription());
        bundle.putString("image",itemclicked.getImage());
        bundle.putString("date",itemclicked.getDate());
        bundle.putString("time",itemclicked.getTime());
        bundle.putInt("days",days);
        bundle.putInt("months",months);
        bundle.putInt("years",years);
        calendaritemview senditem = new calendaritemview();
        senditem.setArguments(bundle);
       getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);


    }
}