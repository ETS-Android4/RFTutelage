package com.android.rftutelage.Reportleave.Report_Leave;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rftutelage.R;
import com.android.rftutelage.Results.results_data;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class Fragment_report_leave extends Fragment {

    TextView frv_student_roll_no,report_leave_date;
    EditText report_leave_subject,frv_duration,frv_note;
    TextView frv_from_date,frv_to_date;
    CircularImageView userimage;
    Button submitbutton;
    ImageButton camera;
    Bitmap bitmap;
    String encodedimage="";
    String apiurl = "http://192.168.43.84/parentportal/report_leave.php";
    private int mYear, mMonth, mDay;

    public Fragment_report_leave() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_leave, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frv_student_roll_no = view.findViewById(R.id.frv_student_roll_no);
        report_leave_date = view.findViewById(R.id.report_leave_date);
        report_leave_subject = view.findViewById(R.id.report_leave_subject);
        frv_duration = view.findViewById(R.id.frv_duration);
        frv_from_date = view.findViewById(R.id.frv_from_date);
        frv_to_date = view.findViewById(R.id.frv_to_date);
        frv_note = view.findViewById(R.id.frv_note);
        userimage = view.findViewById(R.id.userimage);
        camera = view.findViewById(R.id.cameras);
        submitbutton = view.findViewById(R.id.submitbutton);
        report_leave_subject.requestFocus();

        frv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                frv_from_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        frv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        frv_to_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Integer month = calendar.get(Calendar.MONTH)+1;
        String date = calendar.get(Calendar.DAY_OF_MONTH)+"-"+month+"-"+calendar.get(Calendar.YEAR);
        report_leave_date.setText(date);
        frv_student_roll_no.setText(PreferenceUtils.getRollNumber(getContext()));

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaydialog();
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String roll_no = frv_student_roll_no.getText().toString();
                final String date = report_leave_date.getText().toString();
                final String subject = report_leave_subject.getText().toString();
                final String duration = frv_duration.getText().toString();
                final String from = frv_from_date.getText().toString();
                final String to = frv_to_date.getText().toString();
                final String note = frv_note.getText().toString();

                if(roll_no.equals("")||date.equals("")||subject.equals("")||duration.equals("")||from.equals("")||to.equals("")||note.equals("")||roll_no.equals(null)||date.equals(null)||subject.equals(null)||duration.equals(null)||from.equals(null)||to.equals(null)||note.equals(null)){
                    submitbutton.setEnabled(false);
                }else{
                    submitbutton.setEnabled(true);
                    uploadtoserver();
                }

            }
        });
    }

    private void uploadtoserver() {
        final String roll_no = frv_student_roll_no.getText().toString();
        final String date = report_leave_date.getText().toString();
        final String subject = report_leave_subject.getText().toString();
        final String duration = frv_duration.getText().toString();
        final String from = frv_from_date.getText().toString();
        final String to = frv_to_date.getText().toString();
        final String note = frv_note.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonresponse;
                try {
                    jsonresponse = new JSONObject(response);
                    Integer success_code = jsonresponse.getInt("success");
                    if(success_code==0){
                        Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                    }else if(success_code==1){
                        Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_LONG).show();
                    }else{

                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                Integer month = calendar.get(Calendar.MONTH)+1;
                String date = calendar.get(Calendar.YEAR)+"-"+month +"-"+ calendar.get(Calendar.DAY_OF_MONTH);
                report_leave_date.setText(date);
                frv_student_roll_no.setText(PreferenceUtils.getRollNumber(getContext()));
                report_leave_subject.setText("");
                report_leave_subject.requestFocus();
                frv_duration.setText("");
                frv_from_date.setText("");
                frv_to_date.setText("");
                frv_note.setText("");
                userimage.setImageResource(0);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();

                map.put("roll_no",roll_no);
                map.put("date",date);
                map.put("subject",subject);
                map.put("duration",duration);
                map.put("from",from);
                map.put("to",to);
                map.put("note",note);
                map.put("image",encodedimage);
                map.put("subject_code",PreferenceUtils.getsubjectcode(getContext()));

                return map;

            }

        };
        queue.add(request);
    }


    private void displaydialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose one among this");
        //add a list
        String[] items = {"camera","gallery","cancel"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        opencamera();
                        break;
                    case 1:
                        Gallery();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        //Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void Gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"select file"),222);
    }

    private void opencamera(){
        Dexter.withContext(getContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,111);
            }
            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            }
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 111 && resultCode == RESULT_OK){
            bitmap = (Bitmap)data.getExtras().get("data");
            userimage.setImageBitmap(bitmap);
            encodedbitmap(bitmap);
        }

        if(requestCode == 222 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                userimage.setImageBitmap(bitmap);
                encodedbitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void encodedbitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] byteofimages = byteArrayOutputStream.toByteArray();
        encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
        Log.d("encoded_image", "encodedbitmap: "+encodedimage);
    }
}
