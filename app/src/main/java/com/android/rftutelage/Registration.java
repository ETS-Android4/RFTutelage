package com.android.rftutelage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class Registration extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton genderradioButton;
    EditText registerusername,registerrollnumber,registerdateofbirth,registersubjectcode;
    ConstraintLayout constraintLayout;
    CircularProgressButton cirRegisterButton;
    private static final String apiurl = "http://192.168.43.84/parentportal/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        registerusername = findViewById(R.id.RegisterUsername);
        registerrollnumber = findViewById(R.id.Registerrollnumber);
        registerdateofbirth = findViewById(R.id.Registerdateofbirth);
        registersubjectcode = findViewById(R.id.Registersubjectcode);
        constraintLayout = findViewById(R.id.Rconstraintlayout);
        cirRegisterButton = findViewById(R.id.cirRegisterButton);
    }

    public void onclickbuttonMethod(View v){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String name = registerusername.getText().toString();
        String rollno = registerrollnumber.getText().toString();
        String dateofbirth = registerdateofbirth.getText().toString();
        String subjectcode = registersubjectcode.getText().toString();
        genderradioButton = (RadioButton) findViewById(selectedId);
        String gender;
        if(selectedId==-1||name.equals(null)||rollno.equals(null)||dateofbirth.equals(null)||subjectcode.equals(null)||name.equals("")||rollno.equals("")||dateofbirth.equals("")||subjectcode.equals("")){
            Snackbar.make(constraintLayout,"Fill the fields",Snackbar.LENGTH_LONG).show();
        }
        else{
             gender = genderradioButton.getText().toString();
            registration(name,rollno,dateofbirth,subjectcode,gender);
        }
    }

    private void registration(String sname, String srollno, String sdateofbirth, String ssubjectcode, String sgender) {
        cirRegisterButton.startAnimation();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cirRegisterButton.revertAnimation();
                //detaillist.clear();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);
                    Integer result_code = jsonresponse.getInt("result_code");
                    if(result_code==0){

                        Snackbar.make(constraintLayout,"You have already registered",Snackbar.LENGTH_LONG).show();

                    }else if (result_code==1){
                        Snackbar.make(constraintLayout,"None of the student possess above credentials",Snackbar.LENGTH_LONG).show();
                    }
                    else if(result_code==2){
                        Snackbar.make(constraintLayout,"Credentials matched, Registered successfully",Snackbar.LENGTH_LONG).show();
                        startActivity(new Intent(Registration.this,LoginActitvity.class));
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
                Map<String,String> params = new HashMap<String, String>();
                params.put("student_name",sname);
                params.put("date_of_birth",sdateofbirth);
                params.put("subject",ssubjectcode);
                params.put("roll_no",srollno);
                params.put("gender",sgender);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void login(View view){
        startActivity(new Intent(Registration.this,LoginActitvity.class));
    }
}