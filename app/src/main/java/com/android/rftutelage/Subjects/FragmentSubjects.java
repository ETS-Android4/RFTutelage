package com.android.rftutelage.Subjects;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.rftutelage.Calendar.calendar;
import com.android.rftutelage.Calendar.calendarevents;
import com.android.rftutelage.Calendar.calendaritemview;
import com.android.rftutelage.Calendar.calendarviewadapter;
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
import java.util.HashMap;
import java.util.Map;

public class FragmentSubjects extends Fragment implements subjectadapter.OnNoteListener{
    ProgressDialog progressDialog;
    String semester=null;
    GridView semesterGV;
    RecyclerView recyclerView;
    String apiurl = "http://192.168.43.84/parentportal/subjects.php";
    public static ArrayList<subjects> datalist = new ArrayList<subjects>();

    public FragmentSubjects() {
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
        return inflater.inflate(R.layout.fragment_subjects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        semesterGV = view.findViewById(R.id.isGVsemesters);
        recyclerView = view.findViewById(R.id.fsrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            Integer day = bundle.getInt("getnotify");
            displaysubjects(PreferenceUtils.getselectedsemester(getContext()));

        }

        ArrayList<semestermodel> semestermodelArrayList = new ArrayList<semestermodel>();
        semestermodelArrayList.add(new semestermodel("1"));
        semestermodelArrayList.add(new semestermodel("2"));
        semestermodelArrayList.add(new semestermodel("3"));
        semestermodelArrayList.add(new semestermodel("4"));
        semestermodelArrayList.add(new semestermodel("5"));
        semestermodelArrayList.add(new semestermodel("6"));

        semsterGVadapter adapter = new semsterGVadapter(getContext(), semestermodelArrayList);
        semesterGV.setAdapter(adapter);

        semesterGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //semesterGV.setSelection(position);

                switch(position){

                    case 0:
                        semester = "1";
                       displaysubjects(semester);
                       PreferenceUtils.saveselectedsemester(semester,getContext());

                        break;
                    case 1:
                        semester = "2";
                        displaysubjects(semester);
                        PreferenceUtils.saveselectedsemester(semester,getContext());
                        break;
                    case 2:
                        semester = "3";
                        displaysubjects(semester);
                        PreferenceUtils.saveselectedsemester(semester,getContext());
                        break;
                    case 3:
                        semester = "4";
                        displaysubjects(semester);
                        PreferenceUtils.saveselectedsemester(semester,getContext());
                        break;
                    case 4:
                        semester = "5";
                        displaysubjects(semester);
                        PreferenceUtils.saveselectedsemester(semester,getContext());
                        break;
                    case 5:
                        semester = "6";
                        displaysubjects(semester);
                        PreferenceUtils.saveselectedsemester(semester,getContext());
                        break;
                    default:
                        displaysubjects(PreferenceUtils.getcurrentSemester(getContext()));
                }
            }
        });
    }

    private void displaysubjects(String semester) {

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
                try{
                    jsonresponse = new JSONObject(response);
                    Integer result_code = jsonresponse.getInt("success");
                    if(result_code==0){{
                        Toast.makeText(getContext(),"No Subjects to show",Toast.LENGTH_SHORT).show();
                    }}

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array calendar events", jsonArray.toString());

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        subjects table = new subjects();
                        table.setPaper_name(object.getString("paper_name"));
                        table.setPaper_code(object.getString("paper_code"));
                        table.setFaculty1_photo(object.getString("faculty1_photo"));
                        table.setFaculty2_photo(object.getString("faculty2_photo"));
                        table.setFaculty3_photo(object.getString("faculty3_photo"));
                        table.setFaculty1_name(object.getString( "Faculty1_name"));
                        table.setFaculty2_name(object.getString( "faculty2_name"));
                        table.setFaculty3_name(object.getString( "faculty3_name"));
                        datalist.add(table);
                    }

                }catch (JSONException exception){
                    exception.printStackTrace();
                }

                subjectadapter Subjectadapter = new subjectadapter(getContext(),datalist, FragmentSubjects.this::onNoteClicked);
                recyclerView.setAdapter(Subjectadapter);


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
                params.put("semester", semester);
                params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                return params;

            }


        };
        queue.add(stringRequest);




    }

    @Override
    public void onNoteClicked(int position) {
        subjects itemclicked = datalist.get(position);
        PreferenceUtils.savepapercode(itemclicked.getPaper_code(),getContext());
        PreferenceUtils.savefaculty1_code(itemclicked.getFaculty1_name(),getContext());
        PreferenceUtils.savefaculty2_code(itemclicked.getFaculty2_name(),getContext());
        PreferenceUtils.savefaculty3_code(itemclicked.getFaculty3_name(),getContext());
        subjects_menu senditem = new subjects_menu();
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();

    }
}