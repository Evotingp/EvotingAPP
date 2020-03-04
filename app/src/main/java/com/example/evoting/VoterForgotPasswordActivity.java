package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.evoting.utils.Commonfunction;

public class VoterForgotPasswordActivity extends AppCompatActivity {

    EditText edd_phone;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_forgot_password);

        edd_phone = (EditText) findViewById(R.id.edd_phone);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Commonfunction.checkMobileNo(edd_phone.getText().toString())) {
                    edd_phone.setError("PLEASE ENTER 10 DIGIT MOBILE NO");
                    return;
                }
            }
        });
    }
}
