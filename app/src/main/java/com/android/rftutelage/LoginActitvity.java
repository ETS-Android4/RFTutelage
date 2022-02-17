package com.android.rftutelage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import com.android.rftutelage.utils.PreferenceUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActitvity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    TextView loginemail;
    EditText loginrollnumber;
    ConstraintLayout constraintLayout;
    CircularProgressButton cirLoginButton;
    int counter = 2;
    private static final String apiurl = "http://192.168.43.84/parentportal/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_login_actitvity);
        loginemail = findViewById(R.id.Loginemail);
        constraintLayout = findViewById(R.id.LoginConstraintLayout);
        loginrollnumber = findViewById(R.id.Loginrollnumber);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        GoogleSignInButton signInButton = findViewById(R.id.sign_in_button);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    mGoogleSignInClient.revokeAccess().addOnCompleteListener(LoginActitvity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            signIn();
                        }
                    });
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    public void onclickbuttonMethod(View v){
        String email = loginemail.getText().toString();
        String rollno = loginrollnumber.getText().toString();
        if(email.equals(null)||rollno.equals(null)||email.equals("")||rollno.equals("")){
            Snackbar.make(constraintLayout,"Fill the fields",Snackbar.LENGTH_LONG).show();
        }
        else{
            registration(email,rollno);
        }

    }

    private void registration(String semail, String srollno) {
        cirLoginButton.startAnimation();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cirLoginButton.revertAnimation();

                //detaillist.clear();
                Log.d("onresponse", "onresponse is called: ");
                JSONObject jsonresponse;
                try{
                    jsonresponse = new JSONObject(response);
                    Integer result_code = jsonresponse.getInt("result_code");
                    if(result_code==0){

                        Snackbar.make(constraintLayout,"No users found",Snackbar.LENGTH_LONG).show();
                        counter--;
                        if (counter == 0) {
                            Snackbar.make(constraintLayout,"Failed Login Attempts",Snackbar.LENGTH_LONG).show();
                            new CountDownTimer(30000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    cirLoginButton.setEnabled(false);
                                    cirLoginButton.setText("Retry after 30 seconds: " + String.valueOf(millisUntilFinished / 1000));

                                }

                                @Override
                                public void onFinish() {
                                    cirLoginButton.setText("LOGIN");
                                    cirLoginButton.setEnabled(true);
                                    counter = 2;
                                }
                            }.start();
                        }

                    }else if (result_code==1){
                        JSONArray jsonArray = jsonresponse.getJSONArray("products");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                           PreferenceUtils.savecurrentSemester(object.getString("current_semester"),LoginActitvity.this);
                           PreferenceUtils.savesubjectcode(object.getString("subject_code"),LoginActitvity.this);

                        }

                        PreferenceUtils.saveEmail(loginemail.getText().toString(),LoginActitvity.this);
                        PreferenceUtils.saveRollNumber(loginrollnumber.getText().toString(),LoginActitvity.this);
                        loginemail.setText("");
                        loginrollnumber.setText("");
                        Snackbar.make(constraintLayout,"Logged in Successfully",Snackbar.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActitvity.this,MainActivity.class));
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
                params.put("student_email",semail);
                params.put("roll_no",srollno);
                return params;

            }

        };
        queue.add(stringRequest);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(LoginActitvity.this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                loginemail.setText(personEmail);
            }
            // Signed in successfully, show authenticated UI.
            //startActivity(new Intent(LoginActitvity.this,MainActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Api exception", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if((account!=null&&PreferenceUtils.getEmail(LoginActitvity.this)!=""&&PreferenceUtils.getRollNumber(LoginActitvity.this)!="")){
         startActivity(new Intent(LoginActitvity.this,MainActivity.class));
        }

        super.onStart();

    }

    @Override
    public void onBackPressed() {


    }


    public void register(View view){
        startActivity(new Intent(LoginActitvity.this,Registration.class));
    }



}