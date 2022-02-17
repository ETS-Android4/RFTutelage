package com.android.rftutelage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rftutelage.utils.PreferenceUtils;

import java.util.function.Predicate;

public class sharedpreferencessaveandretrievedata extends AppCompatActivity {
    EditText email,rollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpreferencessaveandretrievedata);

        email = findViewById(R.id.sfemail);
        rollno = findViewById(R.id.sfrollno);


    }

    public void save(View view){
        PreferenceUtils.saveEmail(email.getText().toString(),sharedpreferencessaveandretrievedata.this);
    }

    public void retrieve(View view){
        Toast.makeText(this,PreferenceUtils.getfaculty1_code(this)+PreferenceUtils.getfaculty2_code(this),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,PreferenceUtils.getfaculty3_code(this)+PreferenceUtils.getselectedsemester(this),Toast.LENGTH_SHORT).show();

    }
}