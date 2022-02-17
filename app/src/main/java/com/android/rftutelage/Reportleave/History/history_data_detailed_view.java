package com.android.rftutelage.Reportleave.History;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rftutelage.MainActivity;
import com.android.rftutelage.R;
import com.android.rftutelage.Reportleave.report_leave_main_page;
import com.android.rftutelage.Results.FragmentResult;
import com.android.rftutelage.ui.CollegeBlog.CollegeBlogDashboard;
import com.android.rftutelage.utils.PreferenceUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class history_data_detailed_view extends Fragment {

    TextView tvsubject,tvduration,tvdate,tvnote;
    ImageView img_proof_reading;
    Button btnstatus,history_delete_button;
    String apiurl = "http://192.168.43.84/parentportal/delete_history.php";

    public history_data_detailed_view() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putInt("number",1);
                report_leave_main_page senditem = new report_leave_main_page();
                senditem.setArguments(bundle);
                //error found here
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_data_detailed_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        String date = bundle.getString("date");
        String subject = bundle.getString("subject");
        String duration = bundle.getString("duration");
        String from_date = bundle.getString("from_date");
        String to_date = bundle.getString("to_date");
        String note = bundle.getString("note");
        String image = bundle.getString("image");
        String current_date_time = bundle.getString("current_date_time");
        String status = bundle.getString("status");

        TextView tvsubject,tvduration,tvdate,tvnote;
        ImageView img_proof_reading;
        Button btnstatus;

        tvsubject = view.findViewById(R.id.tvsubject);
        tvduration = view.findViewById(R.id.tvduration);
        tvdate = view.findViewById(R.id.tvdate);
        tvnote = view.findViewById(R.id.tvnote);
        btnstatus = view.findViewById(R.id.btnstatus);
        img_proof_reading = view.findViewById(R.id.img_proof_reding);
        LinearLayout proof_reading = view.findViewById(R.id.llproof_reading);
        LinearLayout delete = view.findViewById(R.id.lldelete);
        history_delete_button = view.findViewById(R.id.history_delete_button);

        tvsubject.setText(subject);
        tvduration.setText(duration);
        tvdate.setText(from_date+" - "+to_date);
        tvnote.setText(note);
        if(status.equals("ACCEPTED")){
            btnstatus.setBackgroundColor(Color.parseColor("#25CC84"));
            btnstatus.setText(status);
            delete.setVisibility(View.GONE);
        }else if(status.equals("PENDING")){
            btnstatus.setText(status);
            btnstatus.setBackgroundColor(Color.parseColor("#750000"));
            delete.setVisibility(View.VISIBLE);
        }

        if(image.equals("null")||image.equals("")||image.equals(null)){
            proof_reading.setVisibility(View.GONE);
        }else {
            Glide.with(getContext()).load(image).placeholder(R.drawable.diamondshape).into(img_proof_reading);
        }

      history_delete_button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              delete_data(current_date_time);
          }
      });

    }

    private void delete_data(String current_date_time) {
        Log.d("method is called", "subjects: ");
        // progressDiag.show(getContext(), "loading", "please wait", false);
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Wait while loading...");
            progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progressDialog.show();


        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    progressDialog.dismiss();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try {
                    jsonresponse = new JSONObject(response);
                    Integer result_code = jsonresponse.getInt("result_code");
                    if(result_code==1) {
                        Toast.makeText(getContext(),"Record deleted successfully",Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putInt("number",1);
                        report_leave_main_page senditem = new report_leave_main_page();
                        senditem.setArguments(bundle);
                        //error found here
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, senditem).commit();

                        //Bundle bundle = new Bundle();
                       // bundle.putInt("number",1);
                      //  report_leave_main_page senditem = new report_leave_main_page();
                      //  senditem.setArguments(bundle);
                        //error found here
                      //  getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,senditem).commit();

                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

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
                params.put("student_roll_no", PreferenceUtils.getRollNumber(getContext()));
                params.put("current_date_time", current_date_time);
                //params.put("subject_code", PreferenceUtils.getsubjectcode(getContext()));
                return params;

            }


        };
        queue.add(stringRequest);

    }


}