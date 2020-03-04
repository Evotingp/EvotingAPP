package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.evoting.utils.Commonfunction;

public class CandidateForgotPasswordActivity extends AppCompatActivity {
    EditText edd_email;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_forgot_password);

        edd_email = (EditText) findViewById(R.id.edd_email);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Commonfunction.checkEmail(edd_email.getText().toString())) {
                    edd_email.setError("PLEASE ENTER 10 DIGIT MOBILE NO");
                    return;
                }

            }
        });

    }
}
