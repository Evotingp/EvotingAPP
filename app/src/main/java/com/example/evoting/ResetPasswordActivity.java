package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.evoting.utils.Commonfunction;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText edd_pass, edd_passC;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edd_pass = (EditText) findViewById(R.id.edd_pass);
        edd_passC = (EditText) findViewById(R.id.edd_passC);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Commonfunction.checkPassword(edd_pass.getText().toString())) {
                    edd_pass.setError("PLEASE ENTER 10 DIGIT MOBILE NO");
                    return;
                }
                if (!Commonfunction.checkPassword(edd_passC.getText().toString())) {
                    edd_passC.setError("PLEASE ENTER 10 DIGIT MOBILE NO");
                    return;
                }
                if (!edd_pass.getText().toString().equals(edd_passC.getText().toString())) {
                    edd_pass.setError("PLEASE CHECK THE PASSWORD");
                    edd_passC.setError("PASSWORD DO NOT MATCH");
                    return;
                }
            }
        });
    }
}





