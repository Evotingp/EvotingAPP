package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evoting.utils.Commonfunction;

public class OTPActivity extends AppCompatActivity {

    EditText edd_otp1,edd_otp2,edd_otp3,edd_otp4;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        edd_otp1=(EditText)findViewById(R.id.edd_otp1);
        edd_otp2=(EditText)findViewById(R.id.edd_otp2);
        edd_otp3=(EditText)findViewById(R.id.edd_otp3);
        edd_otp4=(EditText)findViewById(R.id.edd_otp4);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String code = edd_otp1.getText().toString()+edd_otp2.getText().toString()+edd_otp3.getText().toString()+edd_otp4.getText().toString();
                if(!Commonfunction.checkVerification(code))
                {
                    Toast.makeText(OTPActivity.this, "PLEASE REVERIFY THE OTP CODE", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}

