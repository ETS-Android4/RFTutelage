package com.android.rftutelage.Timetable;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class fragment_timetable_view extends Fragment {
    private static final String apiurl = "http://192.168.43.84/parentportal/timetable.php";
   // public static ArrayList<Timetable_details> detaillist = new ArrayList<Timetable_details>();
   // public static Timetable_details table;
    TextView textviewone,textViewtwo,textviewthree,textViewfour,textViewfive,textViewsix,textviewseven,textVieweight,textViewnine,textViewten,textheading;
    LinearLayout libtextview, lltimetaableview;
    //saturday afternoon class
    CardView cvlibraryfaculty;
    String data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                fragment_timetable_view fragment_timetable_view = new fragment_timetable_view();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Fragmenttimetable()).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_timetable_view,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        data = bundle.getString("day");
        Log.d("message",  data);


        timetable();

        textviewone = view.findViewById(R.id.textView11);
       textViewtwo = view.findViewById(R.id.textViewone16);
       textviewthree = view.findViewById(R.id.textViewtwo12);
       textViewfour = view.findViewById(R.id.textViewtwo16);
       textViewfive = view.findViewById(R.id.textViewthree12);
       textViewsix = view.findViewById(R.id.textViewthree16);
       textviewseven = view.findViewById(R.id.textViewfour12);
       textVieweight = view.findViewById(R.id.textViewfour16);
       textViewnine = view.findViewById(R.id.textViewfive12);
       textViewten = view.findViewById(R.id.textViewfive16);
       textheading = view.findViewById(R.id.timetabletextview);
       libtextview = view.findViewById(R.id.libraryfaculty);
       cvlibraryfaculty = view.findViewById(R.id.cvlibraryfaculty);
       lltimetaableview = view.findViewById(R.id.lltimetableview);
        lltimetaableview.setVisibility(View.GONE);




    }


    private void timetable() {
        Log.d("method is called", "timetable: ");
// progressDiag.show(getContext(), "loading", "please wait", false);
        ProgressDialog progressDialog;
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

                    Integer result_code = jsonresponse.getInt("success");
                    if(result_code==0){
                        lltimetaableview.setVisibility(View.GONE);

                    }else{
                        lltimetaableview.setVisibility(View.VISIBLE);
                    }


                    JSONArray jsonArray = jsonresponse.getJSONArray("products");
                    Log.i("Array is", jsonArray.toString());
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                             textheading.setText(object.getString("day"));
                             textviewone.setText(object.getString("nine_to_ten"));
                             textViewtwo.setText(object.getString("faculty"));
                             textviewthree.setText(object.getString("ten_to_eleven"));
                             textViewfour.setText(object.getString("faculty1"));
                             textViewfive.setText(object.getString("eleven_to_twelve"));
                             textViewsix.setText(object.getString("faculty2"));
                             textviewseven.setText(object.getString("twelve_to_one"));
                             textVieweight.setText(object.getString("faculty3"));

                             if(object.getString("two_to_five")!="null"){
                                 cvlibraryfaculty.setVisibility(View.VISIBLE);
                                 libtextview.setVisibility(View.GONE);
                                 textViewnine.setText(object.getString("two_to_five"));
                             }
                             if(object.getString("faculty4")!="null") {
                                 libtextview.setVisibility(View.VISIBLE);
                                 textViewten.setText(object.getString("faculty4"));
                             }

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
                //add cource
                Map<String,String> params = new HashMap<String, String>();
                params.put("semester", PreferenceUtils.getcurrentSemester(getContext()));
                params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                params.put("day",data);
                return params;

            }

        };
        queue.add(stringRequest);

    }

}