package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class VoterProfileEditActivity extends AppCompatActivity implements DataInterface {
    EditText edd_Name,edd_Email,edd_Phone,edd_Add,edd_City,edd_State;
    TextView txt_SaveUserV;

    Webservice_Volley volley;

    String Vid = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_profile_edit);

        edd_Name=(EditText)findViewById(R.id.edd_Name);
        edd_Email=(EditText)findViewById(R.id.edd_Email);
        edd_Phone=(EditText)findViewById(R.id.edd_Phone);
        edd_Add=(EditText)findViewById(R.id.edd_Add);
        edd_City=(EditText)findViewById(R.id.edd_City);
        edd_State=(EditText)findViewById(R.id.edd_State);

        txt_SaveUserV=(TextView) findViewById(R.id.txt_SaveUserV);

        volley = new Webservice_Volley(this,this);

        String data = getIntent().getStringExtra("data");

        try {

            if (!TextUtils.isEmpty(data)) {

                JSONObject Data = new JSONObject(data);

                edd_Name.setText(Data.getString("Vname"));
                edd_Email.setText(Data.getString("Vemail"));
                edd_Phone.setText(Data.getString("Vph"));
                edd_Add.setText(Data.getString("Vaddress"));
                edd_City.setText(Data.getString("Vcity"));
                edd_State.setText(Data.getString("Vstate"));

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

    public void ClickOnVoterSave(View view) {
        String url = Constants.Webserive_Url + "update_voter_profile";
        HashMap<String,String> params = new HashMap<>();
        params.put("Vname",edd_Name.getText().toString());
        params.put("Vemail",edd_Email.getText().toString());
        params.put("Vph",edd_Phone.getText().toString());
        params.put("Vaddress",edd_Add.getText().toString());
        params.put("Vcity",edd_City.getText().toString());
        params.put("Vstate",edd_State.getText().toString());
        params.put("Vid",Vid);

        volley.CallVolley(url,params,"update_voter_profile");
    }
}
