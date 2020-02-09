package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evoting.utils.Commonfunction;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class VoterLoginActivity extends AppCompatActivity implements DataInterface {

    EditText edd_Mobile,edd_Password;
    TextView txt_NewUserV,txt_forgotV;
    Button btn_login;

    Webservice_Volley volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_login);

        edd_Password=(EditText)findViewById(R.id.edd_Password);
        edd_Mobile=(EditText)findViewById(R.id.edd_Mobile);
        txt_NewUserV=(TextView)findViewById(R.id.txt_NewUserV);
        txt_forgotV=(TextView)findViewById(R.id.txt_forgotV);
        btn_login=(Button)findViewById(R.id.btn_login);

        volley = new Webservice_Volley(this,this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Commonfunction.checkMobileNo(edd_Mobile.getText().toString()))
                {
                    edd_Mobile.setError("PLEASE ENTER 10 DIGIT MOBILE NO");
                    return;
                }
                if(!Commonfunction.checkPassword(edd_Password.getText().toString())) {
                    edd_Password.setError("PLEASE ENTER PASSWORD IN RIGHT FORMAT");
                    return;
                }

                String url = Constants.Webserive_Url + "voter_login";
                HashMap<String,String> params = new HashMap<>();
                params.put("Vph",edd_Mobile.getText().toString());
                params.put("Vpassword",edd_Password.getText().toString());
                volley.CallVolley(url,params,"voter_login");

            }
        });

        txt_NewUserV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(VoterLoginActivity.this,VoterActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {

        Toast.makeText(this, jsonObject.toString(), Toast.LENGTH_SHORT).show();

    }
}
