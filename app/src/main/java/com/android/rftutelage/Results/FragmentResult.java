package com.android.rftutelage.Results;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.rftutelage.Attendance.attendance_adapter;
import com.android.rftutelage.Attendance.attendance_datas;
import com.android.rftutelage.R;
import com.android.rftutelage.ui.home.HomeFragment;
import com.android.rftutelage.utils.PreferenceUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FragmentResult extends Fragment {
    ConstraintLayout result_contraint_layout;
    ProgressDialog progressDialog;
    RecyclerView rvresult;
    result_adapter Result_adapter;
    TextView total_percentage,fr_total_marks,fr_obtained_marks,result_semester_one,result_semester_two,result_semester_three,result_semester_four,result_semester_five,result_semester_six;
    ScrollView result_scroll_view;

    String apiurl = "http://192.168.43.84/parentportal/results.php";
    public static ArrayList<results_data> datalist = new ArrayList<results_data>();

    public FragmentResult() {
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
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        total_percentage = view.findViewById(R.id.fresulttv);
        fr_total_marks = view.findViewById(R.id.fr_total_marks);
        fr_obtained_marks = view.findViewById(R.id.fr_obtained_marks);
        result_semester_one = view.findViewById(R.id.result_semester_one);
        result_semester_two = view.findViewById(R.id.result_semester_two);
        result_semester_three = view.findViewById(R.id.result_semester_three);
        result_semester_four = view.findViewById(R.id.result_semester_four);
        result_semester_five = view.findViewById(R.id.result_semester_five);
        result_semester_six = view.findViewById(R.id.result_semester_six);
        result_scroll_view = view.findViewById(R.id.result_scroll_view);
        result_contraint_layout = view.findViewById(R.id.constraint_layout_result_view);

        result_semester_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_scroll_view.setVisibility(View.GONE);
                displaydata("1");
            }
        });
        result_semester_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_scroll_view.setVisibility(View.GONE);
                displaydata("2");
            }
        });
        result_semester_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_scroll_view.setVisibility(View.GONE);
                displaydata("3");
            }
        });
        result_semester_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_scroll_view.setVisibility(View.GONE);
                displaydata("4");
            }
        });
        result_semester_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_scroll_view.setVisibility(View.GONE);
                displaydata("5");
            }
        });
        result_semester_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_scroll_view.setVisibility(View.GONE);
                displaydata("6");
            }
        });
        TextPaint paint = total_percentage.getPaint();
        float width = paint.measureText("Tianjin, China");

        Shader textShader = new LinearGradient(0, 0, width, total_percentage.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        total_percentage.getPaint().setShader(textShader);

        rvresult = (RecyclerView) view.findViewById(R.id.rvresult);
        rvresult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvresult.setHasFixedSize(true);


    }

    private void displaydata(String semester) {

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
                    Integer success_code = jsonresponse.getInt("success");
                    if(success_code==0){
                        result_scroll_view.setVisibility(View.GONE);
                        total_percentage.setVisibility(View.GONE);
                        Snackbar.make(result_contraint_layout,"No results found",Snackbar.LENGTH_LONG).show();
                    }else if(success_code==1){
                        result_scroll_view.setVisibility(View.VISIBLE);
                        total_percentage.setVisibility(View.VISIBLE);
                        total_percentage.setText(jsonresponse.getString("percentage")+" %");
                        fr_total_marks.setText(jsonresponse.getString("total"));
                        fr_obtained_marks.setText(jsonresponse.getString("total_marks_obtained"));
                    }else{
                        result_scroll_view.setVisibility(View.GONE);
                    }

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        results_data table = new results_data();
                        table.setPaper_name(object.getString("paper_name"));
                        table.setTotal_marks(object.getString("total_marks"));
                        table.setObtained_marks(object.getString("obtained_marks"));
                        table.setGrade(object.getString("grade"));
                        datalist.add(table);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                Result_adapter = new result_adapter(getContext(), datalist);
                rvresult.setAdapter(Result_adapter);


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
                params.put("semester",semester);
                params.put("student_roll_no", PreferenceUtils.getRollNumber(getContext()));
                //params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                return params;

            }

        };
        queue.add(stringRequest);

    }
}