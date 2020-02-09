package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evoting.utils.Commonfunction;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class CandidateActivity extends AppCompatActivity implements DataInterface {

    EditText edd_Name,edd_DOB,edd_Email,edd_Phone,edd_Add,edd_City,edd_State,edd_Pass;
    Button btn_submit;

    Webservice_Volley volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);

        edd_Name=(EditText)findViewById(R.id.edd_Name);
        edd_DOB=(EditText)findViewById(R.id.edd_DOB);
        edd_Email=(EditText)findViewById(R.id.edd_Email);
        edd_Phone=(EditText)findViewById(R.id.edd_Phone);
        edd_Add=(EditText)findViewById(R.id.edd_Add);
        edd_City=(EditText)findViewById(R.id.edd_City);
        edd_State=(EditText)findViewById(R.id.edd_State);
        edd_Pass=(EditText)findViewById(R.id.edd_Pass);
     //   edd_Status=(EditText)findViewById(R.id.edd_Status);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        volley = new Webservice_Volley(this,this);

        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!Commonfunction.checkString(edd_Name.getText().toString()))
                {
                    edd_Name.setError("PLEASE ENTER YOUR NAME");
                    return;
                }
                if(!Commonfunction.checkString(edd_DOB.getText().toString()))
                {
                    edd_DOB.setError("PLEASE ENTER CORRECT DATE");
                    return;
                }
                if (!Commonfunction.checkEmail(edd_Email.getText().toString()))
                {
                    edd_Email.setError("PLEASE ENTER 10 DIGIT MOBILE NO");
                    return;
                }
                if (!Commonfunction.checkMobileNo(edd_Phone.getText().toString()))
                {
                    edd_Phone.setError("PLEASE ENTER 10 DIGIT MOBILE NO");
                    return;
                }
                if(!Commonfunction.checkString(edd_Add.getText().toString()))
                {
                    edd_Add.setError("PLEASE ENTER YOUR NAME");
                    return;
                }
                if(!Commonfunction.checkString(edd_City.getText().toString()))
                {
                    edd_City.setError("PLEASE ENTER YOUR NAME");
                    return;
                }
                if(!Commonfunction.checkString(edd_State.getText().toString()))
                {
                    edd_State.setError("PLEASE ENTER YOUR NAME");
                    return;
                }
                if(!Commonfunction.checkPassword(edd_Pass.getText().toString())) {
                    edd_Pass.setError("PLEASE ENTER PASSWORD IN RIGHT FORMAT");
                    return;
                }
              /*  if(!Commonfunction.checkString(edd_Status.getText().toString()))
                {
                    edd_Status.setError("PLEASE ENTER YOUR NAME");
                    return;
                }*/

                String url = Constants.Webserive_Url + "candidate_signup";
                HashMap<String,String> params = new HashMap<>();
                params.put("Cname",edd_Name.getText().toString());
                params.put("Cemail",edd_Email.getText().toString());
                params.put("Cph",edd_Phone.getText().toString());
                params.put("Caddress",edd_Add.getText().toString());
                params.put("Ccity",edd_City.getText().toString());
                params.put("Cstate",edd_State.getText().toString());
                params.put("Cpassword",edd_Pass.getText().toString());
                params.put("Cdob",edd_DOB.getText().toString());
                params.put("Cstatus","");
                params.put("Cphoto","");

                volley.CallVolley(url,params,"candidate_signup");

            }
        });
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        Toast.makeText(this, jsonObject.toString(), Toast.LENGTH_SHORT).show();

    }
}
