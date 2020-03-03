package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evoting.utils.AllSharedPrefernces;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class CandidateProfileEdit extends AppCompatActivity implements DataInterface {
    EditText edd_Name,edd_Email,edd_Phone,edd_Add,edd_City,edd_State,edd_Status;
    TextView txt_SaveUserC;

    Webservice_Volley volley;

    AllSharedPrefernces allSharedPrefernces = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile_edit);

        edd_Name=(EditText)findViewById(R.id.edd_Name);
        edd_Email=(EditText)findViewById(R.id.edd_Email);
        edd_Phone=(EditText)findViewById(R.id.edd_Phone);
        edd_Add=(EditText)findViewById(R.id.edd_Add);
        edd_City=(EditText)findViewById(R.id.edd_City);
        edd_State=(EditText)findViewById(R.id.edd_State);
        edd_Status=(EditText)findViewById(R.id.edd_Status);

        txt_SaveUserC=(TextView) findViewById(R.id.txt_SaveUserC);

        allSharedPrefernces = new AllSharedPrefernces(this);

        volley = new Webservice_Volley(this,this);

        String data = getIntent().getStringExtra("data");

        try {

            if (!TextUtils.isEmpty(data)) {

                JSONObject Data = new JSONObject(data);

                edd_Name.setText(Data.getString("Cname"));
                edd_Email.setText(Data.getString("Cemail"));
                edd_Phone.setText(Data.getString("Cph"));
                edd_Add.setText(Data.getString("Caddress"));
                edd_City.setText(Data.getString("Ccity"));
                edd_State.setText(Data.getString("Cstate"));
                edd_Status.setText(Data.getString("Cstatus"));

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        Toast.makeText(this, jsonObject.toString(), Toast.LENGTH_SHORT).show();

    }


    public void ClickOnCandidateSave(View view) {
        String url = Constants.Webserive_Url + "update_candidate_profile";
        HashMap<String,String> params = new HashMap<>();
        params.put("Cname",edd_Name.getText().toString());
        params.put("Cemail",edd_Email.getText().toString());
        params.put("Cph",edd_Phone.getText().toString());
        params.put("Caddress",edd_Add.getText().toString());
        params.put("Ccity",edd_City.getText().toString());
        params.put("Cstate",edd_State.getText().toString());
        params.put("Cstatus",edd_Status.getText().toString());

        params.put("Cid",allSharedPrefernces.getCustomerNo());

        volley.CallVolley(url,params,"update_candidate_profile");
    }
}
