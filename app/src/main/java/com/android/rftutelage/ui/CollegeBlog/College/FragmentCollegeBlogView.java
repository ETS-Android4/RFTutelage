package com.android.rftutelage.ui.CollegeBlog.College;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rftutelage.R;
import com.android.rftutelage.Staff.staff_data;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentCollegeBlogView extends Fragment implements collegeblogviewadapter.OnNoteListener {
    private  collegeblogviewadapter Collegeblogviewadapter;
    ProgressDialog progressDialog;
    RecyclerView ccbvrecyclerview;
    EditText etsearch;
    ImageButton searchbutton;

    String apiurl = "http://192.168.43.84/parentportal/collegeblog.php";
    String URL = "http://192.168.43.84/parentportal/collegeblogsearch.php";
    public static ArrayList<collegeblogdata> datalist = new ArrayList<collegeblogdata>();
    public static ArrayList<collegeblogdata> filteredList ;

    public FragmentCollegeBlogView() {
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
        return inflater.inflate(R.layout.fragment_college_blog_view_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ccbvrecyclerview = (RecyclerView) view.findViewById(R.id.fcbvrecyclerview);
        etsearch = view.findViewById(R.id.collegeblogetsearch);
        ccbvrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        ccbvrecyclerview.setHasFixedSize(true);

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
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);

                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array calendar events", jsonArray.toString());

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        collegeblogdata table = new collegeblogdata();
                        table.setBlogtitle(object.getString("blogtitle"));
                        table.setBlogdate(object.getString("blogdate"));
                        table.setBlogdescription(object.getString("blogdescription"));
                        table.setBlogimage(object.getString("blogimage"));
                        table.setBlogimage1(object.getString("blogimage1"));
                        table.setBlogimage2(object.getString("blogimage2"));
                        datalist.add(table);


                    }

                }catch (JSONException exception){
                    exception.printStackTrace();
                }

               Collegeblogviewadapter = new collegeblogviewadapter(getContext(), datalist, FragmentCollegeBlogView.this::onNoteClicked);
                ccbvrecyclerview.setAdapter(Collegeblogviewadapter);


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
            collegeblogdata itemclicked = datalist.get(position);

            //  Toast.makeText(getContext(), "youclicked"+itemclicked.getBlogtitle(),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("blogtitle", itemclicked.getBlogtitle());
            bundle.putString("blogdate", itemclicked.getBlogdate());
            bundle.putString("blogdescription", itemclicked.getBlogdescription());
            bundle.putString("blogimage", itemclicked.getBlogimage());
            bundle.putString("blogimage1", itemclicked.getBlogimage1());
            bundle.putString("blogimage2", itemclicked.getBlogimage2());
            collegeblogdatailedview senditem = new collegeblogdatailedview();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();
        }else{

            Log.d("clicked", "onNoteClicked: ");
            collegeblogdata itemclicked = filteredList.get(position);

            //  Toast.makeText(getContext(), "youclicked"+itemclicked.getBlogtitle(),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("blogtitle", itemclicked.getBlogtitle());
            bundle.putString("blogdate", itemclicked.getBlogdate());
            bundle.putString("blogdescription", itemclicked.getBlogdescription());
            bundle.putString("blogimage", itemclicked.getBlogimage());
            bundle.putString("blogimage1", itemclicked.getBlogimage1());
            bundle.putString("blogimage2", itemclicked.getBlogimage2());
            collegeblogdatailedview senditem = new collegeblogdatailedview();
            senditem.setArguments(bundle);
            //error found here
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();
        }


    }

    private void filter(String text) {

        filteredList = new ArrayList<>();

        for (collegeblogdata item : datalist)
        {
            if (item.getBlogtitle().toUpperCase().contains(text.toUpperCase()))
            {
                filteredList.add(item);
            }
        }

        Collegeblogviewadapter.filterList(filteredList);
    }



}