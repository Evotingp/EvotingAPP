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

    EditText edd_Name,edd_Email,edd_Phone,edd_Pass,edd_PassCnf;
    Button btn_submit;

    Webservice_Volley volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);

        edd_Name=(EditText)findViewById(R.id.edd_Name);
        edd_Email=(EditText)findViewById(R.id.edd_Email);
        edd_Phone=(EditText)findViewById(R.id.edd_Phone);
        edd_Pass=(EditText)findViewById(R.id.edd_Pass);
        edd_PassCnf=(EditText)findViewById(R.id.edd_PassCnf);
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


                if(!Commonfunction.checkPassword(edd_Pass.getText().toString())) {
                    edd_Pass.setError("PLEASE ENTER PASSWORD IN RIGHT FORMAT");
                    return;
                }

                if(!edd_Pass.getText().toString().equals(edd_PassCnf.getText().toString()))
                {
                    edd_PassCnf.setError("passwords Are not Same ");
                }

                String url = Constants.Webserive_Url + "candidate_signup";
                HashMap<String,String> params = new HashMap<>();
                params.put("Cname",edd_Name.getText().toString());
                params.put("Cemail",edd_Email.getText().toString());
                params.put("Cph",edd_Phone.getText().toString());
                params.put("Caddress","");
                params.put("Ccity","");
                params.put("Cstate","");
                params.put("Cpassword",edd_Pass.getText().toString());
                params.put("Cdob","");
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
