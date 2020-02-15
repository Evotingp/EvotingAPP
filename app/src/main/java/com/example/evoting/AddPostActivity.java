package com.example.evoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evoting.utils.Commonfunction;
import com.example.evoting.utils.Constants;
import com.example.evoting.utils.DataInterface;
import com.example.evoting.utils.Webservice_Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class AddPostActivity extends AppCompatActivity implements DataInterface {

    TextView edd_Title, edd_Description;
    ImageView imageView;
    Button btn_add;

    Webservice_Volley volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        edd_Title=(TextView)findViewById(R.id.edd_Title);
        edd_Description=(TextView)findViewById(R.id.edd_Description);
        imageView=(ImageView) findViewById(R.id.imageView);
        btn_add =(Button) findViewById(R.id.btn_add);

        volley = new Webservice_Volley(this,this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Commonfunction.checkString(edd_Title.getText().toString())) {
                    edd_Title.setError("PLEASE ENTER THE TITLE");
                    return;
                }

                if (!Commonfunction.checkString(edd_Description.getText().toString())) {
                    edd_Description.setError("PLEASE ENTER THE DESCRIPTION");
                    return;
                }


                String url = Constants.Webserive_Url + "insert_feedpost";
                HashMap<String, String> params = new HashMap<>();

                params.put("Title", edd_Title.getText().toString());
                params.put("Description", edd_Description.getText().toString());
                params.put("Image", "");

                volley.CallVolley(url, params, "insert_feedpost");
            }
        });
    }

    @Override
    public void getData(JSONObject jsonObject, String tag) {
        Toast.makeText(this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
    }
}
