package com.android.rftutelage.ui.CollegeBlog.Student;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.rftutelage.R;
import com.android.rftutelage.ui.CollegeBlog.College.collegeblogdata;
import com.android.rftutelage.ui.CollegeBlog.Faculty.FragmentFacultyBlogView;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdata;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdetailedview;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogviewadapter;
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

public class FragmentStudentBlogView extends Fragment implements studentblogviewadapter.OnNoteListener {
    private studentblogviewadapter Studentblogviewadapter;
    ProgressDialog progressDialog;
    RecyclerView csbvrecyclerview;
    EditText etsearch;
    ImageButton searchbutton;
    String apiurl = "http://192.168.43.84/parentportal/studentblog.php";

    public static ArrayList<studentblogdata> datalist = new ArrayList<studentblogdata>();
    public static ArrayList<studentblogdata> filteredList;
    public FragmentStudentBlogView() {
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
        return inflater.inflate(R.layout.fragment_student_blog_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        csbvrecyclerview = (RecyclerView) view.findViewById(R.id.fsbvrecyclerview);
        etsearch = view.findViewById(R.id.studentblogetsearch);
        csbvrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        csbvrecyclerview.setHasFixedSize(true);
        blogview();

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

    private void blogview() {
        Log.d("method is called", "timetable: ");

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
                Log.d("faculty blog data", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array events", jsonArray.toString());

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        studentblogdata table = new studentblogdata();
                        table.setStudentphoto(object.getString("studentphoto"));
                        table.setName(object.getString("name"));
                        table.setDepartmentandrollno(object.getString("departmentandrollno"));
                        table.setDate(object.getString("date"));
                        table.setImage(object.getString("image"));
                        table.setTitle(object.getString("title"));
                        table.setDecsription(object.getString("decsription"));
                        datalist.add(table);


                    }

                }catch (JSONException exception){
                    exception.printStackTrace();
                }

                Studentblogviewadapter = new studentblogviewadapter(getContext(), datalist, FragmentStudentBlogView.this::onNoteClicked);
                csbvrecyclerview.setAdapter(Studentblogviewadapter);


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

    @Override
    public void onNoteClicked(int position) {

        String data = etsearch.getText().toString();
        if(data.equals("")) {

            Log.d("clicked", "onNoteClicked: ");
            studentblogdata itemclicked = datalist.get(position);

            //  Toast.makeText(getContext(), "youclicked"+itemclicked.getBlogtitle(),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("Studentphoto", itemclicked.getStudentphoto());
            bundle.putString("name", itemclicked.getName());
            bundle.putString("department", itemclicked.getDepartmentandrollno());
            bundle.putString("date", itemclicked.getDate());
            bundle.putString("image", itemclicked.getImage());
            bundle.putString("title", itemclicked.getTitle());
            bundle.putString("description", itemclicked.getDecsription());
            studentblogdetailedview senditem = new studentblogdetailedview();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();
        }else{

            Log.d("clicked", "onNoteClicked: ");
            studentblogdata itemclicked = filteredList.get(position);

            //  Toast.makeText(getContext(), "youclicked"+itemclicked.getBlogtitle(),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("Studentphoto", itemclicked.getStudentphoto());
            bundle.putString("name", itemclicked.getName());
            bundle.putString("department", itemclicked.getDepartmentandrollno());
            bundle.putString("date", itemclicked.getDate());
            bundle.putString("image", itemclicked.getImage());
            bundle.putString("title", itemclicked.getTitle());
            bundle.putString("description", itemclicked.getDecsription());
            studentblogdetailedview senditem = new studentblogdetailedview();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();
        }

    }

    private void filter(String text) {

        filteredList = new ArrayList<>();

        for (studentblogdata item : datalist)
        {
            if (item.getTitle().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        Studentblogviewadapter.filterList(filteredList);
    }


}